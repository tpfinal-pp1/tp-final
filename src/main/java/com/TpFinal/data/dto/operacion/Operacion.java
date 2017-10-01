package com.TpFinal.data.dto.operacion;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.inmueble.Inmueble;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "operaciones")
public abstract class Operacion implements Identificable {
	
	@Id
	@GeneratedValue
	@Column(name = "id_operacion")
	protected Long idOperacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	protected Inmueble inmueble;
	
	@Column(name = "fecha_publicacion")
	protected LocalDate fechaPublicacion;
	
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
	

}
