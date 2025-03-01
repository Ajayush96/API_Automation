package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Routes.API;
import static com.spotify.oauth2.api.Routes.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {


    public static Response post(String Path, String token , Object requestPlaylist)
    {
        return given(getRequestSpec()).
                body(requestPlaylist).auth().oauth2(token).
       // .header("Authorization","Bearer " + token).
                when().
                post(Path)
                .then().spec(getResponseSpec()).extract()
                .response();
    }

    public static Response postAccount(HashMap<String, String> formParams){
       return given(getAccountRequestSpec())
                .formParams(formParams)
                .when().post(API + TOKEN).then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
    public static Response get(String Path, String token)
    {
        return given(getRequestSpec()).auth().oauth2(token).
                when().
                get(Path)
                .then().spec(getResponseSpec()).extract().response();
    }

    public  static Response update(String Path, String token , Object requestPlaylist)
    {
       return given(getRequestSpec()).
                body(requestPlaylist).auth().oauth2(token).
                when().
                put(Path)
                .then().spec(getResponseSpec()).extract().response();
    }
}
