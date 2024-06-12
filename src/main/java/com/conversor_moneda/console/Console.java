package com.conversor_moneda.console;

import java.util.Scanner;

/**
 * La clase Console proporciona métodos para interactuar con la consola.
 * Permite pausar la consola para que el programa espere una entrada del usuario.
 */
public class Console {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Pausa la consola hasta que el usuario presione Enter.
     * Muestra un mensaje indicando al usuario que presione Enter para volver al menú anterior.
     */
    public static void pause() {

        // Muestra un mensaje al usuario
        System.out.print("Presiona Enter para volver al menú anterior...");

        // Lee el siguiente byte de entrada del flujo de entrada estándar
        scanner.nextLine();
    }
}
