package com.reclaimium.itemsapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
public class ItemController {

    @Autowired
    FirebaseService firebaseService;

    @GetMapping("/items")
    public ShopItem getItemDetails(@RequestParam String id) throws ExecutionException, InterruptedException {
        return firebaseService.getItemDetails(id);
    }

    @PostMapping("/items")
    public String createItemDetails(@Valid @RequestBody ShopItem item) throws ExecutionException, InterruptedException {
        return "Created Item with ID " + firebaseService.saveItemDetails(item);
    }


    @PutMapping("/items")
    public String updateItemDetails(@RequestBody ShopItem item, @RequestParam String id) throws ExecutionException, InterruptedException {
        return "Item Successfully Updated At " + firebaseService.updateItemDetails(item, id);
    }

    @DeleteMapping("/items")
    public String deleteItemDetails(@RequestParam String id){
        return "Item has been deleted at "+ firebaseService.deleteItemDetails(id);
    }

}
