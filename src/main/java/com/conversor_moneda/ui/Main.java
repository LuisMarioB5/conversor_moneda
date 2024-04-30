package com.conversor_moneda.ui;

import com.conversor_moneda.logic.console.Console;
import com.conversor_moneda.ui.menus.Convertir;
import com.conversor_moneda.ui.menus.ListasDivisas;

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
               4. Salir del programa
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
                    Console.pause();
                    break;

                case 2:
                    Convertir.menuConvertir();
                    Console.pause();
                    break;

                case 3:
                    System.out.println("Consultando las conversiones anteriores...");
                    Console.pause();
                    break;

                case 4:
                    System.out.println("\nSaliendo del conversor...");
                    return;

                default:
                    System.out.println("\nOpción incorrecta, vuelva a intentar...");
                    Console.pause();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        // Llama al método para mostrar la interfaz de usuario
        showUi();
    }
}
