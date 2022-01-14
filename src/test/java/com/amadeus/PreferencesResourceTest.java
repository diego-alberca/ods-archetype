package com.amadeus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint(PreferencesResource.class)
public class PreferencesResourceTest {

  private final static String USER = "test";
  private final static String EMAIL = "test@amadeus.com";
  private final static String EXPECTED_RESPONSE = "{\"user\":\"" + USER + "\",\"email\":\"" + EMAIL + "\"}";

  @Test
  public void testAddPreferences() {
    given()
        .formParam("user", USER)
        .formParam("email", EMAIL)
        .when()
        .post()
        .then()
        .statusCode(200)
        .body(is(EXPECTED_RESPONSE));
  }

  @Test
  public void testGetPreferences() {
    given()
        .when()
        .get(USER)
        .then()
        .statusCode(200)
        .body(is(EXPECTED_RESPONSE));
  }

  @Test
  public void testDeletePreferences() {
    given()
        .when()
        .delete(USER)
        .then()
        .statusCode(200)
        .body(is("Preferences deleted"));
  }
}
