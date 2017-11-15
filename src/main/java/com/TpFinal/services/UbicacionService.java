package com.TpFinal.services;

import com.TpFinal.properties.Parametros;

import java.io.*;
import java.net.URL;
import java.time.Instant;

public class UbicacionService {
    private final String baseUrl="https://maps.googleapis.com/maps/api/staticmap?";
    private final String size="600x300";
    private final String format="jpg";
    private final String markerName="A";
    private final String path="Files"+ File.separator;


    //Descarga el mapa en /Files
    public String dowloadGStaticMapsWithMarker(String coordinates) {
        URL url = null;
        InputStream in = null;
        String filename="";
        try {
            url = new URL(baseUrl+"center="+coordinates+"&zoom=16&" +
                    "scale=false&" +
                    "size="+size+"&maptype=roadmap" +
                    "&key="+ Parametros.getProperty("mapsKey")+"&format="+format+"" +
                    "&visual_refresh=true&markers=size:mid%7Ccolor:0x162ce9%7C" +
                    "label:"+markerName+"%7C"+coordinates);
            in = new BufferedInputStream(url.openStream());
        } catch (Exception e) {
            e.printStackTrace();
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
                filename=filename.substring(0,10);
            }
            filename=filename+"."+format;
            fos = new FileOutputStream(path+ filename);
            fos.write(response);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

}

