package com.PubliciBot.DM;

import java.util.Objects;

/**
 * Created by Hugo on 14/05/2017.
 * key: un nombre para ese recurso
 * valor: un valor para la key, como puede ser una URL, un objeto de menu, etc
 */
public class Privilegio<T> {

    private Class<T> pantalla;

    public Privilegio(Class<T> c) {
        pantalla = c;
    }

    @Override
    public String toString(){
        return pantalla.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Privilegio<?> that = (Privilegio<?>) o;
        return Objects.equals(pantalla, that.pantalla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pantalla);
    }


}