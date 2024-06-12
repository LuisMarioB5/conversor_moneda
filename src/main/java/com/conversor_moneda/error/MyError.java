package com.conversor_moneda.error;

/**
 * La clase MyError proporciona métodos para imprimir mensajes de error en la consola con formato personalizado.
 * Los mensajes de error se imprimen en rojo para resaltarlos.
 */
public class MyError {
    // Código ANSI para cambiar el color del texto a rojo
    private static final String redColor = "\u001B[31m";

    // Código ANSI para reiniciar el color a su valor por defecto
    private static final String resetColor = "\u001B[0m";

    /**
     * Imprime un mensaje de error en la consola seguido de un salto de línea.
     * El mensaje se muestra en rojo para resaltar el error.
     *
     * @param message El mensaje de error que se imprimirá en la consola.
     */
    public static void println(String message) {
        System.out.println(redColor + message + resetColor);
    }
}
