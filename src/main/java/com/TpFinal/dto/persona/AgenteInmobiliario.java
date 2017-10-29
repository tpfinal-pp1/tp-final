package com.TpFinal.dto.persona;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;

@Entity
@Table(name = "AgentesInmobiliarios")
@PrimaryKeyJoinColumn(name = "idPersona")
public class AgenteInmobiliario extends Empleado {
    @OneToMany(mappedBy = "agenteInmb", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    private Set<Cita> calendarioPersonal = new HashSet<>();

    public AgenteInmobiliario() {
	super();
    }

    protected AgenteInmobiliario(Builder b) {
	super(b);
	this.calendarioPersonal = b.calendarioPersonal;
    }

    public Set<Cita> getCalendarioPersonal() {
	return calendarioPersonal;
    }

    public void setCalendarioPersonal(Set<Cita> calendarioPersonal) {
	this.calendarioPersonal = calendarioPersonal;
    }

    public void addCita(Cita cita) {
	if (!this.calendarioPersonal.contains(cita)) {
	    this.calendarioPersonal.add(cita);
	    cita.setAgenteInmboliliario(this);
	}
    }

    public void removeCita(Cita cita) {
	if (this.calendarioPersonal.contains(cita)) {
	    this.calendarioPersonal.remove(cita);
	    cita.setAgenteInmboliliario(null);
	}
    }

    @Override
    public int hashCode() {
	return 31;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof AgenteInmobiliario))
	    return false;
	AgenteInmobiliario other = (AgenteInmobiliario) obj;
	return getId() != null && Objects.equals(getId(), other.getId());
    }

    public static class Builder extends Empleado.Builder {
	protected Set<Cita> calendarioPersonal = new HashSet<>();
	public Builder setCalendarioPersonal(Set<Cita> calendarioPersonal) {
	    this.calendarioPersonal = calendarioPersonal;
	    return this;
	}
	

	@Override
	public Builder setEstadoEmpeado(EstadoEmpleado dato) {
	    super.setEstadoEmpeado(dato);
	    return this;
	}

	@Override
	public Builder setFechaDeAlta(LocalDate dato) {
	    super.setFechaDeAlta(dato);
	    return this;
	}

	@Override
	public Builder setFechaDeBaja(LocalDate dato) {
	    super.setFechaDeBaja(dato);
	    return this;
	}

	@Override
	public Builder setCredencial(Credencial dato) {
	    super.setCredencial(dato);
	    return this;
	}

	@Override
	public Builder setId(Long dato) {
	    super.setId(dato);
	    return this;
	}

	@Override
	public Builder setNombre(String dato) {
	    super.setNombre(dato);
	    return this;
	}

	@Override
	public Builder setApellido(String dato) {
	    super.setApellido(dato);
	    return this;
	}

	@Override
	public Builder setMail(String dato) {
	    super.setMail(dato);
	    return this;
	}

	@Override
	public Builder setTelefono(String dato) {
	    super.setTelefono(dato);
	    return this;
	}

	@Override
	public Builder setTelefono2(String dato) {
	    super.setTelefono2(dato);
	    return this;
	}

	@Override
	public Builder setDNI(String dato) {
	    super.setDNI(dato);
	    return this;
	}

	@Override
	public Builder setinfoAdicional(String dato) {
	    super.setinfoAdicional(dato);
	    return this;
	}

	@Override
	public Builder setRoles(Set<RolPersona> dato) {
	    super.setRoles(dato);
	    return this;
	}

	@Override
	public Builder setPrefBusqueda(CriterioBusqInmueble dato) {
	    super.setPrefBusqueda(dato);
	    return this;
	}

	@Override
	public Builder setEsInmobiliaria(Boolean dato) {
	    super.setEsInmobiliaria(dato);
	    return this;
	}

	@Override
	public AgenteInmobiliario build() {
	    return new AgenteInmobiliario(this);
	}

    }

}
