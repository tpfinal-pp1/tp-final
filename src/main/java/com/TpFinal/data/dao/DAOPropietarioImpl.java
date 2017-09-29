package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dao.interfaces.DAOPropietario;
import com.TpFinal.data.dto.DashboardNotification;
import com.TpFinal.data.dto.PropietarioDTO;
import com.TpFinal.data.dto.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by Max on 9/29/2017.
 */
public class DAOPropietarioImpl implements DAOPropietario{

    private DAO<PropietarioDTO> dao;

    public DAOPropietarioImpl() { dao = new DAOImpl<>(PropietarioDTO.class); }
    @Override
    public boolean create(PropietarioDTO entidad) { return dao.create(entidad);}

    @Override
    public List<PropietarioDTO> readAll() { return dao.readAll();}

    @Override
    public boolean update(PropietarioDTO entidad) { return dao.update(entidad); }

    @Override
    public boolean delete(PropietarioDTO entidad) { return dao.delete(entidad); }

    @Override
    public boolean save(PropietarioDTO entidad) { return dao.save(entidad); }

    @Override
    public PropietarioDTO findById(Long id) { return dao.findById(id); }

    @Override
    public User authenticate(String userName, String password) { return dao.authenticate(userName,password); }

    @Override
    public int getUnreadNotificationsCount() { return dao.getUnreadNotificationsCount(); }

    @Override
    public Collection<DashboardNotification> getNotifications() { return dao.getNotifications(); }
}
