package com.TpFinal.ejemploHibernate.dto;

import javax.persistence.*;


@Entity
@Table(name = "domicilios")
public class DomicilioDTO implements Identificable {
	@Id @GeneratedValue
	@Column(name = "idDomicilio")
	private Integer idDomicilio;
	
	@Column(name = "calle")
	private String calle;
	
	@Column(name = "altura")
	private int altura;
	
	@Column(name = "piso")
	private int piso;
	
	@Column(name = "departamento")
	private String departamento;
	

	public DomicilioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public DomicilioDTO(Integer idDomicilio, String calle, int altura, int piso, String departamento) {
		super();
		this.idDomicilio = idDomicilio;
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
	}
	
	public DomicilioDTO(String calle, int altura, int piso, String departamento) {
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
	}
	
	public int getIdDomicilio() {
		return idDomicilio;
	}

	public String getCalle() {
		return calle;
	}

	public int getAltura() {
		return altura;
	}

	public int getPiso() {
		return piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	//Recomendado en documentación de hibernate.
	private void setIdDomicilio(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomicilioDTO other = (DomicilioDTO) obj;
		if (altura != other.altura)
			return false;
		if (calle == null) {
			if (other.calle != null)
				return false;
		} else if (!calle.equals(other.calle))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		if (piso != other.piso)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DomicilioDTO [idDomicilio=" + idDomicilio + ", calle=" + calle + ", altura=" + altura + ", piso=" + piso
				+ ", departamento=" + departamento + "]";
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
