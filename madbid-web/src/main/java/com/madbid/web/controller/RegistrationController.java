package com.madbid.web.controller;

import com.ecwid.mailchimp.MailChimpException;
import com.madbid.core.dto.RegistrationData;
import com.madbid.core.exception.DuplicateEmailException;
import com.madbid.core.exception.DuplicateUsernameException;
import com.madbid.core.model.*;
import com.madbid.core.security.util.SecurityUtil;
import com.madbid.core.service.MadbidNotificationService;
import com.madbid.core.service.MessageService;
import com.madbid.core.service.UserMessageService;
import com.madbid.core.service.UserService;
import com.madbid.mailchimp.MailChimpClient;
import com.madbid.web.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;
import java.util.Map;


/**
 * Created by nikolov.
 */
@Controller
@SessionAttributes("user")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";
    protected static final String ERROR_CODE_USERNAME_EXIST = "exist.user.username";
    protected static final String MODEL_NAME_REGISTRATION_DTO = "user";
    protected static final String VIEW_NAME_REGISTRATION_PAGE = "user/registrationForm";

    @Inject
    private UserService userService;

    @Inject
    private MadbidNotificationService madbidNotificationService;

    @Inject
    private HttpServletRequest request;

    @Inject
    private MailChimpClient mailChimpClient;

    @Inject
    private MessageService messageService;

    @Inject
    private UserMessageService userMessageService;

    /**
     * Renders the registration page.
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        LOGGER.debug("Rendering registration page.");

        ProviderSignInUtils signInUtils = new ProviderSignInUtils();
        Connection<?> connection = signInUtils.getConnectionFromSession(request);

        RegistrationData registration = createRegistrationDTO(connection);
        LOGGER.debug("Rendering registration form with information: {}", registration);

        model.addAttribute(MODEL_NAME_REGISTRATION_DTO, registration);

        return VIEW_NAME_REGISTRATION_PAGE;
    }

    /**
     * Creates the form object used in the registration form.
     *
     * @param connection
     * @return If a user is signing in by using a social provider, this method returns a form
     * object populated by the values given by the provider. Otherwise this method returns
     * an empty form object (normal form registration).
     */
    private RegistrationData createRegistrationDTO(Connection<?> connection) {
        RegistrationData dto = new RegistrationData();

        if (connection != null) {
            UserProfile socialMediaProfile = connection.fetchUserProfile();
            dto.setEmail(socialMediaProfile.getEmail());
            dto.setFirstName(socialMediaProfile.getFirstName());
            dto.setLastName(socialMediaProfile.getLastName());
            dto.setUsername(socialMediaProfile.getUsername());
            dto.setRole(Role.ROLE_USER);

            ConnectionKey providerKey = connection.getKey();
            dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
        }

        return dto;
    }

    /**
     * Processes the form submissions of the registration form.
     */
    @Transactional
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationData userAccountData,
                                      BindingResult result,
                                      WebRequest request) throws DuplicateEmailException {
        LOGGER.debug("Registering user account with information: {}", userAccountData);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors found. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("No validation errors found. Continuing registration process.");

        User registered = createUserAccount(userAccountData, result);

        //If email address was already found from the database, render the form view.
        if (registered == null) {
            LOGGER.debug("An email address was found from the database. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("Registered user account with information: {}", registered);

        madbidNotificationService.createUserRegistrationConfirmationEmail(registered, createConfirmationLink(registered));

        //Logs the user in.
//        SecurityUtil.logInUser(registered);
        LOGGER.debug("User {} has been signed in");
        //If the user is signing in by using a social provider, this method call stores
        //the connection to the UserConnection table. Otherwise, this method does not
        //do anything.
        ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils();
        providerSignInUtils.doPostSignUp(registered.getEmail(), request);

        return "redirect:/";
    }

    private String createConfirmationLink(User user) {
        String host = RequestUtils.getHeadersInfo(request).get("origin");
        String urlEncoded = null;
        try {
            urlEncoded = URLEncoder.encode(user.generateUID(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String controllerUrl = String.format("/madbid-web/user/register-confirmation/%s?uid=%s", user.getId(), urlEncoded);

        return host + controllerUrl;
    }

    @RequestMapping(value = "/user/register-confirmation/{id}", method = RequestMethod.GET)
    public String registerUserConfirmation(@PathVariable Long id, @RequestParam(value = "uid", required = true) String uid) throws DuplicateEmailException {
        User user = userService.findOne(id);
        Map<String, String> headersInfo = RequestUtils.getHeadersInfo(request);
        if (user == null) {
            //STODO how to detect who is it
            LOGGER.debug("registerUserConfirmation - Someone tries to hack us.");
            return "redirect:/";
        } else if (!user.generateUID().equals(uid)) {
            //STODO how to detect who is it
            LOGGER.debug("registerUserConfirmation - Someone tries to hack us.");
            return "redirect:/";
        }

        user.setActive(true);
        userService.save(user);
        createWelcomeMessage(user);
        SecurityUtil.logInUser(user);

        try {
            LOGGER.debug("Subscribing person to mailchimp list");
            mailChimpClient.subscribePerson(user.getEmail(), user.getFirstName(), user.getLastName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MailChimpException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    private void createWelcomeMessage(User user) {
        List<User> administrators = userService.findByRole(Role.ROLE_ADMIN);
        //STODO i18n
        Message message = new Message();
        message.setSubject("\u0414\u043e\u0431\u0440\u0435 \u0434\u043e\u0448\u043b\u0438 \u0432 \u0416\u044a\u043b\u0442\u0438\u0446\u0438");
        message.setText("\u0417\u0434\u0440\u0430\u0432\u0435\u0439\u0442\u0435 " + user.getFirstName() + ", <br/> \u0414\u043e\u0431\u0440\u0435 \u0434\u043e\u0448\u043b\u0438 \u0432 \u0416\u044a\u043b\u0442\u0438\u0446\u0438....<br/>\u0410\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440");
        message.setSender(administrators.get(0));
        message = messageService.save(message);

        UserMessage userMessage = new UserMessage();
        userMessage.setRead(false);
        userMessage.setMessage(message);
        userMessage.setUser(user);
        userMessageService.save(userMessage);
    }

    /**
     * Creates a new user account by calling the service method. If the email address is found
     * from the database, this method adds a field error to the email field of the form object.
     */
    private User createUserAccount(RegistrationData userAccountData, BindingResult result) {
        LOGGER.debug("Creating user account with information: {}", userAccountData);
        User registered = null;

        try {
            registered = userService.registerNewUserAccount(userAccountData);
        } catch (DuplicateEmailException ex) {
            LOGGER.debug("An email address: {} exists.", userAccountData.getEmail());
            addFieldError(
                    MODEL_NAME_REGISTRATION_DTO,
                    RegistrationData.FIELD_NAME_EMAIL,
                    userAccountData.getEmail(),
                    ERROR_CODE_EMAIL_EXIST,
                    result);
        } catch (DuplicateUsernameException e) {
            LOGGER.debug("An username: {} exists.", userAccountData.getUsername());
            addFieldError(
                    MODEL_NAME_REGISTRATION_DTO,
                    RegistrationData.FIELD_NAME_USERNAME,
                    userAccountData.getUsername(),
                    ERROR_CODE_USERNAME_EXIST,
                    result);
        } catch (MailChimpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return registered;
    }

    private void addFieldError(String objectName, String fieldName, String fieldValue, String errorCode, BindingResult result) {
        LOGGER.debug(String.format("Adding field error object's: %s field: %s with error code: %s", objectName, fieldName, errorCode));
        FieldError error = new FieldError(
                objectName,
                fieldName,
                fieldValue,
                false,
                new String[]{errorCode},
                new Object[]{},
                errorCode
        );

        result.addError(error);
        LOGGER.debug("Added field error: {} to binding result: {}", error, result);
    }
}
