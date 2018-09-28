package com.madbid.admin.controller;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by dimer on 8/6/14.
 */
@Component("pageNavigation")
public class PageNavigationController implements Serializable {
    private static final long serialVersionUID = -2798290287165854780L;

    private String pageName;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
