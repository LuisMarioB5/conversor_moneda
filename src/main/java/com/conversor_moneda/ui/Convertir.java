package com.conversor_moneda.ui;

import com.conversor_moneda.api.Api;
import com.conversor_moneda.console.Console;
import com.conversor_moneda.converter.Converter;
import com.conversor_moneda.error.MyError;

import java.util.Scanner;

/**
 * Clase que proporciona un menú para realizar conversiones de monedas.
 */
public class Convertir {

    /**
     * Presenta el menú para realizar una conversión de moneda.
     * Solicita al usuario los códigos de las monedas de origen y destino, así como la cantidad a convertir.
     * Realiza la conversión y muestra el resultado.
     */
    public static void show() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();

        // Obtener el código de la moneda de origen
        String codeFrom = getCurrencyCode(scanner, "Ingresa el código de la moneda que deseas cambiar: ");

        // Obtener el código de la moneda de destino
        String codeTo = getCurrencyCode(scanner, "Ingresa el código de la moneda que deseas obtener: ");

        // Obtener la cantidad a convertir
        float amount = getAmountToConvert(scanner, "Ingresa la cantidad que deseas convertir: [" + codeFrom + "] $");

        // Realizar la conversión
        float conversion = Converter.convert(codeFrom, codeTo, amount);

        // Mostrar el valor convertido
        System.out.println("Cantidad convertida: [" + codeTo + "] $" + conversion + '\n');
        Console.pause();
    }

    /**
     * Solicita al usuario que ingrese un código de moneda y lo válida.
     *
     * @param scanner El scanner para leer la entrada del usuario.
     * @param message El mensaje que indica al usuario qué ingresar.
     * @return El código de la moneda ingresado por el usuario.
     */
    private static String getCurrencyCode(Scanner scanner, String message) {
        String code;

        while (true) {
            System.out.print(message);
            code = scanner.nextLine().toUpperCase();

            if (code.length() != 3) {
                MyError.println("Error: Los códigos de las monedas contienen exactamente tres caracteres.");
                MyError.println("Vuelve a intentarlo con un código válido...\n");
                continue;
            }

            if (Api.currencyCodeExists(code)) break;
        }

        return code;
    }

    /**
     * Solicita al usuario que ingrese una cantidad a convertir y la válida.
     *
     * @param scanner El scanner para leer la entrada del usuario.
     * @param message El mensaje que indica al usuario qué ingresar.
     * @return La cantidad ingresada por el usuario.
     */
    private static float getAmountToConvert(Scanner scanner, String message) {
        float amount;

        while (true) {
            System.out.print(message);

            // Verifica si el próximo token del scanner es un float
            if (scanner.hasNextFloat()) {
                amount = scanner.nextFloat();
                scanner.nextLine();
                break;
            } else {
                // Si no es un float, muestra un mensaje de error y consume el token no válido
                MyError.println("Error: Ingresa un valor numérico válido.\n");
                scanner.next();
            }
        }

        return amount;
    }
}
