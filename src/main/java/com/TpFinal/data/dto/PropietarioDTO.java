package com.TpFinal.data.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by Max on 9/29/2017.
 */
@Entity
@Table(name="inquilino")
@PrimaryKeyJoinColumn(name="id")
public class PropietarioDTO extends PersonaDTO{

    @Column(name="telefono2")
    private String telefono2;

    public PropietarioDTO() {super();}

    private PropietarioDTO(Builder b) {
        super(b.id,b.nombre,b.apellido,b.mail,b.telefono);
        this.telefono2=b.telefono2;
    }

    public PropietarioDTO(Long id, String nombre, String apellido, String mail, String telefono,String telefono2) {
        super(id, nombre,apellido, mail,telefono);
        this.telefono2 = telefono2;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public static class Builder{
        private Long id;
        private String nombre;
        private String apellido;
        private String mail;
        private String telefono;
        private String telefono2;

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

        public PropietarioDTO build() {
            return new PropietarioDTO(this);
        }

    }


}
