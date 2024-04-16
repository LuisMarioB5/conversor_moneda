# Conversor de Monedas - Challenge ONE: Proyecto Iniciado

¡Bienvenido/a al repositorio del Conversor de monedas en Java! Este proyecto es la consolidación de mis conocimientos en
la formación **Java Orientado a Objetos G6 - ONE**, ofrecido por [Alura LATAM](https://www.aluracursos.com) en
colaboración con [Oracle](https://www.oracle.com) en el
programa [Oracle Next Education](https://www.oracle.com/lad/education/oracle-next-education). Aquí encontrarás todos los
recursos y el código fuente necesario para implementar y comprender el funcionamiento de este conversor de monedas
en
Java.

## Acerca del Proyecto

El conversor de monedas en Java permite realizar converiones en tiempo real con distintas divisas mediante el
consumo
de la API [Exchange Rate API](https://www.exchangerate-api.com/). La lógica del proyecto se desarrolló siguiendo las
especificaciones del desafío propuesto por Alura LATAM en el archivo
de [Trello](https://trello.com/b/RU41cvaQ/conversor-de-moneda-challenge-one-java-back-end).

### Requisitos para ejecutar el proyecto

- **Java 17**: Asegúrate de tener
  instalado [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) o una versión
  posterior para ejecutar el proyecto correctamente.
- **Exchange Rate API**: Necesitarás obtener una clave de API (API Key)
  de [Exchange Rate API](https://www.exchangerate-api.com/) para realizar consultas de las tasas de cambio. La clave
  debe guardarse en una variable de entorno llamada EXCHANGE_RATE_API_KEY para que el proyecto pueda acceder a ella.
- **Dependencias**: Las dependencias del proyecto están especificadas en el archivo pom.xml y pueden ser gestionadas
  con [Maven](https://maven.apache.org/).

### Cómo ejecutar el proyecto

1. **Clona el repositorio:**

```shell
git clone https://github.com/LuisMarioB5/conversor_moneda.git
cd conversor_moneda
```

2. **Configura la clave de la API:**

- Asegúrate de tener una clave de API válida de [Exchange Rate API](https://www.exchangerate-api.com/).
- Guarda la clave de la API en una variable de entorno llamada `EXCHANGE_RATE_API_KEY` para que el proyecto pueda
  acceder a ella.

3. **Compila y ejecuta el proyecto:**

- Utiliza el siguiente comando para compilar y ejecutar el proyecto con Maven. Ten en cuenta que el proyecto actualmente
  no cuenta con interfaz gráfica:

```shell
mvn compile exec:java
```

## Tecnologías Implementadas

- [**Java 17**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html): Encargado de toda la
  lógica del proyecto, implementando programación orientada a objetos (POO) y el consumo de la API.
- [**Exchange Rate API**](https://www.exchangerate-api.com/): API a la cual se le realizan consultas sobre la tasa
  actual de las monedas.

## Agradecimientos y Recursos

Un agradecimiento especial a [Alura LATAM](https://www.aluracursos.com) y [Oracle](https://www.oracle.com) por
proporcionar los materiales y el desafío que hicieron posible este
proyecto. A continuación, algunos recursos externos que fueron de gran ayuda:

1. **Cursos de Alura LATAM:**
    * 1.1. [Java: creando tu primera aplicación](https://app.aluracursos.com/course/java-creando-primera-aplicacion)
    * 1.2. [Java: aplicando la Orientación a Objetos](https://app.aluracursos.com/course/java-aplicando-orientacion-objetos)
    * 1.3. [Java: trabajar con listas y colecciones de datos](https://app.aluracursos.com/course/java-trabajar-listas-colecciones-datos)
    * 1.4. [Java: consumir API, escribir archivos y manejar errores](https://app.aluracursos.com/course/java-consumir-api-escribir-archivos-manejar-errores)

    
2. **Archivos proporcionados por Alura LATAM:**
    * 2.1. [Cards en Trello](https://trello.com/b/RU41cvaQ)

## Licencia

Este proyecto se distribuye bajo la [Licencia MIT](LICENSE.md), lo que permite su uso, modificación y distribución de
manera libre.
