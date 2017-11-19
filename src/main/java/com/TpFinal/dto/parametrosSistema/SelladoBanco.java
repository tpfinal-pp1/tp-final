package com.TpFinal.dto.parametrosSistema;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;

@Entity
@Table(name = "sellados")
public class SelladoBanco implements Identificable, BorradoLogico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
    @Column(name = "provincia")
    private String provincia;

    @Column(name = "monto")
    private BigDecimal monto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }


    @Override
    public void setEstadoRegistro(EstadoRegistro estado) {

    }

    @Override
    public EstadoRegistro getEstadoRegistro() {
        return EstadoRegistro.ACTIVO;
    }
}
