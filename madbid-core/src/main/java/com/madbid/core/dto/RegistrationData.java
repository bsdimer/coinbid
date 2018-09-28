package com.madbid.core.dto;

import com.madbid.core.model.Role;
import com.madbid.core.model.SocialMediaService;
import com.madbid.core.validation.PasswordsNotEmpty;
import com.madbid.core.validation.PasswordsNotEqual;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by nikolov.
 */
@PasswordsNotEmpty(
        triggerFieldName = "signInProvider",
        passwordFieldName = "password",
        passwordVerificationFieldName = "passwordVerification"
)
@PasswordsNotEqual(
        passwordFieldName = "password",
        passwordVerificationFieldName = "passwordVerification"
)
public class RegistrationData {

    public static final String FIELD_NAME_EMAIL = "email";
    public static final String FIELD_NAME_USERNAME = "username";

    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 127, message = "{field.validation.size}")
    private String username;

    @Email
    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 127, message = "{field.validation.size}")
    private String email;

    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 63, message = "{field.validation.size}")
    private String firstName;

    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 63, message = "{field.validation.size}")
    private String lastName;

    @Size(max = 255, message = "{field.validation.size}")
    private String password;

    @Size(max = 255, message = "{field.validation.size}")
    private String passwordVerification;

//    @NotEmpty(message = "{field.validation.not.empty}")
//    @Digits(integer = 31, fraction = 0, message = "{field.validation.digits}")
    @Size(max = 31, message = "{field.validation.size}")
    private String mobileNumber;

    private boolean active;

    private boolean newsSubscribed = true;

    @NotNull(message = "{field.validation.not.empty}")
    private Role role = Role.ROLE_USER;

    private SocialMediaService signInProvider;

    //After refactoring these fields below have to be grouped in one dto and RegistrationData have to have list of AddressDtos
//    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 63, message = "{field.validation.size}")
    private String country;

//    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 63, message = "{field.validation.size}")
    private String city;

    /*@Size(max = 63, message = "{field.validation.size}")
    private String quarter;*/

//    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 255, message = "{field.validation.size}")
    private String street1;

//    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 255, message = "{field.validation.size}")
    private String street2;

/*//    @NotEmpty(message = "{field.validation.not.empty}")
//    @Digits(integer = 15, fraction = 0, message = "{field.validation.digits}")
    @Size(max = 15, message = "{field.validation.size}")
    private String number;

//    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 7, message = "{field.validation.size}")
    private String entrance;

//    @NotEmpty(message = "{field.validation.not.empty}")
//    @Digits(integer = 7, fraction = 0, message = "{field.validation.digits}")
    @Size(max = 7, message = "{field.validation.size}")
    private String floor;

//    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 7, message = "{field.validation.size}")
//    @Digits(integer = 15, fraction = 0, message = "{field.validation.digits}")
    private String apartment;*/

    @Size(max = 15)
//    @Digits(integer = 15, fraction = 0, message = "{field.validation.digits}")
    private String postcode;


    public RegistrationData() {

    }

    public boolean isNormalRegistration() {
        return signInProvider == null;
    }

    public boolean isSocialSignIn() {
        return signInProvider != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.setUsername(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaService signInProvider) {
        this.signInProvider = signInProvider;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("signInProvider", signInProvider)
                .toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isNewsSubscribed() {
        return newsSubscribed;
    }

    public void setNewsSubscribed(boolean newsSubscribed) {
        this.newsSubscribed = newsSubscribed;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /*public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }*/

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }
}
