package com.reclaimium.itemsapi;

public class ShopItem {
    String id;
    String itemQuantity;
    String newPrice;
    public ShopItem(String id, String itemQuantity, String newPrice) {
        this.id = id;
        this.itemQuantity = itemQuantity;
        this.newPrice = newPrice;
    }

    public ShopItem(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
