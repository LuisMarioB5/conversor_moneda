package com.conversor_moneda.file;

import com.conversor_moneda.error.MyError;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Clase para manejar el tiempo de la última solicitud "GET" a la API.
 * Permite cargar y guardar la última hora de solicitud a la API en un archivo de propiedades.
 */
public class LastApiRequestTime {
    // Nombre del archivo de propiedades que almacena la última hora que se realizó un GET request a la API
    private static final String LAST_API_REQUEST_TIME_FILE = "last_api_request_time.properties";
    // Última hora de solicitud
    private static LocalDateTime lastApiRequestTime;
    // Ruta al directorio de recursos donde se guarda el archivo
    private static final String resourcesPath = "src/main/resources/";

    /**
     * Obtiene la última hora de solicitud "GET" a la API.
     * Carga la última hora en que se realizó la solicitud desde el archivo si aún no se ha cargado.
     *
     * @return La última hora de ejecución.
     */
    public static LocalDateTime get() {
        load(); // Cargar la última hora en que se realizó la solicitud a la API si aún no se ha cargado
        return lastApiRequestTime;
    }

    /**
     * Establece la última hora de solicitud "GET" a la API y la guarda en el archivo de propiedades.
     *
     * @param lastApiRequestTime La última hora de ejecución a establecer.
     */
    public static void set(LocalDateTime lastApiRequestTime) {
        LastApiRequestTime.lastApiRequestTime = lastApiRequestTime;
        save(); // Guardar la última hora en que se realizó la solicitud "GET" a la API en el archivo de propiedades
    }

    /**
     * Carga la última hora de solicitud "GET" a la API desde el archivo de propiedades.
     * Si el archivo no existe, crea uno nuevo y establece la hora actual como última hora de solicitud realizada.
     */
    private static void load() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(resourcesPath + LAST_API_REQUEST_TIME_FILE)) {
            properties.load(fis);
            String lastApiRequestTimeStr = properties.getProperty("lastApiRequestTime");
            if (lastApiRequestTimeStr != null) {
                lastApiRequestTime = LocalDateTime.parse(lastApiRequestTimeStr);
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, crearlo y establecer la hora actual como la última hora de ejecución
            lastApiRequestTime = LocalDateTime.now();
            save();
        } catch (IOException e) {
            // Manejar la excepción si ocurre un error de E/S al leer el archivo
            MyError.println(e.getMessage());
        }
    }

    /**
     * Guarda la última hora de solicitud "GET" a la API en el archivo de propiedades.
     */
    private static void save() {
        Properties properties = new Properties();
        properties.setProperty("lastApiRequestTime", lastApiRequestTime.toString());
        try (FileOutputStream fos = new FileOutputStream(resourcesPath + LAST_API_REQUEST_TIME_FILE)) {
            properties.store(fos, "Last API Request time");
        } catch (IOException e) {
            // Manejar la excepción si no se puede guardar la última hora de ejecución
            MyError.println(e.getMessage());
        }
    }
}
