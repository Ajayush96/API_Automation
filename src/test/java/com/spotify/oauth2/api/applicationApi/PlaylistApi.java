package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.io.FileNotFoundException;

import static com.spotify.oauth2.api.Routes.PLAYLISTS;
import static com.spotify.oauth2.api.Routes.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;


public class PlaylistApi {

    @Step
    public static Response post(Playlist requestPlaylist) throws FileNotFoundException
    {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() + PLAYLISTS,getToken(),requestPlaylist);
    }

    public static Response post(String token,Playlist requestPlaylist) throws FileNotFoundException
    {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUser() + PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String PlaylistId)
    {
        return RestResource.get(PLAYLISTS + "/" + PlaylistId, getToken());
    }

    public  static Response update(String PlaylistId,Playlist requestPlaylist)
    {
        return RestResource.update(PLAYLISTS + "/" + PlaylistId, getToken() , requestPlaylist);

    }
}


