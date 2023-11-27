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
    private static CiudadesProvider theInstance = null;

    private CiudadesProvider() {
    }

    public class Ciudad {
        private StringBuilder xmlNodes;
        private StringBuilder xmlEdges;

        public Ciudad(final StringBuilder xmlNodes, final StringBuilder xmlEdges) {
            this.xmlNodes = xmlNodes;
            this.xmlEdges = xmlEdges;
        }

        public final StringBuilder getXmlNodes() {
            return xmlNodes;
        }

        public final StringBuilder getXmlEdges() {
            return xmlEdges;
        }
    }

    public static CiudadesProvider instance() {
        if (theInstance == null) {
            theInstance = new CiudadesProvider();
        }
        return theInstance;
    }

    /*
     * private String getURLContents(String enlace){
     * URL url = null;
     * try {
     * url = new URI(enlace).toURL();
     * } catch (URISyntaxException | MalformedURLException e){
     * e.printStackTrace();
     * }
     * if(url!=null){
     * try {
     * URLConnection connection = url.openConnection();
     * BufferedReader reader = new BufferedReader(new
     * InputStreamReader(connection.getInputStream()));
     * StringBuilder content = new StringBuilder();
     * String line;
     * 
     * while ((line = reader.readLine()) != null) {
     * content.append(line).append("\n");
     * }
     * reader.close();
     * 
     * String urlString = content.toString();
     * return urlString;
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * return null;
     * }
     */
    private StringBuilder getURLContentsZIP(final String enlace) throws IOException {
        System.out.println("Downloading " + enlace);
        URL url = null;
        try {
            url = new URI(enlace).toURL();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (url != null) {
            GZIPInputStream gzipInputStream = new GZIPInputStream(url.openStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream, "UTF-8"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(Funciones.escapeXML(line));
            }
            reader.close();
            return content;
        }
        return null;
    }

    public Ciudad ciudad(final String nombre) {
        URL url = null;
        try {
            url = new URI("https://losvilos.ucn.cl/eross/ciudades/get.php?d=" + nombre).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        if(url!=null){
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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

                return new Ciudad(getURLContentsZIP(nodes), getURLContentsZIP(edges));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<String> list(){
        URL url = null;
        try {
            url = new URI("https://losvilos.ucn.cl/eross/ciudades/list.php").toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        if(url!=null){
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}