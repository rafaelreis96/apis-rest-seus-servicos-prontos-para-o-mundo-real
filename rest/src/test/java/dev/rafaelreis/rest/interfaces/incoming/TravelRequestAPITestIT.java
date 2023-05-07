package dev.rafaelreis.rest.interfaces.incoming;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import dev.rafaelreis.rest.infrastructure.FileUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = WireMockConfiguration.DYNAMIC_PORT)
@ActiveProfiles("test")
class TravelRequestAPITestIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private WireMockServer server;

	@BeforeEach
	public void setup() {
		RestAssured.port = port;
	}
	
	public void setupServer() {
		server.stubFor(get(urlPathEqualTo("/maps/api/directions/json"))
			.withQueryParam("origin", equalTo("Avenida Paulista, 900"))
			.withQueryParam("destination", equalTo("Avenida Paulista, 1000"))
			.withQueryParam("key", equalTo("APIKEY"))
			.willReturn(okJson(FileUtils.loadFileContents("/responses/gmaps/sample_response.json")))
		);
	}

	@Test
	void testFindNearbyTravelRequests() {
		setupServer();
		
		String passengerId = RestAssured
			.given()
			.contentType(ContentType.JSON)
			.body(FileUtils.loadFileContents("/requests/passengers_api/create_new_passenger.json"))
			.post("/passengers")
			.then()
			.statusCode(200)
			.body("id", notNullValue())
			.body("name", is("Alexandre Saudate"))
			.extract()
			.body()
			.jsonPath()
			.getString("id");
 
		Map<String, String> data = new HashMap<>();
		data.put("passengerId", passengerId);
		
		Integer travelRequestId = RestAssured
			.given()
			.contentType(ContentType.JSON)
			.body(FileUtils.loadFileContents("/requests/travel_requests_api/create_new_request.json", data))
			.post("/travelRequests")
			.then()
			.statusCode(200)
			.body("id", notNullValue())
			.body("origin", is("Avenida Paulista, 1000"))
			.body("destination", is("Avenida Ipiranga, 100"))
			.body("status", is("CREATED"))
			.body("_links.passenger.title", is("Alexandre Saudate"))
			.extract()
			.jsonPath()
			.get("id");
 
		RestAssured
			.given()
			.get("/travelRequests/nearby?currentAddress=Avenida Paulista, 900")
			.then()
			.assertThat()
			.statusCode(200)
			.body("[0].id", is(travelRequestId))
			.body("[0].origin", is("Avenida Paulista, 1000"))
			.body("[0].destination", is("Avenida Ipiranga, 100"))
			.body("[0].status", is("CREATED"));
	}

}
