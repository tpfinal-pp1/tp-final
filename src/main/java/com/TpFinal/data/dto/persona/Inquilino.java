package com.TpFinal.data.dto.persona;

import java.util.Set;

import javax.persistence.*;

import com.TpFinal.data.dto.contrato.ContratoAlquiler;


@Entity
@Table(name="inquilino")
@PrimaryKeyJoinColumn(name="id")
public class Inquilino extends RolPersona {
	
	@Enumerated(EnumType.STRING)
	@Column(name="calificacion")
	private Calificacion calificacion;
	@OneToMany(mappedBy="inquilinoContrato", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<ContratoAlquiler>contratos;
	
	public Inquilino() {
	}

	public Calificacion getCalificacion() {
		return calificacion;
	}

	public Set<ContratoAlquiler> getContratos() {
		return contratos;
	}

	public void setCalificacion(Calificacion calificacion) {
		this.calificacion = calificacion;
	}

	public void setContratos(Set<ContratoAlquiler> contratos) {
		this.contratos = contratos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((calificacion == null) ? 0 : calificacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inquilino other = (Inquilino) obj;
		if (calificacion != other.calificacion)
			return false;
		return true;
	}
	
	

	
}
