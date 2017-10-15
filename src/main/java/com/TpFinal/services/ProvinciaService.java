package com.TpFinal.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.TpFinal.data.dto.JsonUtil;
import com.TpFinal.data.dto.Localidad;
import com.TpFinal.data.dto.LocalidadRAW;
import com.TpFinal.data.dto.Provincia;
import com.vaadin.server.VaadinRequest;
import com.vaadin.util.CurrentInstance;

public class ProvinciaService {
    public enum modoLecturaJson {
	server, local
    };

    private static final String pathTest = "src" + File.separator + "main" + File.separator + "webapp" + File.separator
	    + "Localidades.json";

    private JsonUtil json;
    private String path;
    private List<Provincia> provincias;
    private List<Localidad> localidades = new ArrayList<>();

    public ProvinciaService(modoLecturaJson modoLectura) {
	if (modoLectura == modoLecturaJson.server) {
	    path = CurrentInstance.get(VaadinRequest.class).getService().getBaseDirectory() + File.separator
		    + "Localidades.json";
	} else {
	    path = pathTest;
	}
	json = new JsonUtil();
	getProvinciasJson();

    }

    public ProvinciaService() {
	this(modoLecturaJson.server);
    }

    public List<Provincia> getProvincias() {

	return this.provincias;
    }

    private void getProvinciasJson() {
	List<LocalidadRAW> localidades = json.leerRaw(path);
	this.provincias = json.rawToProvincias(localidades);
		Collections.sort(this.provincias, new Comparator<Provincia>() {
			@Override
			public int compare(Provincia o1, Provincia o2) {
				return o1.getNombre().compareTo(o2.getNombre());
			}
		});
	provincias.forEach(p -> {
	    p.getLocalidades().forEach(l -> this.localidades.add(l));
	});

    }

    public List<Localidad> getLocalidades() {
	return localidades;
    }



    public Localidad getLocalidadFromNombreAndProvincia(String nombreLocalidad, String nombreProvincia) {
	for (Localidad l : localidades) {
	    if (l.getNombre().equalsIgnoreCase(nombreLocalidad) && l.getProvincia().getNombre().equalsIgnoreCase(nombreProvincia)) {
		System.out.println("Localidad Encontrada!");
		return l;
	    }
	}
	System.out.print("Localidad no Encontrada");
	return null;

    }


    public Provincia getProvinciaFromString(String provincia) {
	for (Provincia p : provincias) {
	    if (p.getNombre().equalsIgnoreCase(provincia)) {
		System.out.println("Provincia Encontrada!");
		return p;
	    }
	}
	System.out.print("Provincia no Encontrada");
	return null;
    }

}
