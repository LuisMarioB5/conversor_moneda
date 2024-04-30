package com.conversor_moneda.logic.currency_converter;

import com.conversor_moneda.logic.historial.Conversion;
import com.conversor_moneda.logic.historial.ConversionHistory;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * La clase Converter proporciona métodos para convertir una cantidad de una moneda a otra.
 */
public class Converter {

    private static final CurrencyList currencyList = new CurrencyList();
    private static final ConversionHistory conversionHistory = new ConversionHistory();

    /**
     * Convierte una cantidad de una moneda a otra.
     *
     * @param codeFrom  Código de la moneda (String) de origen.
     * @param codeTo    Código de la moneda (String) de destino.
     * @param amount    La cantidad (en float) a convertir.
     * @return La cantidad convertida en float.
     */
    public static float convert(String codeFrom, String codeTo, float amount) {
        codeFrom = codeFrom.toUpperCase();
        codeTo = codeTo.toUpperCase();

        Currency from = new Currency(codeFrom);
        currencyList.add(from);

        // Si la moneda de origen es igual a la moneda de destino, se devuelve la misma cantidad
        if (codeFrom.equals(codeTo)) {
            Conversion conversion = new Conversion(from, from, amount, amount, 1, LocalDate.now(), LocalTime.now());
            conversionHistory.add(conversion);
            return amount;
        }

        Currency to = new Currency(codeTo);
        currencyList.add(to);


        // Se convierte la cantidad a dólares
        float amountInUSD = amount / from.getRateToUSD();

        // Se convierte la cantidad de dólares a la moneda de destino
        float amountConverted = amountInUSD * to.getRateToUSD();

        Conversion conversion = new Conversion(from, to, amount, amountConverted, getExchangeRate(from, to), LocalDate.now(), LocalTime.now());
        conversionHistory.add(conversion);
        return amountConverted;
    }

    /**
     * Obtiene la tasa de cambio entre dos monedas.
     *
     * @param from  Moneda de origen.
     * @param to    Moneda de destino.
     * @return La tasa de cambio entre las dos monedas.
     */
    public static float getExchangeRate(Currency from, Currency to) {
        // Se convierte la cantidad a dólares
        float amountInUSD = 1 / from.getRateToUSD();

        // Se convierte la cantidad de dólares a la moneda de destino
        return amountInUSD * to.getRateToUSD();
    }

    public static void main(String[] args) {
        // Ejemplos de uso
        System.out.println(Converter.convert("mxn", "dop", 10));
        System.out.println(Converter.convert("usd", "dop", 10));
        System.out.println(Converter.convert("usd", "usd", 10));
        System.out.println(Converter.convert("dop", "dop", 10));
        System.out.println();

        // Imprimir estado de la lista de monedas y del historial de conversiones
        System.out.println("currencyList:");
        System.out.println(currencyList);

        System.out.println("conversionHistory:");
        System.out.println(conversionHistory);

        System.out.println("Terminado");
    }
}
