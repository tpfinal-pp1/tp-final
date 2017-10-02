package com.TpFinal.data.dto.persona;

import com.TpFinal.data.dto.inmueble.Inmueble;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class RolPersona {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    @NotNull
    private Persona persona;

    @Id
    @GeneratedValue
    @Column(name = "idRol")
    protected Long idRol;




}
