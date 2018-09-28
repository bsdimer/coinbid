package com.madbid.core.repository.specification;

import com.madbid.core.model.User;
import com.madbid.core.model.User_;
import com.madbid.core.repository.common.AbstractSpecification;
import com.madbid.core.repository.common.PotentialJoin;
import com.madbid.core.repository.common.Specifications;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolov
 */
public class UserNameSpecification extends AbstractSpecification<User> {
    private String firstName;
    private String lastName;

    public UserNameSpecification(String fullName) {
        String[] nameParts = fullName.split(" ");
        switch (nameParts.length) {
            case 2:
                lastName = nameParts[1];
            case 1:
                firstName = nameParts[0];
        }
    }

    public UserNameSpecification(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, User> userRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(firstName)) {
            predicates.add(builder.like(userRoot.get().get(User_.firstName), firstName + "%"));
        }
        if (!StringUtils.isEmpty(lastName)) {
            predicates.add(builder.like(userRoot.get().get(User_.lastName), lastName + "%"));
        }

        return Specifications.and(predicates, builder);
    }
}
