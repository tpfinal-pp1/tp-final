package com.TpFinal.data.dto.inmueble;

import com.TpFinal.data.dto.BorradoLogico;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.Publicacion;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Inmuebles")
public class Inmueble implements Identificable, BorradoLogico {
    public static final String pIdInmueble = "idInmueble";
    public static final String pAEstrenar = "aEstrenar";
    public static final String pCantAmbientes = "cantidadAmbientes";
    public static final String pCantCocheras = "cantidadCocheras";
    public static final String pCantDormitorios = "cantidadDormitorios";
    public static final String pClaseInmb = "claseInmueble";
    public static final String pConAireAcond = "conAireAcondicionado";
    public static final String pConJardin = "conJardin";
    public static final String pConParrilla = "conParrilla";
    public static final String pConPileta = "conPileta";
    public static final String pDireccion = "direccion";
    public static final String pEstadoInmueble = "estadoInmueble";
    public static final String pSupCubierta = "superficieCubierta";
    public static final String pSupTotal = "superficieTotal";
    public static final String pTipoInmb = "tipoInmueble";
    public static final String pPublicaciones = "publicaciones";
    public static final String pPropietario = "propietario";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Inmueble.pIdInmueble)
    private Long idInmueble;

    @Column(name = Inmueble.pCantAmbientes)
    private Integer cantidadAmbientes;

    @Column(name = Inmueble.pCantDormitorios)
    private Integer cantidadDormitorios;

    @Column(name = Inmueble.pSupTotal)
    private Integer superficieTotal;

    @Column(name = Inmueble.pSupCubierta)
    private Integer superficieCubierta;

    @Column(name = Inmueble.pAEstrenar)
    private Boolean aEstrenar;

    @Column(name = Inmueble.pCantCocheras)
    private Integer cantidadCocheras;

    @Column(name = Inmueble.pConAireAcond)
    private Boolean conAireAcondicionado;

    @Column(name = Inmueble.pConJardin)
    private Boolean conJardin;

    @Column(name = Inmueble.pConParrilla)
    private Boolean conParrilla;

    @Column(name = Inmueble.pConPileta)
    private Boolean conPileta;

    @Enumerated(EnumType.STRING)
    @Column(name = Inmueble.pEstadoInmueble)
    private EstadoInmueble estadoInmueble;

    @Enumerated(EnumType.STRING)
    @Column(name = Inmueble.pTipoInmb)
    private TipoInmueble tipoInmueble;

    @Enumerated(EnumType.STRING)
    @Column(name = Inmueble.pClaseInmb)
    private ClaseInmueble claseInmueble;

    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;

    @ManyToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    @ManyToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "id_proppietario")
    private Propietario propietario;

    @OneToMany(mappedBy = "inmueble",fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE,CascadeType.DELETE})
    protected Set<Publicacion> publicaciones = new HashSet<>();
    
    @OneToMany(mappedBy = "inmueble",fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE,CascadeType.DELETE})
    protected Set<Contrato> contratos = new HashSet<>();

    public Inmueble() {
		super();
		this.setEstadoRegistro(EstadoRegistro.ACTIVO);
    }

    private Inmueble(Builder inmuebleBuilder) {
		this.idInmueble = null;
		this.aEstrenar = inmuebleBuilder.aEstrenar;
		this.cantidadAmbientes = inmuebleBuilder.cantidadAmbientes;
		this.cantidadCocheras = inmuebleBuilder.cantidadCocheras;
		this.cantidadDormitorios = inmuebleBuilder.cantidadDormitorios;
		this.claseInmueble = inmuebleBuilder.claseInmueble;
		this.conAireAcondicionado = inmuebleBuilder.conAireAcondicionado;
		this.conJardin = inmuebleBuilder.conJardin;
		this.conParrilla = inmuebleBuilder.conParilla;
		this.conPileta = inmuebleBuilder.conPileta;
		this.direccion = inmuebleBuilder.direccion;
		this.estadoInmueble = inmuebleBuilder.estadoInmueble;
		this.superficieCubierta = inmuebleBuilder.superficieCubierta;
		this.superficieTotal = inmuebleBuilder.superficieTotal;
		this.tipoInmueble = inmuebleBuilder.tipoInmueble;
		this.publicaciones = inmuebleBuilder.publicaciones;
		this.propietario = inmuebleBuilder.propietario;
		this.contratos = inmuebleBuilder.contratos;
		this.setEstadoRegistro(EstadoRegistro.ACTIVO);
    }

    @Override
    public Long getId() {
    	return idInmueble;
    }

    @SuppressWarnings("unused")
    private void setIdInmueble(Long idInmueble) {
    	this.idInmueble = idInmueble;
    }

    public Integer getCantidadAmbientes() {
    	return cantidadAmbientes;
    }

    public void setCantidadAmbientes(Integer cantidadAmbientes) {
    	this.cantidadAmbientes = cantidadAmbientes;
    }

    public Integer getCantidadDormitorios() {
    	return cantidadDormitorios;
    }

    public void setCantidadDormitorios(Integer cantidadDormitorios) {
    	this.cantidadDormitorios = cantidadDormitorios;
    }

    public Integer getSuperficieTotal() {
    	return superficieTotal;
    }

    public void setSuperficieTotal(Integer superficieTotal) {
    	this.superficieTotal = superficieTotal;
    }

    public Integer getSuperficieCubierta() {
    	return superficieCubierta;
    }

    public void setSuperficieCubierta(Integer superficieCubierta) {
    	this.superficieCubierta = superficieCubierta;
    }

    public Boolean getaEstrenar() {
    	return aEstrenar;
    }

    public void setaEstrenar(Boolean aEstrenar) {
    	this.aEstrenar = aEstrenar;
    }

    public Integer getCantidadCocheras() {
    	return cantidadCocheras;
    }

    public void setCantidadCocheras(Integer cantidadCocheras) {
    	this.cantidadCocheras = cantidadCocheras;
    }

    public Boolean getConAireAcondicionado() {
    	return conAireAcondicionado;
    }

    public void setConAireAcondicionado(Boolean conAireAcondicionado) {
    	this.conAireAcondicionado = conAireAcondicionado;
    }

    public Boolean getConJardin() {
    	return conJardin;
    }

    public void setConJardin(Boolean conJardin) {
    	this.conJardin = conJardin;
    }

    public Boolean getConParilla() {
    	return conParrilla;
    }

    public void setConParilla(Boolean conParilla) {
    	this.conParrilla = conParilla;
    }

    public Boolean getConPileta() {
    	return conPileta;
    }

    public void setConPileta(Boolean conPileta) {
    	this.conPileta = conPileta;
    }

    public EstadoInmueble getEstadoInmueble() {
    	return estadoInmueble;
    }

    public void setEstadoInmueble(EstadoInmueble estadoInmueble) {
    	this.estadoInmueble = estadoInmueble;
    }

    public TipoInmueble getTipoInmueble() {
    	return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
    	this.tipoInmueble = tipoInmueble;
    }

    public ClaseInmueble getClaseInmueble() {
    	return claseInmueble;
    }

    public void setClaseInmueble(ClaseInmueble claseInmueble) {
    	this.claseInmueble = claseInmueble;
    }

    public Direccion getDireccion() {
    	return direccion;
    }

    public void setDireccion(Direccion direccion) {
    	this.direccion = direccion;
    }

    public Set<Publicacion> getPublicaciones() {
    	return publicaciones;
    }

    public void addPublicacion(Publicacion publicacion) {
    	if(!this.publicaciones.contains(publicacion)) {
    		this.publicaciones.add(publicacion);
    		publicacion.setInmueble(this);
    	}
    }

	public void removePublicacion(Publicacion publicacion) {
		if(this.publicaciones.contains(publicacion)) {
			this.publicaciones.remove(publicacion);
			publicacion.setInmueble(null);
		}
	}
	
	public void addContrato(Contrato c) {
	   if(!this.contratos.contains(c)) {
		   this.contratos.add(c);
		   c.setInmueble(this);
	    }
	}
	
	public void removeContrato(Contrato c) {
		if(this.contratos.contains(c)) {
			this.contratos.remove(c);
			c.setInmueble(null);
		}
	}
	
    public Propietario getPropietario() {
    	return propietario;
    }

    public void setPropietario(Propietario propietario) {
    	if(this.propietario!=null && !this.propietario.equals(propietario)) {
    			this.propietario.removeInmueble(this);
    		}
	    this.propietario=propietario;
		if(propietario!=null) 
			propietario.addInmueble(this);
    }
    
    @Override
    public void setEstadoRegistro(EstadoRegistro estado) {
    	this.estadoRegistro = estado;
    }

    @Override
    public EstadoRegistro getEstadoRegistro() {
    	return estadoRegistro;
    }

    public Set<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(Set<Contrato> contratos) {
        this.contratos = contratos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inmueble)) return false;
        Inmueble inmueble = (Inmueble) o;
        return getId() != null && Objects.equals(getId(), inmueble.getId());
    }
 
    @Override
    public int hashCode() {
        return 57;
    }
    
    public boolean isSame(Object obj) {
		if (this == obj)
		    return true;
		if (obj == null)
		    return false;
		if (!(obj instanceof Inmueble))
		    return false;
		Inmueble other = (Inmueble) obj;
		if (aEstrenar == null) {
		    if (other.aEstrenar != null)
			return false;
		} else if (!aEstrenar.equals(other.aEstrenar))
		    return false;
		if (cantidadAmbientes == null) {
		    if (other.cantidadAmbientes != null)
			return false;
		} else if (!cantidadAmbientes.equals(other.cantidadAmbientes))
		    return false;
		if (cantidadCocheras == null) {
		    if (other.cantidadCocheras != null)
			return false;
		} else if (!cantidadCocheras.equals(other.cantidadCocheras))
		    return false;
		if (cantidadDormitorios == null) {
		    if (other.cantidadDormitorios != null)
			return false;
		} else if (!cantidadDormitorios.equals(other.cantidadDormitorios))
		    return false;
		if (claseInmueble != other.claseInmueble)
		    return false;
		if (conAireAcondicionado == null) {
		    if (other.conAireAcondicionado != null)
			return false;
		} else if (!conAireAcondicionado.equals(other.conAireAcondicionado))
		    return false;
		if (conJardin == null) {
		    if (other.conJardin != null)
			return false;
		} else if (!conJardin.equals(other.conJardin))
		    return false;
		if (conParrilla == null) {
		    if (other.conParrilla != null)
			return false;
		} else if (!conParrilla.equals(other.conParrilla))
		    return false;
		if (conPileta == null) {
		    if (other.conPileta != null)
			return false;
		} else if (!conPileta.equals(other.conPileta))
		    return false;
		if (direccion == null) {
		    if (other.direccion != null)
			return false;
		} else if (!direccion.equals(other.direccion))
		    return false;
		if (estadoInmueble != other.estadoInmueble)
		    return false;
		if (superficieCubierta == null) {
		    if (other.superficieCubierta != null)
			return false;
		} else if (!superficieCubierta.equals(other.superficieCubierta))
		    return false;
		if (superficieTotal == null) {
		    if (other.superficieTotal != null)
			return false;
		} else if (!superficieTotal.equals(other.superficieTotal))
		    return false;
		if (tipoInmueble != other.tipoInmueble)
		    return false;
		return true;
    }

    @Override
	public String toString() {
		return  direccion.getCalle()+" N°"+direccion.getNro() + "," + direccion.getLocalidad();
	}



	public static class Builder {
		private Set<Contrato> contratos = new HashSet<>();
		private Propietario propietario;
		private Integer cantidadAmbientes;
		private Integer cantidadDormitorios;
		private Integer superficieTotal;
		private Integer superficieCubierta;
		private Boolean aEstrenar;
		private Integer cantidadCocheras;
		private Boolean conAireAcondicionado;
		private Boolean conJardin;
		private Boolean conParilla;
		private Boolean conPileta;
		private EstadoInmueble estadoInmueble;
		private TipoInmueble tipoInmueble;
		private ClaseInmueble claseInmueble;
		private Direccion direccion;
		private Set<Publicacion> publicaciones = new HashSet<>();
	
		public Builder setCantidadAmbientes(Integer cantidadAmbientes) {
		    this.cantidadAmbientes = cantidadAmbientes;
		    return this;
		}
	
		public Builder setCantidadDormitorios(Integer cantidadDormitorios) {
		    this.cantidadDormitorios = cantidadDormitorios;
		    return this;
		}
	
		public Builder setSuperficieTotal(Integer superficieTotal) {
		    this.superficieTotal = superficieTotal;
		    return this;
		}
	
		public Builder setSuperficieCubierta(Integer superficieCubierta) {
		    this.superficieCubierta = superficieCubierta;
		    return this;
		}
	
		public Builder setaEstrenar(Boolean aEstrenar) {
		    this.aEstrenar = aEstrenar;
		    return this;
		}
	
		public Builder setCantidadCocheras(Integer cantidadCocheras) {
		    this.cantidadCocheras = cantidadCocheras;
		    return this;
		}
	
		public Builder setConAireAcondicionado(Boolean conAireAcondicionado) {
		    this.conAireAcondicionado = conAireAcondicionado;
		    return this;
		}
	
		public Builder setConJardin(Boolean conJardin) {
		    this.conJardin = conJardin;
		    return this;
		}
	
		public Builder setConParilla(Boolean conParilla) {
		    this.conParilla = conParilla;
		    return this;
		}
	
		public Builder setConPileta(Boolean conPileta) {
		    this.conPileta = conPileta;
		    return this;
		}
	
		public Builder setEstadoInmueble(EstadoInmueble estadoInmueble) {
		    this.estadoInmueble = estadoInmueble;
		    return this;
		}
	
		public Builder setTipoInmueble(TipoInmueble tipoInmueble) {
		    this.tipoInmueble = tipoInmueble;
		    return this;
		}
	
		public Builder setClaseInmueble(ClaseInmueble claseInmueble) {
		    this.claseInmueble = claseInmueble;
		    return this;
		}
	
		public Builder setDireccion(Direccion direccion) {
		    this.direccion = direccion;
		    return this;
		}
	
		public Builder setPublicaciones(Set<Publicacion> publicaciones) {
		    this.publicaciones = publicaciones;
		    return this;
		}
	
	
		public Builder addPublicacion(Publicacion publicacion) {
		    this.publicaciones.add(publicacion);
		    return this;
		}
	
		public Builder setPropietario(Propietario propietario) {
		    this.propietario = propietario;
		    return this;
		}
		
		public Builder setContratos(Set<Contrato> contratos) {
		    this.contratos = contratos;
		    return this;
		}
		
		public Builder addContrato(Contrato contrato) {
		    contratos.add(contrato);
		    return this;
		}
	
		public Inmueble build() {
		    return new Inmueble(this);
		}

    }


    

}
