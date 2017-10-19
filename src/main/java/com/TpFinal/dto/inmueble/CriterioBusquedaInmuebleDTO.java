
package com.TpFinal.dto.inmueble;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.TpFinal.dto.Identificable;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.TipoPublicacion;

@Entity
@Table(name = "CriteriosBusquedaPersona")
public class CriterioBusquedaInmuebleDTO implements Identificable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Boolean aEstrenar;
    private Integer minCantAmbientes;
    private Integer maxCantAmbientes;
    private Integer minCantCocheras;
    private Integer maxCantCocheras;
    private Integer minCantDormitorios;
    private Integer maxCantDormitorios;
    @ElementCollection
    @Column(name = "clase_inmueble")
    @Enumerated(EnumType.STRING)
    private List<ClaseInmueble> clasesDeInmueble;
    private Boolean conAireAcondicionado;
    private Boolean conParrilla;
    private Boolean conPileta;
    private Boolean conJardin;
    private String localidad;
    private String provincia;
    @Enumerated(EnumType.STRING)
    private EstadoInmueble estadoInmueble;
    private Integer minSupCubierta;
    private Integer maxSupCubierta;
    private Integer minSupTotal;
    private Integer maxSupTotal;
    @Enumerated(EnumType.STRING)
    private TipoInmueble tipoInmueble;
    private BigDecimal minPrecio;
    private BigDecimal maxPrecio;
    @Enumerated(EnumType.STRING)
    private TipoMoneda tipoMoneda;
    @Enumerated(EnumType.STRING)
    private TipoPublicacion tipoPublicacion;

    public CriterioBusquedaInmuebleDTO() {
	super();
    }

    public CriterioBusquedaInmuebleDTO(CriterioBusquedaInmuebleDTO.Builder builder) {
	super();
	this.aEstrenar = builder.aEstrenar;
	this.minCantAmbientes = builder.minCantAmbientes;
	this.maxCantAmbientes = builder.maxCantAmbientes;
	this.minCantCocheras = builder.minCantCocheras;
	this.maxCantCocheras = builder.maxCantCocheras;
	this.minCantDormitorios = builder.minCantDormitorios;
	this.maxCantDormitorios = builder.maxCantDormitorios;
	this.clasesDeInmueble = builder.clasesDeInmueble;
	this.conAireAcondicionado = builder.conAireAcondicionado;
	this.conParrilla = builder.conParrilla;
	this.conPileta = builder.conPileta;
	this.conJardin = builder.conJardin;
	this.localidad = builder.localidad;
	this.provincia = builder.provincia;

	this.estadoInmueble = builder.estadoInmueble;
	this.minSupCubierta = builder.minSupCubierta;
	this.maxSupCubierta = builder.maxSupCubierta;
	this.minSupTotal = builder.minSupTotal;
	this.maxSupTotal = builder.maxSupTotal;
	this.tipoInmueble = builder.tipoInmueble;
	this.minPrecio = builder.minPrecio;
	this.maxPrecio = builder.maxPrecio;
	this.tipoMoneda = builder.tipoMoneda;
	this.tipoPublicacion = builder.tipoPublicacion;
    }

    public Boolean getaEstrenar() {
	return aEstrenar;
    }

    public void setaEstrenar(Boolean aEstrenar) {
	this.aEstrenar = aEstrenar;
    }

    public Integer getMinCantAmbientes() {
	return minCantAmbientes;
    }

    public void setMinCantAmbientes(Integer minCantAmbientes) {
	this.minCantAmbientes = minCantAmbientes;
    }

    public Integer getMaxCantAmbientes() {
	return maxCantAmbientes;
    }

    public void setMaxCantAmbientes(Integer maxCantAmbientes) {
	this.maxCantAmbientes = maxCantAmbientes;
    }

    public Integer getMinCantCocheras() {
	return minCantCocheras;
    }

    public void setMinCantCocheras(Integer minCantCocheras) {
	this.minCantCocheras = minCantCocheras;
    }

    public Integer getMaxCantCocheras() {
	return maxCantCocheras;
    }

    public void setMaxCantCocheras(Integer maxCantCocheras) {
	this.maxCantCocheras = maxCantCocheras;
    }

    public Integer getMinCantDormitorios() {
	return minCantDormitorios;
    }

    public void setMinCantDormitorios(Integer minCantDormitorios) {
	this.minCantDormitorios = minCantDormitorios;
    }

    public Integer getMaxCantDormitorios() {
	return maxCantDormitorios;
    }

    public void setMaxCantDormitorios(Integer maxCantDormitorios) {
	this.maxCantDormitorios = maxCantDormitorios;
    }

    public List<ClaseInmueble> getClasesDeInmueble() {
	return clasesDeInmueble;
    }

    public void setClasesDeInmueble(List<ClaseInmueble> clasesDeInmueble) {
	this.clasesDeInmueble = clasesDeInmueble;
    }

    public Boolean getConAireAcondicionado() {
	return conAireAcondicionado;
    }

    public void setConAireAcondicionado(Boolean conAireAcondicionado) {
	this.conAireAcondicionado = conAireAcondicionado;
    }

    public Boolean getConParrilla() {
	return conParrilla;
    }

    public void setConParrilla(Boolean conParrilla) {
	this.conParrilla = conParrilla;
    }

    public Boolean getConPileta() {
	return conPileta;
    }

    public void setConPileta(Boolean conPileta) {
	this.conPileta = conPileta;
    }

    public Boolean getConJardin() {
	return conJardin;
    }

    public void setConJardin(Boolean conJardin) {
	this.conJardin = conJardin;
    }

    public String getLocalidad() {
	return localidad;
    }

    public EstadoInmueble getEstadoInmueble() {
	return estadoInmueble;
    }

    public void setEstadoInmueble(EstadoInmueble estadoInmueble) {
	this.estadoInmueble = estadoInmueble;
    }

    public Integer getMinSupCubierta() {
	return minSupCubierta;
    }

    public void setMinSupCubierta(Integer minSupCubierta) {
	this.minSupCubierta = minSupCubierta;
    }

    public Integer getMaxSupCubierta() {
	return maxSupCubierta;
    }

    public void setMaxSupCubierta(Integer maxSupCubierta) {
	this.maxSupCubierta = maxSupCubierta;
    }

    public Integer getMinSupTotal() {
	return minSupTotal;
    }

    public void setMinSupTotal(Integer minSupTotal) {
	this.minSupTotal = minSupTotal;
    }

    public Integer getMaxSupTotal() {
	return maxSupTotal;
    }

    public void setMaxSupTotal(Integer maxSupTotal) {
	this.maxSupTotal = maxSupTotal;
    }

    public TipoInmueble getTipoInmueble() {
	return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
	this.tipoInmueble = tipoInmueble;
    }

    public BigDecimal getMinPrecio() {
	return minPrecio;
    }

    public void setMinPrecio(BigDecimal minPrecio) {
	this.minPrecio = minPrecio;
    }

    public BigDecimal getMaxPrecio() {
	return maxPrecio;
    }

    public void setMaxPrecio(BigDecimal maxPrecio) {
	this.maxPrecio = maxPrecio;
    }

    public TipoMoneda getTipoMoneda() {
	return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
	this.tipoMoneda = tipoMoneda;
    }

    public TipoPublicacion getTipoPublicacion() {
	return tipoPublicacion;
    }

    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
	this.tipoPublicacion = tipoPublicacion;
    }

    public String getProvincia() {
	return provincia;
    }

    public void setProvincia(String provincia) {
	this.provincia = provincia;
    }

    public void setLocalidad(String localidad) {
	this.localidad = localidad;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof CriterioBusquedaInmuebleDTO))
	    return false;
	CriterioBusquedaInmuebleDTO other = (CriterioBusquedaInmuebleDTO) o;
	return getId() != null && Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
	return 157;
    }

    public static class Builder {
	private String provincia;
	private Boolean aEstrenar;
	private Integer minCantAmbientes;
	private Integer maxCantAmbientes;
	private Integer minCantCocheras;
	private Integer maxCantCocheras;
	private Integer minCantDormitorios;
	private Integer maxCantDormitorios;
	private List<ClaseInmueble> clasesDeInmueble;
	private Boolean conAireAcondicionado;
	private Boolean conParrilla;
	private Boolean conPileta;
	private Boolean conJardin;
	private String localidad;
	private EstadoInmueble estadoInmueble;
	private Integer minSupCubierta;
	private Integer maxSupCubierta;
	private Integer minSupTotal;
	private Integer maxSupTotal;
	private TipoInmueble tipoInmueble;
	private BigDecimal minPrecio;
	private BigDecimal maxPrecio;
	private TipoMoneda tipoMoneda;
	private TipoPublicacion tipoPublicacion;

	public Builder setaEstrenar(Boolean aEstrenar) {
	    this.aEstrenar = aEstrenar;
	    return this;
	}

	public Builder setMinCantAmbientes(Integer minCantAmbientes) {
	    this.minCantAmbientes = minCantAmbientes;
	    return this;
	}

	public Builder setMaxCantAmbientes(Integer maxCantAmbientes) {
	    this.maxCantAmbientes = maxCantAmbientes;
	    return this;
	}

	public Builder setMinCantCocheras(Integer minCantCocheras) {
	    this.minCantCocheras = minCantCocheras;
	    return this;
	}

	public Builder setMaxCantCocheras(Integer maxCantCocheras) {
	    this.maxCantCocheras = maxCantCocheras;
	    return this;
	}

	public Builder setMinCantDormitorios(Integer minCantDormitorios) {
	    this.minCantDormitorios = minCantDormitorios;
	    return this;
	}

	public Builder setMaxCantDormitorios(Integer maxCantDormitorios) {
	    this.maxCantDormitorios = maxCantDormitorios;
	    return this;
	}

	public Builder setClasesDeInmueble(List<ClaseInmueble> clasesDeInmueble) {
	    this.clasesDeInmueble = clasesDeInmueble;
	    return this;
	}

	public Builder setConAireAcondicionado(Boolean conAireAcondicionado) {
	    this.conAireAcondicionado = conAireAcondicionado;
	    return this;
	}

	public Builder setConParrilla(Boolean conParrilla) {
	    this.conParrilla = conParrilla;
	    return this;
	}

	public Builder setConPileta(Boolean conPileta) {
	    this.conPileta = conPileta;
	    return this;
	}

	public Builder setConJardin(Boolean conJardin) {
	    this.conJardin = conJardin;
	    return this;
	}

	public Builder setLocalidad(String localidad) {
	    this.localidad = localidad;
	    return this;
	}

	public Builder setProvincia(String provincia) {
	    this.provincia = provincia;
	    return this;
	}

	public Builder setEstadoInmueble(EstadoInmueble estadoInmueble) {
	    this.estadoInmueble = estadoInmueble;
	    return this;
	}

	public Builder setMinSupCubierta(Integer minSupCubierta) {
	    this.minSupCubierta = minSupCubierta;
	    return this;
	}

	public Builder setMaxSupCubierta(Integer maxSupCubierta) {
	    this.maxSupCubierta = maxSupCubierta;
	    return this;
	}

	public Builder setMinSupTotal(Integer minSupTotal) {
	    this.minSupTotal = minSupTotal;
	    return this;
	}

	public Builder setMaxSupTotal(Integer maxSupTotal) {
	    this.maxSupTotal = maxSupTotal;
	    return this;
	}

	public Builder setTipoInmueble(TipoInmueble tipoInmueble) {
	    this.tipoInmueble = tipoInmueble;
	    return this;
	}

	public Builder setMinPrecio(BigDecimal minPrecio) {
	    this.minPrecio = minPrecio;
	    return this;
	}

	public Builder setMaxPrecio(BigDecimal maxPrecio) {
	    this.maxPrecio = maxPrecio;
	    return this;
	}

	public Builder setTipoMoneda(TipoMoneda tipoMoneda) {
	    this.tipoMoneda = tipoMoneda;
	    return this;
	}

	public Builder setTipoPublicacion(TipoPublicacion tipoPublicacion) {
	    this.tipoPublicacion = tipoPublicacion;
	    return this;
	}

	public CriterioBusquedaInmuebleDTO build() {
	    return new CriterioBusquedaInmuebleDTO(this);
	}

    }

    @Override
    public Long getId() {
	return this.id;
    }

}
