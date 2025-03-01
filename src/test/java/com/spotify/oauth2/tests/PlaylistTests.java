package com.spotify.oauth2.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String Access_Token = "    \"access_token\": \"BQC35xSyoWnOLjbz9BuVIFaR0h3YzFS-EHg77W0WIijI_v_10wdGM4VqM8jBpi0fUKIpswhQJj3m5E5zWq_v2-Ehfch6mQHT-SP3txiviVPq8lSeAh5ymTRsUm7kVk34o-5MrucASA3Gej1Pd7tfXVt6KjvXP_ye6eXW" +
            "jhZT2ShRZieqLp6W1xbmqRauBphz_sik3av3HnVaL_wcxAPEOsTrUxfmOlH7aK6EZkaqraUNff_9J5vQALMeCGmcPBMbzy7C7of2VS-HIkqwxbMlU4F2\",\n";

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                addHeader("Authorization", "Bearer " + Access_Token).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                //  expectContentType(ContentType.JSON).
                        log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void ShouldBeAbleToCreateAPlaylist() {
        String Payload = "{\n" +
                "    \"name\": \"New Playlist\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given().
                body(Payload).
                when().
                post("/users/31bhfxyjefb6djj4sjxts5krngjq/playlists")
                .then().spec(responseSpecification).assertThat().statusCode(201).body("name", equalTo("New Playlist"),
                        "description", equalTo("New playlist description"),
                        "public", equalTo(false));
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist() {
        given().
                when().
                get("/playlists/6RAAYheMgyb7EP2y5nH9M8")
                .then().spec(responseSpecification).assertThat().statusCode(200).body("name", equalTo("Updated Playlist Name"),
                        "description", equalTo("Updated playlist description"),
                        "public", equalTo(true));
    }

    @Test
    public void ShouldBeAbleToPutAPlaylist() {
        String Payload = "{\n" +
                "    \"name\": \"Updated Playlist Name\",\n" +
                "    \"description\": \"Updated playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given().
                body(Payload).
                when().
                put("/playlists/6RAAYheMgyb7EP2y5nH9M8")
                .then().spec(responseSpecification).assertThat().statusCode(200);
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylist() {
        String Payload = "{\n" +
                "    \"name\": \"\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given().
                body(Payload).
                when().
                post("/users/31bhfxyjefb6djj4sjxts5krngjq/playlists")
                .then().spec(responseSpecification).assertThat().statusCode(400);
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
        String Payload = "{\n" +
                "    \"name\": \"New Playlist\",\n" +
                "    \"description\": \"New playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given().baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization", "Bearer " + "12345").
                contentType(ContentType.JSON).
                log().all().
                body(Payload).
                when().
                post("/users/31bhfxyjefb6djj4sjxts5krngjq/playlists")
                .then().spec(responseSpecification).assertThat().statusCode(401);
    }


}