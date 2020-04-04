package com.aoher.controller;

import com.aoher.StudentApplication;
import com.aoher.model.Course;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate;
    private HttpHeaders httpHeaders;

    @Before
    public void setUp() {
        restTemplate = new TestRestTemplate();
        httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", createHttpAuthenticationHeaderValue(
                "user1", "secret1"));
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRetrieveStudentCourse() {
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/student/Student1/courses/Course1"),
                HttpMethod.GET, entity, String.class);
        String expected = "{id:Course1,name:Spring,description:10 Steps}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void addCourse() {
        Course course = new Course("Course1", "Spring", "10 Steps",
                Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));

        HttpEntity<Course> entity = new HttpEntity<>(course, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/student/Student1/courses"),
                HttpMethod.POST, entity, String.class
        );

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
        assertTrue(actual.contains("/students/Student1/courses/"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private String createHttpAuthenticationHeaderValue(String userId, String password) {
        String auth = userId + ":" + password;
        byte[] encodedAuth = Base64.encode(auth.getBytes(StandardCharsets.US_ASCII));

        return "Basic " + new String(encodedAuth);
    }
}
