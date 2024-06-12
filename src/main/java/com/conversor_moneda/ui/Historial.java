package com.conversor_moneda.ui;

import com.conversor_moneda.console.Console;
import com.conversor_moneda.converter.Converter;
import com.conversor_moneda.error.MyError;
import com.conversor_moneda.file.MyFile;

import java.util.Scanner;

/**
 * Clase que proporciona un menú para mostrar y guardar la lista de conversiones.
 */
public class Historial {

    /**
     * Muestra la lista de conversiones y ofrece opciones para guardar las conversiones realizadas o volver al menú anterior.
     * Si no hay conversiones en la lista, muestra un mensaje de error.
     */
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
            MyError.println("No haz realizado ninguna conversión.");
            MyError.println("Realiza alguna y vuelve a intentar...");
        }
    }

    /**
     * Muestra un menú para guardar la lista de conversiones en un archivo, volver al menú anterior o salir de la aplicación.
     * Se repite hasta que el usuario elija volver o salir.
     */
    private static void showSaveMenu() {
        Scanner scanner = new Scanner(System.in);
        String menuInicial = """
               Selecciona la opción que desees:
               1. Guardar las conversiones en un archivo (txt, json)
               2. Volver al menú anterior
               3. Salir de la aplicación
               """;

        while (true) {
            System.out.print(menuInicial + "\nOpción: ");
            short option = scanner.nextShort();
            scanner.nextLine();

            switch (option) {
                case 1:
                    saveHistoryToFile();
                    break;

                case 2:
                    System.out.println("Volviendo al menú anterior...\n");
                    Console.pause();
                    return;

                case 3:
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
     * Muestra un menú para elegir el formato en el que se desea guardar la lista de conversiones (texto o JSON).
     * También permite volver al menú anterior o salir de la aplicación.
     */
    private static void saveHistoryToFile() {
        String menuInicial = """
               Selecciona la opción que desees:
               1. Guardar como un archivo de texto (.txt)
               2. Guardar como un archivo JSON (.json)
               3. Volver al menú anterior
               4. Salir de la aplicación
               """;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(menuInicial + "\nOpción: ");
            short selectedOption = scanner.nextShort();
            scanner.nextLine();

            switch (selectedOption) {
                case 1:
                    System.out.print("\nEscribe el nombre del archivo: ");
                    String textFileName = scanner.nextLine();
                    MyFile.saveAsTextFile(Converter.getConversionHistory().getConversionList(), MyFile.checkExtension("src/main/resources/user/" + textFileName, ".txt"));
                    saveAgain(".txt");
                    break;

                case 2:
                    System.out.print("\nEscribe el nombre del archivo: ");
                    String jsonFileName = scanner.nextLine();
                    MyFile.saveAsJsonFile(Converter.getConversionHistory().getConversionList(), MyFile.checkExtension("src/main/resources/user/" + jsonFileName, ".json"));
                    saveAgain(".json");
                    break;

                case 3:
                    System.out.println("Volviendo al menú anterior...\n");
                    Console.pause();
                    System.out.println();
                    return;

                case 4:
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
     * Pregunta al usuario si desea guardar el archivo en un lugar distinto.
     * Si el usuario elige "Sí", se le solicita la nueva ruta para guardar el archivo.
     *
     * @param extension La extensión del archivo a guardar (.txt o .json).
     */
    private static void saveAgain(String extension) {
        if (extension.equals(".txt") || extension.equals(".json")) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("\nDeseas guardar el archivo en un lugar distinto? [S/N]: ");
                String selectedOption = scanner.nextLine().toUpperCase();

                if (selectedOption.startsWith("S")) {
                    selectedOption = "S";
                } else if (selectedOption.startsWith("N")) {
                    selectedOption = "N";
                }

                switch (selectedOption) {
                    case "S":
                        System.out.println("\nDebes ingresar el directorio o ruta del archivo, como en los ejemplos siguientes:");
                        System.out.println("Ejemplo 1 (ruta absoluta en Windows): C:\\Users\\UserX\\Desktop\\nombreDelArchivo");
                        System.out.println("Ejemplo 2 (ruta con relación al proyecto): ruta/del/archivo/con/relación/al/proyecto/nombreDelArchivo\n");

                        System.out.print("Ingresa el directorio o ruta del archivo aquí: ");
                        String newFilePath = scanner.nextLine();

                        if (extension.equals(".txt")) {
                            MyFile.saveAsTextFile(Converter.getConversionHistory().getConversionList(), newFilePath);
                        } else {
                            MyFile.saveAsJsonFile(Converter.getConversionHistory().getConversionList(), newFilePath);
                        }

                        System.out.println();
                        Console.pause();
                        break;

                    case "N":
                        System.out.println("\nVolviendo al menú anterior...\n");
                        return;

                    default:
                        MyError.println("\nOpción incorrecta, vuelva a intentar...");
                        Console.pause();
                        break;
                }
            }
        } else {
            MyError.println("Error: No se soporta la extensión \"" + extension + '"');
            MyError.println("Intenta nuevamente con \".txt\" o \".json\"...\n");
            Console.pause();
        }
    }
}
