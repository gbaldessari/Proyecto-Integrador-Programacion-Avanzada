package cl.ucn.PIPA.utils;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public abstract class Funciones {
    /**
     * Calcula la distancia en kilómetros entre dos puntos
     * geográficos utilizando la fórmula de Haversine.
     *
     * @param startLat Latitud del punto de inicio en grados.
     * @param startLong Longitud del punto de inicio en grados.
     * @param endLat Latitud del punto de destino en grados.
     * @param endLong Longitud del punto de destino en grados.
     * @return La distancia en kilómetros entre los dos puntos.
     */
    public static double haversine(final double startLat,
    final double startLong, final double endLat, final double endLong) {
        // Convertir las latitudes y longitudes de grados a radianes
        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));
        double newStartLat = Math.toRadians(startLat);
        double newEndLat = Math.toRadians(endLat);

        // Calcular la fórmula de Haversine
        double a = haversin(dLat) + Math.cos(newStartLat)
        * Math.cos(newEndLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Radio de la Tierra en kilómetros (aproximado)
        final double radioTierra = 6371;
        return radioTierra * c;
    }

    /**
     * Calcula la función haversin para un valor dado.
     *
     * @param val El valor para el cual se calculará la función haversin.
     * @return El resultado de la función haversin para el valor dado.
     */
    private static double haversin(final double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    /**
     * Convierte un objeto de tipo stringBuilder a Document.
     *
     * @param stringBuilder el StringBuilder a transformar
     * @return el documento transformado
     */
    public static Document convertStringBuilderToDocument(
    final StringBuilder stringBuilder) {
        Document document = null;
        try {
            // Crear un DocumentBuilderFactory
            DocumentBuilderFactory factory
            = DocumentBuilderFactory.newInstance();

            // Crear un DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Convertir el contenido del StringBuilder en una cadena
            String xmlString = stringBuilder.toString();

            // Crear un InputSource desde la cadena
            InputSource inputSource
            = new InputSource(new StringReader(xmlString));

            // Parsear el InputSource en un Document
            document = builder.parse(inputSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * Metodo para escapar caracteres especiales en XML.
     *
     * @param input texto a evaluar
     * @return texto evaluado
     */
    public static String escapeXML(final String input) {
        return input.replaceAll("&", "&amp;");
    }
}
