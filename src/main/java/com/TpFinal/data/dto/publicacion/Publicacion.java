package com.TpFinal.data.dto.publicacion;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.TpFinal.data.dto.BorradoLogico;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.inmueble.Inmueble;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "publicaciones")
public abstract class Publicacion implements Identificable,BorradoLogico {
	
	public static final String pTipoOperacion ="tipoOperacion";
	
	@Id
	@GeneratedValue
	@Column(name = "idOperacion")
	protected Long idOperacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_inmueble")
	@NotNull
	protected Inmueble inmueble;
	
	@Column(name = "fecha_publicacion")
	protected LocalDate fechaPublicacion;
	
	@Enumerated(EnumType.STRING)
	@Column (name = Publicacion.pTipoOperacion)
	protected TipoPublicacion tipoOperacion;
	
	@Enumerated(EnumType.STRING)
	protected EstadoRegistro estadoRegistro;
	
	@Override
	public Long getId() {
		return idOperacion;
	}
	
	@SuppressWarnings("unused")
	private void setIdOperacion(Long idOperacion) {
		this.idOperacion = idOperacion;
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

	public TipoPublicacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoPublicacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	@Override
	public String toString() {
		return "Operacion \n[\nidOperacion=" + idOperacion+"\nfechaPublicacion="
				+ fechaPublicacion + "\ntipoOperacion=" + tipoOperacion + "\n]";
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
