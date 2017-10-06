package com.TpFinal.data.dto.publicacion;

import com.TpFinal.data.dto.BorradoLogico;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.persona.Propietario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "publicaciones")
public abstract class Publicacion implements Identificable,BorradoLogico {
	
	public static final String pTipoPublicacion ="tipoPublicacion";
	
	@Id
	@GeneratedValue
	@Column(name = "idPublicacion")
	protected Long idPublicacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_inmueble")
	@NotNull
	protected Inmueble inmueble;
	
	@Column(name = "fecha_publicacion")
	protected LocalDate fechaPublicacion;
	
	@Enumerated(EnumType.STRING)
	@Column (name = Publicacion.pTipoPublicacion)
	protected TipoPublicacion tipoPublicacion;

	@Enumerated(EnumType.STRING)
	@Column (name = "estado_publicacion")
	protected EstadoPublicacion estadoPublicacion;

	public static String getpTipoPublicacion() {
		return pTipoPublicacion;
	}

	@Enumerated(EnumType.STRING)
	protected EstadoRegistro estadoRegistro;

	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE})
	@JoinColumn(name = "id_proppietario")
	protected Propietario propietarioPublicacion;


	@Override
	public Long getId() {
		return idPublicacion;
	}
	
	@SuppressWarnings("unused")
	private void setIdPublicacion(Long idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public TipoPublicacion getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	public EstadoPublicacion getEstadoPublicacion() { return estadoPublicacion; }

	public void setEstadoPublicacion(EstadoPublicacion estadoPublicacion) { this.estadoPublicacion = estadoPublicacion; }

	public Propietario getPropietarioPublicacion() { return propietarioPublicacion; }

	public void setPropietarioPublicacion(Propietario propietarioPublicacion) { this.propietarioPublicacion = propietarioPublicacion; }

	@Override
	public String toString() {
		return inmueble+" "+tipoPublicacion;
	}
	
	@Override
	public void setEstadoRegistro(EstadoRegistro estado) {
	    this.estadoRegistro = estado;
	    
	}

	@Override
	public EstadoRegistro getEstadoRegistro() {
	    return estadoRegistro;
	}
	
	
	

}
