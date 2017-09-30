package com.TpFinal.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dto.contrato.Contrato;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

public class ContratoUtil {
	
	  public Blob fileToBlob(File doc)  {
	    	Blob archivo=null;
	    	try {
	    		FileInputStream docInputStream= new FileInputStream(doc);
	    		 archivo= Hibernate.getLobCreator(ConexionHibernate.openSession()).createBlob(docInputStream, doc.length());
	    		
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return archivo;
		}
	    
	    //Esto lo pasamos al FileDownloader de vaadin
	    public StreamResource getDocStreamResource(Contrato contrato, String nombreArchivoConExtension) {
	    	@SuppressWarnings("serial")
			StreamSource ss = new StreamSource() {
				@Override
				public InputStream getStream() {
					InputStream is=null;
					try {
						Blob docBlob=contrato.getDocumento();
						byte[] docBlobBytes = docBlob.getBytes(1, (int) docBlob.length());
						is = new ByteArrayInputStream(docBlobBytes);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return is;
				}
			};
	    	StreamResource sr= new StreamResource(ss, nombreArchivoConExtension);
	    	return sr;
	    }
	    
	    @SuppressWarnings("resource")
		public boolean downloadFile(Contrato contrato, String downloadPath) {
	    	//Este es para descargar directamente
	    	boolean ret=true;
	    	try {
				Blob docBlob=contrato.getDocumento();
				byte[] docBlobBytes = docBlob.getBytes(1, (int) docBlob.length());
				FileOutputStream docFOS=new FileOutputStream(downloadPath);
				docFOS.write(docBlobBytes);
			} catch (Exception e) {
				ret=false;
				e.printStackTrace();
			}
	    	return ret;
	    }
		
		//No se usan los dejo como referencia 
		public void leerArchivo(Contrato c, String path) throws SQLException, IOException {
			Blob blob = c.getDocumento();
			 byte[] blobBytes = blob.getBytes(1, (int) blob.length());
			 guardar(path, blobBytes);
		}
		
		public void guardar(String filePath, byte[] fileBytes) throws IOException {
			 FileOutputStream outputStream = new FileOutputStream(filePath);
		        outputStream.write(fileBytes);
		        outputStream.close();
		}

}
