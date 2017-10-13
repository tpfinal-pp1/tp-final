package com.TpFinal.data.dto.inmueble;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.TpFinal.data.dto.Identificable;

@Entity
@Table (name= "coordenadas")
public class Coordenada implements Identificable{
	
	
	public Coordenada() {
		super();
	}
	
	public Coordenada(Double lat, Double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	@Id @GeneratedValue
	private Long idCoordenada;
	
	@Column (name = "latitud")
	private Double lat;
	
	
	@Column (name = "longitud")
	private Double lon;
	
	@Override
	public Long getId() {
		return idCoordenada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lat == null) ? 0 : lat.hashCode());
		result = prime * result + ((lon == null) ? 0 : lon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Coordenada))
			return false;
		Coordenada other = (Coordenada) obj;
		if (lat == null) {
			if (other.lat != null)
				return false;
		} else if (!lat.equals(other.lat))
			return false;
		if (lon == null) {
			if (other.lon != null)
				return false;
		} else if (!lon.equals(other.lon))
			return false;
		return true;
	}

	
}
