package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oauth 2.0")
@Feature("Playlist Api")
public class PlaylistTests extends BaseTest{
   @Step
    public Playlist playlistBuilder(String name, String description , boolean _public)
    {
        return new Playlist().setName(name).setDescription(description).setPublic(_public);
    }
    @Step
    public void assertPlaylistEquql(Playlist requestPlaylist , Playlist responsePlaylist )
    {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }
    @Story("Create a playlist story")
    @Link("https://example.org")
    @Link(name= "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("This is the description")
    @Test(description = "Should Be Able To Create APlaylist")
    public void ShouldBeAbleToCreateAPlaylist() throws FileNotFoundException {

        Playlist requestPlaylist= playlistBuilder(generateName(),generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.getCode()));
        Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEquql(requestPlaylist,responsePlaylist);

    }
    @Story("Create a playlist story")
    @Test(description ="Should Be Able To Get A Playlist" )
    public void ShouldBeAbleToGetAPlaylist() throws FileNotFoundException {
        Playlist requestPlaylist= playlistBuilder("Updated Playlist Name","Updated playlist description", true);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        Playlist responsePlaylist = response.as(Playlist.class);
       // assertPlaylistEquql(requestPlaylist,responsePlaylist);
    }
    @Story("Create a playlist story")
    @Test(description = "Should Be Able To Put A Playlist")
    public void ShouldBeAbleToPutAPlaylist() throws FileNotFoundException {
        Playlist requestPlaylist= playlistBuilder(generateName(),generateDescription(),false);

        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
    }
    @Story("Create a playlist story")
    @Test(description = "Should Not Be Able To Create A Playlist")
    public void ShouldNotBeAbleToCreateAPlaylist() throws FileNotFoundException {
        Playlist requestPlaylist= playlistBuilder("",generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_400.getCode()));
        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(),equalTo(StatusCode.CODE_400.getCode()));
        assertThat(error.getError().getMessage(),equalTo(StatusCode.CODE_400.getMsg()));
    }
    @Story("Create a playlist story")
    @Test(description ="Should Not Be Able To Create A Playlist With ExpiredToken" )
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() throws FileNotFoundException {
        String invalid_Token="1234";
        Playlist requestPlaylist1= playlistBuilder(generateName(),generateDescription(), false);
        Response response = PlaylistApi.post(invalid_Token, requestPlaylist1);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_401.getCode()));
        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(),equalTo(StatusCode.CODE_401.getCode()));
        assertThat(error.getError().getMessage(),equalTo(StatusCode.CODE_401.getMsg()));
    }


}