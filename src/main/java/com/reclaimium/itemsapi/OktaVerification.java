package com.reclaimium.itemsapi;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import com.okta.jwt.JwtVerifiers;
import org.json.JSONObject;

import java.time.Duration;
import java.util.Base64;


public class OktaVerification {

    public String verifyCredentials(String accessToken) throws JwtVerificationException {
        AccessTokenVerifier jwtVerifier = JwtVerifiers.accessTokenVerifierBuilder()
                .setIssuer("https://dev-476180.okta.com/oauth2/ausajo9lsTeun8ZCb4x6")
                .setAudience("api://itemsapi")                // defaults to 'api://default'
                .setConnectionTimeout(Duration.ofSeconds(1)) // defaults to 1s
                .setReadTimeout(Duration.ofSeconds(1))       // defaults to 1s
                .build();

        Jwt result = jwtVerifier.decode(accessToken);

        return result.getTokenValue();
    }

    public String verifyCredentials(String clientId, String clientSecret) throws UnirestException {
        String combinedString = clientId + ":" + clientSecret;

        String encoding = Base64.getEncoder().encodeToString(combinedString.getBytes());

        HttpResponse<String> response = Unirest.post("https://dev-476180.okta.com/oauth2/ausajo9lsTeun8ZCb4x6/v1/token")
                .header("content-type", "application/x-www-form-urlencoded")
                .header("authorization", "Basic " + encoding)
                .header("accept", "application/json")
                .header("cache-control", "no-cache")
                .body("grant_type=client_credentials&scope=customScope")
                .asString();

        //System.out.println("Basic "+ encoding);
        JSONObject extractedBody = new JSONObject(response.getBody());

        return extractedBody.get("access_token").toString();
    }
}
