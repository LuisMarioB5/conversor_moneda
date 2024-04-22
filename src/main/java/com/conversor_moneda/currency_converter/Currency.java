package com.conversor_moneda.currency_converter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

public class Currency {
    private String code;
    private String name;
    private String country;
    private final float rateToUSD;

    public Currency(String code, String name, String country, float rateToUSD) {
        // Adaptar para que tome los valores desde los json

        this.code = code;
        this.name = name;
        this.country = country;
        this.rateToUSD = rateToUSD;
    }

    public float getRateToUSD() {
        return rateToUSD;
    }


    public static void main(String[] args) {

        String filePath = "C:\\Users\\LUISM\\Desktop\\conversor_moneda\\src\\main\\resources\\supported_currencies.json";

        try {

            Gson gson = new Gson();

            JsonObject jsonObject = gson.fromJson(new FileReader(filePath), JsonObject.class);
            JsonObject aedJson = jsonObject.getAsJsonObject("AED");

            String code = aedJson.get("code").getAsString();
            String name = aedJson.get("name").getAsString();
            String country = aedJson.get("country").getAsString();
            float rateToUSD = 0f;

            System.out.println("AED Content:" +
                    "\ncode: " + code +
                    "\nname: " + name +
                    "\ncountry: "+ country +
                    "\nrate to USD: " + rateToUSD);

        } catch (FileNotFoundException e) {
            System.out.println("Error, no se encontró el archivo: " + e);
        }

        URL resourceUrl = Currency.class.getClassLoader().getResource("supported_currencies.json");
        if (resourceUrl == null) {
            System.err.println("No se pudo encontrar el archivo JSON en el directorio resources.");
            return;
        }
        System.out.println(resourceUrl);
    }
}
