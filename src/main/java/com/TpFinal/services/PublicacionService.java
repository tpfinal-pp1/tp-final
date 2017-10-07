package com.TpFinal.services;

import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.interfaces.DAOPublicacion;
import com.TpFinal.data.dto.publicacion.EstadoPublicacion;
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

    public List<Publicacion> readAll() {
        return dao.readAll();
    }

    public boolean delete(Publicacion entidad) {
        return dao.logicalDelete(entidad);
    }

    public boolean save(Publicacion entidad) {
        return dao.saveOrUpdate(entidad);
    }
    
    public boolean updateBidireccioal(Publicacion p) {
    	boolean ret=true;
    	Publicacion publicacion = dao.findById(p.getId());
    	if(p.getInmueble()==null || !p.getInmueble().equals(publicacion.getInmueble())) {
    		publicacion.getInmueble().getPublicaciones().remove(publicacion);
    	}
    	dao.saveOrUpdate(publicacion);
    	dao.saveOrUpdate(p);
    	return ret;
    }

    public Publicacion findById(Long id) {
        return dao.findById(id);
    }


    public List<Publicacion> readAll(String stringFilter) {
        ArrayList <Publicacion> arrayList = new ArrayList();
        List<Publicacion> publicaciones=dao.readAllActives();
        if(stringFilter!=""){

            for (Publicacion publicacion : publicaciones) {

                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || publicacion.toString().toLowerCase()
                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {

                    arrayList.add(publicacion);
                }

            }
        }
        else{
            arrayList.addAll(publicaciones);
        }

        Collections.sort(arrayList, new Comparator<Publicacion>() {

            @Override
            public int compare(Publicacion o1, Publicacion o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        for(Publicacion p : arrayList){
            if(p.getInmueble() != null){
                //p.setPropietarioPublicacion(p.getInmueble().getPropietario());
            }
            if(p.getEstadoPublicacion() == null)
                p.setEstadoPublicacion(EstadoPublicacion.Activa);
        }
        return arrayList;

    }




}
