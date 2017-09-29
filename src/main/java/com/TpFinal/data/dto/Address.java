package com.TpFinal.data.dto;

import javax.persistence.*;


@Entity
@Table(name = "address")
public class Address implements Identificable {
	@Id @GeneratedValue
	@Column(name = "addressID")
	private Long id;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "number")
	private int number;
	
	@Column(name = "floor")
	private int piso;
	
	@Column(name = "apartment")
	private String apartment;

	@Column(name = "city")
	private String city;

	@Column(name = "county")
	private String county;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "zip")
	private String zip;






	public Address(Long id) {
		this.id =id;
	}

	public Address() {
	}


	@Override
	public String toString() {
		return "Address [addressID=" + id + ", street=" + street + ", number=" + number + ", floor=" + piso
				+ ", aparment=" + apartment + "]";
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
