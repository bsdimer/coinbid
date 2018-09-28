package com.madbid.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by nikolov.
 */
@Entity
@Table(name = "pictures")
public class Picture implements Identifiable, Serializable {
    private static final long serialVersionUID = 6960565621154257638L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "picture", nullable = false)
    private byte[] picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
