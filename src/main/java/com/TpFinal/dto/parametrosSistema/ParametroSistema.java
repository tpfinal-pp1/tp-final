package com.TpFinal.dto.parametrosSistema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;
import com.TpFinal.dto.interfaces.Messageable;
import com.TpFinal.dto.persona.Empleado;

@Entity
@Table(name = "parametros")
public class ParametroSistema implements Identificable {
    private static Logger logger = Logger.getLogger(ParametroSistema.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_ParametroSistema")
    private Long id;
    @Column(name = "proximo_a_vencer")
    Integer proximoAVencer = 1;
    @Column(name = "dia_de_pago")
    Integer diaDePago = 10;
    @Column(name = "cant_minima_certificados")
    Integer cantMinimaCertificados = 2;
    @Column(name = "valor_certificado")
    BigDecimal valorCertificado = BigDecimal.valueOf(1900);
    @Column(name = "comision_a_propietario")
    Integer comisionAPropietario =  4;
    @Column(name = "comision_a_inquilino")
    Integer comisionAInquilino = 3;
    @Column(name = "comision_cobro")
    Double comisionCobro = 0.06;
//    @Column(name = "comision_cobro")
//    Double comisionCobro = 0.06;
    

    public ParametroSistema() {

    }

    private ParametroSistema(Builder b) {
	
    }

    public void setId(Long id) {
	this.id = id;
    }   

    @Override
    public Long getId() {
	return id;
    }

    @Override
    public int hashCode() {
	return 31;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof ParametroSistema))
	    return false;
	ParametroSistema other = (ParametroSistema) obj;
	return getId() != null && Objects.equals(getId(), other.getId());
    }

   
    public static class Builder {

	private TipoCita tipoDeCita;
	private String observaciones;
	private String direccionLugar;
	private String citado;
	private LocalDateTime fechahora;
	private Empleado empleado;

	public Builder setTipoDeCita(TipoCita tipoDeCita) {
	    this.tipoDeCita = tipoDeCita;
	    return this;
	}

	public Builder setObservaciones(String observaciones) {
	    this.observaciones = observaciones;
	    return this;
	}

	public Builder setDireccionLugar(String direccionLugar) {
	    this.direccionLugar = direccionLugar;
	    return this;
	}

	public Builder setCitado(String citado) {
	    this.citado = citado;
	    return this;
	}

	public Builder setEmpleado(Empleado empleado) {
	    this.empleado = empleado;
	    return this;
	}

	public Builder setFechahora(LocalDateTime fechahora) {
	    this.fechahora = fechahora;
	    return this;
	}

	public ParametroSistema build() {
	    return new ParametroSistema(this);
	}

    }

}
