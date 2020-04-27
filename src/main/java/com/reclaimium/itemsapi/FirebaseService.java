package com.reclaimium.itemsapi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import com.okta.jwt.JwtVerifiers;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    private String getShopId(String accessToken) throws JwtVerificationException, ExecutionException, InterruptedException {
        AccessTokenVerifier jwtVerifier = JwtVerifiers.accessTokenVerifierBuilder()
                .setIssuer("https://dev-476180.okta.com/oauth2/ausajo9lsTeun8ZCb4x6")
                .setAudience("api://itemsapi")                // defaults to 'api://default'
                .setConnectionTimeout(Duration.ofSeconds(1)) // defaults to 1s
                .setReadTimeout(Duration.ofSeconds(1))       // defaults to 1s
                .build();

        Jwt result = jwtVerifier.decode(accessToken);
        String clientId = result.getClaims().get("cid").toString();

        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference shops = dbFirestore.collection("Shops");
        Query query = shops.whereEqualTo("clientId", clientId);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        if(querySnapshot.get().getDocuments().size() != 1){
            return null;
        }
        String shopId = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            shopId = document.getId();
        }

        if(shopId == null){
            throw new Error("No shop contains correct client id");
        }

        return shopId ;
    }

    public String saveItemDetails(String accessToken, ShopItem shopItem) throws ExecutionException, InterruptedException, JwtVerificationException {
        String shopId = getShopId(accessToken);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> collectionsApiFuture = dbFirestore.collection("Shops").document(Objects.requireNonNull(shopId)).collection("ItemsOnSale").add(shopItem);

        return collectionsApiFuture.get().getId();
    }

    public ShopItem getItemDetails(String accessToken, String id) throws ExecutionException, InterruptedException, JwtVerificationException {

        String shopId = getShopId(accessToken);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("Shops").document(Objects.requireNonNull(shopId)).collection("ItemsOnSale").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        ShopItem item;

        if(document.exists()){
            item = document.toObject(ShopItem.class);
            return item;
        }else{
            return null;
        }
    }

    public String updateItemDetails(ShopItem shop, String accessToken, String id) throws ExecutionException, InterruptedException, JwtVerificationException {
        String shopId = getShopId(accessToken);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("Shops").document(Objects.requireNonNull(shopId)).collection("ItemsOnSale").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> shopMap = mapper.convertValue(shop, new TypeReference<>() {});
        shopMap.values().removeAll(Collections.singleton(null));

        if(shopMap.containsKey("sellByDate")){
            Date date = new Date(Long.parseLong(shopMap.get("sellByDate").toString())*1000);
            shopMap.put("sellByDate" , date);
        }

        if(document.exists()){
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("Shops").document(shopId).collection("ItemsOnSale").document(id).update(shopMap);
            return collectionsApiFuture.get().getUpdateTime().toString();
        }else{
            return null;
        }
    }


    public String deleteItemDetails(String accessToken, String id) throws InterruptedException, ExecutionException, JwtVerificationException {
        String shopId = getShopId(accessToken);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection("Shops").document(shopId).collection("ItemsOnSale").document(id).delete();
        return id;
    }

}
