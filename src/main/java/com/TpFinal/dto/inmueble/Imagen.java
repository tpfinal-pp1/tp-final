package com.TpFinal.dto.inmueble;

import org.hibernate.annotations.Cascade;

import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.Objects;

@Embeddable
@Table(name = "Imagenes")
public class Imagen {

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "extension")
    private String extension;
    @Column(name = "path")
    private String path;
    @Column(name = "imagen")
    private Blob imagen;

    @Column(name = "portada")
    private boolean isPortada;

    public Imagen() {
	super();
    }

    public Blob getImagen() {
	return imagen;
    }

    public void setImagen(Blob imagen) {
	this.imagen = imagen;
    }

    public boolean isPortada() {
	return isPortada;
    }

    public void setIsPortada(boolean portada) {
	this.isPortada = portada;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    private Imagen(Builder b) {
	this.nombre = b.nombre;
	this.extension = b.extension;
	this.imagen = b.archivo;
	this.isPortada = b.isPortada;
	this.path = b.path;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getExtension() {
	return extension;
    }

    public void setExtension(String extension) {
	this.extension = extension;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((extension == null) ? 0 : extension.hashCode());
	result = prime * result + (isPortada ? 1231 : 1237);
	result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
	result = prime * result + ((path == null) ? 0 : path.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Imagen))
	    return false;
	Imagen other = (Imagen) obj;
	if (extension == null) {
	    if (other.extension != null)
		return false;
	} else if (!extension.equals(other.extension))
	    return false;
	if (isPortada != other.isPortada)
	    return false;
	if (nombre == null) {
	    if (other.nombre != null)
		return false;
	} else if (!nombre.equals(other.nombre))
	    return false;
	if (path == null) {
	    if (other.path != null)
		return false;
	} else if (!path.equals(other.path))
	    return false;
	return true;
    }

    public static class Builder {
	private String path;
	private boolean isPortada;
	private String nombre;
	private String extension;
	private Blob archivo;

	public Builder setPath(String path) {
	    this.path = path;
	    return this;
	}

	public Builder setNombre(String dato) {
	    this.nombre = dato;
	    return this;
	}

	public Builder setExtension(String dato) {
	    this.extension = dato;
	    return this;
	}

	public Builder setDocumento(Blob dato) {
	    this.archivo = dato;
	    return this;
	}

	public Builder setIsPortada(boolean isPortada) {
	    this.isPortada = isPortada;
	    return this;
	}

	public Imagen build() {
	    return new Imagen(this);
	}

    }

}
