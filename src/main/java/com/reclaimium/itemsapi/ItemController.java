package com.reclaimium.itemsapi;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.okta.jwt.JwtVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RestController
public class ItemController {

    @Autowired
    FirebaseService firebaseService;
    OktaVerification oktaVerification = new OktaVerification();
    @GetMapping("/items")
    public ShopItem getItemDetails(
            @RequestHeader(required = false) String accessToken,
            @RequestHeader(required = false) String clientId,
            @RequestHeader(required = false) String clientSecret,
            @RequestHeader String shopId,
            @RequestParam String itemId)
            throws ExecutionException, InterruptedException, JwtVerificationException, UnirestException {
        if(accessToken != null){
            String verifiedToken = oktaVerification.verifyCredentials(accessToken);

            if(verifiedToken.equals(accessToken)){
                return firebaseService.getItemDetails(verifiedToken, itemId);
            }

        }
        if(clientId != null && clientSecret != null){
            String verifiedToken = oktaVerification.verifyCredentials(clientId, clientSecret);
            return firebaseService.getItemDetails(verifiedToken, itemId);
        }

        return null;
    }

    @PostMapping("/items")
    public String createItemDetails(@RequestHeader String shopId, @Valid @RequestBody ShopItem item) throws ExecutionException, InterruptedException {
        return "Created Item with ID " + firebaseService.saveItemDetails(shopId, item);
    }


    @PutMapping("/items")
    public String updateItemDetails(@RequestBody ShopItem item, @RequestHeader String shopId, @RequestParam String itemId) throws ExecutionException, InterruptedException {
        return "Item Successfully Updated At " + firebaseService.updateItemDetails(item, shopId, itemId);
    }

    @DeleteMapping("/items")
    public String deleteItemDetails(@RequestHeader String shopId, @RequestParam String itemId){
        return "Item has been deleted at "+ firebaseService.deleteItemDetails(shopId, itemId);
    }

}
