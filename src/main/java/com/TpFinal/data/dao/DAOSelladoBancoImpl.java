package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOContratoDuracion;
import com.TpFinal.data.dao.interfaces.DAOSelladoBanco;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.parametrosSistema.SelladoBanco;

public class DAOSelladoBancoImpl extends DAOImpl<SelladoBanco> implements DAOSelladoBanco {
    public DAOSelladoBancoImpl() {
        super(SelladoBanco.class);
    }
}
