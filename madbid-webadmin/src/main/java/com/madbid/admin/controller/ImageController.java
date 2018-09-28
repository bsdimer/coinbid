package com.madbid.admin.controller;

import com.madbid.core.dto.PictureData;
import com.madbid.core.model.Picture;
import com.madbid.core.service.PictureService;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolov
 */
@Component("imageController")
@Scope(value = "session")
public class ImageController implements Serializable {

    private static final long serialVersionUID = 8330843268032901868L;

    private List<PictureData> pictures = new ArrayList<>();

    @Inject
    private PictureService pictureService;

    public StreamedContent getImageFromDB() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            if (imageId != null && !imageId.isEmpty()) {
                Picture picture = pictureService.findOne(Long.parseLong(imageId));

                if (picture != null) {
                    return new DefaultStreamedContent(new ByteArrayInputStream(picture.getPicture()), "image/jpg");
                }
                //STODO discuss and if needed set default image
//            InputStream resourceAsStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/img/no_image.png");
//            return new DefaultStreamedContent(new ByteArrayInputStream(IOUtils.toByteArray(resourceAsStream)), "image/jpg");
            }
            return new DefaultStreamedContent();
        }
    }

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            PictureData picture = findPictureData(Long.parseLong(imageId));

            if(picture != null){
                return new DefaultStreamedContent(new ByteArrayInputStream(picture.getPicture()), "image/jpg");
            }

            return new DefaultStreamedContent();
        }
    }

    private PictureData findPictureData(Long identifier) {
        for (PictureData picture : pictures) {
            if(picture.getIdentifier().equals(identifier)) {
                return picture;
            }
        }
        return null;
    }

    public void removePictureData(Long identifier) {
        pictures.remove(findPictureData(identifier));
    }

    public List<PictureData> getPicturesData() {
        return pictures;
    }

    public void setPicturesData(List<PictureData> pictures) {
        this.pictures = pictures;
    }
}