package com.madbid.admin.view;

import org.primefaces.component.menuitem.UIMenuItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimer on 1/17/14.
 */
@Component("menu")
@Scope("session")
public class MenuMB implements Serializable {
    private static final long serialVersionUID = 9021752171695193370L;

    private Map<String, String> navMap = new HashMap<String, String>();

    @Inject
    TabViewMB tabBean;

    @Inject
    MainPanelBean panelBean;


    public MenuMB() {
    }

    public void onClick(ActionEvent event) {
        String id = ((UIMenuItem) ((ActionEvent) event).getSource()).getId();
        String header = (String) ((UIMenuItem) ((ActionEvent) event).getSource()).getValue();
        /**
         *  Used to produce Tabs
         */
        tabBean.addTab(id, header);

        /**
         * Used to produce flat forms without multitasking
         */
        //panelBean.showView(id, header);
    }

//    public void onPrint(ActionEvent event) {
//    	String id = ((CommandLink) ((ActionEvent) event).getSource()).getId();
//    	
//    	if(id.equals(DAILY_REPORT)) {
//    		printer.printReceiptReport(true);
//    	} else if(id.equals(INTERIM_REPORT)) {
//    		printer.printReceiptReport(false);
//    	}
//    }

    /**
     * Shows the view by parsing config entity with view bindings
     *
     * @param event
     */
    public void showConfiguredView(ActionEvent event) {
        String id = ((UIMenuItem) ((ActionEvent) event).getSource()).getId();
        String label = (String) ((UIMenuItem) ((ActionEvent) event).getSource()).getValue();
        tabBean.addTab(id, label);
    }

    public void setTabBean(TabViewMB tabBean) {
        this.tabBean = tabBean;
    }

    public void setPanelBean(MainPanelBean panelBean) {
        this.panelBean = panelBean;
    }
}
