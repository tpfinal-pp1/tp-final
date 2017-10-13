package com.TpFinal.services;

import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.interfaces.DAOPublicacion;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.*;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PublicacionService {
    private DAOPublicacion dao;
    public final static PublicacionVenta INSTANCIA_VENTA= InstanciaPublicacionVenta();
   public final static PublicacionAlquiler INSTANCIA_ALQUILER= InstanciaPublicacionAlquiler();



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
    	boolean ret=true;
    	if(entidad!=null) {
    		if(entidad.getId()!=null) {
    			Publicacion antigua= dao.findById(entidad.getId());
    			if(!antigua.getInmueble().equals(entidad.getInmueble())){
    				antigua.getInmueble().getPublicaciones().remove(antigua);
    				dao.saveOrUpdate(antigua);
    			}
    		}
    		
    		 ret= dao.saveOrUpdate(entidad);
    	}
       return ret;
    }
    
    //ToDelete
    @Deprecated
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

     static PublicacionAlquiler InstanciaPublicacionAlquiler() {
        Persona p= new Persona();
        p.addRol(new Propietario());

      return  new PublicacionAlquiler.Builder()
                 .setValorCuota(new BigDecimal(0))
                 .setFechaPublicacion(LocalDate.now())
                 .setMoneda(TipoMoneda.Pesos)
                 .setContratoAlquiler(new ContratoAlquiler())
                 .setInmueble(new Inmueble.Builder()
                         .setaEstrenar(false)
                         .setCantidadAmbientes(0)
                         .setCantidadCocheras(0)
                         .setCantidadDormitorios(0)
                         .setClaseInmueble(ClaseInmueble.OtroInmueble)
                         .setConAireAcondicionado(false)
                         .setConJardin(false)
                         .setConParilla(false)
                         .setConPileta(false)
                         .setDireccion(new Direccion.Builder()
                                 .setCalle("")
                                 .setCodPostal("")
                                 .setCoordenada(new Coordenada())
                                 .setLocalidad("")
                                 .setNro(0)
                                 .setPais("Argentina")
                                 .setProvincia("")
                                 .build())
                         .setEstadoInmueble(EstadoInmueble.NoPublicado)
                         .setPropietario((Propietario)p.getRol(Rol.Propietario))
                         .setSuperficieCubierta(0)
                         .setSuperficieTotal(0)
                         .setTipoInmueble(TipoInmueble.Vivienda)
                         .build())
                 .build();
    }

    static  PublicacionVenta InstanciaPublicacionVenta(){
        Persona p= new Persona();
        p.addRol(new Propietario());
        PublicacionVenta PV=new PublicacionVenta.Builder()
                .setPrecio(new BigDecimal(0))
                .setFechaPublicacion(LocalDate.now())
                .setMoneda(TipoMoneda.Pesos)
                .setContratoVenta(new ContratoVenta())
                .setInmueble(new Inmueble.Builder()
                        .setaEstrenar(false)
                        .setCantidadAmbientes(0)
                        .setCantidadCocheras(0)
                        .setCantidadDormitorios(0)
                        .setClaseInmueble(ClaseInmueble.OtroInmueble)
                        .setConAireAcondicionado(false)
                        .setConJardin(false)
                        .setConParilla(false)
                        .setConPileta(false)
                        .setDireccion(new Direccion.Builder()
                                .setCalle("")
                                .setCodPostal("")
                                .setCoordenada(new Coordenada())
                                .setLocalidad("")
                                .setNro(0)
                                .setPais("Argentina")
                                .setProvincia("")
                                .build())
                        .setEstadoInmueble(EstadoInmueble.NoPublicado)
                        .setPropietario((Propietario)p.getRol(Rol.Propietario))
                        .setSuperficieCubierta(0)
                        .setSuperficieTotal(0)
                        .setTipoInmueble(TipoInmueble.Vivienda)
                        .build())
                .build();
        return PV;

    }


}
