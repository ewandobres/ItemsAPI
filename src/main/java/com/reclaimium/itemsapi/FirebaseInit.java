package com.reclaimium.itemsapi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
@Service
public class FirebaseInit {
    @PostConstruct
    public void initalize() {
        try{

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream serviceAccount = classloader.getResourceAsStream("serviceAccountKey.json");


            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(Objects.requireNonNull(serviceAccount)))
                    .setDatabaseUrl("https://reclaimium-2e023.firebaseio.com")
                    .build();

        FirebaseApp.initializeApp(options);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
