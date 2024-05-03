package com.conversor_moneda.ui;

import com.conversor_moneda.logic.console.Console;
import com.conversor_moneda.ui.menus.Historial;
import com.conversor_moneda.ui.menus.ListasDivisas;
import com.conversor_moneda.ui.menus.MenuConvertir;

import java.util.Scanner;

public class Main {

    /**
     * Muestra la interfaz de usuario principal del conversor de divisas.
     */
    public static void showUi() {
        // Mensaje de bienvenida
        String bienvenida = """
               ###########################################################
               
               *** Bienvenido/a al conversor de divisas en tiempo real ***
               
               ###########################################################
               """;

        // Menú inicial
        String menuInicial = """
               
               Selecciona la opción que desees:
               1. Consultar la lista de divisas
               2. Convertir divisa
               3. Consultar conversiones anteriores
               4. Salir de la aplicación
               """;

        short opcionElegida = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Mostrar el mensaje de bienvenida y el menú inicial
            if (opcionElegida == 0) {
                System.out.print(bienvenida + menuInicial + "\nOpción: ");
            } else {
                System.out.print(menuInicial + "\nOpción: ");
            }

            // Leer la opción elegida por el usuario
            opcionElegida = scanner.nextShort();

            // Realizar acciones según la opción elegida
            switch (opcionElegida) {
                case 1:
                    ListasDivisas.show();
                    break;

                case 2:
                    MenuConvertir.show();
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
     * @param status El código de estado que indica si la finalización es normal (0) o si hay un error (!= 0).
     */
    public static void exitApp(int status) {
        System.out.println("Saliendo de la aplicación...");
        System.exit(status);
    }

    public static void main(String[] args) {
        // Llama al método para mostrar la interfaz de usuario
        showUi();
    }
}
