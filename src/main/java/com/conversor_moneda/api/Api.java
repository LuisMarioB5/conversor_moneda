package com.conversor_moneda.api;

import com.conversor_moneda.error.MyError;
import com.conversor_moneda.file.LastApiRequestTime;
import com.conversor_moneda.file.MyFile;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * Clase que maneja la comunicación con la API de tipos de cambio.
 * Permite enviar solicitudes a la API y gestionar la respuesta obtenida.
 */
public class Api {
    // Clave de la API obtenida de las variables de entorno.
    private static String apiKey = System.getenv("EXCHANGE_RATE_API_KEY");

    // Almacena la respuesta de la API
    private static String response;

    // Almacena el directorio del archivo que contiene la respuesta de la API
    private final static String resourcesPath = "src/main/resources/";
    private static String jsonFilePath;

    /**
     * Determina si se debe realizar una nueva solicitud de respuesta a la API o si se puede utilizar la respuesta existente en el archivo.
     * Compara la hora de la última ejecución almacenada con la hora actual y devuelve true si han pasado al menos un día (24 horas) desde la última ejecución, de lo contrario, devuelve false.
     *
     * @return true si se debe realizar una nueva solicitud de respuesta a la API, false si se puede utilizar la respuesta existente en el archivo.
     */
    private static boolean shouldRequestNewResponse() {
        LocalDateTime lastExecutionTime = LastApiRequestTime.get();
        LocalDateTime now = LocalDateTime.now();
        return ChronoUnit.DAYS.between(lastExecutionTime, now) >= 1;
    }

    /**
     * Envía una solicitud GET a la API de tipos de cambio y guarda la respuesta en la variable estática "response".
     * Si la variable de entorno con la clave de la API no está configurada, solicita al usuario que ingrese la clave manualmente.
     * Si el archivo con la respuesta JSON no existe o si ha pasado más de un día desde la última solicitud, realiza una nueva solicitud a la API.
     * De lo contrario, carga la respuesta almacenada en el archivo.
     *
     * @throws RuntimeException sí se produce un error de E/S o una interrupción al enviar la solicitud o recibir la respuesta.
     */
    public static void setResponse() {
        if (apiKey == null){
            Scanner scanner = new Scanner(System.in);
            MyError.println("No tienes la variable de entorno con la clave de la API bien configurada.");
            MyError.println("Configúrala correctamente o ingrésala a continuación...");
            System.out.print("\nIngresa tu clave de ExchangeRate-API: ");
            apiKey = scanner.next();
        }

        // Comprobar si se debe crear o es necesario realizar una nueva solicitud de respuesta a la API
        if (!(new File(getJsonFilePath()).exists()) || shouldRequestNewResponse()) {
            // URL de la API con la clave incluida.
            final String URL = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";

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
                Api.response = response.body();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Error al intentar obtener una respuesta de la API: " + e.getMessage(), e);
            }

            // Establece la última vez que se realizó una solicitud a la API
            LastApiRequestTime.set(LocalDateTime.now());

            // Guarda la respuesta en un archivo JSON
            saveJsonAsFile();
        } else {
            // En caso de que no se deba realizar otra solicitud, se carga la última respuesta
            loadResponse();
        }
    }

    /**
     * Carga la respuesta almacenada en el archivo JSON en la variable de clase "response".
     * Lee el contenido del archivo JSON ubicado en la ruta especificada, lo convierte en un objeto "JsonObject" utilizando Gson, y luego lo asigna a la variable de clase "response" como una cadena JSON.
     *
     * @throws RuntimeException Sí ocurre algún error durante la lectura del archivo.
     */
    private static void loadResponse() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(getJsonFilePath())) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            response = jsonObject.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la respuesta desde el archivo: " + e.getMessage(), e);
        }
    }

    /**
     * Guarda la respuesta JSON en un archivo en la carpeta de recursos del proyecto.
     * Filtra la respuesta para incluir solo los tipos de cambio ("conversion_rates") y guarda este objeto filtrado en el archivo JSON.
     */
    private static void saveJsonAsFile() {
        setJsonFilePath();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonObject jsonObject = gson.fromJson(new StringReader(response), JsonObject.class);
        JsonObject jsonFilter = jsonObject.getAsJsonObject("conversion_rates");

        MyFile.save(gson.toJson(jsonFilter), MyFile.checkExtension(getJsonFilePath(), ".json"));
    }

    /**
     * Verifica si el código de moneda especificado existe en el archivo JSON.
     *
     * @param currencyCode El código de moneda a verificar.
     * @return true si el código de moneda existe en el archivo JSON, false de lo contrario.
     * @throws RuntimeException si ocurre un error al leer el archivo JSON.
     */
    public static boolean currencyCodeExists(String currencyCode) {
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
            MyError.println("Error: el código \"" + currencyCode + "\" no existe.");
            MyError.println("Vuelve a intentarlo con un código válido...\n");
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Establece la ruta del archivo JSON.
     */
    private static void setJsonFilePath() {
        Api.jsonFilePath = resourcesPath + "conversion_rates.json";
    }

    /**
     * Obtiene la ruta del archivo JSON.
     *
     * @return La ruta del archivo JSON.
     */
    private static String getJsonFilePath() {
        // Si la ruta del archivo JSON es nula, la inicializa
        if (jsonFilePath == null) {
            setJsonFilePath();
        }
        // Devuelve la ruta del archivo JSON actual
        return jsonFilePath;
    }
}
