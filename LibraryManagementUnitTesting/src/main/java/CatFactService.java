package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CatFactService {
    private static final String API_URL = "https://catfact.ninja/fact";
    
    public String getRandomCatFact() {
        try {
            HttpURLConnection connection = getConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                // Extract fact from JSON response
                String json = response.toString();
                int factStart = json.indexOf("\"fact\":\"") + 8;
                int factEnd = json.indexOf("\"", factStart);
                return json.substring(factStart, factEnd);
            } else {
                return "Failed to retrieve cat fact. Response code: " + responseCode;
            }
        } catch (IOException e) {
            return "Error retrieving cat fact: " + e.getMessage();
        }
    }
    
    // Protected method that can be overridden in tests
    protected HttpURLConnection getConnection() throws IOException {
        URL url = new URL(API_URL);
        return (HttpURLConnection) url.openConnection();
    }
}