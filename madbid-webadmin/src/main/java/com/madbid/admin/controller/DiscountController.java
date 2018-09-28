package com.madbid.admin.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * Created by nikolov on 3/11/14.
 */
@Component("discountController")
@Scope("prototype")
@Transactional
public class DiscountController extends CommonTabBean implements Serializable, ITabBean {
    @PostConstruct
    private void init() {

    }
}