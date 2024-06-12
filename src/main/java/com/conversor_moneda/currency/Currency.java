package com.conversor_moneda.currency;

import com.conversor_moneda.api.Api;
import com.conversor_moneda.error.MyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

/**
 * La clase Currency representa una moneda con su código, nombre, país y tasa de conversión a USD.
 */
public class Currency {
    private String code; // Código de la moneda
    private String name; // Nombre de la moneda
    private String country; // País de la moneda
    private float rateToUSD; // Tasa de conversión a USD

    /**
     * Constructor de la clase Currency.
     *
     * @param currencyCode Código de la moneda en formato ISO 4217
     */
    public Currency(String currencyCode) {
        // Asegura que el código de la moneda esté en mayúsculas
        currencyCode = currencyCode.toUpperCase();

        try {
            Api.currencyCodeExists(currencyCode);
        } catch (NullPointerException e) {
            MyError.println(e.getMessage());
        }

        // Ruta donde se encuentran los archivos JSON
        String resourcesPath = "src/main/resources/";

        // Objeto Gson para manejar JSON
        Gson gson = new Gson();

        // Objetos JsonObject para almacenar los datos del JSON
        JsonObject supportedCurrenciesJsonObject;
        JsonObject convertionRateJsonObject;
        try {
            // Lee el archivo JSON de monedas admitidas
            JsonObject jsonObject = gson.fromJson(new FileReader(resourcesPath + "supported_currencies.json"),
                    JsonObject.class);
            // Obtiene el objeto JSON de la moneda especificada
            supportedCurrenciesJsonObject = jsonObject.getAsJsonObject(currencyCode);

            // Lee el archivo JSON de tasas de conversión
            convertionRateJsonObject = gson.fromJson(new FileReader(resourcesPath + "conversion_rates.json"),
                    JsonObject.class);

            // Inicializa los atributos de la moneda
            assert supportedCurrenciesJsonObject != null;
            assert convertionRateJsonObject != null;
            this.code = supportedCurrenciesJsonObject.get("code").getAsString();
            this.name = supportedCurrenciesJsonObject.get("name").getAsString();
            this.country = supportedCurrenciesJsonObject.get("country").getAsString();
            this.rateToUSD = convertionRateJsonObject.get(currencyCode).getAsFloat();
        } catch (FileNotFoundException | NullPointerException e) {
            MyError.println(e.getMessage());
        }
    }

    /**
     * Obtiene el código de la moneda.
     *
     * @return Código de la moneda
     */
    public String getCode() {
        return code;
    }

    /**
     * Obtiene la tasa de conversión de la moneda a USD.
     *
     * @return Tasa de conversión a USD
     */
    public float getRateToUSD() {
        return rateToUSD;
    }

    /**
     * Establece la tasa de conversión a USD.
     *
     * @param rateToUSD la tasa de conversión a USD
     */
    public void setRateToUSD(float rateToUSD) {
        this.rateToUSD = rateToUSD;
    }

    /**
     * Devuelve una representación de cadena de la moneda.
     *
     * @return Representación de cadena de la moneda
     */
    @Override
    public String toString() {
        return "code: \"" + this.code + '\"' +
                "\nname: \"" + this.name + '\"' +
                "\ncountry: \"" + this.country + '\"' +
                "\nrateToUSD: " + this.rateToUSD;
    }

    /**
     * Método equals para comparar dos instancias de Currency basándose en sus códigos.
     *
     * @param obj Objeto a comparar
     * @return true si las instancias son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Currency currency = (Currency) obj;
        return Objects.equals(code, currency.code);
    }

    /**
     * Método hashCode para generar un código hash basado en el código de la moneda.
     *
     * @return Código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
