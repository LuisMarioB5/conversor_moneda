package com.conversor_moneda.ui.menus;

import com.conversor_moneda.logic.console.Console;
import com.conversor_moneda.logic.currency_converter.Converter;
import com.conversor_moneda.logic.error.MyError;
import com.conversor_moneda.logic.historial.Conversion;

import com.conversor_moneda.ui.Main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Historial {

    public static void show() {
        String message = """
                
                ################################################
    
                 Lista de conversiones realizadas anteriormente

                ################################################
                """;

        if (Converter.hasConversionHistory()) {
            System.out.print(message);
            System.out.println(Converter.getConversionHistory());
            showSaveMenu();
        } else {
            MyError.println("No hay conversiones disponibles en el historial.");
            MyError.println("Realiza alguna y vuelve a intertar...\n");
        }
    }

    private static void showSaveMenu() {
        Scanner scanner = new Scanner(System.in);
        String menuInicial = """
               Selecciona la opción que desees:
               1. Guardar el historial en un archivo (txt, json)
               2. Volver al menú anterior
               """;

        while (true) {
            System.out.print(menuInicial + "\nOpción: ");
            short opcion = scanner.nextShort();

            switch (opcion) {
                case 1:
                    saveHistoryToFile();
                    break;

                case 2:
                    System.out.println("\nVolviendo al menú anterior...");
                    return;

                default:
                    MyError.println("\nOpción incorrecta, vuelva a intentar...");
                    Console.pause();
                    break;
            }
        }
    }

    private static void saveHistoryToFile() {
        // Menú inicial
        String menuInicial = """

               Selecciona la opción que desees:
               1. Guardar como un archivo de texto (.txt)
               2. Guardar como un archivo JSON (.json)
               3. Volver al menú anterior
               4. Salir de la aplicación
               """;

        short opcionElegida = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Mostrar el menú inicial
            System.out.print(menuInicial + "\nOpción: ");

            // Leer la opción elegida por el usuario
            opcionElegida = scanner.nextShort();

            // Realizar acciones según la opción elegida
            switch (opcionElegida) {
                case 1:
                    writeToTextFile(Converter.getConversionHistory().getConversionList());
                    showReturnOrExitMenu();
                    break;

                case 2:
                    writeToJsonFile(Converter.getConversionHistory().getConversionList());
                    showReturnOrExitMenu();
                    break;

                case 3:
                    System.out.println("Volviendo al menú anterior...\n");
                    Console.pause();
                    return;

                case 4:
                    Main.exitApp(0);

                default:
                    MyError.println("\nOpción incorrecta, vuelva a intentar...");
                    Console.pause();
                    break;
            }
        }
    }

    private static void writeToTextFile(List<Conversion> conversionHistory) {
        String filePath = "src/main/resources/user_resources/historialConversiones.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int transactionNumber = 1;
            for (Conversion conversion : conversionHistory) {
                writer.write("Transacción " + transactionNumber + ":\n");
                writer.write("Moneda de origen: " + conversion.getOriginCurrency().getCode() + "\n");
                writer.write("Moneda objetivo: " + conversion.getTargetCurrency().getCode() + "\n");
                writer.write("Cantidad a convertir: [" + conversion.getOriginCurrency().getCode() + "] $" + conversion.getAmountToConvert() + "\n");
                writer.write("Cantidad convertida: [" + conversion.getTargetCurrency().getCode() + "] $" + conversion.getAmountConverted() + "\n");
                writer.write("Tasa de conversión: " + conversion.getConversionRate() + "\n");
                writer.write("Fecha de conversión: " + conversion.getConversionDate() + "\n");
                writer.write("Hora de conversión: " + conversion.getConversionTime() + "\n\n");
                transactionNumber++;
            }
            System.out.println("Archivo guardado exitosamente en la ruta: " + filePath);
        } catch (IOException e) {
            MyError.println(e.getMessage());
        }
    }

    // TODO REVISAR PARA QUE FUNCIONE BIEN, SIGUE DANDO ERROR, NO PERMITE ESCRIBIR EL ARCHIVO
    private static void writeToJsonFile(List<Conversion> conversionHistory) {
        String filePath = "src/main/resources/user_resources/historialConversiones.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(conversionHistory, fileWriter);
            System.out.println("Archivo guardado exitosamente en la ruta: " + filePath);
        } catch (IOException e) {
            MyError.println(e.getMessage());
        }
    }

    private static void showReturnOrExitMenu() {
        // Menú inicial
        String menuInicial = """

               Selecciona la opción que desees:
               1. Volver al menú anterior
               2. Salir de la aplicación
               """;

        short opcionElegida = 0;
        Scanner scanner = new Scanner(System.in);

        // Mostrar el menú inicial
        System.out.print(menuInicial + "\nOpción: ");

        // Leer la opción elegida por el usuario
        opcionElegida = scanner.nextShort();

        // Realizar acciones según la opción elegida
        switch (opcionElegida) {
            case 1:
                return;

            case 2:
                Main.exitApp(0);

            default:
                MyError.println("\nOpción incorrecta, vuelva a intentar...");
                Console.pause();
                break;
        }
    }

    public static void main(String[] args) {
        Converter.convert("usd", "dop", 50);
        Converter.convert("usd", "cop", 50);
        Historial.show();
    }
}
