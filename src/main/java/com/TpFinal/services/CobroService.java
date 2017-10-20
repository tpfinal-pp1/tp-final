package com.TpFinal.services;


import com.TpFinal.data.dao.DAOCobroImpl;
import com.TpFinal.data.dao.interfaces.DAOCobro;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.dto.persona.Persona;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.tapestry.pageload.EstablishDefaultParameterValuesVisitor;
import org.omg.DynamicAny.DynAnySeqHelper;

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
		List<Cobro>ret=dao.readAllActives();
		calcularDatosFaltantes(ret);
		return ret;
	}
	
	public List<Cobro> readCobrosByEstado(EstadoCobro estado){
		return  dao.findCobrobyEstado(estado);
	}
	
	//lo deje en public para testearlo
	public void calcularDatosFaltantes(List<Cobro>cobros) {
		cobros.forEach(c ->{
			if(hayQueCalcular(c)) {
				Long cantidadDias=ChronoUnit.DAYS.between(c.getFechaDeVencimiento(), LocalDate.now());
				if(cantidadDias>0) {
					if(c.getContrato().getTipoInteresPunitorio().equals(TipoInteres.Simple)) {
						BigDecimal interes= new BigDecimal(c.getContrato().getInteresPunitorio().toString());
						System.out.println("En la bd "+interes.toString());
						interes=interes.divide(new BigDecimal("100"));
						System.out.println("despues de pasarlo a decimal "+interes.toString());
						interes=interes.multiply(new BigDecimal(cantidadDias.toString()));
						interes=c.getMontoOriginal().multiply(interes);
						c.setInteres(interes);
						BigDecimal nuevoValor=c.getMontoOriginal().add(interes);
						c.setMontoRecibido(nuevoValor);
						BigDecimal comision= c.getMontoRecibido().multiply(new BigDecimal("0.06"));
						c.setComision(comision);
						BigDecimal montoPropietario=c.getMontoRecibido().subtract(comision);
						c.setMontoPropietario(montoPropietario);
					}else if(c.getContrato().getTipoInteresPunitorio().equals(TipoInteres.Acumulativo)) {
						BigDecimal interes= new BigDecimal(c.getContrato().getInteresPunitorio().toString());
						interes=interes.divide(new BigDecimal("100"));
						BigDecimal valorAnterior= c.getMontoOriginal();
						for(int i=0; i<cantidadDias;i++) {
							valorAnterior=valorAnterior.add(valorAnterior.multiply(interes));
						}
						c.setMontoRecibido(valorAnterior);
						c.setInteres(c.getMontoRecibido().subtract(c.getMontoOriginal()));
						BigDecimal comision= c.getMontoRecibido().multiply(new BigDecimal("0.06"));
						c.setComision(comision);
						BigDecimal montoPropietario=c.getMontoRecibido().subtract(comision);
						c.setMontoPropietario(montoPropietario);
					}
				}
			}
		});
	}
	
	private boolean hayQueCalcular(Cobro c) {
		return c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO) && c.getContrato().getEstadoContrato().equals(EstadoContrato.Vigente);
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
