package com.TpFinal.data.dto.persona;

import java.util.Set;

import javax.persistence.*;

import com.TpFinal.data.dto.contrato.ContratoAlquiler;


@Entity
@Table(name="inquilino")
@PrimaryKeyJoinColumn(name="id")
public class Inquilino extends RolPersona {
	
	@Enumerated(EnumType.STRING)
	@Column(name="calificacion")
	Calificacion calificacion;
	@OneToMany(mappedBy="inquilinoContrato", fetch=FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, 
			CascadeType.PERSIST, CascadeType.REFRESH})
	Set<ContratoAlquiler>contratos;
	

	
}
