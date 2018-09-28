package com.madbid.core.model;

import com.madbid.notification.model.Placeholdable;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolov.
 */
@Entity
@Table(name = "users")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends DateTimeAuditable implements Identifiable, Serializable, Placeholdable {
    private static final long serialVersionUID = -1319788358790914258L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 63, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 63, nullable = false)
    private String lastName;

    @Column(name = "username", length = 127)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "email", length = 127, nullable = false)
    private String email;

    @Column(name = "mobile_number", length = 31)
    private String mobileNumber;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "news_subscribed")
    private boolean newsSubscribed;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderColumn
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coin> coins = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bid> bids = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserMessage> messages = new ArrayList<UserMessage>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Discount> discounts = new ArrayList<Discount>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 31)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_in_provider", length = 31)
    private SocialMediaService signInProvider;

    public User() {
        // this.createdDate = new LocalDateTime();
    }

    public String generateUID() {
        String data = "s" + id + "ecu" + email + "re";
        MessageDigest messageDigest = null;
        String encryptedData = null;
        String encodedData = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(data.getBytes());

            encryptedData = new String(messageDigest.digest());
            encodedData = Base64.encodeBase64String(encryptedData.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isNewsSubscribed() {
        return newsSubscribed;
    }

    public void setNewsSubscribed(boolean newsSubscribed) {
        this.newsSubscribed = newsSubscribed;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaService signInProvider) {
        this.signInProvider = signInProvider;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return String.format("[Username: %s]", this.username);
    }

    public List<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<UserMessage> messages) {
        this.messages = messages;
    }

    @Transient
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String getValueByFieldName(String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = this.getClass().getDeclaredField(fieldName);
        return field.get(this.getClass().cast(this)).toString();
    }

//    @Override
//    public List<String> getPlaceholders() {
//        return ObjectUtils.getFieldNames(this.getClass());
//    }
}
