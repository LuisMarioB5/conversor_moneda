package com.conversor_moneda.currency_converter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * La clase Currency representa una moneda con su código, nombre, país y tasa de conversión a USD.
 */
public class Currency {
    private final String code; // Código de la moneda
    private final String name; // Nombre de la moneda
    private final String country; // País de la moneda
    private final float rateToUSD; // Tasa de conversión a USD

    /**
     * Constructor de la clase Currency.
     *
     * @param currencyCode Código de la moneda en formato ISO 4217
     */
    public Currency(String currencyCode) {
        // Asegura que el código de la moneda esté en mayúsculas
        currencyCode = currencyCode.toUpperCase();

        // Ruta donde se encuentran los archivos JSON
        String resourcesPath = "src/main/resources/";

        // Objeto Gson para manejar JSON
        Gson gson = new Gson();

        // Objetos JsonObject para almacenar los datos del JSON
        JsonObject supportedCurrenciesJsonObject = null;
        JsonObject convertionRateJsonObject = null;
        try {
            // Lee el archivo JSON de monedas admitidas
            JsonObject jsonObject = gson.fromJson(new FileReader(resourcesPath + "supported_currencies.json"),
                    JsonObject.class);
            // Obtiene el objeto JSON de la moneda especificada
            supportedCurrenciesJsonObject = jsonObject.getAsJsonObject(currencyCode);

            // Lee el archivo JSON de tasas de conversión
            convertionRateJsonObject = gson.fromJson(new FileReader(resourcesPath + "conversion_rates.json"),
                    JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Inicializa los atributos de la moneda
        this.code = supportedCurrenciesJsonObject.get("code").getAsString();
        this.name = supportedCurrenciesJsonObject.get("name").getAsString();
        this.country = supportedCurrenciesJsonObject.get("country").getAsString();
        this.rateToUSD = convertionRateJsonObject.get(currencyCode).getAsFloat();
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
     * Obtiene el nombre de la moneda.
     *
     * @return Nombre de la moneda
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el país de la moneda.
     *
     * @return País de la moneda
     */
    public String getCountry() {
        return country;
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
     * Método principal para probar la clase Currency.
     *
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        // Crear una instancia de la moneda DOP (Peso Dominicano)
        Currency dop = new Currency("dop");
        System.out.println(dop);

        // Crear una instancia de la moneda USD (Dólar Estadounidense)
        Currency usd = new Currency("usd");
        System.out.println("\n" + usd);

        // Crear una instancia de la moneda EUR (Euro)
        Currency eur = new Currency("eur");
        System.out.println("\n" + eur);
    }
}
