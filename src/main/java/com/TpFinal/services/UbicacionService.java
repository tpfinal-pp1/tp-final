package com.TpFinal.services;

import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.properties.Parametros;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.util.Map;

public class UbicacionService {

   //GeoCoding
   private final String geocodeFormat="json";
    private final String baseGeoCodingUrl ="https://maps.googleapis.com/maps/api/geocode/"+geocodeFormat+"?";



    //Static Maps
    private final String baseStaticMapsUrl ="https://maps.googleapis.com/maps/api/staticmap?";
    private final String size="600x300";
    private final String mapFormat ="jpg";
    private final String markerName="A";
    private final String path="Files"+ File.separator;


    public Coordenada geoCode(Direccion direccion){
       String filename= dowloadGeoCodingData(direccion);

       Coordenada ret=null;
        if(filename.equals(""))
            return new Coordenada(null,null);
        try {
            ret= getCoordinatesFromJson(path+filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private Coordenada getCoordinatesFromJson(String filename) throws FileNotFoundException {

        final JsonParser parser = new JsonParser();
        final JsonElement jsonElement = parser.parse(new FileReader(filename));
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        //RESULTS
        Map.Entry<String, JsonElement> results=jsonObject.entrySet().iterator().next();
        final JsonElement value = results.getValue();
        //[
        JsonArray array=value.getAsJsonArray();
        //{
        if(array.size()==0){
            return new Coordenada(null,null);
        }
        JsonObject elementos=array.get(0).getAsJsonObject();
        // "geometry" : {
        JsonObject geometry=elementos.get("geometry").getAsJsonObject();
        // "location" : {
         JsonObject coordenada=geometry.get("location").getAsJsonObject();
       // "lat" :
        Double lat=coordenada.get("lat").getAsDouble();
        // "lng" :
        Double lon=coordenada.get("lng").getAsDouble();

        return new Coordenada(lat,lon);
    }

    private String replaceSpaces(String stringconEspacios){
        String ret="";
        for (int i = 0; i <stringconEspacios.length() ; i++) {
            char actual=stringconEspacios.charAt(i);
            if(actual==' '){
                actual='+';
            }
            ret=ret+actual;

        }
        return ret;

    }

    private String dowloadGeoCodingData(Direccion direccion){
        URL url = null;
        InputStream in = null;
        String filename="";
        try {

            url = new URL(baseGeoCodingUrl + "address="+direccion.getNro()
                    +"+"+ replaceSpaces(direccion.getCalle())+",+"
                    + replaceSpaces(direccion.getLocalidad())+",+"
                    + replaceSpaces(direccion.getProvincia())+"&key="+Parametros.getProperty("geoKey"));
            in = new BufferedInputStream(url.openStream());
        } catch (Exception e) {
            System.err.println("No hay Conexion a Internet... Geocoding ha fallado");
            return filename;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        try {
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] response = out.toByteArray();
        FileOutputStream fos = null;
        try {
            File files=new File("Files");
            if(!files.exists())
                files.mkdir();
            filename="geocode_"+Instant.now().toEpochMilli();
            if(filename.length()>20){
                filename=filename.substring(0,20);
            }
            filename=filename+"."+ geocodeFormat;
            fos = new FileOutputStream(path+ filename);
            fos.write(response);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }


    private Image getMapImage(Coordenada coordinates){
        Image image =null;
        if(coordinates.equals(new Coordenada(null,null)))
            return null;
        try {
            String file=dowloadGStaticMapsWithMarker(coordinates.toString());
            if(file.equals("")){
                return null;
            }
            File pathToFile = new File(path+file);

            image = ImageIO.read(pathToFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return image;
    }

    public Image getMapImage(Inmueble inm){

        Coordenada coordenadas=inm.getDireccion().getCoordenada();
        if(coordenadas.equals(new Coordenada(null,null))) {
            coordenadas = geoCode(inm.getDireccion());
        }

        return getMapImage(coordenadas);
    }


    //Descarga el mapa en /Files
    private String dowloadGStaticMapsWithMarker(String coordinates) {
        URL url = null;
        InputStream in = null;
        String filename="";

        try {
            url = new URL(baseStaticMapsUrl +"center="+coordinates+"&zoom=16&" +
                    "scale=2&" +
                    "size="+size+"&maptype=roadmap" +
                    "&key="+ Parametros.getProperty("mapsKey")+"&mapFormat="+ mapFormat +"" +
                    "&visual_refresh=true&markers=size:mid%7Ccolor:0x162ce9%7C" +
                    "label:"+markerName+"%7C"+coordinates);
            in = new BufferedInputStream(url.openStream());
        } catch (Exception e) {
            System.err.println("No hay Conexion a Internet... Static Maps ha fallado");
            return filename;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        try {
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] response = out.toByteArray();
        FileOutputStream fos = null;
        try {
            File files=new File("Files");
            if(!files.exists())
                files.mkdir();
            filename="map_"+Instant.now().toEpochMilli();
            if(filename.length()>20){
                filename=filename.substring(0,20);
            }
            filename=filename+"."+ mapFormat;
            fos = new FileOutputStream(path+ filename);
            fos.write(response);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

}

