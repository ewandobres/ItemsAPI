package com.reclaimium.itemsapi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    public String saveItemDetails(ShopItem shopItem) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> collectionsApiFuture = dbFirestore.collection("items").add(shopItem);

        return collectionsApiFuture.get().getId();
    }

    public ShopItem getItemDetails(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("items").document(id);
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

    public String updateItemDetails(ShopItem shop, String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("items").document(id);
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
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("items").document(id).update(shopMap);
            return collectionsApiFuture.get().getUpdateTime().toString();
        }else{
            return null;
        }
    }


    public String deleteItemDetails(String id){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection("items").document(id).delete();
        return id;
    }

}
