package com.TpFinal.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoImpl;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.DAOImpl;
import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dao.interfaces.DAOContrato;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.persona.Persona;

public class ContratoService {
	private DAOContratoAlquiler daoAlquiler;
    private DAOContratoVenta daoVenta;
    private DAOContrato daoContrato;

    
    public ContratoService() {
    	daoAlquiler=new DAOContratoAlquilerImpl();
        daoVenta= new DAOContratoVentaImpl();
        daoContrato= new DAOContratoImpl();
    }
    
    public boolean save(Contrato contrato, File doc) {
    	boolean ret=false;
    	if(contrato.getClass().equals(ContratoVenta.class)) {
    		ContratoVenta c=(ContratoVenta)contrato;
    		ret=daoVenta.saveContrato(c, doc);
    	}else {
    		ContratoAlquiler c= (ContratoAlquiler)contrato;
    		ret=daoAlquiler.saveContrato(c, doc);
    	}
    	return ret;
    }
    
    public boolean save(Contrato contrato) {
    	boolean ret=false;
    	if(contrato.getClass().equals(ContratoVenta.class)) {
    		ContratoVenta c=(ContratoVenta)contrato;
    		ret=daoVenta.save(c);
    	}else {
    		ContratoAlquiler c= (ContratoAlquiler)contrato;
    		ret=daoAlquiler.save(c);
    	}
    	return ret;
    }
    
    public boolean delete(Contrato contrato) {
    	boolean ret=false;
    	if(contrato.getClass().equals(ContratoVenta.class)) {
    		ContratoVenta c=(ContratoVenta)contrato;
    		ret=daoVenta.logicalDelete(c);
    	}else {
    		ContratoAlquiler c= (ContratoAlquiler)contrato;
    		ret=daoAlquiler.logicalDelete(c);
    	}
    	return ret;
    }
    
    public boolean deleteSerious(Contrato contrato) {
    	boolean ret=false;
    	if(contrato.getClass().equals(ContratoVenta.class)) {
    		ContratoVenta c=(ContratoVenta)contrato;
    		ret=daoVenta.delete(c);
    	}else {
    		ContratoAlquiler c= (ContratoAlquiler)contrato;
    		ret=daoAlquiler.delete(c);
    	}
    	return ret;
    }
    
    public boolean update(Contrato contrato) {
    	boolean ret=false;
    	if(contrato.getClass().equals(ContratoVenta.class)) {
    		ContratoVenta c=(ContratoVenta)contrato;
    		ret=daoVenta.update(c);
    	}else {
    		ContratoAlquiler c= (ContratoAlquiler)contrato;
    		ret=daoAlquiler.update(c);
    	}
    	return ret;
    }
    
    public boolean update(Contrato contrato, File doc) {
    	boolean ret=false;
    	if(contrato.getClass().equals(ContratoVenta.class)) {
    		ContratoVenta c=(ContratoVenta)contrato;
    		ret=daoVenta.saveContrato(c, doc);
    	}else {
    		ContratoAlquiler c= (ContratoAlquiler)contrato;
    		ret=daoAlquiler.saveContrato(c, doc);
    	}
    	return ret;
    }
    
    public List<ContratoAlquiler>readAllAlquiler(){
    	return daoAlquiler.readAllActives();
    }
    
    public List<ContratoVenta>readAllVenta(){
    	return daoVenta.readAllActives();
    }
    
    public List<Contrato>readAllContratos(){
    	return daoContrato.readAllActives();
    }
    
	 public synchronized List<ContratoAlquiler> findAllAlquiler(String stringFilter) {
	        ArrayList arrayList = new ArrayList();
	        List<ContratoAlquiler> contratos=daoAlquiler.readAllActives();
	        if(stringFilter!=""){

	            for (ContratoAlquiler contrato : contratos) {

	                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
	                                            || contrato.toString().toLowerCase()
	                                            .contains(stringFilter.toLowerCase());
	                    if (passesFilter) {

	                        arrayList.add(contrato);
	                    }

	            }
	        }
	        else{
	            arrayList.addAll(contratos);
	        }

	        Collections.sort(arrayList, new Comparator<ContratoAlquiler>() {

				@Override
				public int compare(ContratoAlquiler o1, ContratoAlquiler o2) {
					return (int) (o2.getId() - o1.getId());
				}
	        });
	        return arrayList;
	    }




	public synchronized List<ContratoAlquiler> findAllVenta(String stringFilter) {
	        ArrayList arrayList = new ArrayList();
	        List<ContratoVenta> contratos=daoVenta.readAllActives();
	        if(stringFilter!=""){

	            for (ContratoVenta contrato : contratos) {

	                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
	                                            || contrato.toString().toLowerCase()
	                                            .contains(stringFilter.toLowerCase());
	                    if (passesFilter) {

	                        arrayList.add(contrato);
	                    }

	            }
	        }
	        else{
	            arrayList.addAll(contratos);
	        }

	        Collections.sort(arrayList, new Comparator<ContratoVenta>() {

				@Override
				public int compare(ContratoVenta o1, ContratoVenta o2) {
					return (int) (o2.getId() - o1.getId());
				}
	        });
	        return arrayList;
	    }

	public synchronized List<Contrato> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        List<Contrato> contratos=daoContrato.readAllActives();
        if(stringFilter!=""){

            for (Contrato contrato : contratos) {

                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                                            || contrato.toString().toLowerCase()
                                            .contains(stringFilter.toLowerCase());
                    if (passesFilter) {

                        arrayList.add(contrato);
                    }

            }
        }
        else{
            arrayList.addAll(contratos);
        }

        Collections.sort(arrayList, new Comparator<Contrato>() {

			@Override
			public int compare(Contrato o1, Contrato o2) {
				return (int) (o2.getId() - o1.getId());
			}
        });
        return arrayList;
    }

	

}
