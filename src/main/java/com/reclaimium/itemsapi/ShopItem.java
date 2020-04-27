package com.reclaimium.itemsapi;
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

    public ShopItem(){

    }
}
