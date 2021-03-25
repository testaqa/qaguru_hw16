package tests;

import api.Auth;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DemowebshopTest {

    @BeforeAll
    static void beforeAll(){
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
    }

    @Test
    void emailFriendLogged(){
        var cookies = new Auth().login("qaguru@qa.guru", "qaguru@qa.guru1");

        var response = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookies(cookies)
                .body("FriendEmail=asdf%40test.com&YourEmailAddress=qaguru%40qa.guru&PersonalMessage=&send-email=Send+email")
                .when()
                .post("/productemailafriend/45")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response();

        assertThat(response.asString(), containsString("Your message has been sent"));
    }

    @Test
    void emailFriendAnonymous(){
        var response = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("FriendEmail=asdf%40test.com&YourEmailAddress=qaguru%40qa.guru&PersonalMessage=&send-email=Send+email")
                .when()
                .post("/productemailafriend/45")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response();

        assertThat(response.asString(), containsString("Only registered customers can use email a friend feature"));
    }
}
