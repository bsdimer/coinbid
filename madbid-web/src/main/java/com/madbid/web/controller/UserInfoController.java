package com.madbid.web.controller;

import com.madbid.core.model.Auction;
import com.madbid.core.model.AuctionState;
import com.madbid.core.model.Role;
import com.madbid.core.model.User;
import com.madbid.core.service.AuctionService;
import com.madbid.core.service.MadbidNotificationService;
import com.madbid.core.service.UserService;
import com.madbid.core.service.proxy.AuctionAdvancedDTO;
import com.madbid.core.service.proxy.AuctionDTO;
import com.madbid.web.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.management.*;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dimer on 10/29/14.
 */
@Controller
public class UserInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MadbidNotificationService madbidNotificationService;

    @Autowired
    private AuctionService auctionService;
    private int REST_DEFAULT_PAGE_SIZE = 5;


    @RequestMapping(value = "/user/userAuctions", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public List<AuctionDTO> getUserAuction(WebRequest request, Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<AuctionDTO> userAuctions = new ArrayList<>();
        for (Auction userAuction : auctionService.getUserAuctions(user)) {
            userAuctions.add(AuctionDTO.fromAuction(userAuction));
        }
        return userAuctions;
    }

    @RequestMapping(value = "/rest/{auctionState}/lazyList/{firstId}/{onField}/{sortOrder}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<AuctionAdvancedDTO> getUserAuctionGet(@PathVariable("auctionState") String auctionState,
                                                      @PathVariable("firstId") int firstId,
                                                      @PathVariable("onField") String onField,
                                                      @PathVariable("sortOrder") String sortOrder, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<AuctionAdvancedDTO> userAuctions = new ArrayList<>();

        if (auctionState.indexOf("userAuctions") == 0) {
            for (Auction userAuction : auctionService.getUserAuctions(user)) {
                userAuctions.add(AuctionAdvancedDTO.fromAuction(userAuction, auctionService, user));
            }
        } else if (auctionState.indexOf("wonAuctions") == 0) {
            for (Auction userAuction : auctionService.getWonAuctions(user)) {
                userAuctions.add(AuctionAdvancedDTO.fromAuction(userAuction, auctionService, user));
            }
        } else if (auctionState.indexOf("nonWonAuctions") == 0) {
            for (Auction userAuction : auctionService.getUserAuctions(user)) {
                if ((userAuction.getState().equals(AuctionState.WON) || userAuction.getState().equals(AuctionState.CLOSED))
                        && userAuction.getLastBidder().equals(user)) {
                    userAuctions.add(AuctionAdvancedDTO.fromAuction(userAuction, auctionService, user));
                }
            }
        }
        return userAuctions.subList(firstId, (userAuctions.size() < firstId + REST_DEFAULT_PAGE_SIZE ? userAuctions.size() : firstId + 5));

    }

    @RequestMapping(value="/user/uploadWinAuctionPicture/{auctionId}", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(MultipartHttpServletRequest request, Principal principal,
                                                 @PathVariable("auctionId") Long auctionId) throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException {
        Iterator<String> itr =  request.getFileNames();

        MultipartFile mpf = request.getFile(itr.next());
        Auction auction = auctionService.findOneDeep(auctionId);
        try {
            auction.setWinnerPicture(mpf.getBytes());
            auctionService.save(auction);

            List<User> administrators = userService.findByRole(Role.ROLE_ADMIN);
            madbidNotificationService.createUserAuctionWinningPictureNotificationEmail(auction, auction.getLastBidder(), auction.getItemInventory(), administrators, RequestUtils.getEndPoints().get(0));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (MalformedObjectNameException | AttributeNotFoundException | MBeanException
                | ReflectionException | InstanceNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        LOGGER.info("Picture uploaded by winner");
        LOGGER.info("Auction:" + auctionId);

        return "success";
    }
}
