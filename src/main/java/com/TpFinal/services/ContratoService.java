package com.TpFinal.services;

import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoImpl;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContrato;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoVenta;

import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Propietario;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.exceptions.services.ContratoServiceException;
import com.TpFinal.view.contrato.FiltroContrato;
import com.TpFinal.view.reportes.ItemRepAlquileresACobrar;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class ContratoService {
    public static enum instancia {
	venta, alquiler
    };

    private final static Logger logger = Logger.getLogger(ContratoService.class);

    private DAOContratoAlquiler daoAlquiler;
    private DAOContratoVenta daoVenta;
    private DAOContrato daoContrato;
    private InmuebleService inmuebleService;
    private ContratoDuracionService contratoDuracionService;
    private boolean existe = false;

    BigDecimal gananciaInmobiliariaPagosCobrados;
    BigDecimal gananciaInmobiliariaTodosLosCobros;
    BigDecimal ingresosTotalesPagosCobrados;
    BigDecimal ingresosTotalesPagosPendientes;
    int cantidadPagosCobrados;
    int cantidadPagosPendientes;

    public ContratoService() {
	daoAlquiler = new DAOContratoAlquilerImpl();
	daoVenta = new DAOContratoVentaImpl();
	daoContrato = new DAOContratoImpl();
	inmuebleService = new InmuebleService();
	contratoDuracionService = new ContratoDuracionService();
	this.gananciaInmobiliariaPagosCobrados = BigDecimal.ZERO;
	this.gananciaInmobiliariaTodosLosCobros = BigDecimal.ZERO;
	this.ingresosTotalesPagosCobrados = BigDecimal.ZERO;
	this.ingresosTotalesPagosPendientes = BigDecimal.ZERO;
	this.cantidadPagosCobrados = 0;
	this.cantidadPagosPendientes = 0;
    }

    public List<ItemRepAlquileresACobrar> getCobrosOrdenadosPorAño() {

	LocalDate fechaActual = LocalDate.now();

	LocalDate fechaMesActual;

	if (fechaActual.getMonthValue() == 12) {
	    fechaMesActual = LocalDate.of(fechaActual.getYear() + 1, 1, 1);
	}

	else {
	    fechaMesActual = LocalDate.of(fechaActual.getYear(), fechaActual.getMonthValue() + 1, 1);
	}

	CobroService cobroService = new CobroService();

	List<ItemRepAlquileresACobrar> itemsReporte = new ArrayList<>();

	List<ContratoAlquiler> contratosVigentes = this.getContratosAlquilerVigentes();

	List<Cobro> cobros = new ArrayList<>();

	contratosVigentes.forEach(contrato -> {
	    if (contrato.getCobros() != null) {
		cobros.addAll(contrato.getCobros().stream().filter(c -> {

		    return c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO);
		})
			.filter(c -> {
			    return c.getFechaDeVencimiento().compareTo(fechaMesActual) < 0;
			})
			.collect(Collectors.toList()));

		cobroService.calcularDatosFaltantes(cobros);
		cobros.forEach(cobro -> {
		    itemsReporte.add(new ItemRepAlquileresACobrar(contrato.getInquilinoContrato(),
			    cobro, contrato.getMoneda()));
		});
	    }
	});

	itemsReporte.sort(Comparator.comparing(ItemRepAlquileresACobrar::getAnio).reversed()
		.thenComparing(ItemRepAlquileresACobrar::getNumeroMes)
		.thenComparing(ItemRepAlquileresACobrar::getApellido)
		.thenComparing(ItemRepAlquileresACobrar::getNombre));

	return itemsReporte;

    }

    public boolean verificarSiExisteCobroConMasDeUnAnio(LocalDate fechaActual) {

	// existe = false;

	List<ContratoAlquiler> contratosVigentes = this.getContratosAlquilerVigentes();

	List<Cobro> cobros = new ArrayList<>();

	contratosVigentes.forEach(contrato -> {
	    if (contrato.getCobros() != null) {
		cobros.addAll(contrato.getCobros().stream()
			.filter(c -> {

			    return c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO);
			})
			.collect(Collectors.toList()));

	    }
	});

	cobros.forEach(cobro -> {
	    if (Integer.valueOf(cobro.getFechaDeVencimiento().getYear()) < fechaActual.getYear()) {
		existe = true;
	    }
	});

	return existe;
    }

    public List<ItemRepAlquileresACobrar> getListadoAlquileresACobrar(Integer mesDesde, Integer yearDesde,
	    Integer mesHasta, Integer yearHasta) {
	CobroService cobroService = new CobroService();
	LocalDate fechaDesde = LocalDate.of(yearDesde, mesDesde, 1);

	LocalDate fechaHasta = LocalDate.of(yearHasta, mesHasta, 28);
	List<ItemRepAlquileresACobrar> itemsReporte = new ArrayList<>();

	List<ContratoAlquiler> contratosVigentes = this.getContratosAlquilerVigentes();

	List<Cobro> cobros = new ArrayList<>();

	contratosVigentes.forEach(contrato -> {
	    if (contrato.getCobros() != null) {
		cobros.addAll(contrato.getCobros().stream()
			.filter(c -> {
			    return c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO);
			})
			.filter(c -> {
			    return c.getFechaDeVencimiento().compareTo(fechaDesde) >= 0;
			})
			.filter(c -> {
			    return c.getFechaDeVencimiento().compareTo(fechaHasta) <= 0;
			})
			.collect(Collectors.toList()));

		cobroService.calcularDatosFaltantes(cobros);
		cobros.forEach(cobro -> {
		    itemsReporte.add(new ItemRepAlquileresACobrar(contrato.getInquilinoContrato(),
			    cobro, contrato.getMoneda()));
		});
	    }
	});

	itemsReporte.sort(Comparator.comparing(ItemRepAlquileresACobrar::getAnio).reversed()
		.thenComparing(ItemRepAlquileresACobrar::getNumeroMes)
		.thenComparing(ItemRepAlquileresACobrar::getApellido)
		.thenComparing(ItemRepAlquileresACobrar::getNombre));

	return itemsReporte;
    }

    public List<ItemRepAlquileresACobrar> getListadoAlquileresACobrar(LocalDate fechaDesde, LocalDate fechaHasta) {

	CobroService cobroService = new CobroService();

	List<ItemRepAlquileresACobrar> itemsReporte = new ArrayList<>();

	List<ContratoAlquiler> contratosVigentes = this.getContratosAlquilerVigentes();

	List<Cobro> cobros = new ArrayList<>();

	contratosVigentes.forEach(contrato -> {
	    if (contrato.getCobros() != null) {
		cobros.addAll(contrato.getCobros().stream()
			.filter(c -> {

			    return c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO);
			})
			.filter(c -> {

			    return fechaDesde != null ? c.getFechaDeVencimiento().compareTo(fechaDesde) >= 0 : true;
			})
			.filter(c -> {

			    return fechaHasta != null ? c.getFechaDeVencimiento().compareTo(fechaHasta) <= 0 : true;
			})
			.collect(Collectors.toList()));

		cobroService.calcularDatosFaltantes(cobros);
		cobros.forEach(cobro -> {

		    itemsReporte.add(new ItemRepAlquileresACobrar(contrato.getInquilinoContrato(),
			    cobro, contrato.getMoneda()));
		});
	    }
	});

	itemsReporte.sort(Comparator.comparing(ItemRepAlquileresACobrar::getAnio).reversed()
		.thenComparing(ItemRepAlquileresACobrar::getNumeroMes)
		.thenComparing(ItemRepAlquileresACobrar::getApellido)
		.thenComparing(ItemRepAlquileresACobrar::getNombre));

	return itemsReporte;
    }

    public List<Object> getListadoAlquileresACobrarObject(LocalDate fechaDesde, LocalDate fechaHasta) {

	CobroService cobroService = new CobroService();

	List<ItemRepAlquileresACobrar> itemsReporte = new ArrayList<>();

	List<ContratoAlquiler> contratosACobrar = this.getContratosAlquilerCobrables();

	List<Cobro> cobros = new ArrayList<>();

	contratosACobrar.forEach(contrato -> {
	    if (contrato.getCobros() != null) {
		cobros.addAll(contrato.getCobros().stream()
			.filter(c -> {
			    return c.getEstadoCobro().equals(EstadoCobro.NOCOBRADO);
			})
			.filter(c -> {
			    return fechaDesde != null ? c.getFechaDeVencimiento().compareTo(fechaDesde) >= 0 : true;
			})
			.filter(c -> {
			    return fechaHasta != null ? c.getFechaDeVencimiento().compareTo(fechaHasta) <= 0 : true;
			})
			.collect(Collectors.toList()));
		cobroService.calcularDatosFaltantes(cobros);
		cobros.forEach(cobro -> {
		    itemsReporte.add(new ItemRepAlquileresACobrar(contrato.getInquilinoContrato(),
			    cobro, contrato.getMoneda()));
		});
	    }
	});
	itemsReporte.sort(Comparator.comparing(ItemRepAlquileresACobrar::getAnio).reversed()
		.thenComparing(ItemRepAlquileresACobrar::getNumeroMes)
		.thenComparing(ItemRepAlquileresACobrar::getApellido)
		.thenComparing(ItemRepAlquileresACobrar::getNombre));

	return itemsReporte.stream().map(i -> (Object) i ).collect(Collectors.toList());
    }

    public List<ContratoAlquiler> getContratosAlquilerVigentes() {
	List<ContratoAlquiler> contratosVigentes = daoAlquiler.readAllActives().stream()
		.filter(c -> {
		    return c.getEstadoContrato().equals(EstadoContrato.Vigente);
		})
		.collect(Collectors.toList());
	return contratosVigentes;
    }

    public List<ContratoAlquiler> getContratosAlquilerCobrables() {
	List<ContratoAlquiler> contratosCobrables = daoAlquiler.readAllActives().stream()
		.filter(c -> {
		    return !c.getEstadoContrato().equals(EstadoContrato.EnProcesoDeCarga);
		})
		.collect(Collectors.toList());
	return contratosCobrables;
    }

    public boolean saveOrUpdate(Contrato contrato, File doc) throws ContratoServiceException {
	boolean ret = false;
	if (contrato.getId() != null) {
	    Contrato contratoAntiguo = daoContrato.findById(contrato.getId());
	    if (contrato.getInmueble() != null && !contratoAntiguo.getInmueble().equals(contrato.getInmueble())) {
		inmuebleService.desvincularContrato(contratoAntiguo);
	    }
	}
	if (contrato instanceof ContratoVenta) {
	    ContratoVenta c = (ContratoVenta) contrato;
	    if (doc != null) {
		ret = daoVenta.mergeContrato(c, doc);
		if (ret == false)
		    throw new ContratoServiceException("Fallo daoVenta.mergeContrato(c,doc) ");

	    } else {
		ret = daoVenta.merge(c);
		if (ret == false)
		    throw new ContratoServiceException("Fallo daoVenta.merge(c)");
	    }
	} else {
	    ContratoAlquiler c = (ContratoAlquiler) contrato;

	    if (doc != null) {
		ret = daoAlquiler.mergeContrato(c, doc);
		if (ret == false)
		    throw new ContratoServiceException("Fallo daAlquiler.mergeContrato(c, doc)");
	    } else {
		ret = daoAlquiler.merge(c);
		if (ret == false)
		    throw new ContratoServiceException("Fallo daoAlquiler.merge(c)");
	    }
	}

	return ret;
    }

    public boolean delete(Contrato contrato) {
	boolean ret = false;
	if (contrato instanceof ContratoVenta) {
	    ContratoVenta c = (ContratoVenta) contrato;
	    ret = daoVenta.logicalDelete(c);
	} else {
	    ContratoAlquiler c = (ContratoAlquiler) contrato;
	    ret = daoAlquiler.logicalDelete(c);
	}
	return ret;
    }

    public List<Contrato> readAll() {
	List<Contrato> ret = daoContrato.readAllActives();
	return ret;
    }

    public void actualizarEstadoContratosAlquiler() {
	List<Contrato> contratos = readAll();
	contratos.stream().filter(c -> c instanceof ContratoAlquiler)
		.map(c -> (ContratoAlquiler) c)
		.forEach(actualizarEstadoContrato());
    }

    private Consumer<? super ContratoAlquiler> actualizarEstadoContrato() {
	return contratoAlquiler -> {
	    if (getFechaVencimiento(contratoAlquiler).compareTo(LocalDate.now()) <= 0) {
		contratoAlquiler.setEstadoContrato(EstadoContrato.Vencido);
	    } else if (faltaMenosDeUnMesParaVencimiento(contratoAlquiler)) {
		contratoAlquiler.setEstadoContrato(EstadoContrato.ProximoAVencer);
	    }
	    daoAlquiler.merge(contratoAlquiler);
	};
    }

    private boolean faltaMenosDeUnMesParaVencimiento(ContratoAlquiler contratoAlquiler) {
	boolean ret = true && contratoAlquiler.getEstadoContrato().equals(EstadoContrato.Vigente);
	logger.debug("Meses entre fechas: " + ChronoUnit.MONTHS.between(LocalDate.now(), getFechaVencimiento(
		contratoAlquiler)));
	ret = ret && ChronoUnit.MONTHS.between(LocalDate.now(), getFechaVencimiento(contratoAlquiler)) <= 1;
	logger.debug("Años entre fechas: " + ChronoUnit.YEARS.between(LocalDate.now(), getFechaVencimiento(
		contratoAlquiler)));
	ret = ret && ChronoUnit.YEARS.between(LocalDate.now(), getFechaVencimiento(contratoAlquiler)) <= 0;
	return ret;
    }

    public List<Contrato> findAll(FiltroContrato filtro) {
	actualizarEstadoContratosAlquiler();
	List<Contrato> contratos = daoContrato.readAllActives()
		.stream()
		.filter(filtro.getFiltroCompuesto())
		.collect(Collectors.toList());
	contratos.sort(Comparator.comparing(Contrato::getId));
	return contratos;
    }

    public PublicacionVenta getPublicacionVentaActiva(Inmueble i) {
	PublicacionVenta ret = null;
	Set<Publicacion> publicaciones = i.getPublicaciones();
	if (publicaciones != null && publicaciones.size() > 0) {

	    List<Publicacion> ventas = i.getPublicaciones().stream()
		    .filter(p -> p.getEstadoRegistro().equals(EstadoRegistro.ACTIVO) && p instanceof PublicacionVenta)
		    .collect(Collectors.toList());
	    if (ventas != null && ventas.size() > 0)
		ret = (PublicacionVenta) ventas.get(0);
	}
	return ret;
    }

    public PublicacionAlquiler getPublicacionAlquilerActiva(Inmueble i) {
	PublicacionAlquiler ret = null;
	Set<Publicacion> publicaciones = i.getPublicaciones();
	if (publicaciones != null && publicaciones.size() > 0) {
	    List<Publicacion> alquileres = i.getPublicaciones().stream()
		    .filter(p -> p.getEstadoRegistro().equals(EstadoRegistro.ACTIVO)
			    && p instanceof PublicacionAlquiler)
		    .collect(Collectors.toList());
	    if (alquileres != null && alquileres.size() > 0)
		ret = (PublicacionAlquiler) alquileres.get(0);
	}
	return ret;
    }

    public static LocalDate getFechaVencimiento(ContratoAlquiler c) {
	LocalDate ret;
	ret = c.getFechaCelebracion().plus(c.getDuracionContrato().getDuracion(), ChronoUnit.MONTHS);
	return ret;
    }

    public static ContratoAlquiler getInstanciaAlquiler() {
	return new ContratoAlquiler.Builder()
		.setDiaDePago(10)
		.setDuracionContrato(ContratoDuracionService.getInstancia())
		.setInquilinoContrato(PersonaService.getPersonaConInquilino())
		.setInteresPunitorio(0.0)
		.setIntervaloActualizacion(24)
		.setTipoIncrementoCuota(TipoInteres.Acumulativo)
		.setTipoInteresPunitorio(TipoInteres.Simple)
		.setValorIncial(BigDecimal.ZERO)
		.setDocumento(null)
		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		.setFechaCelebracion(LocalDate.now())
		.setInmueble(InmuebleService.getInstancia())
		.build();
    }

    public static ContratoVenta getInstanciaVenta() {
	return new ContratoVenta.Builder()
		.setPrecioVenta(new BigDecimal("0"))
		.setFechaCelebracion(LocalDate.now())
		.setDocumento(null)
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
			.setPropietario(new Propietario.Builder()
				.setPersona(new Persona())
				.build())
			.build())
		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		.build();
    }

    public void addCobros(ContratoAlquiler contrato) {
	if (contrato.getDuracionContrato() != null && contrato.getEstadoContrato().equals(EstadoContrato.Vigente)
		&& (contrato.getCobros() == null || contrato.getCobros().size() == 0)) {
	    if (contrato.getCobros() == null)
		contrato.setCobros(new HashSet<>());
	    BigDecimal valorAnterior = contrato.getValorInicial();
	    for (int i = 0; i < contrato.getDuracionContrato().getDuracion(); i++) {
		// si el dia de celebracion es mayor o igual al dia de pago entonces las coutas
		// empiezan el proximo mes
		LocalDate fechaCobro = LocalDate.of(contrato.getFechaCelebracion().getYear(), contrato
			.getFechaCelebracion().getMonthValue(), contrato.getDiaDePago());
		if (contrato.getFechaCelebracion().getDayOfMonth() >= (int) contrato.getDiaDePago()) {
		    fechaCobro = fechaCobro.plusMonths(i + 1);
		} else {
		    fechaCobro = fechaCobro.plusMonths(i);
		}

		Cobro c = new Cobro.Builder()
			.setNumeroCuota(i + 1)
			.setFechaDeVencimiento(fechaCobro)
			.setMontoOriginal(valorAnterior)
			.setMontoRecibido(valorAnterior)
			.setInteres(new BigDecimal(0))
			.setMontoPropietario(new BigDecimal(0))
			.setComision(new BigDecimal(0))
			.build();
		c.setComision(valorAnterior.multiply(new BigDecimal(0.06)));
		c.setMontoPropietario(valorAnterior.subtract(c.getComision()));

		if ((i + 1) % contrato.getIntervaloActualizacion() == 0) {
		    if (contrato.getTipoIncrementoCuota().equals(TipoInteres.Acumulativo)) {
			BigDecimal incremento = new BigDecimal(contrato.getPorcentajeIncrementoCuota().toString());
			incremento = incremento.divide(new BigDecimal("100"));
			BigDecimal aux = valorAnterior.multiply(incremento);
			valorAnterior = valorAnterior.add(aux);
		    } else if (contrato.getTipoIncrementoCuota().equals(TipoInteres.Simple)) {
			BigDecimal incremento = new BigDecimal(contrato.getPorcentajeIncrementoCuota().toString());
			incremento = incremento.divide(new BigDecimal("100"));
			BigDecimal aux = contrato.getValorInicial().multiply(incremento);
			valorAnterior = valorAnterior.add(aux);
		    }
		}
		contrato.addCobro(c);
	    }
	}
    }

    public List<ItemRepAlquileresACobrar> getListadoAlquileresCobradosPorMes(LocalDate fechaMes) {

	LocalDate fechaDesde;
	LocalDate fechaHasta;

	if (fechaMes.getMonthValue() == 12) {
	    fechaDesde = LocalDate.of(fechaMes.getYear(), fechaMes.getMonthValue(), 1);
	    fechaHasta = LocalDate.of(fechaMes.getYear() + 1, 1, 1);
	}

	else {
	    fechaDesde = LocalDate.of(fechaMes.getYear(), fechaMes.getMonthValue(), 1);
	    fechaHasta = LocalDate.of(fechaMes.getYear(), fechaMes.getMonthValue() + 1, 1);
	}

	CobroService cobroService = new CobroService();

	List<ItemRepAlquileresACobrar> itemsReporte = new ArrayList<>();

	List<ContratoAlquiler> contratosVigentes = this.getContratosAlquilerVigentes();

	List<Cobro> cobros = new ArrayList<>();

	contratosVigentes.forEach(contrato -> {
	    if (contrato.getCobros() != null) {
		cobros.addAll(contrato.getCobros().stream()
			.filter(c -> {

			    return c.getEstadoCobro().equals(EstadoCobro.COBRADO);
			})
			.filter(c -> {

			    return fechaDesde != null ? c.getFechaDeVencimiento().compareTo(fechaDesde) >= 0 : true;
			})
			.filter(c -> {

			    return fechaHasta != null ? c.getFechaDeVencimiento().compareTo(fechaHasta) < 0 : true;
			})
			.collect(Collectors.toList()));

		cobroService.calcularDatosFaltantes(cobros);
		cobros.forEach(cobro -> {

		    itemsReporte.add(new ItemRepAlquileresACobrar(contrato.getInquilinoContrato(),
			    cobro, contrato.getMoneda()));
		});
	    }
	});

	itemsReporte.sort(Comparator.comparing(ItemRepAlquileresACobrar::getAnio).reversed()
		.thenComparing(ItemRepAlquileresACobrar::getNumeroMes)
		.thenComparing(ItemRepAlquileresACobrar::getApellido)
		.thenComparing(ItemRepAlquileresACobrar::getNombre));

	return itemsReporte;
    }

    public List<ItemRepAlquileresACobrar> getListadoTodosLosAlquileresDeUnMes(LocalDate fechaMes) {

	LocalDate fechaDesde;
	LocalDate fechaHasta;

	if (fechaMes.getMonthValue() == 12) {
	    fechaDesde = LocalDate.of(fechaMes.getYear(), fechaMes.getMonthValue(), 1);
	    fechaHasta = LocalDate.of(fechaMes.getYear() + 1, 1, 1);
	}

	else {
	    fechaDesde = LocalDate.of(fechaMes.getYear(), fechaMes.getMonthValue(), 1);
	    fechaHasta = LocalDate.of(fechaMes.getYear(), fechaMes.getMonthValue() + 1, 1);
	}

	CobroService cobroService = new CobroService();

	List<ItemRepAlquileresACobrar> itemsReporte = new ArrayList<>();

	List<ContratoAlquiler> contratosVigentes = this.getContratosAlquilerVigentes();

	List<Cobro> cobros = new ArrayList<>();

	contratosVigentes.forEach(contrato -> {
	    if (contrato.getCobros() != null) {
		cobros.addAll(contrato.getCobros().stream()
			.filter(c -> {

			    return fechaDesde != null ? c.getFechaDeVencimiento().compareTo(fechaDesde) >= 0 : true;
			})
			.filter(c -> {

			    return fechaHasta != null ? c.getFechaDeVencimiento().compareTo(fechaHasta) < 0 : true;
			})
			.collect(Collectors.toList()));

		cobroService.calcularDatosFaltantes(cobros);
		cobros.forEach(cobro -> {

		    itemsReporte.add(new ItemRepAlquileresACobrar(contrato.getInquilinoContrato(),
			    cobro, contrato.getMoneda()));
		});
	    }
	});

	itemsReporte.sort(Comparator.comparing(ItemRepAlquileresACobrar::getAnio).reversed()
		.thenComparing(ItemRepAlquileresACobrar::getNumeroMes)
		.thenComparing(ItemRepAlquileresACobrar::getApellido)
		.thenComparing(ItemRepAlquileresACobrar::getNombre));

	return itemsReporte;
    }

    public static void setMontoInicialRenovacio(ContratoAlquiler ca) {
	BigDecimal valorOriginal = ca.getValorInicial();
	BigDecimal ret;
	if (ca.getTipoIncrementoCuota() == TipoInteres.Simple) {
	    BigDecimal interes = new BigDecimal(ca.getPorcentajeIncrementoCuota().toString());
	    interes = interes.divide(new BigDecimal("100"));
	    interes = interes.multiply(new BigDecimal(ca.getDuracionContrato().getDuracion() / ca
		    .getIntervaloActualizacion()));
	    ret = valorOriginal.multiply(BigDecimal.ONE.add(interes));
	} else {
	    BigDecimal interes = new BigDecimal(ca.getPorcentajeIncrementoCuota().toString());
	    interes = interes.divide(new BigDecimal("100"));
	    ret = valorOriginal;
	    for (int i = 1; i < ca.getDuracionContrato().getDuracion() / ca
		    .getIntervaloActualizacion(); i++) {
		ret = ret.multiply(BigDecimal.ONE.add(interes));
	    }
	}
	ca.setValorInicial(ret);

    }
}
