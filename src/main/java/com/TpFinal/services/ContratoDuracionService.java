package com.TpFinal.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.TpFinal.data.dao.DAOContratoDuracionImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoDuracion;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.view.duracionContratos.FiltroDuracion;

public class ContratoDuracionService {
    DAOContratoDuracion dao;

    public ContratoDuracionService() {
	dao = new DAOContratoDuracionImpl();
    }

    public boolean saveOrUpdate(ContratoDuracion p) {
	return dao.saveOrUpdate(p);
    }

    public boolean delete(ContratoDuracion p) {
	return dao.logicalDelete(p);
    }

    public List<ContratoDuracion> readAll() {
	return dao.readAllActives();
    }

    public List<ContratoDuracion> findAll(FiltroDuracion filtro) {
	List<ContratoDuracion> duraciones = dao.readAllActives().stream()
						.filter(filtro.getFiltroCompuesto())
						.collect(Collectors.toList());
	duraciones.sort(Comparator.comparing(ContratoDuracion::getId));
	return duraciones;
    }

    public static ContratoDuracion getInstancia() {
	return new ContratoDuracion.Builder()
		.setDescripcion("")
		.setDuracion(1)
		.build();
    }

}
