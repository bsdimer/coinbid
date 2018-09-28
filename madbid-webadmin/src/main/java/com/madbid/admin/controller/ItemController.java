package com.madbid.admin.controller;

import com.madbid.admin.utils.LocaleUtils;
import com.madbid.core.dto.PictureData;
import com.madbid.core.model.InventoryState;
import com.madbid.core.model.Item;
import com.madbid.core.model.ItemInventory;
import com.madbid.core.model.Picture;
import com.madbid.core.repository.ItemRepository;
import com.madbid.core.service.ItemInventoryService;
import com.madbid.core.service.ItemService;
import com.madbid.core.service.PictureService;
import com.madbid.lazyLoaders.ItemLazyLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolov
 */
@Component("itemController")
/*@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)*/
@Scope("prototype")
public class ItemController extends CommonTabBean implements Serializable, ITabBean {

    private static final String ITEM_SUCCESSFULLY_CREATED_MESSAGE_KEY = "create.item.message";
    private static final String ITEM_INVENTORY_SUCCESSFULLY_CREATED_MESSAGE_KEY = "create.item.inventory.message";
    private static final long serialVersionUID = -2333321358601505181L;

    private ItemLazyLoader items;
    private List<Item> filteredItems;
    private List<ItemInventory> itemInventories;
    private List<ItemInventory> filteredItemInventories;
    private Item item;
    private Item selectedItem;
    private Item selectedItemDetails;
    private ItemInventory selectedItemIneventory;
    private ItemInventory itemInventory;
    //Picture index in item details dialog
    private int pictureIndex;

    @Inject
    private ItemService itemService;

    @Inject
    private ItemInventoryService itemInventoryService;
    @Inject
    private ItemRepository itemRepozitory;
    @Inject
    private ImageController imageController;
    @Inject
    private PictureService pictureService;

    @PostConstruct
    private void init() {
        item = new Item();
        itemInventory = new ItemInventory();
        items = new ItemLazyLoader(itemService);
        itemInventories = new ArrayList<>();
        imageController.getPicturesData().clear();
        pictureIndex = 0;
    }

    public void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void createItem() {
        item.setPictures(PictureData.toPictures(imageController.getPicturesData()));
        itemService.save(item);
        addMessage(LocaleUtils.getLocaliziedMessage(ITEM_SUCCESSFULLY_CREATED_MESSAGE_KEY), "", FacesMessage.SEVERITY_INFO);
        item = new Item();
        imageController.getPicturesData().clear();
        RequestContext.getCurrentInstance().execute("PF('addNewItemDialog').hide();");
    }

    public void updateItem() {
        itemService.save(selectedItem);
    }

    public void editItem(RowEditEvent event) {
        Item item = (Item) event.getObject();
        itemService.save(item);
    }

    public void createItemInventory() {
        itemInventory.setState(InventoryState.AVAILABLE);
        itemInventory.setItem(selectedItem);
        itemInventoryService.save(itemInventory);
        //Adding invetory to list updates inventory table,
        //but what if there is more than one INVENTORY_OPERATORs
        itemInventories.add(itemInventory);
        addMessage(LocaleUtils.getLocaliziedMessage(ITEM_INVENTORY_SUCCESSFULLY_CREATED_MESSAGE_KEY), "", FacesMessage.SEVERITY_INFO);
        itemInventory = new ItemInventory();
    }

    public void editItemInventory(RowEditEvent event) {
        ItemInventory itemInventory = (ItemInventory) event.getObject();
        itemInventoryService.save(itemInventory);
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();

        PictureData pictureData = new PictureData();
        pictureData.setIdentifier(new Long(imageController.getPicturesData().size()));
        pictureData.setPicture(file.getContents());
        pictureData.setItem(item);
        pictureData.setFilename(file.getFileName());
        pictureData.setSize(file.getSize());

        imageController.getPicturesData().add(pictureData);
    }

    public void handleFileUploadAndSave(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();

        Picture picture = new Picture();
        picture.setItem(selectedItemDetails);
        picture.setPicture(file.getContents());

        pictureService.save(picture);
        selectedItemDetails.getPictures().add(picture);
    }

    public void deletePicture() {
        Picture picture = selectedItem.getPictures().get(pictureIndex);
        selectedItem.getPictures().remove(pictureIndex);
        pictureService.delete(picture);
        pictureIndex = 0; //we set index to 0 because imageswitch shows the first image after update
    }

    public void selectPictureIndex(int direction) {
        if (selectedItemDetails != null) {
            int size = selectedItemDetails.getPictures().size();
            if (pictureIndex == 0 && direction == -1) {
                pictureIndex = size - 1;
            } else if (pictureIndex == size - 1 && direction == 1) {
                pictureIndex = 0;
            } else {
                pictureIndex += direction;
            }
        }
    }

    public LazyDataModel<Item> getItems() {
        return items;
    }

    public List<Item> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Item> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public Item getItem() {
        return item;
    }

    public List<ItemInventory> getItemInventories() {
        return itemInventories;
    }

    public List<ItemInventory> getFilteredItemInventories() {
        return filteredItemInventories;
    }

    public void setFilteredItemInventories(List<ItemInventory> filteredItemInventories) {
        this.filteredItemInventories = filteredItemInventories;
    }

    public InventoryState[] getStates() {
        return InventoryState.values();
    }

    public ItemInventory getItemInventory() {
        return itemInventory;
    }

    public void setItems(ItemLazyLoader items) {
        this.items = items;
    }

    public void setSelectedItem(Item selectedItem) {
        if (selectedItem != null) {
            this.selectedItem = selectedItem;
            itemInventories = itemInventoryService.findAllByItem(selectedItem);
        }
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItemIneventory(ItemInventory selectedItemIneventory) {
        this.selectedItemIneventory = selectedItemIneventory;
    }

    public ItemInventory getSelectedItemIneventory() {
        return selectedItemIneventory;
    }

    public void setItemShortDescription(String description) {
        if (description != null && description.length() > 0) {
            this.selectedItem.setDescription(description);
        }
    }

    public String getItemShortDescription() {
        if (selectedItem != null) {
            return selectedItem.getDescription();
        }
        return "";
    }

    public void setItemLongDescription(String description) {
        if (description != null && description.length() > 0) {
            this.selectedItem.setLongDescription(description);
        }
    }

    public String getItemLongDescription() {
        if (selectedItem != null) {
            return selectedItem.getLongDescription();
        }
        return "";
    }

}