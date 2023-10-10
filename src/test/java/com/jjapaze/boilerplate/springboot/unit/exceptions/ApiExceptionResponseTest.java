package com.jjapaze.boilerplate.springboot.unit.exceptions;

import com.jjapaze.boilerplate.springboot.exceptions.ApiExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiExceptionResponseTest {

    private ApiExceptionResponse apiExceptionResponse;

    @BeforeEach
    void setUp() {
        apiExceptionResponse = new ApiExceptionResponse("Error message", HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @Test
    void testGetMessage() {
        assertEquals("Error message", apiExceptionResponse.getMessage());
    }

    @Test
    void testGetStatus() {
        assertEquals(HttpStatus.BAD_REQUEST, apiExceptionResponse.getStatus());
    }

    @Test
    void testGetTime() {
        assertEquals(LocalDateTime.class, apiExceptionResponse.getTime().getClass());
    }

    @Test
    void testSetMessage() {
        apiExceptionResponse.setMessage("New error message");
        assertEquals("New error message", apiExceptionResponse.getMessage());
    }

    @Test
    void testSetStatus() {
        apiExceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, apiExceptionResponse.getStatus());
    }

    @Test
    void testSetTime() {
        LocalDateTime newTime = LocalDateTime.now().minusMinutes(5);
        apiExceptionResponse.setTime(newTime);
        assertEquals(newTime, apiExceptionResponse.getTime());
    }
}
