package com.PubliciBot.DM;

/**
 * Created by Hugo on 14/05/2017.
 */
public class AccionPublicitaria {

    private String nombreAccion;
    private int periodicidadSegundos;
    private Medio medio;
    private String destino;


    public AccionPublicitaria()
    {
        this.nombreAccion = "";
        this.periodicidadSegundos = 1;
        this.medio = new Medio();
        this.destino = "";
    }

    public int getPeriodicidadSegundos() {
        return periodicidadSegundos;
    }

    public void setPeriodicidadSegundos(int periodicidadSegundos) {
        this.periodicidadSegundos = periodicidadSegundos;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public AccionPublicitaria(String nombre, int periodicidadSegundos, Medio medioAccion, String destino)
    {
        this.nombreAccion = nombre;
        this.destino=destino;
        this.periodicidadSegundos =periodicidadSegundos;
        this.medio = medioAccion;

    }


    public String getNombreAccion() {
        return nombreAccion;
    }

    public void setNombreAccion(String nombreAccion) {
        this.nombreAccion = nombreAccion;
    }

    public int getValorPeriodicidad() {
        return periodicidadSegundos;
    }

    public void setValorPeriodicidad(int valorPeriodicidad) {
        this.periodicidadSegundos = valorPeriodicidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccionPublicitaria that = (AccionPublicitaria) o;

        if (periodicidadSegundos != that.periodicidadSegundos) return false;
        if (nombreAccion != null ? !nombreAccion.equals(that.nombreAccion) : that.nombreAccion != null) return false;
        if (medio != null ? !medio.equals(that.medio) : that.medio != null) return false;
        return destino != null ? destino.equals(that.destino) : that.destino == null;
    }

    @Override
    public int hashCode() {
        int result = nombreAccion != null ? nombreAccion.hashCode() : 0;
        result = 31 * result + periodicidadSegundos;
        result = 31 * result + (medio != null ? medio.hashCode() : 0);
        result = 31 * result + (destino != null ? destino.hashCode() : 0);
        return result;
    }

    public Medio getMedio() {
        return medio;
    }

    public void setMedio(Medio medio) {
        this.medio = medio;
    }


    @Override
    public String toString(){


        return "Nombre: "+this.nombreAccion+"\n Medio:"+this.medio.toString()+"\n Destino: "+this.destino+"\nPeriodicidad: "+this.periodicidadSegundos+" Segundos";
    }


}
