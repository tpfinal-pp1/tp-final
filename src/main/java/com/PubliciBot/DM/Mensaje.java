package com.PubliciBot.DM;

/**
 * Created by Hugo on 22/05/2017.
 */
public class Mensaje {
    private String textoMensaje;
    private String imagenMensajePath;
    private String asunto;

    public Mensaje(String textoMensaje, String imagenMensaje) {
        this.textoMensaje = textoMensaje;
        this.imagenMensajePath = imagenMensaje;
    }

    public Mensaje(String textoMensaje, String imagenMensaje,String asunto) {
        this.asunto=asunto;
        this.textoMensaje = textoMensaje;
        this.imagenMensajePath = imagenMensaje;
    }

    public Mensaje (){
        this.textoMensaje = "";
        this.asunto = "";
        this.imagenMensajePath = "";
    }

    public String getAsunto() {
        return asunto;
    }

    public String getTextoMensaje() {
        return textoMensaje;
    }

    public String getImagenMensajePath() {
        return imagenMensajePath;
    }

    @Override
    public String toString(){
        return "Texto mensaje "+ this.textoMensaje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mensaje mensaje = (Mensaje) o;

        if (textoMensaje != null ? !textoMensaje.equals(mensaje.textoMensaje) : mensaje.textoMensaje != null)
            return false;
        return imagenMensajePath != null ? imagenMensajePath.equals(mensaje.imagenMensajePath) : mensaje.imagenMensajePath == null;
    }

    @Override
    public int hashCode() {
        int result = textoMensaje != null ? textoMensaje.hashCode() : 0;
        result = 31 * result + (imagenMensajePath != null ? imagenMensajePath.hashCode() : 0);
        return result;
    }
}
