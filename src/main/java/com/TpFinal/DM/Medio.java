package com.TpFinal.DM;

/**
 * Created by Hugo on 11/06/2017.
 */
public class Medio {

    private TipoMedio tipoMedio;
    private String usuarioPerfilOrigen;
    private String contrasenaPerfilOrigen;
    private String perfilDestino;

    public Medio()
    {
        tipoMedio = TipoMedio.EMAIL;

        usuarioPerfilOrigen = "";
        contrasenaPerfilOrigen = "";
        perfilDestino = "";
    }

    public TipoMedio getTipoMedio() {
        return tipoMedio;
    }

    public void setTipoMedio(TipoMedio tipoMedio) {
        this.tipoMedio = tipoMedio;
    }


    public String getUsuarioPerfilOrigen() {
        return usuarioPerfilOrigen;
    }

    public void setUsuarioPerfilOrigen(String usuarioPerfilOrigen) {
        this.usuarioPerfilOrigen = usuarioPerfilOrigen;
    }

    public String getContrasenaPerfilOrigen() {
        return contrasenaPerfilOrigen;
    }

    public void setContrasenaPerfilOrigen(String contrasenaPerfilOrigen) {
        this.contrasenaPerfilOrigen = contrasenaPerfilOrigen;
    }

    public String getPerfilDestino() {
        return perfilDestino;
    }

    public void setPerfilDestino(String perfilDestino) {
        this.perfilDestino = perfilDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medio medio = (Medio) o;

        if (tipoMedio != medio.tipoMedio) return false;
        if (usuarioPerfilOrigen != null ? !usuarioPerfilOrigen.equals(medio.usuarioPerfilOrigen) : medio.usuarioPerfilOrigen != null)
            return false;
        if (contrasenaPerfilOrigen != null ? !contrasenaPerfilOrigen.equals(medio.contrasenaPerfilOrigen) : medio.contrasenaPerfilOrigen != null)
            return false;
        return perfilDestino != null ? perfilDestino.equals(medio.perfilDestino) : medio.perfilDestino == null;
    }

    @Override
    public int hashCode() {
        int result = tipoMedio != null ? tipoMedio.hashCode() : 0;
        result = 31 * result + (usuarioPerfilOrigen != null ? usuarioPerfilOrigen.hashCode() : 0);
        result = 31 * result + (contrasenaPerfilOrigen != null ? contrasenaPerfilOrigen.hashCode() : 0);
        result = 31 * result + (perfilDestino != null ? perfilDestino.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){

        return "Tipo de Medio: "+this.tipoMedio.toString();
    }
}
