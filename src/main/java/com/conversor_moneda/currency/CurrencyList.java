package com.conversor_moneda.currency;

import java.util.LinkedList;
import java.util.List;

/**
 * Representa una lista de monedas únicas.
 */
public class CurrencyList {
    // Lista que almacena las monedas únicas
    private final List<Currency> currencyList;

    /**
     * Constructor de la clase CurrencyList.
     * Inicializa la lista de monedas como una LinkedList vacía.
     */
    public CurrencyList() {
        this.currencyList = new LinkedList<>();
    }

    /**
     * Obtiene la lista de monedas.
     *
     * @return La lista de monedas.
     */
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    /**
     * Agrega una moneda a la lista si no está presente.
     *
     * @param currency La moneda que se va a agregar.
     */
    public void add(Currency currency) {
        if (!(this.currencyList.contains(currency))) {
            this.currencyList.add(currency);
        }
    }

    /**
     * Retorna una representación en cadena de la lista de monedas.
     * Cada moneda se imprime en una línea separada.
     *
     * @return Una representación en cadena de la lista de monedas.
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
