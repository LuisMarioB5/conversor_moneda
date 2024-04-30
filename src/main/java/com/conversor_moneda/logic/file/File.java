package com.conversor_moneda.logic.file;

import java.io.FileWriter;
import java.io.IOException;

public class File {

    /**
     * Función que guarda un texto en un archivo.
     *
     * @param content  El String que se desea guardar.
     * @param filePath El String que contiene el directorio del nuevo archivo.
     *                 Ejemplo 1: C:\Users\UserX\Desktop\nombreDelArchivo.extension
     *                 Ejemplo 2: direccionDelProyecto\nombreDelArchivo.extension
     * @throws RuntimeException Si no se logra crear el archivo.
     */
    public static void save(String content, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Escribir el String en el archivo
            writer.write(content);
            System.out.println("El archivo ha sido guardado en el directorio: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e);
        }
    }
}
