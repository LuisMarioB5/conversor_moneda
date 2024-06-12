package com.conversor_moneda.file;

import com.conversor_moneda.adapters.LocalDateAdapter;
import com.conversor_moneda.adapters.LocalTimeAdapter;
import com.conversor_moneda.conversion.ConversionHistory;
import com.conversor_moneda.currency.Currency;
import com.conversor_moneda.error.MyError;
import com.conversor_moneda.conversion.Conversion;
import com.google.gson.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Clase que proporciona métodos para guardar y cargar conversiones ({@link Conversion}) de moneda en archivos JSON y de texto.
 */
public class MyFile {
    private static boolean isFirstCallOfSaveAsJsonFile = true;
    private static final List<Conversion> oldConversions = new ArrayList<>();
    private static boolean isFirstCallOfSaveAsTextFile = true;
    private static String contentOldTextFile = "";
    private static final AtomicReference<Short> error = new AtomicReference<>((short) 0);

    /**
     * Función que guarda un texto en un archivo.
     *
     * @param content  El String que se desea guardar.
     * @param filePath El String que contiene el directorio del nuevo archivo.
     *                 Ejemplo 1 (Windows): C:\Users\UserX\Desktop\nombreDelArchivo.extension
     *                 Ejemplo 2 (proyecto): ruta/del/archivo/con/relación/al/proyecto/nombreDelArchivo.extension
     */
    public static void save(String content, String filePath) {
        CountDownLatch latch = new CountDownLatch(1);

        // Crear un nuevo hilo para guardar el archivo en segundo plano
        Thread thread = new Thread(() -> {
            try (FileWriter writer = new FileWriter(filePath)) {
                // Escribir el String en el archivo
                writer.write(content);
            } catch (IOException e) {
                // Manejar cualquier error de entrada/salida y mostrar un mensaje de error
                MyError.println("Error: " + e.getMessage());
                MyError.println("Vuelve a intentar...");
                error.set((short) 1);
            } finally {
                latch.countDown(); // Indica que la operación ha terminado
            }
        });

        // Iniciar el hilo para guardar el archivo
        thread.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            MyError.println("Error: " + e);
        }
    }

    /**
     * Guarda la lista de conversiones ({@link Conversion}) en un archivo JSON en la ruta especificada.
     *
     * @param conversionHistory La lista de conversiones que se desea guardar en formato JSON.
     * @param filePath          La ruta donde se guardará el archivo JSON.
     */
    public static void saveAsJsonFile(List<Conversion> conversionHistory, String filePath) {
        filePath = checkExtension(filePath, ".json");

        if (filePath.equals("null")) {
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        error.set((short) 0);

        String finalFilePath = filePath;

        File file = new File(finalFilePath);
        if (isFirstCallOfSaveAsJsonFile) {
            if (file.exists()) {
                oldConversions.addAll(loadFromJsonFile(finalFilePath));
            }
            isFirstCallOfSaveAsJsonFile = false;
        }

        // Verificar si el directorio padre del archivo existe, si no existe, intentar crearlo
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                MyError.println("No se pudo crear el directorio: " + parentDir.getAbsolutePath());
                error.set((short) 1);
                latch.countDown();
                return;
            }
        }

        Thread thread = getThread(conversionHistory, finalFilePath, latch);

        // Iniciar el hilo para guardar el archivo
        thread.start();

        try {
            latch.await();
            if (error.get() == 0) {
                if (!conversionHistory.isEmpty()) {
                    System.out.println("Archivo guardado exitosamente en la ruta: " + filePath);
                } else if (new File(filePath).exists()) {
                    System.out.println("Archivo ya existe en la ruta: " + filePath);
                }
            }
        } catch (InterruptedException e) {
            MyError.println("Error: " + e);
        }
    }

    /**
     * Obtiene un nuevo hilo para guardar el historial de conversiones en un archivo json en segundo plano.
     *
     * @param conversionHistory Lista de objetos Conversion que se desea guardar en el archivo.
     * @param finalFilePath     Ruta completa del archivo donde se guardará el historial de conversiones.
     * @param latch             CountDownLatch utilizado para sincronizar la finalización de la operación.
     * @return Un nuevo objeto Thread configurado para guardar el historial de conversiones en el archivo.
     */
    private static Thread getThread(List<Conversion> conversionHistory, String finalFilePath, CountDownLatch latch) {
        List<Conversion> finalOldConversions = new ArrayList<>(oldConversions);

        // Crear un nuevo hilo para guardar el archivo en segundo plano
        return new Thread(() -> {
            if (!conversionHistory.isEmpty()) {
                // Crear un objeto Gson con el formato de impresión y los adaptadores de LocalDate y LocalTime registrados
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                        .create();

                try (FileWriter fileWriter = new FileWriter(finalFilePath)) {
                    List<Conversion> finalConversions = finalOldConversions.isEmpty() ? conversionHistory : ConversionHistory.combine(finalOldConversions, conversionHistory);
                    gson.toJson(finalConversions, fileWriter);
                } catch (IOException e) {
                    // Manejar cualquier error de entrada/salida y mostrar un mensaje de error
                    MyError.println("Error: " + e.getMessage());
                    MyError.println("Vuelve a intentar...");
                    error.set((short) 1);
                } finally {
                    latch.countDown(); // Indica que la operación ha terminado
                }
            } else {
                MyError.println("La lista se encuentra vacía...");
                latch.countDown();
            }
        });
    }

    /**
     * Carga una lista de conversiones ({@link Conversion}) desde un archivo JSON.
     *
     * @param filePath La ruta del archivo JSON desde donde se cargarán las conversiones.
     * @return La lista de conversiones cargadas.
     */
    public static List<Conversion> loadFromJsonFile(String filePath) {
        filePath = checkExtension(filePath, ".json");

        List<Conversion> conversionHistory = new LinkedList<>();
        try (FileReader reader = new FileReader(filePath)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    Currency originCurrency = extractCurrency(jsonObject.getAsJsonObject("Moneda de origen"));
                    Currency targetCurrency = extractCurrency(jsonObject.getAsJsonObject("Moneda objetivo"));
                    float amountToConvert = jsonObject.get("Cantidad a convertir").getAsFloat();
                    float amountConverted = jsonObject.get("Cantidad convertida").getAsFloat();
                    float conversionRate = jsonObject.get("Tasa de conversion").getAsFloat();
                    String conversionDate = jsonObject.get("Fecha de conversion").getAsString();
                    String conversionTime = jsonObject.get("Hora de conversion").getAsString();

                    // Formateador para la fecha en formato "yyyy-MM-dd"
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    // Formateador para la hora en formato "HH:mm:ss"
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                    LocalDate date = LocalDate.parse(conversionDate, dateFormatter);
                    LocalTime time = LocalTime.parse(conversionTime, timeFormatter);

                    Conversion conversion = new Conversion(originCurrency, targetCurrency, amountToConvert, amountConverted, conversionRate, date, time);
                    conversionHistory.add(conversion);
                }
            }
        } catch (IOException e) {
            MyError.println(e.getMessage());
        }

        return conversionHistory;
    }

    /**
     * Extrae los detalles de una moneda ({@link Currency}) desde un objeto JSON.
     *
     * @param currencyObject El objeto JSON que contiene los detalles de la moneda.
     * @return Un objeto Currency con los detalles extraídos.
     */
    private static Currency extractCurrency(JsonObject currencyObject) {
        Currency currency = new Currency(currencyObject.get("code").getAsString());
        currency.setRateToUSD(currencyObject.get("rateToUSD").getAsFloat());
        return currency;
    }

    /**
     * Guarda la lista de conversiones ({@link Conversion}) en un archivo de texto en la ruta especificada.
     *
     * @param conversionHistory La lista de conversiones que se desea guardar en formato de texto.
     * @param filePath          La ruta donde se guardará el archivo de texto.
     */
    public static void saveAsTextFile(List<Conversion> conversionHistory, String filePath) {
        filePath = checkExtension(filePath, ".txt");

        if (filePath.equals("null")) {
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        error.set((short) 0);

        String finalFilePath = filePath;

        if (isFirstCallOfSaveAsTextFile) {
            if (new File(finalFilePath).exists()) {
                contentOldTextFile = loadContentFromTextFile(finalFilePath);
            }
            isFirstCallOfSaveAsTextFile = false;
        }

        // Crear el directorio si no existe
        File directory = new File(finalFilePath).getParentFile();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                MyError.println("Error: No se pudo crear el directorio para el archivo.");
                return;
            }
        }

        // Crear un nuevo hilo para guardar el archivo en segundo plano
        Thread thread = new Thread(() -> {
            if (!conversionHistory.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalFilePath))) {
                    int transactionNumber = 0;

                    if (new File(finalFilePath).exists()) {
                        writer.write(contentOldTextFile);
                        transactionNumber = getLastTransactionNumber(contentOldTextFile) + 1;
                    }

                    for (Conversion conversion : conversionHistory) {
                        // Escribir los detalles de cada conversión en el archivo de texto
                        writer.write(contentOldTextFile.isEmpty() ? "" : "\n");
                        writer.write("Transacción " + transactionNumber + ":\n");
                        writer.write("Moneda de origen: " + conversion.originCurrency().getCode() + "\n");
                        writer.write("Moneda objetivo: " + conversion.targetCurrency().getCode() + "\n");
                        writer.write("Cantidad a convertir: [" + conversion.originCurrency().getCode() + "] $" + conversion.amountToConvert() + "\n");
                        writer.write("Cantidad convertida: [" + conversion.targetCurrency().getCode() + "] $" + conversion.amountConverted() + "\n");
                        writer.write("Tasa de conversión: " + conversion.conversionRate() + "\n");
                        writer.write("Fecha de conversión: " + conversion.conversionDate() + "\n");
                        writer.write("Hora de conversión: " + conversion.conversionTime());
                        writer.write(contentOldTextFile.isEmpty() ? "\n\n" : "");
                        transactionNumber++;
                    }
                } catch (IOException e) {
                    // Manejar cualquier error de entrada/salida y mostrar un mensaje de error
                    MyError.println("Error: " + e.getMessage());
                    MyError.println("Vuelve a intentar...");
                    error.set((short) 1);
                } finally {
                    latch.countDown(); // Indica que la operación ha terminado
                }
            } else {
                MyError.println("La lista se encuentra vacía...");
                latch.countDown();
            }
        });

        // Iniciar el hilo para guardar el archivo
        thread.start();

        try {
            latch.await();
            if (error.get() == 0 && !conversionHistory.isEmpty()) {
                System.out.println("Archivo guardado exitosamente en la ruta: " + filePath);
            }
        } catch (InterruptedException e) {
            MyError.println("Error: " + e);
        }
    }

    /**
     * Carga el contenido de un archivo de texto desde la ruta especificada.
     *
     * @param filePath La ruta del archivo de texto.
     * @return El contenido del archivo de texto.
     */
    public static String loadContentFromTextFile(String filePath) {
        filePath = checkExtension(filePath, ".txt");

        if (filePath.equals("null")) {
            return "Error";
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            MyError.println("Error al cargar contenido del archivo: " + e.getMessage());
        }
        return content.toString();
    }

    /**
     * Obtiene el número de la última transacción desde el contenido del archivo de texto.
     *
     * @param content El contenido del archivo de texto.
     * @return El número de la última transacción.
     */
    private static int getLastTransactionNumber(String content) {
        // Dividir el contenido del archivo por líneas
        String[] lines = content.split("\n");

        // Buscar la última línea que contiene "Transacción"
        String lastTransactionLine = "";
        for (String line : lines) {
            if (line.toLowerCase().startsWith("transacción")) {
                lastTransactionLine = line;
            }
        }

        // Verificar si la línea de la última transacción está vacía
        if (lastTransactionLine.isEmpty()) {
            // Manejar el caso en que no se encuentra ninguna transacción
            return 0;
        }

        // Extraer el número de la última transacción de la última línea
        String[] parts = lastTransactionLine.split(" ");
        // Verificar si hay suficientes partes para obtener el número de transacción
        if (parts.length >= 2) {
            return Integer.parseInt(parts[1].replace(":", ""));
        } else {
            // Manejar el caso en que el formato de la última línea no sea el esperado
            return 0;
        }
    }

    /**
     * Verifica y ajusta la extensión del archivo según el tipo esperado (.txt o .json).
     *
     * @param filePath La ruta del archivo.
     * @param extTarget La extensión de archivo esperada.
     * @return La ruta del archivo con la extensión correcta, o "null" si la ruta es inválida.
     */
    public static String checkExtension(String filePath, String extTarget) {
        extTarget = extTarget.toLowerCase();

        if (!filePath.contains(".")) {
            return filePath.concat(extTarget);
        }

        if ((extTarget.equals(".txt") || extTarget.equals(".json"))) {
            String notExtTarget = extTarget.equals(".txt") ? ".json" : ".txt";

            if (filePath.endsWith(notExtTarget)) {
                return filePath.replace(notExtTarget, extTarget);
            } else if (filePath.endsWith(extTarget)) {
                return filePath;
            }

        } else {
            MyError.println("Error: Este método solo esta diseñado para archivos .txt o .json...");
        }

        MyError.println("Error: la ruta del archivo no es válida...");
        return "null";
    }
}
