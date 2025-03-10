package com.springcommerce.product_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import static org.hamcrest.Matchers.is;

@Import(TestcontainersConfiguration.class)
//running the test container on a random port so that it does not conflict
// with the actual product service running on 8080
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	// by specifying this annotation, spring boot will automatically inject the URI from the application properties
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	// this variable will be dynamically injected when the test-container is running on a port
	@LocalServerPort
	private Integer port;

	// the following annotation indicates that this method should be run before each test method is executed
	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}


	static {
		mongoDBContainer.start();
	}

	// this test will try to create a product, and it will see if it's created successfully or not
	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
				    "name": "iPhone 15 Pro",
				    "description": "Very Nice",
				    "price": 999.09
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iPhone 15 Pro"))
				.body("description", Matchers.equalTo("Very Nice"))
				.body("price.toString()", is("999.09"));
	}

}
