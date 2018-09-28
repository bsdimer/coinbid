package com.madbid.admin.controller;

import com.ecwid.mailchimp.MailChimpException;
import com.madbid.admin.utils.CoinsStatistics;
import com.madbid.admin.utils.LocaleUtils;
import com.madbid.core.dto.RegistrationData;
import com.madbid.core.exception.DuplicateEmailException;
import com.madbid.core.exception.DuplicateUsernameException;
import com.madbid.core.model.Role;
import com.madbid.core.model.User;
import com.madbid.core.repository.UserRepository;
import com.madbid.core.service.UserService;
import com.madbid.core.service.CoinService;
import com.madbid.lazyLoaders.UsersLazyLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

/**
 * Created by dimer on 8/4/14.
 */
@Component("userController")
/*@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)*/
@Scope("prototype")
@Transactional
public class UserController extends CommonTabBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 770265857139107389L;
    private static final String USER_SUCCESSFULLY_CREATED_MESSAGE_KEY = "create.user.message";
    private static final String USER_SUCCESSFULLY_DEACTIVATED_MESSAGE_KEY = "delete.user.message";

    private RegistrationData registration;
    private List<User> filteredUsers;
    private Role[] roles;

    @Inject
    private UserRepository repository;

    @Inject
    private UserService userService;

    @Inject
    private CoinService coinService;

    private LazyDataModel<User> users;

    private User selectedUser;
    private CoinsStatistics consStatistics = new CoinsStatistics();
    private BigDecimal currencyValue = BigDecimal.valueOf(0d);
    private Currency selectedCurrency = Currency.getInstance("BGN");
    private int coins2Add = 0;

    @PostConstruct
    private void init() {
        registration = new RegistrationData();
        registration.setActive(true);

        roles = Role.values();
        users = new UsersLazyLoader(userService);
    }

    public void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void create() {
        User user = null;
        try {
            if (!FacesContext.getCurrentInstance().isValidationFailed()) {
                registration.setPasswordVerification(registration.getPassword());

                userService.registerNewUserAccount(registration);
                // refreshUsers();

                addMessage(LocaleUtils.getLocaliziedMessage(USER_SUCCESSFULLY_CREATED_MESSAGE_KEY), "", FacesMessage.SEVERITY_INFO);
                registration = new RegistrationData();
                //Do not hide dialog on UI level because validation for duplication of email is in backend
                RequestContext.getCurrentInstance().execute("PF('addNewUserDialog').hide();");
            }
        } catch (DuplicateEmailException e) {
            e.printStackTrace();
            addMessage(LocaleUtils.getLocaliziedMessage(DuplicateEmailException.SUMMARY_KEY),
                    "", FacesMessage.SEVERITY_ERROR);
        } catch (DuplicateUsernameException e) {
            e.printStackTrace();
            addMessage(LocaleUtils.getLocaliziedMessage(DuplicateUsernameException.SUMMARY_KEY),
                    "", FacesMessage.SEVERITY_ERROR);
        } catch (MailChimpException e) {
            e.printStackTrace();
            addMessage(e.getMessage(),
                    "", FacesMessage.SEVERITY_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            addMessage(e.getMessage(),
                    "", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void delete(User user) {
        userService.delete(user);
        addMessage(LocaleUtils.getLocaliziedMessage(USER_SUCCESSFULLY_DEACTIVATED_MESSAGE_KEY), "", FacesMessage.SEVERITY_INFO);
    }

    public void edit(RowEditEvent event) {
        User user = (User) event.getObject();
        userService.save(user);
    }

    public RegistrationData getRegistration() {
        return registration;
    }

    public void setRegistration(RegistrationData registration) {
        this.registration = registration;
    }

    public LazyDataModel<User> getUsers() {
        return users;
    }

    public void setUsers(LazyDataModel<User> users) {
        this.users = users;
    }

    public Role[] getRoles() {
        return roles;
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        if (selectedUser != null) {
            User userWithCoinsDeep = userService.findUserWithCoinsDeep(selectedUser.getId());
            this.selectedUser = userWithCoinsDeep;
            consStatistics = new CoinsStatistics(userWithCoinsDeep);
        }
    }

    public CoinsStatistics getConsStatistics() {
        return consStatistics;
    }

    public void setConsStatistics(CoinsStatistics consStatistics) {
        this.consStatistics = consStatistics;
    }

    public int coinsAvailable(User user) {
        return userService.getCoinsAvailable(user);
    }

    public int coinsAvailable() {
        return userService.getCoinsAvailable(selectedUser);
    }

    public int coinsSpent() {
        return userService.getCoinsSpent(selectedUser);
    }

    public void addCoins() {
        User user = userService.findUserWithCoinsDeep(selectedUser.getId());
        userService.addCoins(user, currencyValue, coins2Add, selectedCurrency);
    }

    public void setCurrencyValue(BigDecimal currencyValue) {
        this.currencyValue = currencyValue;
    }

    public BigDecimal getCurrencyValue() {
        return currencyValue;
    }

    public void setSelectedCurrency(Currency selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public Currency getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setCoins2Add(int coins2Add) {
        this.coins2Add = coins2Add;
    }

    public int getCoins2Add() {
        return coins2Add;
    }
}