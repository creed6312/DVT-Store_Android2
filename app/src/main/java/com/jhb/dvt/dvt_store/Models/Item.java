package com.jhb.dvt.dvt_store.Models;

/**
 * Created by CreeD on 2016/02/10.
 */
public class Item {

    private String id ;
    private String name ;
    private String details ;
    private String imageUrl ;
    private double price ;

    public Item(String id, String name, String details, String imageUrl, double price)
    {
        this.id = id ;
        this.name = name ;
        this.details = details ;
        this.imageUrl = imageUrl ;
        this.price = price ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
