package com.conversor_moneda.logic.historial;

import com.conversor_moneda.logic.currency_converter.Currency;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa una conversión de divisa realizada en un momento específico.
 */
public class Conversion {
    private final Currency originCurrency; // Divisa de origen
    private final Currency targetCurrency; // Divisa objetivo
    private final float amountToConvert;   // Cantidad de divisa a convertir
    private final float amountConverted;   // Cantidad de divisa convertida
    private final float conversionRate;    // Tasa de conversión
    private final LocalDate conversionDate;// Fecha de la conversión
    private final LocalTime conversionTime;// Hora de la conversión

    /**
     * Crea una nueva instancia de Conversion con los parámetros especificados.
     *
     * @param originCurrency  Divisa de origen.
     * @param targetCurrency  Divisa objetivo.
     * @param amountToConvert Cantidad de divisa a convertir.
     * @param amountConverted Cantidad de divisa convertida.
     * @param conversionRate  Tasa de conversión.
     * @param conversionDate  Fecha de la conversión.
     * @param conversionTime  Hora de la conversión.
     */
    public Conversion(Currency originCurrency, Currency targetCurrency, float amountToConvert,
                      float amountConverted, float conversionRate, LocalDate conversionDate, LocalTime conversionTime) {
        this.originCurrency = originCurrency;
        this.targetCurrency = targetCurrency;
        this.amountToConvert = amountToConvert;
        this.amountConverted = amountConverted;
        this.conversionRate = conversionRate;
        this.conversionDate = conversionDate;
        this.conversionTime = conversionTime;
    }

    public Currency getOriginCurrency() {
        return originCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public float getAmountToConvert() {
        return amountToConvert;
    }

    public float getAmountConverted() {
        return amountConverted;
    }

    public float getConversionRate() {
        return conversionRate;
    }

    public LocalDate getConversionDate() {
        return conversionDate;
    }

    public LocalTime getConversionTime() {
        return conversionTime;
    }

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
