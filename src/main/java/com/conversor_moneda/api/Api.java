package com.conversor_moneda.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase que maneja la comunicación con la API de tipos de cambio.
 */
public class Api {
    // Clave de la API obtenida de las variables de entorno.
    private static final String API_KEY = System.getenv("EXCHANGE_RATE_API_KEY");
    // URL de la API con la clave incluida.
    private static final String URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    /**
     * Método que envía una solicitud GET a la API de tipos de cambio.
     *
     * @return El cuerpo de la respuesta de la API como una cadena ("String").
     *
     * @throws RuntimeException Si se produce un error de E/S o una interrupción al enviar la solicitud
     *                          o recibir la respuesta.
     */
    public static String getResponse() {
        // Crear una instancia de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Crear una solicitud HTTP GET con la URL de la API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta como una cadena
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al intentar obtener una respuesta de la API: " + e.getMessage(), e);
        }
    }
}
