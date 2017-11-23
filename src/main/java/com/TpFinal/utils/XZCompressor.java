package com.TpFinal.utils;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XZCompressor {
    @Deprecated
    public static String descomprimir(String archivoADesComprimir, String NombreDesComprimido,String path)throws Exception{
        //DESCOMPRIMIR
        FileInputStream fin = new FileInputStream(path+archivoADesComprimir+".xz");
        BufferedInputStream in = new BufferedInputStream(fin);
        FileOutputStream out2 = new FileOutputStream(path+NombreDesComprimido);
        XZInputStream xzIn = new XZInputStream(in);
        final byte[] buffer = new byte[8192];
        int n = 0;
        while (-1 != (n = xzIn.read(buffer))) {
            out2.write(buffer, 0, n);
        }
        out2.close();
        xzIn.close();
        return NombreDesComprimido;
    }

    public static String descomprimir(String archivoADesComprimir,String path)throws Exception{
        //DESCOMPRIMIR
        FileInputStream fin = new FileInputStream(path+"comprimido_"+archivoADesComprimir);
        BufferedInputStream in = new BufferedInputStream(fin);
        FileOutputStream out2 = new FileOutputStream(path+"descomprimido_"+archivoADesComprimir);
        XZInputStream xzIn = new XZInputStream(in);
        final byte[] buffer = new byte[8192];
        int n = 0;
        while (-1 != (n = xzIn.read(buffer))) {
            out2.write(buffer, 0, n);
        }
        out2.close();
        xzIn.close();
        return "descomprimido_"+archivoADesComprimir;
    }

    public static String comprimir(String archivoAComprimir,String path)throws Exception{
        FileInputStream inFile = new FileInputStream(path+archivoAComprimir);
        FileOutputStream outfile = new FileOutputStream(path+"comprimido_"+archivoAComprimir);
        LZMA2Options options = new LZMA2Options();

        options.setPreset(7); // play with this number: 6 is default but 7 works better for mid sized archives ( > 8mb)

        XZOutputStream out = new XZOutputStream(outfile, options);

        byte[] buf = new byte[8192];
        int size;
        while ((size = inFile.read(buf)) != -1)
            out.write(buf, 0, size);

        out.finish();

        return "comprimido_"+archivoAComprimir;
    }
    @Deprecated
    public static String comprimir(String archivoAComprimir, String nombreComprimido,String path)throws Exception{
        FileInputStream inFile = new FileInputStream(path+archivoAComprimir);
        FileOutputStream outfile = new FileOutputStream(path+nombreComprimido+".xz");

        LZMA2Options options = new LZMA2Options();

        options.setPreset(7); // play with this number: 6 is default but 7 works better for mid sized archives ( > 8mb)

        XZOutputStream out = new XZOutputStream(outfile, options);

        byte[] buf = new byte[8192];
        int size;
        while ((size = inFile.read(buf)) != -1)
            out.write(buf, 0, size);

        out.finish();

        return nombreComprimido;
    }

}
