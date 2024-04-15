package com.conversor_moneda.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Api {
    private static final String API_KEY = System.getenv("EXCHANGE_RATE_API_KEY");
    private static final String URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}
