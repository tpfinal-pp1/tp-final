package com.TpFinal.domain.persona;



import com.TpFinal.domain.Identificable;
import com.TpFinal.domain.inmueble.Direccion;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted
 * in the database and can also be easily converted to different formats like JSON.
 */
// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
@Entity
@Table(name = "persona")
@Inheritance(strategy=InheritanceType.JOINED)
public class Persona implements Identificable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre ="";

    @Column(name = "apellido")
    private String apellido ="";

    @Column(name = "telefono")
    private String telefono ="";

    @Column(name = "email")
    private String mail ="";

    @Column(name = "FdeNac")
    private LocalDate FdeNac;

    @Column(name = "dni")
    private String DNI="";

    @Column(name = "info")
    private String masInformación="";

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressID")
    private Direccion address;


    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;



    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Direccion getAddress() {
        return address;
    }

    public void setAddress(Direccion address) {
        this.address = address;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }



    public enum Sexo {
        Masculino, Femenino;

        @Override
        public String toString(){

            if(this== Sexo.Masculino){
                return "Masculino";}
            else{return "Femenino";}

        }
        public static Sexo toGenero(String gen){
            switch (gen) {
                case "Masculino": return Masculino;
                case "Femenino": return Femenino;
                case "m": return Masculino;
                case "f": return Femenino;
            }
            return null;
        }

    }


    public Persona(Long id, String nombre, String apellido, String mail, String telefono) {
        this.id=id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.telefono = telefono;
    }
    public Persona(Long idd){
        id=idd;
    }
    public Persona(){

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String firstName) {
        this.nombre = firstName;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String lastName) {
        this.apellido = lastName;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String phone) {
        this.telefono = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String email) {
        this.mail = email;
    }

    public LocalDate getFdeNac() {
        return FdeNac;
    }

    public void setFdeNac(LocalDate fdeNac) {
        FdeNac = fdeNac;
    }

    @Override
    public Persona clone() throws CloneNotSupportedException {
        try {
            return (Persona) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", Nombre=" + nombre
                + ", Apellido=" + apellido + ", Telefono=" + telefono + ", email="
                + mail + ", FdeNac= "  + FdeNac+'}';
    }




}
