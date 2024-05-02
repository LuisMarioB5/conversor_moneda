package com.conversor_moneda.logic.console;

import com.conversor_moneda.logic.error.MyError;
import com.conversor_moneda.ui.Main;

import java.io.IOException;
import java.util.Scanner;

/**
 * La clase Console proporciona métodos para interactuar con la consola.
 * Permite pausar la consola para que el programa espere una entrada del usuario.
 */
public class Console {

    /**
     * Pausa la consola hasta que el usuario presione Enter.
     * Muestra un mensaje indicando al usuario que presione Enter para volver al menú anterior.
     * Captura y maneja cualquier excepción de entrada/salida (IOException) que pueda ocurrir durante la espera.
     */
    public static void pause() {
        System.out.print("Presiona Enter para volver al menú anterior...");

        try {
            // Lee el siguiente byte de entrada del flujo de entrada estándar (System.in)
            int input = System.in.read();
        } catch (IOException e) {
            // Captura y maneja cualquier excepción de entrada/salida (IOException) que pueda ocurrir durante la espera
            MyError.println(e.getMessage());
        }
    }


}
