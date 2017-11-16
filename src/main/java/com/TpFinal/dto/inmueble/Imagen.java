package com.TpFinal.dto.inmueble;

import org.hibernate.annotations.Cascade;

import com.TpFinal.dto.Identificable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.Objects;

@Entity
@Table(name = "Imagenes")
public class Imagen implements Identificable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idImagen")
    private Long idImagen;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "extension")
    private String extension;
    @Column(name = "path")
    private String path;
    @Column(name = "imagen")
    private Blob imagen;
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE })
    @JoinColumn(name = "id_inmueble")
    @NotNull
    protected Inmueble inmueble;

    @Column(name = "portada")
    private boolean isPortada;

    public Long getIdImagen() {
	return idImagen;
    }

    public Inmueble getInmueble() {
	return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
	if (inmueble != null) {
	    this.inmueble = inmueble;
	    this.inmueble.addImagem(this);
	} else {
	    if (this.inmueble != null) {
		this.inmueble.removeImagen(this);
		this.inmueble = null;
	    }
	}
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
    public Long getId() {
	return this.idImagen;
    }

    @Override
    public int hashCode() {
	return 57;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof Imagen))
	    return false;
	Imagen p = (Imagen) obj;
	return Objects.equals(p.getId(), this.getId());
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
