package com.PubliciBot.DAO.Interfaces;


import com.PubliciBot.DM.ArbolTags;

/**
 * Created by Hugo on 20/05/2017.
 */
public interface ArbolDAO extends DAO<ArbolTags> {

    ArbolTags recuperar();

}
