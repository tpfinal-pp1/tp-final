package com.TpFinal.utils;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class XZCompressor {

    public static String descomprimir(String archivoADesComprimir,String path)throws Exception{

        //DESCOMPRIMIR
        FileInputStream fin = new FileInputStream(path+File.separator+archivoADesComprimir);
        BufferedInputStream in = new BufferedInputStream(fin);
        String descomprimido=archivoADesComprimir;

        if(archivoADesComprimir.contains(".xz")){
            int corte=archivoADesComprimir.indexOf(".xz");
            descomprimido=archivoADesComprimir.substring(0,corte);
        }
        File descomp=new File(path+File.separator+descomprimido);
        while (descomp.exists()) {
            String name="copia_" + descomp.getName();
            descomp = new File(path+File.separator+name);
        }
        System.out.println("descomp"+descomp.getName());
        FileOutputStream out2 = new FileOutputStream(path+File.separator+descomp.getName());
        XZInputStream xzIn = new XZInputStream(in);
        final byte[] buffer = new byte[8192];
        int n = 0;
        while (-1 != (n = xzIn.read(buffer))) {
            out2.write(buffer, 0, n);
        }
        out2.close();
        xzIn.close();
        return descomp.getName();
    }

    public static String comprimir(String archivoAComprimir,String path)throws Exception{
        FileInputStream inFile = new FileInputStream(path+File.separator+archivoAComprimir);
        LocalDate localDate=LocalDate.now();
        FileOutputStream outfile = new FileOutputStream(path+File.separator+"Backup-"+localDate.toString()+".mv.db.xz");
        LZMA2Options options = new LZMA2Options();

        options.setPreset(7); // play with this number: 6 is default but 7 works better for mid sized archives ( > 8mb)

        XZOutputStream out = new XZOutputStream(outfile, options);

        byte[] buf = new byte[8192];
        int size;
        while ((size = inFile.read(buf)) != -1)
            out.write(buf, 0, size);

        out.finish();
        out.close();
        return "Backup-"+localDate.toString()+".mv.db.xz";
    }


}
