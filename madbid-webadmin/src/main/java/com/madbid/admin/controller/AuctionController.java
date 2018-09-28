package com.madbid.admin.controller;

import com.madbid.admin.utils.LocaleUtils;
import com.madbid.core.model.Auction;
import com.madbid.core.repository.AuctionRepository;
import com.madbid.core.service.AuctionService;
import com.madbid.core.service.proxy.AuctionDTO;
import com.madbid.lazyLoaders.AuctionsLazyLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dimer on 8/4/14.
 */
@Component("auctionController")
/*@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)*/
@Scope("prototype")
public class AuctionController extends CommonTabBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 3974201006282124403L;
    private static final String AUCTION_SUCCESSFULLY_CREATED_MESSAGE_KEY = "craete.auction.message";
    private static final String TOPIC_UPDATE_AUCTIONS = "updateAuctionsInternal";
    private static final String TOPIC_UPDATE_AUCTION = "updateAuctionInternal";
    private static final String TOPIC_CREATE_AUCTION = "createAuctionInternal";

    private LazyDataModel<Auction> auctions;
    private List<Auction> filteredAuctions;
    private Auction auction;


    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    @Qualifier("topicJMSTemplate")
    private JmsTemplate jmsTemplate;
    private String toggleBB;

    public void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Transactional
    public void create() {
        if (auction.getStartPrice() == null) {
            auction.setStartPrice(new BigDecimal(0d));
        }

        auction.setTimer(auction.getAuctionDuration());
        auctionService.save(auction);
        jmsTemplate.convertAndSend(TOPIC_CREATE_AUCTION, AuctionDTO.fromAuction(auction));

        // Reload logic
        auction = new Auction();
        auction.setStartPrice(new BigDecimal(0d));
        addMessage(LocaleUtils.getLocaliziedMessage(AUCTION_SUCCESSFULLY_CREATED_MESSAGE_KEY), "", FacesMessage.SEVERITY_INFO);
        RequestContext.getCurrentInstance().execute("PF('addNewAuctionDialog').hide();");
    }

    @Transactional
    public void edit(RowEditEvent event) {
        Auction auction = (Auction) event.getObject();
        auctionService.save(auction);
        jmsTemplate.convertAndSend(TOPIC_UPDATE_AUCTION, AuctionDTO.fromAuction(auctionService.findOneDeep(auction.getId())));
    }

    @PostConstruct
    private void init() {
        auction = new Auction();
        auction.setStartPrice(new BigDecimal(0d));
        auctions = new AuctionsLazyLoader(auctionService);
    }

    public LazyDataModel<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(LazyDataModel<Auction> auctions) {
        this.auctions = auctions;
    }

    public AuctionService getAuctionService() {
        return auctionService;
    }

    public void setAuctionService(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    public List<Auction> getFilteredAuctions() {
        return filteredAuctions;
    }

    public void setFilteredAuctions(List<Auction> filteredAuctions) {
        this.filteredAuctions = filteredAuctions;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Date getMinDate() {
        return new Date();
    }

    @Transactional
    public void toggleBB(Auction auction) {
        auction.setEnableBB(!auction.getEnableBB());
        auctionService.save(auction);
    }
}