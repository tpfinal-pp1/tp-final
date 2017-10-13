package com.TpFinal.data.dto.publicacion;

import com.TpFinal.data.dto.BorradoLogico;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.persona.Propietario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "publicaciones")
public abstract class Publicacion implements Identificable, BorradoLogico {

    public static final String pTipoPublicacion = "tipoPublicacion";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPublicacion")
    protected Long idPublicacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_inmueble")
    @NotNull
    protected Inmueble inmueble;

    @Column(name = "fecha_publicacion")
    protected LocalDate fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = Publicacion.pTipoPublicacion)
    protected TipoPublicacion tipoPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_publicacion")
    protected EstadoPublicacion estadoPublicacion;

    public static String getpTipoPublicacion() {
	return pTipoPublicacion;
    }

    @Enumerated(EnumType.STRING)
    protected EstadoRegistro estadoRegistro;

    @Override
    public Long getId() {
	return idPublicacion;
    }

    @SuppressWarnings("unused")
    private void setIdPublicacion(Long idPublicacion) {
	this.idPublicacion = idPublicacion;
    }

    public Inmueble getInmueble() {
	return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {	
	    this.inmueble = inmueble;	
    }

    public LocalDate getFechaPublicacion() {
	return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
	this.fechaPublicacion = fechaPublicacion;
    }

    public TipoPublicacion getTipoPublicacion() {
	return tipoPublicacion;
    }

    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
	this.tipoPublicacion = tipoPublicacion;
    }

    public EstadoPublicacion getEstadoPublicacion() {
	return estadoPublicacion;
    }

    public void setEstadoPublicacion(EstadoPublicacion estadoPublicacion) {
	this.estadoPublicacion = estadoPublicacion;
    }

    @Override
    public String toString() {
	return inmueble + " " + tipoPublicacion;
    }

    @Override
    public void setEstadoRegistro(EstadoRegistro estado) {
	this.estadoRegistro = estado;

    }

    @Override
    public EstadoRegistro getEstadoRegistro() {
	return estadoRegistro;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((estadoPublicacion == null) ? 0 : estadoPublicacion.hashCode());
	result = prime * result + ((estadoRegistro == null) ? 0 : estadoRegistro.hashCode());
	result = prime * result + ((fechaPublicacion == null) ? 0 : fechaPublicacion.hashCode());
	result = prime * result + ((idPublicacion == null) ? 0 : idPublicacion.hashCode());
	result = prime * result + ((tipoPublicacion == null) ? 0 : tipoPublicacion.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Publicacion))
	    return false;
	Publicacion other = (Publicacion) obj;
	if (estadoPublicacion != other.estadoPublicacion)
	    return false;
	if (estadoRegistro != other.estadoRegistro)
	    return false;
	if (fechaPublicacion == null) {
	    if (other.fechaPublicacion != null)
		return false;
	} else if (!fechaPublicacion.equals(other.fechaPublicacion))
	    return false;
	if (idPublicacion == null) {
	    if (other.idPublicacion != null)
		return false;
	} else if (!idPublicacion.equals(other.idPublicacion))
	    return false;
	if (tipoPublicacion != other.tipoPublicacion)
	    return false;
	return true;
    }
    
    

}
