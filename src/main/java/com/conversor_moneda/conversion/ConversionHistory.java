package com.conversor_moneda.conversion;

import java.util.LinkedList;
import java.util.List;

/**
 * Representa el conversion de conversiones realizadas.
 */
public class ConversionHistory {
    private final List<Conversion> conversionList;

    /**
     * Constructor para inicializar el conversion de conversiones.
     */
    public ConversionHistory() {
        this.conversionList = new LinkedList<>();
    }

    /**
     * Obtiene el conversion completo de conversiones.
     * @return Lista de conversiones ({@link Conversion}).
     */
    public List<Conversion> getConversionList() {
        return conversionList;
    }

    /**
     * Agrega una nueva conversión ({@link Conversion}) al conversion, si aún no está presente.
     * @param conversion La conversión a agregar.
     */
    public void add(Conversion conversion) {
        if (!(this.conversionList.contains(conversion))) {
            this.conversionList.add(conversion);
        }
    }

    /**
     * Combina dos listas de Conversiones en una sola lista.
     *
     * @param first  Primera lista de {@link Conversion}.
     * @param second Segunda lista de {@link Conversion}.
     * @return Una nueva lista que contiene todas las conversiones de ambas listas.
     */
    public static List<Conversion> combine(List<Conversion> first, List<Conversion> second) {
        List<Conversion> conversions = new LinkedList<>();

        conversions.addAll(first);
        conversions.addAll(second);

        return conversions;
    }

    /**
     * Devuelve true si la cadena esta vacía, de lo contrario false
     * @return boolean que representa si la lista esta vacía o no.
     */
    public boolean isEmpty() {
        return conversionList.isEmpty();
    }

    /**
     * Devuelve una representación en formato de cadena del conversion de conversiones.
     * @return Cadena que representa el conversion de conversiones.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        short num = 1;
        for (Conversion conversion : this.getConversionList()) {
            stringBuilder.append('\n').append(num).append(": \n").append(conversion).append('\n');
            num++;
        }
        return stringBuilder.toString();
    }
}
