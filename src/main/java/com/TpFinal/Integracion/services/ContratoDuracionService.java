package com.TpFinal.Integracion.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.TpFinal.data.dao.DAOContratoDuracionImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoDuracion;
import com.TpFinal.UnitTests.dto.contrato.ContratoDuracion;

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
	
	public List<ContratoDuracion>readAll(){
		return dao.readAllActives();
	}
	
	 public synchronized List<ContratoDuracion> findAll(String stringFilter) {
	        ArrayList arrayList = new ArrayList();
	        List<ContratoDuracion> duracionesContratos=dao.readAllActives();
	        if(stringFilter!=""){

	            for (ContratoDuracion duracionContrato : duracionesContratos) {

	                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
	                                            || duracionContrato.toString().toLowerCase()
	                                            .contains(stringFilter.toLowerCase());
	                    if (passesFilter) {

	                        arrayList.add(duracionContrato);
	                    }

	            }
	        }
	        else{
	            arrayList.addAll(duracionesContratos);
	        }

	        Collections.sort(arrayList, new Comparator<ContratoDuracion>() {

	            @Override
	            public int compare(ContratoDuracion o1, ContratoDuracion o2) {
	                return (int) (o2.getId() - o1.getId());
	            }
	        });
	        return arrayList;
	    }
	 
	public static ContratoDuracion getInstancia() {
		return new ContratoDuracion.Builder()
				.setDescripcion("")
				.setDuracion(0)
				.build();
	}
	
	
}
