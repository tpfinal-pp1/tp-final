package com.TpFinal.data.dto.persona;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.inmueble.Inmueble;


@Entity
@Table(name="propietarios")
@PrimaryKeyJoinColumn(name="id")
public class Propietario extends RolPersona {
	
	
	
	@OneToMany(mappedBy="propietario", fetch=FetchType.EAGER, cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Set<Inmueble>inmuebles= new HashSet<>();
	
		
	public Propietario() {super();}
	
	private Propietario(Builder b) {
	 	super(b.persona, b.estadoRegistro);
		this.inmuebles=b.inmuebles;
	
	}
	

	public Set<Inmueble> getInmuebles() {
	    return inmuebles;
	}

	public void setInmuebles(Set<Inmueble> inmuebles) {
	    this.inmuebles = inmuebles;
	}


	public static class Builder{
		private Set<Inmueble>inmuebles = new HashSet<>();
		private Persona persona;
		private EstadoRegistro estadoRegistro;
		
		public Builder setInmuebles(Set<Inmueble> inmuebles) {
		    this.inmuebles = inmuebles;
		    return this;
		}
		
		public Builder addInmueble(Inmueble i) {
		    inmuebles.add(i);
		    return this;
		}
		
		public Builder setPersona(Persona dato) {
			this.persona=dato;
			return this;
		}
		
		public Builder setEstadoRegistro(EstadoRegistro estadoRegistro) {
		    this.estadoRegistro = estadoRegistro;
		    return this;
		}
		

		public Propietario build() {
			return new Propietario(this);
		}
	}
	
	

	
}
