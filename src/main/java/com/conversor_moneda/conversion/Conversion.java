package com.conversor_moneda.conversion;

import com.conversor_moneda.currency.Currency;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa una conversión de moneda realizada en un momento específico.
 *
 * @param originCurrency  Moneda de origen.
 * @param targetCurrency  Moneda objetivo.
 * @param amountToConvert Cantidad de moneda a convertir.
 * @param amountConverted Cantidad de moneda convertida.
 * @param conversionRate  Tasa de conversión.
 * @param conversionDate  Fecha de la conversión.
 * @param conversionTime  Hora de la conversión.
 */
public record Conversion(@SerializedName("Moneda de origen") Currency originCurrency,
                         @SerializedName("Moneda objetivo") Currency targetCurrency,
                         @SerializedName("Cantidad a convertir") float amountToConvert,
                         @SerializedName("Cantidad convertida") float amountConverted,
                         @SerializedName("Tasa de conversion") float conversionRate,
                         @SerializedName("Fecha de conversion") LocalDate conversionDate,
                         @SerializedName("Hora de conversion") LocalTime conversionTime) {

    /**
     * Devuelve una representación de cadena de esta conversión.
     *
     * @return Representación de cadena de la conversión.
     */
    @Override
    public String toString() {
        return "Moneda de origen: " + originCurrency.getCode() +
                "\nMoneda objetivo: " + targetCurrency.getCode() +
                "\nCantidad a convertir: [" + originCurrency.getCode() + "] $" + amountToConvert +
                "\nCantidad convertida: [" + targetCurrency.getCode() + "] $" + amountConverted +
                "\nTasa de conversion: " + conversionRate +
                "\nFecha de conversion: " + conversionDate +
                "\nHora de conversion: " + conversionTime;
    }
}
