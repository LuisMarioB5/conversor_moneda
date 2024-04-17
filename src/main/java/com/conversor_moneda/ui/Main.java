package com.conversor_moneda.ui;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola, no he empezado aquí...");

        Scanner scanner = new Scanner(System.in);
        String bienvenida = """
                ###############################################################

                ***** Bienvenido/a al conversor de divisas en tiempo real *****
                
                ###############################################################
                """;
        String menuInicial = """
                
                Selecciona la opción que desees:
                1. Convertir divisas
                2. Consultar conversiones anteriores
                3. Salir
                
                """;
        short opcionElegida = 0;

        while (opcionElegida != 3){
            if (opcionElegida == 0) {
                System.out.print(bienvenida + menuInicial + "Opción: ");
            } else {
                System.out.print(menuInicial + "Opción: ");
            }
            opcionElegida = scanner.nextShort();

            switch (opcionElegida) {
                case 1:
                    System.out.println("Mostrando el menú para convertir divisas....");
                    scanner.next(); // Detiene el programa hasta que el usuario ingrese un caracter y de a enter.
                    break;

                case 2:
                    System.out.println("Mostrando el menú para consultar las conversiones anteriores....");
                    scanner.next(); // Detiene el programa hasta que el usuario ingrese un caracter y de a enter.
                    break;

                case 3:
                    System.out.println("Saliendo del conversor de divisas...");
                    break;

                default:
                    System.out.println("Opción incorrecta, vuelva a intentar...");
                    scanner.next(); // Detiene el programa hasta que el usuario ingrese un caracter y de a enter.
                    break;
            }
        }
    }
}
