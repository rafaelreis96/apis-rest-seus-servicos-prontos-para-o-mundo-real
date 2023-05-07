package dev.rafaelreis.rest.interfaces.incoming;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PassengerAPITestIT {
	
	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setup() {
		RestAssured.port = port;
	}
	
	@Test
	public void testCreatePassenger() {
		String createPassengerJSON = "{\"name\": \"Alexandre Saudate\"}";
		
		RestAssured
			.given()
			.contentType(ContentType.JSON)
			.body(createPassengerJSON)
			.post("/passengers")
			.then()
			.statusCode(200)
			.body("id", notNullValue())
			.body("name", equalTo("Alexandre Saudate"));
		
	}
	

}
