package com.epam.apitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.apache.myfaces.blank.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiTest {
    @Test
    public void testGetAllUsers() {
        RequestSpecification requestSpecification =
                RestAssured.given().auth().oauth2("8d3edc50fd5dbb75c78aa0e6b003827314f21f4aa8f03facd79465c96ce44c55");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        requestSpecification.headers(headers);
        Response response = requestSpecification.expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().get("https://gorest.co.in/public/v2/users");
        ResponseBody body = response.getBody();
        List<User> users = response.jsonPath().getList("", User.class);
        System.out.println("Response Body is: " + body.asString());
        Assert.assertEquals(users.size(), 10);
    }
}