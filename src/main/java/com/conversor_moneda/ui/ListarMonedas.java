package com.conversor_moneda.ui;

import com.conversor_moneda.console.Console;
import com.conversor_moneda.error.MyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Clase que proporciona menús para consultar diferentes listas de monedas.
 */
public class ListarMonedas {

    /**
     * Muestra el menú principal para consultar las listas de monedas.
     * Permite al usuario seleccionar entre diferentes opciones para consultar las listas de monedas no disponibles,
     * con alta volatilidad, disponibles, volver al menú anterior o salir de la aplicación.
     */
    public static void show() {
        String menuInicial = """
              
              Selecciona la opción que desees:
              1. Consultar monedas no disponibles
              2. Consultar monedas con alta volatilidad
              3. Consultar monedas disponibles
              4. Volver al menú anterior
              5. Salir de la aplicación
              """;
        short option;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(menuInicial + "\nOpción: ");
            option = scanner.nextShort();
            scanner.nextLine();

            switch (option) {
                case 1:
                    showNotSupportedCurrenciesList();
                    Console.pause();
                    break;

                case 2:
                    showHighVolatilityCurrenciesList();
                    Console.pause();
                    break;

                case 3:
                    showSupportedCurrenciesList();
                    Console.pause();
                    break;

                case 4:
                    System.out.println("Volviendo al menú anterior...\n");
                    Console.pause();
                    return;

                case 5:
                    Main.exitApp(0);
                    return;

                default:
                    MyError.println("\nOpción incorrecta, vuelva a intentar...");
                    Console.pause();
                    break;
            }
        }
    }

    /**
     * Muestra la lista de monedas no soportadas.
     * Lee los datos del archivo JSON correspondiente y los imprime en formato de tabla.
     */
    public static void showNotSupportedCurrenciesList() {
        short maxSpace = 45;
        String hashtag = "#";
        String emptySpace = " ";
        String notSupportedTitle = "Moneda que no se ofrecen tasa de cambio";
        String notSupportedTitleSpaces = emptySpace.repeat((maxSpace - notSupportedTitle.length()) / 2);

        System.out.println();
        System.out.println(hashtag.repeat(maxSpace));
        System.out.println();
        System.out.println(notSupportedTitleSpaces + notSupportedTitle + notSupportedTitleSpaces);
        System.out.println();
        System.out.println(hashtag.repeat(maxSpace));
        System.out.println();

        System.out.println("""
               La app solo tiene una moneda no disponible,
                 debido a sanciones y falta de comercio
                             internacional.
              """);

        printCurrencyTable("not_supported_currencies.json");
        System.out.println();
    }

    /**
     * Muestra la lista de monedas con alta volatilidad.
     * Lee los datos del archivo JSON correspondiente y los imprime en formato de tabla.
     */
    public static void showHighVolatilityCurrenciesList() {
        short maxSpace = 56;
        String hashtag = "#";
        String emptySpace = " ";
        String notSupportedTitle = "Lista de moneda con alta volatilidad para convertir";
        String notSupportedTitleSpaces = emptySpace.repeat((maxSpace - notSupportedTitle.length()) / 2);

        System.out.println();
        System.out.println(hashtag.repeat(maxSpace));
        System.out.println();
        System.out.println(notSupportedTitleSpaces + notSupportedTitle + notSupportedTitleSpaces);
        System.out.println();
        System.out.println(hashtag.repeat(maxSpace));
        System.out.println();

        System.out.println("""
             Las siguientes monedas experimentan una mayor
             volatilidad y diferencias sustanciales entre los tipos
             de cambio reales disponibles en diferentes mercados y
             los publicados oficialmente. En estos casos utilizamos
             por defecto los tipos publicados por los bancos
             centrales correspondientes. Tenga especial cuidado al
             utilizar nuestros datos para estas monedas, ya que
             existe la posibilidad de que no coincidan con otras
             fuentes.
             """);

        printCurrencyTable("high_volatility_currencies.json");
        System.out.println();
    }

    /**
     * Muestra la lista de monedas soportadas.
     * Lee los datos del archivo JSON correspondiente y los imprime en formato de tabla.
     */
    public static void showSupportedCurrenciesList() {
        short maxSpace = 95;
        String hashtag = "#";
        String emptySpace = " ";
        String notSupportedTitle = "Lista de moneda disponibles para convertir";
        String notSupportedTitleSpaces = emptySpace.repeat((maxSpace - notSupportedTitle.length()) / 2);

        System.out.println();
        System.out.println(hashtag.repeat(maxSpace));
        System.out.println();
        System.out.println(notSupportedTitleSpaces + notSupportedTitle + notSupportedTitleSpaces);
        System.out.println();
        System.out.println(hashtag.repeat(maxSpace));
        System.out.println();

        System.out.println("""
              La app tiene disponible para convertir las 161 monedas mundiales que circulan habitualmente y
                 que se enumeran a continuación. Estas cubren el 99% de todos los estados y territorios
                                               reconocidos por la ONU.
              """);

        printCurrencyTable("supported_currencies.json");
        System.out.println();
    }

    /**
     * Imprime una tabla de monedas a partir de un archivo JSON.
     *
     * @param fileName El nombre del archivo JSON que contiene los datos de las monedas.
     */
    public static void printCurrencyTable(String fileName) {
        fileName = fileName.toLowerCase();
        String filePath = "";
        String resourcesPath = "src/main/resources/";
        String extension = fileName.endsWith(".json") ? "" : ".json";
        String fileNameWithExtension = filePath + fileName + extension;

        if (!fileExists(resourcesPath, fileNameWithExtension)) {
            System.err.println("El archivo debe ser un JSON (.json)");
            return;
        }

        if (fileName.contains("supported_currencies") || fileName.contains("not_supported_currencies") || fileName.contains("high_volatility_currencies")) {
            filePath = resourcesPath + fileNameWithExtension;
        } else {
            System.err.println("El archivo JSON (.json) no es apto para mostrar.");
            return;
        }

        try {
            // Lee el archivo JSON
            JsonObject jsonObject = new Gson().fromJson(new FileReader(filePath, StandardCharsets.UTF_8), JsonObject.class);

            // Imprime la tabla de monedas
            // Definir las longitudes máximas
            String Code = "Código";
            String Name = "Nombre";
            String Country = "País";
            int maxCodeLength = Code.length();
            int maxNameLength = Name.length();
            int maxCountryLength = Country.length();

            for (String key : jsonObject.keySet()) {
                JsonObject currency = jsonObject.getAsJsonObject(key);
                String code = currency.get("code").getAsString();
                String name = currency.get("name").getAsString();
                String country = currency.get("country").getAsString();

                // Determinar la longitud máxima para cada columna
                maxCodeLength = Math.max(maxCodeLength, code.length());
                maxNameLength = Math.max(maxNameLength, name.length());
                maxCountryLength = Math.max(maxCountryLength, country.length());
            }

            String hyphen = "-";
            String empty = " ";
            String line = '+' + hyphen.repeat(maxCodeLength + 2) + '+' + hyphen.repeat(maxNameLength + 2) + '+' + hyphen.repeat(maxCountryLength + 2) + '+';

            // Imprimir la tabla de monedas
            System.out.println(line);
            System.out.println("| " + Code + " | " + Name + empty.repeat(maxNameLength - Name.length()) + " | " + Country + empty.repeat(maxCountryLength - Country.length()) + " |");
            System.out.println(line);

            for (String key : jsonObject.keySet()) {
                JsonObject currency = jsonObject.getAsJsonObject(key);
                String code = currency.get("code").getAsString();
                String name = currency.get("name").getAsString();
                String country = currency.get("country").getAsString();

                // Ajustar el formato de impresión según las longitudes máximas
                System.out.printf("|  %-" + maxCodeLength + "s| %-" + maxNameLength + "s | %-" + maxCountryLength + "s |\n", code, name, country);
            }

            System.out.println(line);

        } catch (IOException e) {
            throw new RuntimeException("Error de entrada o salida: " + e);
        }
    }

    /**
     * Verifica si un archivo existe en el directorio especificado.
     *
     * @param filePath La ruta del directorio.
     * @param fileName El nombre del archivo.
     * @return true si el archivo existe, false de lo contrario.
     */
    private static boolean fileExists(String filePath, String fileName) {
        String completeFilePath = filePath + fileName;
        File file = new File(completeFilePath);
        return file.exists();
    }
}
