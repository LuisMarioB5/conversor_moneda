package com.conversor_moneda.ui;

import com.conversor_moneda.api.Api;
import com.conversor_moneda.currency_converter.Converter;
import com.conversor_moneda.currency_converter.Currency;

import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void showUi() {
        String bienvenida = """
               ###########################################################
               
               *** Bienvenido/a al conversor de divisas en tiempo real ***
               
               ###########################################################
               """;


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

            if (opcionElegida == 0) {
                System.out.print(bienvenida + menuInicial + "\nOpción: ");
            } else {
                System.out.print(menuInicial + "\nOpción: ");

            }
            opcionElegida = scanner.nextShort();

            switch (opcionElegida) {
                case 1:
                    ListasDivisas.show();
                    pauseConsole();
                    break;

                case 2:
                    menuConvertir();
                    pauseConsole();
                    break;

                case 3:
                    System.out.println("Consultando las conversiones anteriores...");
                    pauseConsole();
                    break;

                case 4:
                    System.out.println("\nSaliendo del conversor...");
                    return;

                default:
                    System.out.println("\nOpción incorrecta, vuelva a intentar...");
                    pauseConsole();
                    break;
            }
        }
    }

    public static void menuConvertir() {
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        String codeFrom = getCurrencyCode(scanner, "Ingresa el código de la divisa que deseeas cambiar: ");
        if (codeFrom == null) return;

        String codeTo = getCurrencyCode(scanner, "Ingresa el código de la divisa que deseeas obtener: ");
        if (codeTo == null) return;

        System.out.print("Ingresa la cantidad que deseas convertir de " + codeFrom + " a " + codeTo + ": ");
        float amount = scanner.nextFloat();

        scanner.close();

        //TODO COMENZAR EL PROCESO DE CONVERSION
        float conversion = Converter.convert(codeFrom, codeTo, amount);

        //PRUEBA
        System.out.println("\nDATOS DE LA PRUEBA:\ncodeFrom: " + codeFrom + "\ncodeTo: " + codeTo + "\namount (" + codeFrom + "): " + amount + "\nValor convertido: " + conversion);
    }

    public static String getCurrencyCode(Scanner scanner, String message) {
        System.out.print(message);
        String code = scanner.nextLine();
        code = code.toUpperCase();

        if (code.length() != 3) {
            System.err.println("Error: Los códigos de las monedas contienen exactamente tres caracteres.");
            System.out.println("Vuelve a intentarlo...");
            return null;
        }

        //TODO AQUI DEBE COLOCARSE LA VEFICACION DE QUE EL CODIGO DE LA MONEDA EXISTA
        Api api = new Api();
        api.currencyCodeExits(code);

        return code;
    }







    // Método para pausar la consola
    public static void pauseConsole() {
        System.out.print("Presiona Enter para continuar...");
        try {
            int input = System.in.read();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Hola, no he empezado aquí...");
        pauseConsole();
//        showUi();
        menuConvertir();
    }
}
