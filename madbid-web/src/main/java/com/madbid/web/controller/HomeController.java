package com.madbid.web.controller;

import com.madbid.core.model.Auction;
import com.madbid.core.service.AuctionService;
import com.madbid.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by nikolov.
 */
@Controller
@SessionAttributes("auctions")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    protected static final String VIEW_NAME_FAQ = "faq";
    protected static final String VIEW_NAME_HOMEPAGE = "index";
    public static final String AUCTIONS_MODEL_ATTRIBUTENAME = "auctions";

    @Inject
    private AuctionService auctionService;

    @Inject
    private UserService userService;

    @ModelAttribute
    public void addAuctions(Model model) {
        List<Auction> auctionList = auctionService.findActiveDeep();
        model.addAttribute(AUCTIONS_MODEL_ATTRIBUTENAME, auctionList);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showHomePage() {
        LOGGER.debug("Rendering homepage.");
        return VIEW_NAME_HOMEPAGE;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage2() {
        LOGGER.debug("Rendering homepage.");
        return VIEW_NAME_HOMEPAGE;
    }

    @RequestMapping(value = "/faq", method = RequestMethod.GET)
    public String showFaq() {
        LOGGER.debug("Rendering FAQ.");
        return VIEW_NAME_FAQ;
    }

    @RequestMapping(value = "/user/userPanel", method = RequestMethod.GET)
    public String showUserPanel() {
        return "user/userPanel";
    }


    @RequestMapping(value = "/auctionDetails/{id}", method = RequestMethod.GET)
    public String onAuctionDetails(@PathVariable("id") String id, Model model) {
        Auction auction = auctionService.findOneDeep(Long.parseLong(id));
        model.addAttribute("auction", auction);
        return "auctionDetails";
    }

}
