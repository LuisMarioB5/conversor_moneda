package com.conversor_moneda.converter;

import com.conversor_moneda.currency.Currency;
import com.conversor_moneda.currency.CurrencyList;
import com.conversor_moneda.error.MyError;
import com.conversor_moneda.conversion.Conversion;
import com.conversor_moneda.conversion.ConversionHistory;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * La clase Converter proporciona métodos para convertir una cantidad de una moneda ({@link Currency}) a otra.
 */
public class Converter {
    // Instancia de CurrencyList y ConversionHistory
    private static final CurrencyList currencyList = new CurrencyList();
    private static final ConversionHistory conversionHistory = new ConversionHistory();

    /**
     * Convierte una cantidad de una moneda ({@link Currency}) a otra.
     *
     * @param codeFrom  Código de la moneda (String) de origen.
     * @param codeTo    Código de la moneda (String) de destino.
     * @param amount    La cantidad (en float) a convertir.
     * @return La cantidad convertida en float.
     */
    public static float convert(String codeFrom, String codeTo, float amount) {
        // Código convertido a mayúsculas
        codeFrom = codeFrom.toUpperCase();
        codeTo = codeTo.toUpperCase();

        // Creación de instancias Currency y adición a la lista de monedas
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

        /// Conversión a dólares y luego a la moneda de destino
        float amountInUSD = amount / from.getRateToUSD();
        float amountConverted = amountInUSD * to.getRateToUSD();

        // Registro de la conversión en el conversion
        Conversion conversion = new Conversion(from, to, amount, amountConverted, getExchangeRate(from, to), LocalDate.now(), LocalTime.now());
        conversionHistory.add(conversion);
        return amountConverted;
    }

    /**
     * Obtiene la tasa de cambio entre dos monedas ({@link Currency}).
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

    /**
     * Obtiene el conversion de conversiones ({@link ConversionHistory}).
     *
     * @return El conversion de conversiones.
     */
    public static ConversionHistory getConversionHistory() {
        if (conversionHistory.isEmpty()) {
            MyError.println("Error: No se ha realizado ninguna conversion, realice al menos una y vuelva a intentar...\n");
        }

        return conversionHistory;
    }

    /**
     * Verifica si hay conversiones en el conversion.
     *
     * @return true si hay conversiones en el conversion, false si está vacío.
     */
    public static boolean hasConversionHistory() {
        return !conversionHistory.isEmpty();
    }
}
