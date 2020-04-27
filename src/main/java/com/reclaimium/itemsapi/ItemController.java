package com.reclaimium.itemsapi;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.okta.jwt.JwtVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
    public String createItemDetails(
            @RequestHeader(required = false) String accessToken,
            @RequestHeader(required = false) String clientId,
            @RequestHeader(required = false) String clientSecret,
            @Valid @RequestBody ShopItem item)
            throws ExecutionException, InterruptedException, UnirestException, JwtVerificationException {
        if(accessToken != null){
            String verifiedToken = oktaVerification.verifyCredentials(accessToken);

            if(verifiedToken.equals(accessToken)){
                return "Created Item with ID " + firebaseService.saveItemDetails(verifiedToken, item);
            }

        }
        if(clientId != null && clientSecret != null){
            String verifiedToken = oktaVerification.verifyCredentials(clientId, clientSecret);
            return "Created Item with ID " + firebaseService.saveItemDetails(verifiedToken, item);
        }

        return null;
    }


    @PutMapping("/items")
    public String updateItemDetails(
            @RequestBody ShopItem item,
            @RequestHeader(required = false) String accessToken,
            @RequestHeader(required = false) String clientId,
            @RequestHeader(required = false) String clientSecret,
            @RequestParam String itemId)
            throws ExecutionException, InterruptedException, JwtVerificationException, UnirestException {
        if(accessToken != null){
            String verifiedToken = oktaVerification.verifyCredentials(accessToken);

            if(verifiedToken.equals(accessToken)){
                return "Item Successfully Updated At " + firebaseService.updateItemDetails(item, verifiedToken, itemId);
            }

        }
        if(clientId != null && clientSecret != null){
            String verifiedToken = oktaVerification.verifyCredentials(clientId, clientSecret);
            return "Item Successfully Updated At " + firebaseService.updateItemDetails(item, verifiedToken, itemId);
        }
        return null;
    }

    @DeleteMapping("/items")
    public String deleteItemDetails(
            @RequestHeader(required = false) String accessToken,
            @RequestHeader(required = false) String clientId,
            @RequestHeader(required = false) String clientSecret,
            @RequestParam String itemId)
            throws JwtVerificationException, UnirestException, ExecutionException, InterruptedException {
        if(accessToken != null){
            String verifiedToken = oktaVerification.verifyCredentials(accessToken);

            if(verifiedToken.equals(accessToken)){
                return "Item has been deleted at "+ firebaseService.deleteItemDetails(verifiedToken, itemId);
            }

        }
        if(clientId != null && clientSecret != null){
            String verifiedToken = oktaVerification.verifyCredentials(clientId, clientSecret);
            return "Item has been deleted at "+ firebaseService.deleteItemDetails(verifiedToken, itemId);
        }
        return null;
    }

}
