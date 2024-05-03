package com.conversor_moneda.ui.menus;

import com.conversor_moneda.logic.console.Console;
import com.conversor_moneda.logic.currency_converter.Converter;
import com.conversor_moneda.logic.error.MyError;
import com.conversor_moneda.logic.file.File;
import com.conversor_moneda.ui.Main;

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
               3. Salir de la aplicación
               """;

        while (true) {
            System.out.print(menuInicial + "\nOpción: ");
            short opcion = scanner.nextShort();

            switch (opcion) {
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
                    File.saveAsTextFile(Converter.getConversionHistory().getConversionList(), "src/main/resources/user_resources/historialConversiones.txt");
                    saveAgain(".txt");
                    showReturnOrExitMenu();
                    break;

                case 2:
                    File.saveASJsonFile(Converter.getConversionHistory().getConversionList(), "src/main/resources/user_resources/historialConversiones.json");
                    saveAgain(".json");
                    showReturnOrExitMenu();
                    break;

                case 3:
                    System.out.println("Volviendo al menú anterior...\n");
                    Console.pause();
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

    private static void saveAgain(String extension) {

        if (extension.equals(".txt") || extension.equals(".json")) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Mostrar el menú inicial
                System.out.print("\nDeseas guardar el archivo en un lugar distinto? [S/N]: ");

                // Leer la opción elegida por el usuario
                String opcionElegida = scanner.nextLine().toUpperCase();

                if (opcionElegida.startsWith("S")) {
                    opcionElegida = "S";
                } else if (opcionElegida.startsWith("N")) {
                    opcionElegida = "N";
                }

                // Realizar acciones según la opción elegida
                switch (opcionElegida) {
                    case "S":
                        System.out.println("\nDebes ingresar el directorio o ruta del archivo, como en los ejemplos siguientes:");
                        System.out.printf("Ejemplo 1 (ruta absoluta): C:\\Users\\UserX\\Desktop\\nombreDelArchivo%s\n", extension);
                        System.out.printf("Ejemplo 2 (ruta con relación al proyecto): direccionDelProyecto\\nombreDelArchivo%s\n\n", extension);

                        System.out.print("Ingresa el directorio o ruta del archivo aquí: ");
                        String newFilePath = scanner.nextLine();

                        if (extension.equals(".txt")) {
                            File.saveAsTextFile(Converter.getConversionHistory().getConversionList(), newFilePath);
                        }

                        if (extension.equals(".json")) {
                            File.saveASJsonFile(Converter.getConversionHistory().getConversionList(), newFilePath);
                        }

                        System.out.println();
                        Console.pause();
                        break;

                    case "N":
                        System.out.println("\nMostrando el siguiente menú...");
                        return;

                    default:
                        MyError.println("\nOpción incorrecta, vuelva a intentar...");
                        Console.pause();
                        break;
                }
            }
        } else {
            MyError.println("Error: No se soporta la extexsión \"" + extension + '"');
            MyError.println("Intenta nuevamente con \".txt\" o \".json\"...\n");
            Console.pause();
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
                System.out.println("Volviendo al menú anterior...\n");
                Console.pause();
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
        Converter.convert("dop", "cop", 50);
        Historial.show();

    }
}
