package com.conversor_moneda.logic.error;

/**
 * La clase MyError proporciona métodos para imprimir mensajes de error en la consola con formato personalizado.
 * Los mensajes de error se imprimen en rojo para resaltarlos.
 */
public class MyError {

    /**
     * Imprime un mensaje de error en la consola seguido de un salto de línea.
     * El mensaje se muestra en rojo para resaltar el error.
     *
     * @param message El mensaje de error que se imprimirá en la consola.
     */
    public static void println(String message) {
        // Código ANSI para cambiar el color del texto a rojo
        String redColor = "\u001B[31m";

        // Código ANSI para reiniciar el color a su valor por defecto
        String resetColor = "\u001B[0m";

        System.out.println(redColor + message + resetColor);
    }

    /**
     * Imprime un mensaje de error en la consola.
     * El mensaje se muestra en rojo para resaltar el error.
     *
     * @param message El mensaje de error que se imprimirá en la consola.
     */
    public static void print(String message) {
        // Código ANSI para cambiar el color del texto a rojo
        String redColor = "\u001B[31m";

        // Código ANSI para reiniciar el color a su valor por defecto
        String resetColor = "\u001B[0m";

        System.out.print(redColor + message + resetColor);
    }
}
