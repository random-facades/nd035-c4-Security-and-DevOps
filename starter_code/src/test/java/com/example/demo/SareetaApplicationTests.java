package com.example.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.TestUtils.LoginRequest;
import com.example.demo.model.requests.CreateUserRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SareetaApplicationTests {


	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(restTemplate);
	}

	@Test
	public void test_create_user() throws Exception {
		String username = "CREATE_USER";
		String password = "CREATE_PASSWORD";

		final String baseUrl = "http://localhost:8080/api/user/create";
		URI uri = new URI(baseUrl);

		CreateUserRequest req = new CreateUserRequest();
		req.setUsername(username);
		req.setPassword(password);
		req.setConfirmPassword(password);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, req, String.class);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getBody());
	}

	@Test 
	public void test_unauthorized_access() throws Exception {
		String username = "BAD_USER";
		String password = "BAD_PASSWORD";

		final String baseUrl = "http://localhost:8080/login";
		URI uri = new URI(baseUrl);

		LoginRequest req = new LoginRequest();
		req.setUsername(username);
		req.setPassword(password);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, req, String.class);

		assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
	}


	@Test 
	public void test_create_login_and_check_user() throws Exception {
		String username = "LOGIN_USER";
		String password = "LOGIN_PASSWORD";

		String baseUrl = "http://localhost:8080/api/user/create";
		URI uri = new URI(baseUrl);

		CreateUserRequest create = new CreateUserRequest();
		create.setUsername(username);
		create.setPassword(password);
		create.setConfirmPassword(password);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, create, String.class);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getBody());

		baseUrl = "http://localhost:8080/login";
		uri = new URI(baseUrl);

		LoginRequest login = new LoginRequest();
		login.setUsername(username);
		login.setPassword(password);

		result = this.restTemplate.postForEntity(uri, login, String.class);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getHeaders().get("Authorization"));

		baseUrl = "http://localhost:8080/api/user/" + username;

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", result.getHeaders().get("Authorization").get(0));
		result = restTemplate.exchange(baseUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getBody());

		JSONObject json = new JSONObject(result.getBody());

		assertEquals(username, json.getString("username"));

	}
}
