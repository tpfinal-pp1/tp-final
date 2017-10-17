package com.TpFinal.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class JsonUtil {
	
	public List<LocalidadRAW> leerRaw(String path)
	{
	    	
		List<LocalidadRAW> ret=null;
		Gson gson=new Gson();
		 try
		 {
			 Type tipo = new TypeToken<List<LocalidadRAW>>(){}.getType();
			 BufferedReader br = new BufferedReader(new FileReader(path));
			 ret=gson.fromJson(br, tipo);
		 }
		 catch (Exception e){
			 e.printStackTrace();
		 }
		 return ret;
	}
	
	public List<Provincia> leerProvincias(String path)
	{
		List<Provincia> ret=null;
		Gson gson=new Gson();
		 try
		 {
			 Type tipo = new TypeToken<List<Provincia>>(){}.getType();
			 BufferedReader br = new BufferedReader(new FileReader(path));
			 ret=gson.fromJson(br, tipo);
		 }
		 catch (Exception e){
			 e.printStackTrace();
		 }
		 return ret;
	}
	
	public boolean crearProviciasJson(List<Provincia>provincias, String path) {
		boolean ret= false;
		Gson gson= new GsonBuilder().setPrettyPrinting().create();
		String json=gson.toJson(provincias);
		
		try
		{
			FileWriter writer=new FileWriter(path);
			writer.write(json);
			writer.close();
			ret=true;
		}
		catch(Exception e)
		{
			ret=false;
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<Provincia> rawToProvincias(List<LocalidadRAW>raws){
		List<Provincia>ret=new ArrayList<>();
		Set<String>nombreProvincias=new HashSet<>();
		
		raws.forEach(r ->{
			nombreProvincias.add(r.getProvincia());
		});
		nombreProvincias.forEach(nombre -> { 
			ret.add(new Provincia(nombre));
		});
		
		raws.forEach(raw ->{
			ret.forEach(provincia ->{
				if(raw.getProvincia().equals(provincia.getNombre())) {
					Localidad l = new Localidad(raw.getNombre(), raw.getCodPosta());
					l.setProvincia(provincia);
					provincia.addLocalidad(l);
				}
			});
		});
		return ret;
	}


}
