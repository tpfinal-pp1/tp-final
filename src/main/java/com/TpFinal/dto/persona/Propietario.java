package com.TpFinal.dto.persona;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.Apropiable;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.inmueble.Inmueble;


@Entity
@Table(name="propietarios")
@PrimaryKeyJoinColumn(name="id")
public class Propietario extends RolPersona implements Apropiable {
	
	
	
	@OneToMany(mappedBy="propietario", fetch=FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Set<Inmueble>inmuebles= new HashSet<>();
	
		
	public Propietario() {super();}
	
	private Propietario(Builder b) {
	 	super(b.persona, EstadoRegistro.ACTIVO);
		this.inmuebles=b.inmuebles;
	
	}
	public void addInmueble(Inmueble i) {
		if(!this.inmuebles.contains(i)) {
			inmuebles.add(i);
			i.setPropietario(this);
		}
	}
	
	public void removeInmueble(Inmueble i) {
		if(this.inmuebles.contains(i)) {
			this.inmuebles.remove(i);
			i.setPropietario(null);
		}
	}

	public Set<Inmueble> getInmuebles() {
	    return inmuebles;
	}

	public void setInmuebles(Set<Inmueble> inmuebles) {
	    this.inmuebles = inmuebles;
	}
	
	@Override
	public String getNombreCompleto() {
		return this.getPersona().getNombre()+" "+this.getPersona().getApellido();
	}

	@Override
	public String toString() {
	    
	    String ret = "";
	    if (super.getPersona() != null) {
		ret = super.getPersona().getNombre() + " " + super.getPersona().getApellido();
	    }
	    return ret;
	}
	
	   @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Propietario)) return false;
	        Propietario inmueble = (Propietario) o;
	        return getId() != null && Objects.equals(getId(), inmueble.getId());
	    }
	 
	    @Override
	    public int hashCode() {
	        return 57;
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
