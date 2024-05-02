package com.conversor_moneda.logic.historial;

import java.util.LinkedList;
import java.util.List;

/**
 * Representa el historial de conversiones realizadas.
 */
public class ConversionHistory {
    private final List<Conversion> conversionList;

    /**
     * Constructor para inicializar el historial de conversiones.
     */
    public ConversionHistory() {
        this.conversionList = new LinkedList<>();
    }

    /**
     * Obtiene el historial completo de conversiones.
     * @return Lista de conversiones.
     */
    public List<Conversion> getConversionList() {
        return conversionList;
    }


    /**
     * Agrega una nueva conversión al historial, si aún no está presente.
     * @param conversion La conversión a agregar.
     */
    public void add(Conversion conversion) {
        if (!(this.conversionList.contains(conversion))) {
            this.conversionList.add(conversion);
        }
    }

    /**
     * Devuelve true si la cadena esta vacía, de lo contrario false
     * @return boolean que representa si la lista esta vacia o no.
     */
    public boolean isEmpty() {
        return conversionList.isEmpty();
    }

    /**
     * Devuelve una representación en formato de cadena del historial de conversiones.
     * @return Cadena que representa el historial de conversiones.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        short num = 1;
        for (Conversion conversion : this.getConversionList()) {
            stringBuilder.append('\n').append(num).append(". \n").append(conversion).append('\n');
            num++;
        }
        return stringBuilder.toString();
    }
}
