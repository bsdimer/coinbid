package com.madbid.core.model;

import com.madbid.notification.model.Placeholdable;

import javax.persistence.*;
import java.lang.reflect.Field;

/**
 * Created by nikolov.
 */
@Entity
@Table(name = "addresses")
public class Address extends DateTimeAuditable implements Identifiable, Placeholdable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contactName", length = 63)
    private String contactName;

    @Column(name = "country", length = 63)
    private String country;

    @Column(name = "state", length = 63)
    private String state;

    @Column(name = "city", length = 63)
    private String city;

    @Column(name = "street1", length = 255)
    private String street1;

    @Column(name = "street2", length = 255)
    private String street2;

    @Column(name = "postcode", length = 15)
    private String postcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public String getValueByFieldName(String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = this.getClass().getDeclaredField(fieldName);
        return field.get(this.getClass().cast(this)).toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (postcode != null) {
            sb.append(postcode.concat(" "));
        }

        if (city != null) {
            sb.append(city.concat(", "));
        }

        if (country != null) {
            sb.append(country.concat(", "));
        }

        if (street1 != null) {
            sb.append(street1.concat(", "));
        }

        if (street2 != null) {
            sb.append(street2.concat(", "));
        }

        return sb.toString();
    }


}
