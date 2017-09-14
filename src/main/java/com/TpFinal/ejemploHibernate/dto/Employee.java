package com.TpFinal.ejemploHibernate.dto;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Identificable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "salary")
	private int salary;

	public Employee() {
	}
	
	public Employee(String firstName, String lastName, int salary) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
	}

	//Recomendado en documentación de hibernate.
	private void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first_name) {
		this.firstName = first_name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String last_name) {
		this.lastName = last_name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "First Name: " + firstName + "\nLastName: " + lastName + "\nSalary: " + salary + "\n";
	}
}
