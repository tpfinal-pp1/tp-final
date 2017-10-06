package com.TpFinal.services;

import java.io.File;
import java.util.ArrayList;
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

	provincias.forEach(p -> {
	    p.getLocalidades().forEach(l -> this.localidades.add(l));
	});

    }

    public List<Localidad> getLocalidades() {
	return localidades;
    }

    public Localidad getLocalidadFromCodPostal(String codPostal) {
	for (Localidad l : localidades) {
	    if (l.getCodigoPostal() == codPostal)
		return l;
	}
	return null;

    }

    public Object getProvinciaFromString(String provincia) {
	// TODO Auto-generated method stub
	return null;
    }

}
