# Conversor de Monedas - Challenge ONE: Proyecto Finalizado

¡Bienvenido/a al repositorio del Conversor de monedas en Java! Este proyecto es la consolidación de mis conocimientos en
la formación **Java Orientado a Objetos G6 - ONE**, ofrecido por [Alura LATAM](https://www.aluracursos.com) en colaboración con [Oracle](https://www.oracle.com) en
el programa [Oracle Next Education](https://www.oracle.com/lad/education/oracle-next-education). Aquí encontrarás todos los recursos y el código fuente necesarios para 
implementar y comprender el funcionamiento de este conversor de monedas en Java.

## Acerca del Proyecto

El conversor de monedas en Java permite realizar conversiones en tiempo real con distintas monedas mediante el
consumo de la API [Exchange Rate API](https://www.exchangerate-api.com/). La lógica del proyecto se desarrolló siguiendo las especificaciones del 
desafío propuesto por Alura LATAM en el archivo de [Trello](https://trello.com/b/RU41cvaQ/conversor-de-moneda-challenge-one-java-back-end).

### Requisitos para ejecutar el proyecto

- **Java 17**: Asegúrate de tener instalado [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) o una versión posterior para ejecutar el proyecto 
  correctamente.
- **Exchange Rate API**: Necesitarás obtener una clave de API (API Key)de [Exchange Rate API](https://www.exchangerate-api.com/) para realizar 
  consultas de las tasas de cambio. La clave debe guardarse en una variable de entorno llamada `EXCHANGE_RATE_API_KEY`
  para que el proyecto pueda acceder a ella, en caso de no tenerla configurada se te pedirá ingresarla en el sistema.
- **Maven**: Asegúrate de tener instalado [Maven](https://maven.apache.org/) para gestionar las dependencias y ejecutar los comandos de 
  compilación y ejecución.

### Instalación

1. **Clona el repositorio:**

    ```shell
    git clone https://github.com/LuisMarioB5/conversor_moneda.git
    cd conversor_moneda
    ```

2. **Configura la clave de la API:**

    - Asegúrate de tener una clave de API válida de [Exchange Rate API](https://www.exchangerate-api.com/).
    - Si tienes permisos guarda la clave de la API en una variable de entorno llamada `EXCHANGE_RATE_API_KEY` para que el proyecto pueda
      acceder a ella.
        - **Windows**:
          ```shell
          setx EXCHANGE_RATE_API_KEY "tu_clave_de_api"
          ```

        - **Linux/Mac**:
          ```shell
          export EXCHANGE_RATE_API_KEY="tu_clave_de_api"
          ```
   - En caso de no tener los permisos para crear la variable de entorno, el sistema te pedirá ingresarla manualmente al 
     iniciar el proyecto.

3. **Instala las dependencias:**

   ```shell
   mvn install
   ```

### Cómo ejecutar el proyecto

* **Establece la página de códigos a UTF-8** (Opcional, solo si encuentras problemas con caracteres especiales):
    ```shell
    chcp 65001
    ```

* **Compila y ejecuta el proyecto:**
    ```shell
    mvn compile exec:java
    ```

### Ejemplos de uso:

En el menú principal se encuentran 4 opciones principales:

1. **Consultar la lista de monedas**: Muestra otro menú en el cual se presentan las opciones de presentar las monedas no
   disponibles, volátiles y disponibles.
    * **Monedas no disponibles**: Muestra una lista de monedas que no están disponibles para conversiones debido a
      diversas razones.
    * **Monedas con alta volatilidad**: Muestra una lista de las monedas que tienen alta volatilidad y pueden tener
      tasas de cambio menos exactas.
    * **Monedas disponibles**: Muestra una lista de todas las monedas que pueden ser utilizadas para conversiones.
2. **Convertir monedas**: Permite realizar las conversiones que desees con todas las monedas permitidas.
    * Ejemplo:
    ```text
    Ingresa el código de la moneda que deseas cambiar: USD
    Ingresa el código de la moneda que deseas obtener: EUR
    Ingresa la cantidad que deseas convertir: [USD] $100
    Cantidad convertida: [EUR] $93.13
    ``` 
3. **Consultar conversiones anteriores**: Muestra todas las conversiones que realices en la ejecución del programa, y te
   mostrará otro menú para guardar las conversiones en un archivo (txt o json), en caso de tener un archivo con ese
   mismo nombre se agregarán las conversiones realizadas en esa ejecución al final del archivo. En caso de no tener
   conversiones mostrará un error y te pedirá realizar alguna conversión primero.
    * Ejemplo con conversiones:
      ```text
        ################################################
        
        Lista de conversiones realizadas anteriormente
        
        ################################################
        
        1:
        Moneda de origen: USD
        Moneda objetivo: EUR
        Cantidad a convertir: [USD] $100.0
        Cantidad convertida: [EUR] $93.13
        Tasa de conversion: 0.9313
        Fecha de conversion: 2024-06-12
        Hora de conversion: 13:51:56.231249900
        
        Selecciona la opción que desees:
        1. Guardar las conversiones en un archivo (txt, json)
        2. Volver al menú anterior
        3. Salir de la aplicación
      ```

    * Ejemplo sin conversiones:
      ```text
      No haz realizado ninguna conversión.
      Realiza alguna y vuelve a intentar...
      ```

4. **Salir de la aplicación**: Permite cerrar el sistema luego de concluir su uso.

## Estructura del proyecto

```text
src/main/
|-- java/com/conversor_moneda/
|   |-- adapters/
|   |   |-- LocalDateAdapter.java
|   |   |-- LocalTimeAdapter.java
|   |-- api/
|   |   |-- Api.java
|   |-- console/
|   |   |-- Console.java
|   |-- conversion/
|   |   |-- Conversion.java
|   |   |-- ConversionHistory.java
|   |-- converter/
|   |   |-- Converter.java
|   |-- currency/
|   |   |-- Currency.java
|   |   |-- CurrencyList.java
|   |-- error/
|   |   |-- MyError.java
|   |-- file/
|   |   |-- LastApiRequestTime.java
|   |   |-- MyFile.java
|   |-- ui/
|       |-- Convertir.java
|       |-- Historial.java
|       |-- ListarMonedas.java
|       |-- Main.java
|-- resources/
    |-- high_volatility_currencies.json
    |-- not_supported_currencies.json
    |-- supported_currencies.json
```

## Tecnologías Implementadas

- [**Java 17**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html): Encargado de toda la
  lógica del proyecto, implementando programación orientada a objetos (POO) y el consumo de la API.
- [**Exchange Rate API**](https://www.exchangerate-api.com/): API a la cual se le realizan consultas sobre la tasa actual de las monedas.

## Agradecimientos y Recursos

Un agradecimiento especial a [Alura LATAM](https://www.aluracursos.com) y [Oracle](https://www.oracle.com) por proporcionar los materiales y el desafío que 
hicieron posible este proyecto. A continuación, algunos recursos externos que fueron de gran ayuda para la realización
del proyecto:

1. **Cursos de Alura LATAM:**
    - [Java: creando tu primera aplicación](https://app.aluracursos.com/course/java-creando-primera-aplicacion)
    - [Java: aplicando la Orientación a Objetos](https://app.aluracursos.com/course/java-aplicando-orientacion-objetos)
    - [Java: trabajar con listas y colecciones de datos](https://app.aluracursos.com/course/java-trabajar-listas-colecciones-datos)
    - [Java: consumir API, escribir archivos y manejar errores](https://app.aluracursos.com/course/java-consumir-api-escribir-archivos-manejar-errores)

2. **Archivos proporcionados por Alura LATAM:**
    - [Cards en Trello](https://trello.com/b/RU41cvaQ)

## Licencia

Este proyecto se distribuye bajo la [Licencia MIT](LICENSE.md), lo que permite su uso, modificación y distribución de
manera libre.
