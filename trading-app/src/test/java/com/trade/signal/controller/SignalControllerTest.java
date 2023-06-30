package com.trade.signal.controller;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.trade.TradingApp;
import com.trade.signal.service.SignalProcessor;

import io.restassured.RestAssured;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TradingApp.class)
public class SignalControllerTest {
	
	@LocalServerPort
	private int port;
	
	private final String baseURL="trading-app/";
	
	@MockBean
	private SignalProcessor signalProcessor;
	
	@BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }
	
	@Test
	void callingPing_returnPong() {
		io.restassured.response.Response response = get(baseURL + "signal/ping");
		
		response.then().statusCode(200);
		
		assertEquals("pong", response.getBody().asString());
	}
	

	@Test
	void callSignalWithInvalidInput() {
		io.restassured.response.Response response = post(baseURL+"signal/1000000000000000000000000000");
		response.then().statusCode(400);
	
	}
	
	@Test
	void testvalidSignal() {
		io.restassured.response.Response response = given().header("Content-Type", MediaType.APPLICATION_JSON_VALUE).post(baseURL+"signal/1");
		response.then().statusCode(200);
		assertEquals("Success", response.getBody().asString());
		
	}
	
	@Test
	void testException() {
		doThrow(IndexOutOfBoundsException.class).when(signalProcessor).handleSignal(eq(-1), isNull());
		io.restassured.response.Response response = given().header("Content-Type", MediaType.APPLICATION_JSON_VALUE).post(baseURL+"signal/-1");
		response.then().statusCode(500);
		
	}

}
