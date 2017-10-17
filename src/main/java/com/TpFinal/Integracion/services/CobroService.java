package com.TpFinal.Integracion.services;

import com.TpFinal.data.dao.DAOCobroImpl;
import com.TpFinal.data.dao.interfaces.DAOCobro;
import com.TpFinal.UnitTests.dto.cobro.Cobro;
import com.TpFinal.UnitTests.dto.cobro.EstadoCobro;
import com.TpFinal.UnitTests.dto.contrato.Contrato;
import com.TpFinal.UnitTests.dto.contrato.ContratoAlquiler;
import com.TpFinal.UnitTests.dto.contrato.EstadoContrato;
import com.TpFinal.UnitTests.dto.contrato.TipoInteres;
import com.TpFinal.UnitTests.dto.persona.Persona;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class CobroService {
	
	DAOCobro dao;
	List<Cobro>cobros;
	public CobroService() {
		dao=new DAOCobroImpl();
		//llenarConDatosHardCodeados();
	}
	
	public boolean save(Cobro c) {
		return dao.saveOrUpdate(c);
	}
	
	public boolean delete(Cobro c) {
		return dao.logicalDelete(c);
	}
	
	public List<Cobro> readAll() {
		return dao.readAllActives();
	}
	
	public void calcularDatosFaltantes(List<Cobro>cobros) {
		cobros.forEach(c ->{
			if(hayQueCalcular(c)) {
				Long cantidadDias=ChronoUnit.DAYS.between(c.getFechaDeVencimiento(), LocalDate.now());
				if(cantidadDias>0) {
					if(c.getContrato().getTipoInteresPunitorio().equals(TipoInteres.Simple)) {
						BigDecimal interes= new BigDecimal(c.getContrato().getInteresPunitorio().toString());
						interes=interes.multiply(new BigDecimal(cantidadDias.toString()));
						interes=c.getMontoOriginal().multiply(interes);
						BigDecimal nuevoValor=c.getMontoOriginal().add(interes);
						c.setMontoRecibido(nuevoValor);
					}else if(c.getContrato().getTipoInteresPunitorio().equals(TipoInteres.Acumulativo)) {
						BigDecimal interes= new BigDecimal(c.getContrato().getInteresPunitorio().toString());
						BigDecimal valorAnterior= c.getMontoOriginal();
						for(int i=0; i<cantidadDias;i++) {
							valorAnterior=valorAnterior.add(valorAnterior.multiply(interes));
						}
						c.setMontoRecibido(valorAnterior);
					}
				}
			}
		});
	}
	
	private boolean hayQueCalcular(Cobro c) {
		return c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO) && !c.getContrato().getEstadoContrato().equals(EstadoContrato.EnProcesoDeCarga);
	}
	
	//Ver que se necesita "arriba"
	 public synchronized List<Cobro> findAll(String stringFilter) {
	        ArrayList arrayList = new ArrayList();
	        List<Cobro> cobros=dao.readAllActives();
	        if(stringFilter!=""){

	            for (Cobro cobro : cobros) {

	                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
	                                            || cobro.toString().toLowerCase()
	                                            .contains(stringFilter.toLowerCase());
	                    if (passesFilter) {

	                        arrayList.add(cobro);
	                    }

	            }
	        }
	        else{
	            arrayList.addAll(cobros);
	        }

	        Collections.sort(arrayList, new Comparator<Persona>() {

	            @Override
	            public int compare(Persona o1, Persona o2) {
	                return (int) (o2.getId() - o1.getId());
	            }
	        });
	        return arrayList;
	    }
	 
		//Ver que se necesita "arriba"
	 public synchronized List<Cobro> findByEstado(String stringFilter) {
	        ArrayList arrayList = new ArrayList();
	        List<Cobro> cobros=dao.readAllActives();
	        if(stringFilter!=""){

	            for (Cobro cobro : cobros) {

	                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
	                                            || cobro.getEstadoCobroString().toLowerCase()
	                                            .contains(stringFilter.toLowerCase());
	                    if (passesFilter) {

	                        arrayList.add(cobro);
	                    }

	            }
	        }
	        else{
	            arrayList.addAll(cobros);
	        }

	        Collections.sort(arrayList, new Comparator<Persona>() {

	            @Override
	            public int compare(Persona o1, Persona o2) {
	                return (int) (o2.getId() - o1.getId());
	            }
	        });
	        return arrayList;
	    }
	
	private void llenarConDatosHardCodeados() {
		List<Contrato>  contratos = new ContratoService().readAll();
		int cobroIndex = 0;
		for(int i = 0; i < contratos.size(); i++) {
			if (contratos.get(i) instanceof ContratoAlquiler) {
				addRandomPayment();
				cobros.get(cobroIndex).setContrato((ContratoAlquiler) contratos.get(i));
				cobroIndex++;
			}
		}
	}
	//FIXME este metodo es temporal hasta que se implemente el dao
	private void addRandomPayment(){
		Random randomGenerator = new Random();
		int upperBound = 500;
		int lowerBound = 100;
		Integer comision = randomGenerator.nextInt(upperBound-lowerBound)+lowerBound;
		Integer montoOriginal = randomGenerator.nextInt(upperBound-lowerBound)+lowerBound;
		Integer montoPropietario = randomGenerator.nextInt(upperBound-lowerBound)+lowerBound;
		Integer montoRecibido = randomGenerator.nextInt(upperBound-lowerBound)+lowerBound;
		Integer interes = randomGenerator.nextInt(upperBound-lowerBound)+lowerBound;;
		Integer numeroCuenta = randomGenerator.nextInt(upperBound-lowerBound)+lowerBound;
		Integer fechaPago = randomGenerator.nextInt(upperBound-lowerBound)+lowerBound;
		Integer fechaVencimiento = randomGenerator.nextInt(upperBound-lowerBound)+lowerBound;

		LocalDate pago = LocalDate.now().plusDays(fechaPago);
		LocalDate vencimiento = LocalDate.now().plusDays(fechaVencimiento);

		cobros.add(new Cobro.Builder()
				.setComision(new BigDecimal(comision))
				.setFechaDePago(pago)
				.setFechaDeVencimiento(vencimiento)
				.setMontoOriginal(new BigDecimal(montoOriginal))
				.setMontoPropietario(new BigDecimal(montoPropietario))
				.setMontoRecibido(new BigDecimal(montoRecibido))
				.setNumeroCuota(numeroCuenta)
				.setInteres(new BigDecimal(interes))
				.build());
	}
}
