package com.conversor_moneda.api;

import com.conversor_moneda.logic.file.File;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase que maneja la comunicación con la API de tipos de cambio.
 */
public class Api {
    // Clave de la API obtenida de las variables de entorno.
    private final String API_KEY = System.getenv("EXCHANGE_RATE_API_KEY");

    // URL de la API con la clave incluida.
    private final String URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    // Almacena la respuesta de la API
    private String response;

    // Almacena el directorio del archivo que contine la respuesta de la API
    private final String jsonFilePath = "src/main/resources/conversion_rates.json";

    /**
     * Método que envía una solicitud GET a la API de tipos de cambio, y guarda la respuesta en la variable response.
     *
     * @throws RuntimeException Si se produce un error de E/S o una interrupción al enviar la solicitud
     *                          o recibir la respuesta.
     */
    public void setResponse() {
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
            this.response = response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al intentar obtener una respuesta de la API: " + e.getMessage(), e);
        }
    }

    /**
     * Método que devuelve el valor de la variable response.
     *
     * @return La respuesta de la API
     */
    public String getResponse() {
        // Si la variable response no se ha inicializado se inicializa aquí
        if (this.response == null) {
            this.setResponse();
        }

        return response;
    }

    /**
     * Método que guarda el JSON en un archivo nombrado "conversion_rates" en la carpeta de resources del proyecto.
     */
    public void saveJsonAsFile() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonObject jsonObject = gson.fromJson(new StringReader(this.getResponse()), JsonObject.class);
        JsonObject jsonFilter = jsonObject.getAsJsonObject("conversion_rates");

        File.save(gson.toJson(jsonFilter), getJsonFilePath());
    }

    //TODO
    // APLICAR ESTE METODO EN LA PARTE DE PREGUNTAR EL CODIGO DE LA MONEDA AL USUARIO,
    // PARA ASEGURARSE QUE EL CODIGO QUE INTRODUJO EL USUAIRO EXISTE.


    public boolean currencyCodeExits(String currencyCode) {
        currencyCode = currencyCode.toUpperCase();

        try {
            FileReader reader = new FileReader(getJsonFilePath());

            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (String key : jsonObject.keySet()) {
                if (key.equals(currencyCode)){
                    return true;
                }
            }

            reader.close();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getJsonFilePath() {
        return jsonFilePath;
    }

    /**
     * Método principal para probar la clase Api.
     *
     * @param args Los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
//        try {
//            Api api = new Api();
//            api.saveJsonAsFile();
//        } catch (IllegalStateException e) {
//            System.err.println(e.getMessage());
//        }
//        System.out.println("El programa terminó sin detenerse...");

        Api api = new Api();
        System.out.println(api.currencyCodeExits("usKd"));
    }
}
