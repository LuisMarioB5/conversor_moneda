package com.conversor_moneda.ui;

import com.conversor_moneda.api.Api;
import com.conversor_moneda.console.Console;

import java.util.Scanner;

/**
 * Clase principal que maneja la interfaz de usuario del conversor de monedas.
 * Proporciona un menú para interactuar con el usuario y realizar diferentes acciones como consultar listas de monedas,
 * convertir monedas, consultar el conversion de conversiones y salir de la aplicación.
 */
public class Main {

    /**
     * Muestra la interfaz de usuario principal del conversor de monedas.
     * Presenta un menú con opciones para consultar la lista de monedas, convertir monedas, consultar el conversion de conversiones
     * y salir de la aplicación. Maneja la entrada del usuario y ejecuta la acción correspondiente.
     */
    public static void showUi() {
        // Mensaje de bienvenida
        String bienvenida = """
               ###########################################################
               
               *** Bienvenido/a al conversor de monedas en tiempo real ***
               
               ###########################################################
               """;

        // Menú inicial
        String menuInicial = """
               
               Selecciona la opción que desees:
               1. Consultar la lista de monedas
               2. Convertir moneda
               3. Consultar conversiones anteriores
               4. Salir de la aplicación
               """;

        short option = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Mostrar el mensaje de bienvenida y el menú inicial
            if (option == 0) {
                System.out.print(bienvenida + menuInicial + "\nOpción: ");
            } else {
                System.out.print(menuInicial + "\nOpción: ");
            }

            // Leer la opción elegida por el usuario
            option = scanner.nextShort();
            scanner.nextLine();

            // Realizar acciones según la opción elegida
            switch (option) {
                case 1:
                    ListarMonedas.show();
                    break;

                case 2:
                    Api.setResponse();
                    Convertir.show();
                    break;

                case 3:
                    Historial.show();
                    break;

                case 4:
                    exitApp(0);
                    return;

                default:
                    System.out.println("\nOpción incorrecta, vuelva a intentar...");
                    Console.pause();
                    break;
            }
        }
    }

    /**
     * Termina la ejecución de la aplicación y sale del programa.
     * Muestra un mensaje indicando que la aplicación está saliendo.
     *
     * @param status El código de estado que indica si la finalización es normal (0) o si hay un error (!= 0).
     */
    public static void exitApp(int status) {
        System.out.println("Saliendo de la aplicación...");
        System.exit(status);
    }

    /**
     * Método principal para iniciar la aplicación.
     * Llama al método {@link #showUi()} para mostrar la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        // Llama al método para mostrar la interfaz de usuario
        showUi();
    }
}
