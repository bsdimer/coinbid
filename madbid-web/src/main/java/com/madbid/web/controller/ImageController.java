package com.madbid.web.controller;

import com.madbid.core.model.Auction;
import com.madbid.core.model.Picture;
import com.madbid.core.repository.AuctionRepository;
import com.madbid.core.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dimer on 8/25/14.
 */
@Controller
public class ImageController {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @RequestMapping(value = "/image/id/{id}")
         @ResponseBody
         public byte[] showImage(@PathVariable Integer id) {
        Picture picture = pictureRepository.findOne(id.longValue());
        return picture.getPicture();
    }

    @RequestMapping(value = "/image/auction/{id}")
    @ResponseBody
    public byte[] showAuctionWinnerImage(@PathVariable Integer id) {
        Auction auction = auctionRepository.findOne(id.longValue());
        return auction.getWinnerPicture();
    }
}
