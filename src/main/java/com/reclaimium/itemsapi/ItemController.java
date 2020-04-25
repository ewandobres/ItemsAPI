package com.reclaimium.itemsapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class ItemController {

    @Autowired
    FirebaseService firebaseService;

    @GetMapping("/getItemDetails")
    public ShopItem getItemDetails(@RequestHeader String id) throws ExecutionException, InterruptedException {
        return firebaseService.getItemDetails(id);
    }

    @PostMapping("/createItemDetails")
    public String createItemDetails(@RequestBody ShopItem item) throws ExecutionException, InterruptedException {
        return "Create Item with ID " + firebaseService.saveItemDetails(item);
    }

    @PutMapping("/updateItemDetails")
    public String updateItemDetails(@RequestBody ShopItem item) throws ExecutionException, InterruptedException {
        return "Your Item Is" + firebaseService.getItemDetails(item.id);
    }

    @DeleteMapping("/deleteItemDetails")
    public String deleteItemDetails(@RequestHeader String id) throws ExecutionException, InterruptedException {
        return firebaseService.deleteItemDetails(id);
    }

}
