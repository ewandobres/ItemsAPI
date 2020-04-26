package com.reclaimium.itemsapi;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Component
public class ShopItem {
    @NotNull
    String name;
    @NotNull
    String itemQuantity;
    @NotNull
    String newPrice;
    @NotNull
    String oldPrice;
    @NotNull
    Date sellByDate;


    public ShopItem(String name, String itemQuantity, String newPrice, String oldPrice, String sellByDate) {
        this.name = name;
        this.itemQuantity = itemQuantity;
        this.newPrice = newPrice;
        this.oldPrice = oldPrice;
        this.sellByDate = new Date(Long.parseLong(sellByDate)*1000);
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }




    public ShopItem(){

    }

    public String getName() {
        return name;
    }

    public void setId(String name) {
        this.name = name;
    }
    public Date getSellByDate() {
        return sellByDate;
    }

    public void setSellByDate(Date sellByDate) {
        this.sellByDate = sellByDate;
    }
    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }



}
