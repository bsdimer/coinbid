package com.madbid.web.controller;

import com.ecwid.mailchimp.MailChimpException;
import com.madbid.core.model.User;
import com.madbid.core.service.UserService;
import com.madbid.core.service.proxy.ResponseDTO;
import com.madbid.mailchimp.MailChimpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by nikolov.
 */
@Controller
@RequestMapping(value = "/notification")
public class NotificationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    @Inject
    private UserService userService;

    @Inject
    private MailChimpClient mailChimpClient;

    @Transactional
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public @ResponseBody
    ResponseDTO redirectRequestToRegistrationPage(HttpServletResponse response, WebRequest request, Principal principal) throws IOException, MailChimpException {
        try {
            if (principal.getName().indexOf("guest") == 0) {
                throw new SecurityException("request with invalid session");
            } else {
                String email = request.getParameter("email");
                User user = userService.findByEmail(principal.getName());
                mailChimpClient.subscribePerson(email, user.getFirstName(), user.getLastName());
            }
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setSubscribe(true);
            return responseDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
