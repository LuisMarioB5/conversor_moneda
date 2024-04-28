package com.conversor_moneda.currency_converter;

/**
 * La clase Converter proporciona métodos para convertir una cantidad de una moneda a otra.
 */
public class Converter {

    /**
     * Convierte una cantidad de una moneda a otra.
     *
     * @param codeFrom  Código de la moneda (String) de origen.
     * @param codeTo    Código de la moneda (String) de destino.
     * @param amount    La cantidad (en float) a convertir.
     * @return La cantidad convertida en float.
     */
    public static float convert(String codeFrom, String codeTo, float amount) {
        Currency from = new Currency(codeFrom);
        Currency to = new Currency(codeTo);

        // Si la moneda de origen es igual a la moneda de destino, se devuelve la misma cantidad
        if (from.equals(to)) {
            return amount;
        }

        // Se convierte la cantidad a dólares
        float amountInUSD = amount / from.getRateToUSD();
        // Se convierte la cantidad de dólares a la moneda de destino
        return amountInUSD * to.getRateToUSD();
    }
}
