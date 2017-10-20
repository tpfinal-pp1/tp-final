package com.TpFinal.dto.inmueble;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "portada")
public class Imagen {

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE })
    @JoinColumn(name = "id_inmueble")
    @NotNull
    protected Inmueble inmueble;

    @Column(name = "imagen")
    protected Blob imagen;

    @Column(name = "portada")
    private boolean portada;



}
