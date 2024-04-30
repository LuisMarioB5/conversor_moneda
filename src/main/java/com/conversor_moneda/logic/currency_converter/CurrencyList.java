package com.conversor_moneda.logic.currency_converter;

import java.util.LinkedList;
import java.util.List;

/**
 * Representa una lista de divisas únicas.
 */
public class CurrencyList {
    // Lista que almacena las divisas únicas
    private final List<Currency> currencyList;

    /**
     * Constructor de la clase CurrencyList.
     * Inicializa la lista de divisas como una LinkedList vacía.
     */
    public CurrencyList() {
        this.currencyList = new LinkedList<>();
    }

    /**
     * Obtiene la lista de divisas.
     *
     * @return La lista de divisas.
     */
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    /**
     * Agrega una divisa a la lista si no está presente.
     *
     * @param currency La divisa que se va a agregar.
     */
    public void add(Currency currency) {
        if (!(this.currencyList.contains(currency))) {
            this.currencyList.add(currency);
        }
    }

    /**
     * Retorna una representación en cadena de la lista de divisas.
     * Cada divisa se imprime en una línea separada.
     *
     * @return Una representación en cadena de la lista de divisas.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Currency currency : this.getCurrencyList()) {
            sb.append(currency).append("\n\n");
        }
        return sb.toString();
    }
}
