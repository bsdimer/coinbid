package com.madbid.core.dto;

import com.madbid.core.model.Item;
import com.madbid.core.model.Picture;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolov.
 */
public class PictureData {
    private Long identifier;

    private byte[] picture;

    private Item item;

    private String filename;

    private Long size;

    public String readableFileSize() {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public Picture toPicture() {
        Picture picture = new Picture();
        picture.setPicture(this.picture);
        picture.setItem(item);

        return picture;
    }

    public static List<Picture> toPictures(List<PictureData> pictureDtos) {
        List<Picture> pictures = new ArrayList<>();
        for (PictureData pictureDto : pictureDtos) {
            pictures.add(pictureDto.toPicture());
        }
        return pictures;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
