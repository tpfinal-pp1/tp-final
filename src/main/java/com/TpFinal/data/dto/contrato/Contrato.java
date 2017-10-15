package com.TpFinal.data.dto.contrato;

import com.TpFinal.data.dto.BorradoLogico;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.time.LocalDate;

@Entity
@Table(name = "contrato")
@Inheritance(strategy = InheritanceType.JOINED)
public class Contrato implements Identificable, BorradoLogico {
    private static final String estadoRegistroS = "estadoRegistro";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
    @Column(name = "fechaCelebracion")
    protected LocalDate fechaCelebracion;
    @Column(name = "documento")
    protected Blob documento;
    @Enumerated(EnumType.STRING)
    @Column(name = Contrato.estadoRegistroS)
    private EstadoRegistro estadoRegistro;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_inmueble")
    @NotNull
    protected Inmueble inmueble;
    @Enumerated(EnumType.STRING)
    @Column(name = "moneda")
    private TipoMoneda moneda;

    public Contrato() {
    }

    public Contrato(Long id, LocalDate fechaCelebracion, Blob documento, EstadoRegistro estado, Inmueble inmueble) {
	super();
	this.id = id;
	this.fechaCelebracion = fechaCelebracion;
	this.documento = documento;
	this.estadoRegistro = estado;
	this.inmueble = inmueble;
	this.moneda = TipoMoneda.Pesos;
    }

    @Override
    public Long getId() {
	return this.id;
    }
    

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    public LocalDate getFechaCelebracion() {
	return fechaCelebracion;
    }

    public void setFechaCelebracion(LocalDate fechaCelebracion) {
	this.fechaCelebracion = fechaCelebracion;
    }

    public Blob getDocumento() {
	return documento;
    }

    public void setDocumento(Blob documento) {
	this.documento = documento;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Inmueble getInmueble() {
	return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
	this.inmueble = inmueble;
    }



    @Override
    public void setEstadoRegistro(EstadoRegistro estado) {
	this.estadoRegistro = estado;

    }

    @Override
    public EstadoRegistro getEstadoRegistro() {
	return estadoRegistro;
    }

}
