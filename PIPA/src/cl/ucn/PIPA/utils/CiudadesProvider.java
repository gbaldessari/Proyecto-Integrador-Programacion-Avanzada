package cl.ucn.PIPA.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Esta clase se encarga de listar y descargar archivos de datos NODE y EDGE
 * para ciudades. El origen de los datos es un servidor "en la nube".
 */
public final class CiudadesProvider {
    /** */
    private static CiudadesProvider theInstance = null;

    private CiudadesProvider() {
    }

    public class Ciudad {
        /** */
        private StringBuilder xmlNodes;
        /** */
        private StringBuilder xmlEdges;

        /**
         * Objeto ciudad.
         *
         * @param xmlNodesEntregado xml de nodos
         * @param xmlEdgesEntregado xml de arcos
         */
        public Ciudad(final StringBuilder xmlNodesEntregado,
        final StringBuilder xmlEdgesEntregado) {
            xmlNodes = xmlNodesEntregado;
            xmlEdges = xmlEdgesEntregado;
        }

        /**
         * Funcion que retorna el archivo xml de nodos.
         *
         * @return el archivo xml de nodos
         */
        public final StringBuilder getXmlNodes() {
            return xmlNodes;
        }

        /**
         * Funcion que retorna el archivo xml de arcos.
         *
         * @return el archivo xml de arcos
         */
        public final StringBuilder getXmlEdges() {
            return xmlEdges;
        }
    }

    /**
     * Funcion que instancia el proveedor de ciudades.
     *
     * @return el proveedor de ciudades
     */
    public static CiudadesProvider instance() {
        if (theInstance == null) {
            theInstance = new CiudadesProvider();
        }
        return theInstance;
    }


    private StringBuilder getURLContentsZIP(final String enlace) {
        System.out.println("Downloading " + enlace);
        URL url = null;
        try {
            url = new URI(enlace).toURL();
            GZIPInputStream gzipInputStream = new GZIPInputStream(
            url.openStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
            gzipInputStream, "UTF-8"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(Funciones.escapeXML(line));
            }
            reader.close();
            return content;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Funcion que retorna la ciudad a partir de su nombre.
     *
     * @param nombre el nombre de la ciudad
     * @return la ciudad obtenida
     */
    public Ciudad ciudad(final String nombre) {
        URL url = null;
        try {
            url = new URI(
            "https://losvilos.ucn.cl/eross/ciudades/get.php?d="
            + nombre).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        if (url != null) {
            try {
                HttpURLConnection connection
                = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                JSONObject json = new JSONObject(response.toString());
                String nodes = json.getString("nodes");
                String edges = json.getString("edges");

                return new Ciudad(getURLContentsZIP(nodes),
                getURLContentsZIP(edges));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Funcion que retorna los nombres de las ciudades.
     * @return los nombres de las ciudades
     */
    public List<String> list() {
        URL url = null;
        try {
            url = new URI(
            "https://losvilos.ucn.cl/eross/ciudades/list.php").toURL();
            HttpURLConnection connection
                = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                JSONArray json = new JSONArray(response.toString());
                ArrayList<String> result = new ArrayList<>();

                for (Object o : json) {
                    result.add(o.toString());
                }
                return result;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
