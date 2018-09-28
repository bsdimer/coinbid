package com.madbid.admin.controller;

import com.madbid.core.model.Payment;
import com.madbid.core.model.User;
import com.madbid.core.service.PaymentService;
import com.madbid.core.service.UserService;
import com.madbid.lazyLoaders.PaymentsLazyLoader;
import org.hibernate.LazyInitializationException;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dimer on 8/4/14.
 */
@Component("paymentsController")
@Scope("prototype")
@Transactional
public class PaymentsController extends CommonTabBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 920265857139107311L;


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    private LazyDataModel<Payment> payments;

    private List<Payment> filteredPayments;

    private Payment selectedPayment;

    @PostConstruct
    private void init() {
        payments = new PaymentsLazyLoader(paymentService);
    }

    public void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public LazyDataModel<Payment> getPayments() {
        return payments;
    }

    public void setPayments(LazyDataModel<Payment> payments) {
        this.payments = payments;
    }

    public Payment getSelectedPayment() {
        if (selectedPayment != null && selectedPayment.getPayer().getId() != null) {
            selectedPayment = paymentService.findOne(selectedPayment.getId());
        }
        return selectedPayment;
    }

    public void setSelectedPayment(Payment selectedPayment) {
        this.selectedPayment = selectedPayment;
    }

    public List<Payment> getFilteredPayments() {
        return filteredPayments;
    }

    public void setFilteredPayments(List<Payment> filteredPayments) {
        this.filteredPayments = filteredPayments;
    }
}