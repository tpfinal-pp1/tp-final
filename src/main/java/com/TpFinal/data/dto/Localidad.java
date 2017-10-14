package com.TpFinal.data.dto;

import javax.persistence.Entity;

@Entity

public class Localidad {

	 
		private String nombre;
	    private String codigoPostal;
	    private Provincia provincia;

	    public Localidad() {}
	    
	    public Localidad(String nombre, String codPostal){
	        this.nombre=nombre;
	        this.codigoPostal=codPostal;
	    }
	    
	    public Localidad(String nombre, String codPostal, Provincia provincia){
	        this.nombre=nombre;
	        this.codigoPostal=codPostal;
	        this.provincia = provincia;
	    }

	    @Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Localidad other = (Localidad) obj;
			if (codigoPostal == null) {
				if (other.codigoPostal != null)
					return false;
			} else if (!codigoPostal.equals(other.codigoPostal))
				return false;
			if (nombre == null) {
				if (other.nombre != null)
					return false;
			} else if (!nombre.equals(other.nombre))
				return false;
			if (provincia == null) {
				if (other.provincia != null)
					return false;
			} else if (!provincia.equals(other.provincia))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
			result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
			result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
			return result;
		}

	    @Override
	    public String toString() {
	    	if(this.nombre.equals("Ciudad Autonoma Buenos Aires"))
	    		return "C.A.B.A - "+this.codigoPostal;
	    	else {
				return nombre;
			}
	    }

		public String getNombre() {
			return nombre;
		}

		public String getCodigoPostal() {
			return codigoPostal;
		}

		public Provincia getProvincia() {
			return provincia;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public void setCodigoPostal(String codigoPostal) {
			this.codigoPostal = codigoPostal;
		}

		public void setProvincia(Provincia provincia) {
			this.provincia = provincia;
		}

	  

}
