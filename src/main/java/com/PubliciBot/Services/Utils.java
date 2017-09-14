package com.PubliciBot.Services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Hugo on 25/06/2017.
 */
public class Utils {



    public static String getProperty(String nombreProperty)
    {
        return System.getProperty(nombreProperty);
    }

    public static String generarNombreArchivoImagen()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dateFormat.format(new Date()) + ".png";
    }

    public static boolean isExistFile(String file)
    {
        File archivo = new File(file);

        if (archivo.exists())
            return true;

        return false;
    }

    public static String renameFile(String fullPathSource, String pathDestino)
    {
        String ret = pathDestino + generarNombreArchivoImagen();
        Path source = Paths.get(fullPathSource);
        try {
            Files.move(source, source.resolveSibling(ret));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
