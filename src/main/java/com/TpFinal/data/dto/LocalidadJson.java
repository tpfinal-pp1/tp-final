package com.TpFinal.data.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import com.TpFinal.utils.GeneradorDeDatos;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.vaadin.server.VaadinRequest;
import com.vaadin.util.CurrentInstance;


public class LocalidadJson {
	
	public List<Localidad> leer(String path)
	{
		List<Localidad> ret=null;
		Gson gson=new Gson();
		 try
		 {
			 Type tipo = new TypeToken<List<Localidad>>(){}.getType();
			 BufferedReader br = new BufferedReader(new FileReader(path));
			 ret=gson.fromJson(br, tipo);
		 }
		 catch (Exception e){
			 e.printStackTrace();
		 }
		 return ret;
	}


}
