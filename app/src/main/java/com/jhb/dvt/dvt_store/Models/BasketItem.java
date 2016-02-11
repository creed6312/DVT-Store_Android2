package com.jhb.dvt.dvt_store.Models;

import java.io.Serializable;

/**
 * Created by SSardinha on 2016-02-11.
 */
public class BasketItem implements Serializable{

    private String id ;
    private int quantity ;

    public BasketItem(String id)
    {
        this.id = id ;
        this.quantity = 1 ;
    }

    public void increaseQuantity()
    {
        this.quantity += 1 ;
    }

    public void decreaseQuantity()
    {
        this.quantity -= 1 ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
