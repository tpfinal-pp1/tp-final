package com.TpFinal.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileExistsException;

import com.TpFinal.data.dao.DAOCobroImpl;
import com.TpFinal.data.dao.interfaces.DAOCobro;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.cobro.TipoCobro;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.view.cobros.FiltroCobros;

public class CobroService {

	DAOCobro dao;
	List<Cobro> cobros;

	public CobroService() {
		dao = new DAOCobroImpl();
		// llenarConDatosHardCodeados();
	}

	public boolean save(Cobro c) {
		return dao.saveOrUpdate(c);
	}

	public boolean delete(Cobro c) {
		return dao.logicalDelete(c);
	}

	public List<Cobro> readAll() {
		List<Cobro> ret = dao.readAllActives();
		calcularDatosFaltantes(ret.stream().filter(c -> c.getContrato() instanceof ContratoAlquiler).collect(Collectors.toList()));
		return ret;
	}

	public List<Cobro> readNoCobrados() {
		return this.readAll().stream()
				.filter(c -> c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO)
						|| c.getMontoRecibido().compareTo(new BigDecimal("0.00")) == 0
						|| c.getMontoRecibido() == null
						|| c.getFechaDePago() == null)
				.collect(Collectors.toList());
	}

	public List<Cobro> readCobrosByEstado(EstadoCobro estado) {
		return dao.findCobrobyEstado(estado);
	}

	public void calcularDatosFaltantes(List<Cobro> cobros) {
		cobros.forEach(c -> {
			if (hayQueCalcular(c)) {
				Long cantidadDias = ChronoUnit.DAYS.between(c.getFechaDeVencimiento(), LocalDate.now());
				if (cantidadDias > 0) {
					ContratoAlquiler contrato=(ContratoAlquiler) c.getContrato();
					if (contrato.getTipoInteresPunitorio().equals(TipoInteres.Simple)) {
						BigDecimal interes = new BigDecimal(contrato.getInteresPunitorio().toString());
						interes = interes.divide(new BigDecimal("100"));
						interes = interes.multiply(new BigDecimal(cantidadDias.toString()));
						interes = c.getMontoOriginal().multiply(interes);
						c.setInteres(interes);
						BigDecimal nuevoValor = c.getMontoOriginal().add(interes);
						c.setMontoRecibido(nuevoValor);
						BigDecimal comision = c.getMontoRecibido().multiply(new BigDecimal("0.06"));
						c.setComision(comision);
						BigDecimal montoPropietario = c.getMontoRecibido().subtract(comision);
						c.setMontoPropietario(montoPropietario);
					} else if (contrato.getTipoInteresPunitorio().equals(TipoInteres.Acumulativo)) {
						BigDecimal interes = new BigDecimal(contrato.getInteresPunitorio().toString());
						interes = interes.divide(new BigDecimal("100"));
						BigDecimal valorAnterior = c.getMontoOriginal();
						for (int i = 0; i < cantidadDias; i++) {
							valorAnterior = valorAnterior.add(valorAnterior.multiply(interes));
						}
						c.setMontoRecibido(valorAnterior);
						c.setInteres(c.getMontoRecibido().subtract(c.getMontoOriginal()));
						BigDecimal comision = c.getMontoRecibido().multiply(new BigDecimal("0.06"));
						c.setComision(comision);
						BigDecimal montoPropietario = c.getMontoRecibido().subtract(comision);
						c.setMontoPropietario(montoPropietario);
					}
				}
			}
		});
	}

	private boolean hayQueCalcular(Cobro c) {
		return c.getTipoCobro().equals(TipoCobro.Alquiler)||c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO);
	}

	// Ver que se necesita "arriba"
	public List<Cobro> findAll(FiltroCobros filtro) {
		List<Cobro> cobros;
		cobros = this.readAll().stream()
				.filter(filtro.getFiltroCompuesto())
				.collect(Collectors.toList());
		cobros.sort(Comparator.comparing(Cobro::getFechaDeVencimiento).reversed());
		return cobros;
	}

	// Ver que se necesita "arriba"
	public synchronized List<Cobro> findByEstado(String stringFilter) {
		ArrayList arrayList = new ArrayList();
		List<Cobro> cobros = this.readAll();
		if (stringFilter != "") {

			for (Cobro cobro : cobros) {

				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| cobro.getEstadoCobroString().toLowerCase()
						.equals(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(cobro);
				}

			}
		} else {
			arrayList.addAll(cobros);
		}

		Collections.sort(arrayList, new Comparator<Cobro>() {

			@Override
			public int compare(Cobro o1, Cobro o2) {
				return (int) o1.getFechaDeVencimiento().compareTo(o2.getFechaDeVencimiento());
			}
		});
		return arrayList;
	}
	
	public boolean esAtrasado(Cobro c) {
		return c.getFechaDeVencimiento().compareTo(LocalDate.now())<0;
	}
	
	public void enviarMailPorPagoAtrasado(Cobro c) throws IllegalArgumentException, FileExistsException {
		ContratoAlquiler ca= (ContratoAlquiler) c.getContrato();
		Persona propietario=ca.getPropietario();
		if(propietario.getMail()==null || propietario.getMail().length()==0)
			throw new IllegalArgumentException("El mail del propietario es invalido");
		else {
			LocalDate fechaVencimiento=c.getFechaDeVencimiento();
			LocalDate fechaPago=c.getFechaDePago();
			
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
		    String fdvLinda = fechaVencimiento.format(formatters);
		    String fdpLinda = fechaPago.format(formatters);
			
			new MailSender().enviarMail(propietario.getMail(), "Pago atrasado", "El inquilino "+ca.getInquilinoContrato().getPersona().toString()+"\n "
					+"Fecha de vencimiento: "+fdvLinda+"\n "
					+"Fecha del pago: "+fdpLinda+"\n "
					+"Monto total: "+c.getMontoRecibido().toString()+" "+ca.getMoneda().toString()
					);
		}
	}

}
