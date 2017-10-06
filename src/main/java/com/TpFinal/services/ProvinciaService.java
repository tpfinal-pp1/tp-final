package com.TpFinal.services;

import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.dto.JsonUtil;
import com.TpFinal.data.dto.Localidad;
import com.TpFinal.data.dto.LocalidadRAW;
import com.TpFinal.data.dto.Provincia;

public class ProvinciaService {
    private JsonUtil json;
    private String path;
    private List<Provincia> provincias;
    private List<Localidad> localidades = new ArrayList<>();

    public ProvinciaService(String pathJson) {
	path = pathJson;
	json = new JsonUtil();
	getProvinciasJson();
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

}
