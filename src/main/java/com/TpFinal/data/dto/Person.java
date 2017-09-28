package com.TpFinal.data.dto;



import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted
 * in the database and can also be easily converted to different formats like JSON.
 */
// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
@Entity
@Table(name = "people")
public class Person implements Serializable, Cloneable,Identificable {

        @Id
        @GeneratedValue
        @Column(name = "personID")
        private Long id;

    @Column(name = "firstName")
    private String firstName="";

    @Column(name = "lastName")
    private String lastName="";

    @Column(name = "phone")
    private String phone="";

    @Column(name = "email")
    private String email="";

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Column(name = "dni")
    private String DNI ="";

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressID")
    private Address address;


    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;



    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }



    public enum Sex {
        Masculino, Femenino;

        @Override
        public String toString(){

            if(this== Sex.Masculino){
                return "Masculino";}
            else{return "Femenino";}

        }
        public String genreEndWord(){

            if(this== Sex.Masculino){
                return "o";}
            else{return "a";}

        }
        public static Sex toGenero(String gen){
            switch (gen) {
                case "Masculino": return Masculino;
                case "Femenino": return Femenino;
                case "m": return Masculino;
                case "f": return Femenino;
            }
            return null;
        }

    }



    public Person(Long idd){
        id=idd;
    }
    public Person(){

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        try {
            return (Person) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", phone=" + phone + ", email="
                + email + ", birthDate=" + birthDate + '}';
    }




}
