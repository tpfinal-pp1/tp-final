package com.TpFinal.dto.notificacion;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;
import com.TpFinal.dto.interfaces.Messageable;

@Entity
@Table(name="notificaciones")
public class Notificacion implements Identificable, BorradoLogico {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Notificacion")
	private Long id;
	@Column(name="Mensaje")
	private String mensaje;
	@Column(name="Titulo")
	private String titulo;
	@Column(name="visto")
	private Boolean visto;
	@Column(name="user")
	private String usuario;
	@Column(name="idCita")
	private String idCita;
	@Enumerated(EnumType.STRING)
	@Column(name="estadoRegistro")
	private EstadoRegistro estadoRegistro;
	@Column(name="fechaCreacion")
	private LocalDateTime fechaCreacion;
	
	public Notificacion() {
		this.visto=false;
		this.estadoRegistro=EstadoRegistro.ACTIVO;
		this.fechaCreacion=LocalDateTime.now();
	}

	public Notificacion(Messageable m) {
		this.mensaje=m.getMessage();
		this.titulo=m.getTitulo();
		this.visto=false;
		this.estadoRegistro=EstadoRegistro.ACTIVO;
		this.fechaCreacion=LocalDateTime.now();
	}


	public String getIdCita() {
		return idCita;
	}

	public void setIdCita(String idCita) {
		this.idCita = idCita;
	}

	@Override
	public void setEstadoRegistro(EstadoRegistro estado) {
		this.estadoRegistro=estado;
	}

	@Override
	public EstadoRegistro getEstadoRegistro() {
		return this.estadoRegistro;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean isVisto() {
		return visto;
	}

	public void setVisto(Boolean visto) {
		this.visto = visto;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Boolean getVisto() {
		return visto;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Notificacion))
			return false;
		Notificacion other = (Notificacion) obj;
		return getId() != null && Objects.equals(getId(), other.getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

}
