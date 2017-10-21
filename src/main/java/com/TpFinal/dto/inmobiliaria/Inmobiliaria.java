package com.TpFinal.dto.inmobiliaria;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.Apropiable;
import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
@Entity
@Table(name = "inmobiliarias")
public class Inmobiliaria implements Identificable, BorradoLogico, Apropiable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private Long id;
	@Column(name = "cuit")
	@NotNull
	private String cuit;
	@Column(name = "nombre")
	private String nombre;
	@OneToMany(mappedBy="inmobiliaria", fetch=FetchType.EAGER)
	@Cascade({ CascadeType.SAVE_UPDATE})
	private Set<Inmueble>inmuebles=new HashSet<>();;
	@Column(name="estadoRegistro")
	private EstadoRegistro estadoRegistro;
	@OneToOne
	@Cascade(CascadeType.ALL)
	private Direccion direccion;
	@Column(name = "mail")
	private String mail;
	@Column(name = "telefono")
	private String telefono;
	
	
	public Inmobiliaria() {this.estadoRegistro=EstadoRegistro.ACTIVO;}
	
	private Inmobiliaria(Builder b) {
		this.nombre=b.nombre;
		this.cuit=b.cuit;
		this.estadoRegistro=EstadoRegistro.ACTIVO;
		this.direccion=b.direccion;
		this.mail=b.mail;
		this.telefono=b.telefono;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setInmuebles(Set<Inmueble> inmuebles) {
		this.inmuebles = inmuebles;
	}
	
	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}

	public String getMail() {
		return mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String getNombreCompleto() {
		return nombre;
	}

	@Override
	public Set<Inmueble> getInmuebles() {
		return this.inmuebles;
	}

	@Override
	public void addInmueble(Inmueble i) {
		System.out.println(this.inmuebles.size());
		if(!this.inmuebles.contains(i)) {
			inmuebles.add(i);
			i.setInmobiliaria(this);
		}
		
	}

	@Override
	public void removeInmueble(Inmueble i) {
		if(this.inmuebles.contains(i)) {
			this.inmuebles.remove(i);
			i.setInmobiliaria(null);
		}
	}

	@Override
	public void setEstadoRegistro(EstadoRegistro estado) {
		this.estadoRegistro=estado;
		
	}

	@Override
	public EstadoRegistro getEstadoRegistro() {
		return this.estadoRegistro;
	}

	@Override
	public Long getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuit == null) ? 0 : cuit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inmobiliaria other = (Inmobiliaria) obj;
		if (cuit == null) {
			if (other.cuit != null)
				return false;
		} else if (!cuit.equals(other.cuit))
			return false;
		return true;
	}
	
	public boolean isSame(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inmobiliaria other = (Inmobiliaria) obj;
		if (cuit == null) {
			if (other.cuit != null)
				return false;
		} else if (!cuit.equals(other.cuit))
			return false;
		if (estadoRegistro != other.estadoRegistro)
			return false;
		if (inmuebles == null) {
			if (other.inmuebles != null)
				return false;
		} else if (!inmuebles.equals(other.inmuebles))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

	public static class Builder{
		private String nombre;
		private Set<Inmueble>inmuebles=new HashSet<>();
		private String cuit;
		private Direccion direccion;
		private String mail;
		private String telefono;
		
		public Builder setNombre(String nombre) {
			this.nombre=nombre;
			return this;
		}
		
		public Builder setInmuebles(Set<Inmueble>dato) {
			this.inmuebles=dato;
			return this;
		}
		
		public Builder setCuit(String dato){
			this.cuit=dato;
			return this;
		}
		public Builder setDireccion(Direccion dato){
			this.direccion=dato;
			return this;
		}
		public Builder setMail(String dato){
			this.mail=dato;
			return this;
		}
		public Builder setTelefono(String dato){
			this.telefono=dato;
			return this;
		}
		
		
		
		public Inmobiliaria build() {
			return new Inmobiliaria(this);
		}
	}

}
