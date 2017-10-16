package com.TpFinal.services;

import com.TpFinal.data.dto.cobro.Cobro;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CobroService {
	
	List<Cobro>cobros;
	
	public CobroService() {
		cobros=new ArrayList<>();
		llenarConDatosHardCodeados();
	}
	
	public void save(Cobro c) {
		cobros.add(c);
	}
	
	public void delete(Cobro c) {
		cobros.remove(c);
	}
	
	public void update(Cobro c) {
		//TODO al dope hacerlo ahora, tira un print como que funca
	}
	
	public List<Cobro> readAll() {
		return this.cobros;
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
	//TODO este metodo es temporal hasta que se implemente el dao
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
