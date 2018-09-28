package com.madbid.admin.controller;

import com.madbid.admin.view.TabViewMB;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by dimer on 8/16/14.
 */
public abstract class CommonTabBean implements Serializable {
    private static final long serialVersionUID = 3704415467982908984L;

    @Inject
    private TabViewMB tabViewMB;

    protected CommonTabBean() {
    }

    public Boolean getClosable() {
        return true;
    }

    public TabViewMB getTabViewMB() {
        return tabViewMB;
    }

    public void setTabViewMB(TabViewMB tabViewMB) {
        this.tabViewMB = tabViewMB;
    }

    protected String generateResouceFilePath(String suffix) {
        return this.getClass().getClassLoader().getResource(suffix).getFile();
    }


}
