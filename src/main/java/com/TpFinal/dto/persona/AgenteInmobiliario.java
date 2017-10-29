package com.TpFinal.dto.persona;

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
import com.TpFinal.dto.cita.Recordatorio;

@Entity
@Table(name = "AgentesInmobiliarios")
@PrimaryKeyJoinColumn(name = "idPersona")
public class AgenteInmobiliario extends Empleado {
    @OneToMany(mappedBy = "agenteInmb", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    private Set<Cita> calendarioPersonal = new HashSet<>();

    public AgenteInmobiliario() {
	// TODO Auto-generated constructor stub
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

}
