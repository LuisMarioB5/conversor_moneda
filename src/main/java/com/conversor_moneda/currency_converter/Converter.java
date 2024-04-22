package com.conversor_moneda.currency_converter;

/**
 * La clase Converter proporciona métodos para convertir una cantidad de una moneda a otra.
 */
public class Converter {

    /**
     * Convierte una cantidad de una moneda a otra.
     *
     * @param from   La moneda de origen.
     * @param to     La moneda de destino.
     * @param amount La cantidad a convertir.
     * @return La cantidad convertida.
     */
    public static float convert(Currency from, Currency to, float amount) {
        // Si la moneda de origen es igual a la moneda de destino, devolvemos la misma cantidad
        if (from == to) {
            return amount;
        }

        // Convertimos la cantidad a dólares
        float amountInUSD = amount / from.getRateToUSD();
        // Convertimos la cantidad de dólares a la moneda de destino
        return amountInUSD * to.getRateToUSD();
    }
}
