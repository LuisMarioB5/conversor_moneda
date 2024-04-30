package com.conversor_moneda.ui.menus;

import com.conversor_moneda.logic.api.Api;
import com.conversor_moneda.logic.currency_converter.Converter;
import com.conversor_moneda.logic.error.MyError;

import java.util.Scanner;

public class Convertir {

    /**
     * Presenta el menú para realizar una conversión de divisa.
     */
    public static void menuConvertir() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();

        // Obtener el código de la divisa de origen
        String codeFrom = getCurrencyCode(scanner, "Ingresa el código de la divisa que deseeas cambiar: ");

        // Obtener el código de la divisa de destino
        String codeTo = getCurrencyCode(scanner, "Ingresa el código de la divisa que deseeas obtener: ");

        // Obtener la cantidad a convertir
        float amount = getAmountToConvert(scanner, "Ingresa la cantidad que deseas convertir de " + codeFrom + " a " + codeTo + ": ");

        // Realizar la conversión
        float conversion = Converter.convert(codeFrom, codeTo, amount);

        // Mostrar los detalles de la conversión
        System.out.println("\nResultado de la conversión:");
        System.out.println("Divisa origen: " + codeFrom);
        System.out.println("Divisa objetivo: " + codeTo);
        System.out.println("Cantidad a convertir: [" + codeFrom + "] $" + amount);
        System.out.println("Cantidad convertida: [" + codeTo + "] $" + conversion);
    }

    /**
     * Solicita al usuario que ingrese un código de divisa y lo valida.
     *
     * @param scanner El scanner para leer la entrada del usuario.
     * @param message El mensaje que indica al usuario qué ingresar.
     * @return El código de la divisa ingresado por el usuario.
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

            Api api = new Api();
            if (api.currencyCodeExits(code)) break;
        }

        return code;
    }

    /**
     * Solicita al usuario que ingrese una cantidad a convertir y la valida.
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
