package com.TpFinal.services;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.notificacion.NotificadorJob;
import org.apache.log4j.Logger;

import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoImpl;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContrato;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.cobro.TipoCobro;
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
import com.TpFinal.dto.publicacion.EstadoPublicacion;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.exceptions.services.ContratoServiceException;
import com.TpFinal.exceptions.services.PublicacionServiceException;
import com.TpFinal.view.contrato.FiltroContrato;
import com.TpFinal.view.reportes.ItemRepAlquileresACobrar;

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
    private PublicacionService publicacionService;
    private boolean existe = false;

    BigDecimal gananciaInmobiliariaPagosCobrados;
    BigDecimal gananciaInmobiliariaTodosLosCobros;
    BigDecimal ingresosTotalesPagosCobrados;
    BigDecimal ingresosTotalesPagosPendientes;
    int cantidadPagosCobrados;
    int cantidadPagosPendientes;
    Integer porcentajeComisionComprador;
    Integer porcentajeComisionVendedor;

    public ContratoService() {
	daoAlquiler = new DAOContratoAlquilerImpl();
	daoVenta = new DAOContratoVentaImpl();
	daoContrato = new DAOContratoImpl();
	inmuebleService = new InmuebleService();
	contratoDuracionService = new ContratoDuracionService();
	publicacionService = new PublicacionService();
	this.gananciaInmobiliariaPagosCobrados = BigDecimal.ZERO;
	this.gananciaInmobiliariaTodosLosCobros = BigDecimal.ZERO;
	this.ingresosTotalesPagosCobrados = BigDecimal.ZERO;
	this.ingresosTotalesPagosPendientes = BigDecimal.ZERO;
	this.cantidadPagosCobrados = 0;
	this.cantidadPagosPendientes = 0;
	this.porcentajeComisionComprador = 4;
	this.porcentajeComisionVendedor = 3;
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

    public boolean rescindirContrato(Contrato contrato) {
	if (contrato.getId() == null)
	    return false;
	if (!puedeSerRescindido(contrato))
	    return false;

	if (contrato instanceof ContratoAlquiler)
	    return cancelarContratoAlquiler((ContratoAlquiler) contrato);
	else if (contrato instanceof ContratoVenta)
	    return cancelarContratoVenta((ContratoVenta) contrato);

	return false;
    }

    public static boolean puedeSerRescindido(Contrato contrato) {
	EstadoContrato estadoContrato = contrato.getEstadoContrato();
	if (contrato instanceof ContratoVenta && estadoContrato == EstadoContrato.Celebrado) {
	    return contrato.getCobros().stream().anyMatch(cobro -> !cobro.getEstadoCobro().equals(EstadoCobro.COBRADO));
	} else
	    return estadoContrato == EstadoContrato.ProximoAVencer
		    || estadoContrato == EstadoContrato.Vigente;

    }

    private boolean cancelarContratoAlquiler(ContratoAlquiler alquiler) {
	alquiler.setEstadoContrato(EstadoContrato.Rescindido);
	CobroService cobroService = new CobroService();
	Planificador.get().setNotificacion(new NotificadorJob());
	Planificador.get().setMailSender(new MailSender());

	for (Cobro cobro : cobroService.readNoCobrados()) {
	    if (cobro.getContrato().equals(alquiler)) {
		Planificador.get().removeJobCobroVencido(cobro);
		Planificador.get().removeJobCobroPorVencer(cobro);
		LocalDate now = LocalDate.now();
		if (now.plusMonths(1).isBefore(cobro.getFechaDeVencimiento())) {
		    alquiler.removeCobro(cobro);
		    cobroService.delete(cobro);

		}
	    }
	}

	try {
	    Planificador.get().removeJobAlquilerPorVencer(alquiler);
	    Planificador.get().removeJobAlquilerVencido(alquiler);
	} catch (Exception e) {
	    logger.info("ERROR AL ELIMINAR TRIGGER CONTRATO");
	    e.printStackTrace();
	}
	return daoAlquiler.saveOrUpdate(alquiler);
    }

    private boolean cancelarContratoVenta(ContratoVenta venta) {
	venta.setEstadoContrato(EstadoContrato.Rescindido);
	CobroService cobroService = new CobroService();
	Planificador.get().setNotificacion(new NotificadorJob());
	Planificador.get().setMailSender(new MailSender());

	for (Cobro cobro : cobroService.readNoCobrados()) {
	    if (cobro.getContrato().equals(venta)) {
		Planificador.get().removeJobCobroVencido(cobro);
		Planificador.get().removeJobCobroPorVencer(cobro);
		venta.removeCobro(cobro);
		cobroService.delete(cobro);
	    }
	}

	return daoVenta.saveOrUpdate(venta);
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

    public List<Object> getListadoAlquileresACobrar(LocalDate fechaDesde, LocalDate fechaHasta) {

	CobroService cobroService = new CobroService();
	List<ItemRepAlquileresACobrar> itemsReporte = new ArrayList<>();
	List<ContratoAlquiler> contratosACobrar = this.getContratosAlquilerCobrables();
	List<Cobro> cobros = new ArrayList<>();

	contratosACobrar.forEach(contrato -> {
	    cobros.clear();
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

	return itemsReporte.stream().map(i -> (Object) i).collect(Collectors.toList());
    }

    public List<Object> getListadoAlquileresDelMes(LocalDate fechaDesde, LocalDate fechaHasta,
	    boolean incluirCobrosPendientes) {

	CobroService cobroService = new CobroService();
	List<ItemRepAlquileresACobrar> itemsReporte = new ArrayList<>();
	List<ContratoAlquiler> contratosACobrar = this.getContratosAlquilerCobrables();
	List<Cobro> cobros = new ArrayList<>();
	if (logger.isDebugEnabled()) {
	    logger.debug("=================================================================");
	    logger.debug("Contratos a procesar: " + contratosACobrar.size());
	}

	contratosACobrar.forEach(contrato -> {
	    cobros.clear();
	    if (contrato.getCobros() != null) {
		if (logger.isDebugEnabled()) {
		    logger.debug("=================================================================");
		    logger.debug("Cantidad de cobros del contrato: " + contrato.getCobros().size());
		}
		cobros.addAll(contrato.getCobros().stream()
			.filter(c -> {
			    if (!incluirCobrosPendientes)
				return c.getEstadoCobro().equals(EstadoCobro.COBRADO);
			    else
				return true;
			})
			.filter(c -> {
			    return fechaDesde != null ? c.getFechaDeVencimiento().compareTo(fechaDesde) >= 0 : true;
			})
			.filter(c -> {
			    return fechaHasta != null ? c.getFechaDeVencimiento().compareTo(fechaHasta) <= 0 : true;
			})
			.collect(Collectors.toList()));

		if (logger.isDebugEnabled())
		    logger.debug("Cantidad de cobros obtenidos: " + cobros.size());

		cobroService.calcularDatosFaltantes(cobros);
		if (logger.isDebugEnabled()) {
		    cobros.forEach(c -> {
			ContratoAlquiler c1 = (ContratoAlquiler) c.getContrato();
			logger.debug("Cobro " + c.getId() + " Moneda:" + c1.getMoneda() + " Inquilino:" + c1
				.getInquilinoContrato().getPersona());
		    });
		}
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
	if (logger.isDebugEnabled()) {
	    logger.debug("=====================================================");
	    logger.debug("Cantidad de items obtenidos: " + itemsReporte.size());
	}
	itemsReporte.forEach(i -> System.out.println(i + " Moneda: " + i.getTipoMonedaString()));
	return itemsReporte.stream().map(i -> (Object) i).collect(Collectors.toList());
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
		// else if (c.getEstadoContrato().equals(EstadoContrato.Celebrado))
		// addCobroVenta(c);
	    } else {
		ret = daoVenta.merge(c);
		if (ret == false)
		    throw new ContratoServiceException("Fallo daoVenta.merge(c)");
	    }
	} else {
	    ContratoAlquiler c = (ContratoAlquiler) contrato;
	    if (c.getRandomKey() == null)
		c.generateUUID();

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
	ret = c.getFechaIngreso().plus(c.getDuracionContrato().getDuracion(), ChronoUnit.MONTHS);
	return ret;
    }

    public ContratoAlquiler getUltimoAlquiler() {
	List<ContratoAlquiler> ContratoAlquilers = daoAlquiler.readAllActives();
	ContratoAlquilers.sort((c1, c2) -> c2.getId().compareTo(c1.getId()));
	return ContratoAlquilers.get(0);
    }

    public static ContratoAlquiler getInstanciaAlquiler() {
	return new ContratoAlquiler.Builder()
		.setDiaDePago(ParametrosSistemaService.getParametros().getDiaDePago())
		.setDuracionContrato(ContratoDuracionService.getInstancia())
		.setInquilinoContrato(PersonaService.getPersonaConInquilino())
		.setInteresPunitorio(0.0)
		.setIntervaloActualizacion(1)
		.setTipoIncrementoCuota(TipoInteres.Acumulativo)
		.setTipoInteresPunitorio(TipoInteres.Simple)
		.setValorIncial(BigDecimal.ZERO)
		.setDocumento(null)
		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		.setFechaIngreso(LocalDate.now())
		.setInmueble(InmuebleService.getInstancia())
		.setCantCertificadosGarantes(ParametrosSistemaService.getParametros().getCantMinimaCertificados())
		.build();
    }

    public static ContratoVenta getInstanciaVenta() {
	return new ContratoVenta.Builder()
		.setPrecioVenta(new BigDecimal("0"))
		.setFechaIngreso(LocalDate.now())
		.setDocumento(null)
		.setComisionAComprador(ParametrosSistemaService.getParametros().getComisionAComprador())
		.setComisionAVendedor(ParametrosSistemaService.getParametros().getComisionAVendededor())
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

    public void addCobroVenta(ContratoVenta contrato) {
	if (contrato.getCobros() == null)
	    contrato.setCobros(new HashSet<>());

	// XXX remplazar por lo de la bd
	ProvinciaService provinciaService = new ProvinciaService();
	Provincia provincia = provinciaService.getProvinciaFromString(
		contrato.getInmueble().getDireccion().getProvincia());
	BigDecimal sellado = provinciaService.getSelladoFromProvincia(provincia);
	BigDecimal comisionComprador = new BigDecimal(contrato.getPrecioVenta().toString());
	BigDecimal comisionVendedor = new BigDecimal(contrato.getPrecioVenta().toString());
	BigDecimal total = new BigDecimal(contrato.getPrecioVenta().toString());
	BigDecimal totalComision = new BigDecimal("0");
	BigDecimal montoVendedor = new BigDecimal(contrato.getPrecioVenta().toString());

	comisionComprador = comisionComprador.multiply(new BigDecimal(contrato.getComisionAComprador())).divide(
		new BigDecimal("100"));
	comisionVendedor = comisionVendedor.multiply(new BigDecimal(contrato.getComsionAVendedor())).divide(
		new BigDecimal("100"));
	total = total.add(comisionComprador).add(comisionVendedor);
	totalComision = totalComision.add(comisionComprador).add(comisionVendedor);
	montoVendedor = montoVendedor.subtract(comisionVendedor);
	BigDecimal interesSellado = contrato.getPrecioVenta().multiply(sellado).divide(new BigDecimal("100"));
	total = total.add(interesSellado);

	Cobro c = new Cobro.Builder()
		.setNumeroCuota(0)
		.setFechaDeVencimiento(contrato.getFechaCelebracion())
		.setMontoOriginal(contrato.getPrecioVenta())
		.setMontoRecibido(total)
		.setInteres(interesSellado)
		.setMontoPropietario(montoVendedor)
		.setComision(totalComision)
		.setTipoCobro(TipoCobro.Venta)
		.build();
	contrato.addCobro(c);
    }

    public void addCobrosAlquiler(ContratoAlquiler contrato) {
	if (contrato.getDuracionContrato() != null && contrato.getEstadoContrato().equals(EstadoContrato.Vigente)
		&& (contrato.getCobros() == null || contrato.getCobros().size() == 0)) {
	    if (contrato.getCobros() == null)
		contrato.setCobros(new HashSet<>());
	    BigDecimal valorAnterior = contrato.getValorInicial();
	    for (int i = 0; i < contrato.getDuracionContrato().getDuracion(); i++) {
		// si el dia de celebracion es mayor o igual al dia de pago entonces las coutas
		// empiezan el proximo mes
		LocalDate fechaCobro = LocalDate.of(contrato.getFechaIngreso().getYear(), contrato
			.getFechaIngreso().getMonthValue(), contrato.getDiaDePago());
		if (contrato.getFechaIngreso().getDayOfMonth() >= (int) contrato.getDiaDePago()) {
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
			.setTipoCobro(TipoCobro.Alquiler)
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

    public Cobro cobrarCuota(Integer numero, ContratoAlquiler contrato) {
	List<Cobro> ret = new ArrayList<>();
	contrato.getCobros().forEach(cobro -> {
	    if (cobro.getNumeroCuota().equals(numero)) {
		cobro.setEstadoCobro(EstadoCobro.COBRADO);
		cobro.setFechaDePago(contrato.getFechaCelebracion());
		ret.add(cobro);
	    }
	});
	return ret.get(0);
    }

    public Cobro getCuota(Integer numeroCouta, ContratoAlquiler contrato) {
	return contrato.getCobros().stream()
		.filter(c -> c.getNumeroCuota().equals(numeroCouta))
		.collect(Collectors.toList())
		.get(0);
    }

    public static BigDecimal getMontoUltimaCuota(ContratoAlquiler ca) {
	BigDecimal valorOriginal = ca.getValorInicial();
	BigDecimal ret;
	if (ca.getTipoIncrementoCuota() == TipoInteres.Simple) {
	    BigDecimal interes = new BigDecimal(ca.getPorcentajeIncrementoCuota().toString());
	    interes = interes.divide(new BigDecimal("100"));
	    interes = interes.multiply(new BigDecimal(ca.getDuracionContrato().getDuracion() / ca
		    .getIntervaloActualizacion()).subtract(BigDecimal.ONE));
	    ret = valorOriginal.multiply(BigDecimal.ONE.add(interes));
	} else {
	    BigDecimal interes = new BigDecimal(ca.getPorcentajeIncrementoCuota().toString());
	    interes = interes.divide(new BigDecimal("100"));
	    ret = valorOriginal;
	    for (int i = 1; i < (ca.getDuracionContrato().getDuracion() / ca
		    .getIntervaloActualizacion()); i++) {
		ret = ret.multiply(BigDecimal.ONE.add(interes));
	    }
	}
	return ret;
    }

    public static void setMontoInicialRenovacion(ContratoAlquiler ca) {
	BigDecimal valorOriginal = ca.getValorInicial();
	BigDecimal ret;
	if (ca.getTipoIncrementoCuota() == TipoInteres.Simple) {
	    BigDecimal interes = new BigDecimal(ca.getPorcentajeIncrementoCuota().toString());
	    interes = interes.divide(new BigDecimal("100"));
	    interes = interes.multiply(new BigDecimal(ca.getDuracionContrato().getDuracion() / ca
		    .getIntervaloActualizacion()).subtract(BigDecimal.ONE));
	    ret = valorOriginal.multiply(BigDecimal.ONE.add(interes));
	} else {
	    BigDecimal interes = new BigDecimal(ca.getPorcentajeIncrementoCuota().toString());
	    interes = interes.divide(new BigDecimal("100"));
	    ret = valorOriginal;
	    for (int i = 1; i < (ca.getDuracionContrato().getDuracion() / ca
		    .getIntervaloActualizacion()); i++) {
		ret = ret.multiply(BigDecimal.ONE.add(interes));
	    }
	}
	ca.setValorInicial(ret);

    }

    public void finalizarPublicacionAsociada(Contrato contrato) {
	if (contrato != null && contrato.getInmueble() != null) {
	    if (contrato instanceof ContratoAlquiler) {
		PublicacionAlquiler pubA = getPublicacionAlquilerActiva(contrato.getInmueble());
		try {
		    if (pubA != null) {
			pubA.setEstadoPublicacion(EstadoPublicacion.Terminada);
			publicacionService.save(pubA);
		    }
		} catch (PublicacionServiceException e) {
		    e.printStackTrace();
		}
	    } else {
		PublicacionVenta pubV = getPublicacionVentaActiva(contrato.getInmueble());
		try {
		    if (pubV != null) {
			pubV.setEstadoPublicacion(EstadoPublicacion.Terminada);
			publicacionService.save(pubV);
		    }
		} catch (PublicacionServiceException e) {
		    e.printStackTrace();
		}
	    }
	}

    }

    public static BigDecimal getValorTotalAlquiler(ContratoAlquiler contrato) {
	BigDecimal total = BigDecimal.ZERO;
	BigDecimal valorAnterior = contrato.getValorInicial();
	for (int i = 0; i < contrato.getDuracionContrato().getDuracion(); i++) {

	    total = total.add(valorAnterior);

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
	}
	if (logger.isDebugEnabled()) {
	    logger.debug("Valor total del contrato: " + total);
	}
	return total;
    }

    public static BigDecimal getValorSelladoAlquiler(ContratoAlquiler contrato) {
	BigDecimal total = getValorTotalAlquiler(contrato);
	ProvinciaService provinciaService = new ProvinciaService();
	Provincia provincia = provinciaService.getProvinciaFromString(
		contrato.getInmueble().getDireccion().getProvincia());
	BigDecimal sellado = provinciaService.getSelladoFromProvincia(provincia);
	total = total.multiply(sellado).divide(BigDecimal.valueOf(100));
	if (logger.isDebugEnabled()) {
	    logger.debug("Valor total del sellado: " + total);
	}
	return total;
    }
    
    public static BigDecimal getValorCertificadosGarantes(ContratoAlquiler contrato) {
	return ParametrosSistemaService.getParametros().getValorCertificado().multiply(BigDecimal.valueOf(contrato.getCantCertificadosGarantes()));
    }
    
    public static BigDecimal getValorEntrada(ContratoAlquiler contrato) {
	return contrato.getValorInicial()
		.add((getMontoUltimaCuota(contrato).multiply(BigDecimal.valueOf(2))))
		.add(getValorSelladoAlquiler(contrato))
		.add(getValorCertificadosGarantes(contrato));
    }
}
