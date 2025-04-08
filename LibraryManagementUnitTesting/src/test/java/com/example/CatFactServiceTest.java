package com.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class CatFactServiceTest {

    // Test class that extends HttpURLConnection for testing
    private static class MockHttpURLConnection extends HttpURLConnection {
        private int responseCode;
        private InputStream inputStream;
        private boolean exceptionOnResponseCode = false;

        public MockHttpURLConnection() {
            super(null);
        }

        public void setResponseCode(int code) {
            this.responseCode = code;
        }

        public void setInputStream(InputStream stream) {
            this.inputStream = stream;
        }

        public void setExceptionOnResponseCode(boolean throwException) {
            this.exceptionOnResponseCode = throwException;
        }

        @Override
        public int getResponseCode() throws IOException {
            if (exceptionOnResponseCode) {
                throw new IOException("Network error");
            }
            return responseCode;
        }

        @Override
        public InputStream getInputStream() {
            return inputStream;
        }

        // Required abstract method implementations
        @Override public void disconnect() {}
        @Override public boolean usingProxy() { return false; }
        @Override public void connect() {}
    }

    @Test
    public void testGetRandomCatFact() throws IOException {
        // Arrange
        MockHttpURLConnection mockConnection = new MockHttpURLConnection();
        
        // Sample response from https://catfact.ninja/fact
        String jsonResponse = "{\"fact\":\"The first true cats came into existence about 12 million years ago and were the Proailurus.\",\"length\":86}";
        
        // Create input stream with our test JSON
        InputStream inputStream = new ByteArrayInputStream(jsonResponse.getBytes());
        
        // Configure mock connection behavior
        mockConnection.setResponseCode(200);
        mockConnection.setInputStream(inputStream);
        
        // Create a testable service that uses our mock
        CatFactService catFactService = new CatFactService() {
            @Override
            protected HttpURLConnection getConnection() throws IOException {
                return mockConnection;
            }
        };
        
        // Act
        String result = catFactService.getRandomCatFact();
        
        // Assert
        assertEquals("The first true cats came into existence about 12 million years ago and were the Proailurus.", result);
    }

    @Test
    public void testGetRandomCatFact_APIError() throws IOException {
        // Arrange - simulate API error
        MockHttpURLConnection mockConnection = new MockHttpURLConnection();
        mockConnection.setResponseCode(500);
        
        CatFactService catFactService = new CatFactService() {
            @Override
            protected HttpURLConnection getConnection() throws IOException {
                return mockConnection;
            }
        };
        
        // Act
        String result = catFactService.getRandomCatFact();
        
        // Assert
        assertTrue(result.contains("Failed to retrieve cat fact"));
        assertTrue(result.contains("500"));
    }

  
}