package com.madbid.admin.view;


import com.madbid.admin.controller.ITabBean;

/**
 * Created by dimer on 2/25/14.
 */
public class PortalTab extends CustomTab implements ITabBean {

    private TabViewMB tabViewMB;

    public PortalTab(String view, String label, TabViewMB tabView) {
        super(view, label, tabView);
    }

    @Override
    public Boolean getClosable() {
        return false;
    }

    public TabViewMB getTabViewMB() {
        return this.tabViewMB;
    }

    public void setTabViewMB(TabViewMB tabViewMB) {

        this.tabViewMB = tabViewMB;
    }
}
