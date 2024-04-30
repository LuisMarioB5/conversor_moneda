package com.conversor_moneda.logic.console;

import com.conversor_moneda.logic.error.MyError;

import java.io.IOException;

/**
 * La clase Console proporciona métodos para interactuar con la consola.
 * Permite pausar la consola para que el programa espere una entrada del usuario.
 */
public class Console {

    /**
     * Pausa la consola hasta que el usuario presione Enter.
     * Muestra un mensaje indicando al usuario que presione Enter para continuar.
     * Captura y maneja cualquier excepción de entrada/salida (IOException) que pueda ocurrir durante la espera.
     */
    public static void pause() {
        System.out.print("Presiona Enter para continuar...");
        try {
            int input = System.in.read();
        } catch (IOException e) {
            MyError.println(e.getMessage());
        }
    }
}
