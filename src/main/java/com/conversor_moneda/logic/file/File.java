package com.conversor_moneda.logic.file;

import com.conversor_moneda.logic.error.MyError;
import com.conversor_moneda.logic.historial.Conversion;
import com.conversor_moneda.logic.json.LocalDateAdapter;
import com.conversor_moneda.logic.json.LocalTimeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;


public class File {

    /**
     * Función que guarda un texto en un archivo.
     *
     * @param content  El String que se desea guardar.
     * @param filePath El String que contiene el directorio del nuevo archivo.
     *                 Ejemplo 1: C:\Users\UserX\Desktop\nombreDelArchivo.extension
     *                 Ejemplo 2: direccionDelProyecto\nombreDelArchivo.extension
     */
    public static void save(String content, String filePath) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Short> error = new AtomicReference<>((short) 0);

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
            if (error.get() == 0) {
                System.out.println("Archivo guardado exitosamente en la ruta: " + filePath);
            }
        } catch (InterruptedException e) {
            MyError.println("Error: " + e);
        }
    }

    /**
     * Guarda la lista de conversiones en un archivo JSON en la ruta especificada.
     *
     * @param conversionHistory La lista de conversiones que se desea guardar en formato JSON.
     * @param filePath          La ruta donde se guardará el archivo JSON.
     */
    public static void saveASJsonFile(List<Conversion> conversionHistory, String filePath) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Short> error = new AtomicReference<>((short) 0);

        // Crear un nuevo hilo para guardar el archivo en segundo plano
        Thread thread = new Thread(() -> {
            if (!conversionHistory.isEmpty()) {
                // Crear un objeto Gson con el formato de impresión y los adaptadores de LocalDate y LocalTime registrados
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                        .create();

                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    // Convertir la lista de conversiones a JSON y escribir en el archivo
                    gson.toJson(conversionHistory, fileWriter);
                } catch (IOException e) {
                    // Manejar cualquier error de entrada/salida y mostrar un mensaje de error
                    MyError.println("Error: " + e.getMessage());
                    MyError.println("Vuelve a intentar...");
                    error.set((short) 1);
                } finally {
                    latch.countDown(); // Indica que la operación ha terminado
                }
            } else {
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
     * Guarda la lista de conversiones en un archivo de texto en la ruta especificada.
     *
     * @param conversionHistory La lista de conversiones que se desea guardar en formato de texto.
     * @param filePath          La ruta donde se guardará el archivo de texto.
     */
    public static void saveAsTextFile(List<Conversion> conversionHistory, String filePath) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Short> error = new AtomicReference<>((short) 0);


        // Crear un nuevo hilo para guardar el archivo en segundo plano
        Thread thread = new Thread(() -> {
            if (!conversionHistory.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    int transactionNumber = 1;
                    for (Conversion conversion : conversionHistory) {
                        // Escribir los detalles de cada conversión en el archivo de texto
                        writer.write("Transacción " + transactionNumber + ":\n");
                        writer.write("Moneda de origen: " + conversion.getOriginCurrency().getCode() + "\n");
                        writer.write("Moneda objetivo: " + conversion.getTargetCurrency().getCode() + "\n");
                        writer.write("Cantidad a convertir: [" + conversion.getOriginCurrency().getCode() + "] $" + conversion.getAmountToConvert() + "\n");
                        writer.write("Cantidad convertida: [" + conversion.getTargetCurrency().getCode() + "] $" + conversion.getAmountConverted() + "\n");
                        writer.write("Tasa de conversión: " + conversion.getConversionRate() + "\n");
                        writer.write("Fecha de conversión: " + conversion.getConversionDate() + "\n");
                        writer.write("Hora de conversión: " + conversion.getConversionTime() + "\n\n");
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
}