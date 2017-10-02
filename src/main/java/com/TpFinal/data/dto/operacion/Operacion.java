package com.TpFinal.data.dto.operacion;

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

import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.inmueble.Inmueble;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "operaciones")
public abstract class Operacion implements Identificable {
	
	public static final String pTipoOperacion ="tipoOperacion";
	
	@Id
	@GeneratedValue
	@Column(name = "idOperacion")
	protected Long idOperacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "inmueble")
	@NotNull
	protected Inmueble inmueble;
	
	@Column(name = "fecha_publicacion")
	protected LocalDate fechaPublicacion;
	
	@Enumerated(EnumType.STRING)
	@Column (name = Operacion.pTipoOperacion)
	protected TipoOperacion tipoOperacion;
	
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

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	@Override
	public String toString() {
		return "Operacion \n[\nidOperacion=" + idOperacion+"\nfechaPublicacion="
				+ fechaPublicacion + "\ntipoOperacion=" + tipoOperacion + "\n]";
	}	
	
	
	

}
