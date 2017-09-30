package com.TpFinal.data.dto;

import com.TpFinal.data.dto.persona.Persona;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by Max on 9/29/2017.
 */
@Entity
@Table(name="propietario")
@PrimaryKeyJoinColumn(name="id")
public class Propietario extends Persona {


    public Propietario() {super();}

    private Propietario(Builder b) {
        super(b.id,b.nombre,b.apellido,b.mail,b.telefono,b.telefono2,b.DNI,b.infoAdicional);

    }

    public Propietario(Long id, String nombre, String apellido, String mail, String telefono, String telefono2,String DNI, String infoAdicional) {
        super(id, nombre,apellido, mail,telefono,telefono2,DNI,infoAdicional);

    }


    public static class Builder{
        private Long id;
        private String nombre;
        private String apellido;
        private String mail;
        private String telefono;
        private String telefono2;
        private String DNI;
        private String infoAdicional;

        public Builder setInfoAdicional(String infoAdicional) {
            this.infoAdicional=infoAdicional;
            return this;
        }


        public Builder setDNI(String DNI) {
            this.DNI=DNI;
            return this;
        }

        public Builder setId(Long dato) {
            this.id=dato;
            return this;
        }

        public Builder setNombre(String dato) {
            this.nombre=dato;
            return this;
        }

        public Builder setApellido(String dato) {
            this.apellido=dato;
            return this;
        }

        public Builder setMail(String dato) {
            this.mail=dato;
            return this;
        }

        public Builder setTelefono(String dato) {
            this.telefono=dato;
            return this;
        }

        public Builder setTelefono2(String dato) {
            this.telefono2=dato;
            return this;
        }

        public Propietario build() {
            return new Propietario(this);
        }

    }


}
