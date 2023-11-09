package cl.ucn.PIPA.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Esta clase se encarga de listar y descargar archivos de datos NODE y EDGE
 * para ciudades. El origen de los datos es un servidor "en la nube".
 * 
 * Para ver cómo se usa esta clase, busque el "main" que está al final de 
 * este archivo.
 * 
 * Nótese: para que esta clase funcione, debe descargar el archivo jar
 * https://search.maven.org/remotecontent?filepath=org/json/json/20231013/json-20231013.jar
 * 
 * y agregarlo a las "biblotecas referenciadas" en su proyecto.
 * 
 * @see https://github.com/stleary/JSON-java
 */
public class CiudadesProvider
{
    private CiudadesProvider()
    {

    }

    private static CiudadesProvider theInstance = null;

    public static CiudadesProvider instance()
    {
        if (theInstance == null) {
            theInstance = new CiudadesProvider();
        }
        return theInstance;
    }

    private String getURLContents(String enlace) throws IOException
    {
        URL url = new URL(enlace);
        URLConnection connection = url.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }

        reader.close();

        String urlString = content.toString();
        return urlString;
    }

    private String getURLContentsZIP(String enlace) throws IOException
    {
        System.out.println("Downloading " + enlace);
        
        URL url = new URL(enlace);
        GZIPInputStream gzipInputStream = new GZIPInputStream(url.openStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream, "UTF-8"));

        String line;
        StringBuilder content = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        
        return content.toString();
    }

    public Ciudad ciudad(String nombre) throws IOException
    {
        URL url = new URL("https://losvilos.ucn.cl/eross/ciudades/get.php?d=" + nombre);
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
    }

    public List<String> list() throws IOException
    {
        URL url = new URL("https://losvilos.ucn.cl/eross/ciudades/list.php");
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
    }

    public class Ciudad
    {
        private String xmlNodes;
        private String xmlEdges;

        public Ciudad(String xmlNodes, String xmlEdges)
        {
            this.xmlNodes = xmlNodes;
            this.xmlEdges = xmlEdges;
        }

        public String getXmlNodes()
        {
            return xmlNodes;
        }

        public String getXmlEdges()
        {
            return xmlEdges;
        }
    }
}