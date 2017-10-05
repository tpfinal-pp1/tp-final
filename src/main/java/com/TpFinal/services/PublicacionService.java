package com.TpFinal.services;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dao.interfaces.DAOPublicacion;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.publicacion.Publicacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PublicacionService {
    private DAOPublicacion dao;

    public PublicacionService() {
        dao = new DAOPublicacionImpl();
    }

    public List<Publicacion> readAll(String busqueda) {
        return dao.readAll();
    }

    public boolean delete(Publicacion entidad) {
        return dao.delete(entidad);
    }

    public boolean save(Publicacion entidad) {
        return dao.saveOrUpdate(entidad);
    }

    public Publicacion findById(Long id) {
        return dao.findById(id);
    }
    
    //TODO
    public synchronized List<Contrato> findAll(String stringFilter) {
    	throw new IllegalArgumentException("Falta implementar");
//        ArrayList arrayList = new ArrayList();
//        List<Contrato> contratos=daoContrato.readAllActives();
//		
//        if(stringFilter!=""){
//
//            for (Contrato contrato : contratos) {
//
//                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
//                                            || contrato.toString().toLowerCase()
//                                            .contains(stringFilter.toLowerCase());
//                    if (passesFilter) {
//
//                        arrayList.add(contrato);
//                    }
//
//            }
//        }
//        else{
//            arrayList.addAll(contratos);
//        }
//
//        Collections.sort(arrayList, new Comparator<Contrato>() {
//
//			@Override
//			public int compare(Contrato o1, Contrato o2) {
//				return (int) (o2.getId() - o1.getId());
//			}
//        });
//        return arrayList;
    }



}
