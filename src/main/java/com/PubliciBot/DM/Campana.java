package com.PubliciBot.DM;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hugo on 14/05/2017.
 */
public class Campana implements Serializable, Cloneable{


    private String nombre;
    private String descripcion;
    private int duracion;
    private UnidadMedida unidadMedida;
    private Date fechaInicio;
    private Mensaje mensaje;
    private ArrayList<Tag> tags;
    private ArrayList<AccionPublicitaria> acciones;
    private ArrayList<Post> posts;

    private EstadoCampana estadoCampana;

    private Long id;


    public Campana(String nombre, String descripcion, Date fechaInicio, int duracion, UnidadMedida unidadMedida, Mensaje mensaje ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.unidadMedida = unidadMedida;
        this.estadoCampana = EstadoCampana.PRELIMINAR;
        this.mensaje = mensaje;
        this.tags = new ArrayList<Tag>();
        this.acciones = new ArrayList<>();

        posts=new ArrayList<Post>();

    }

    public Campana(){
        this.nombre = "";
        this.descripcion = "";
        this.fechaInicio = new Date();
        this.duracion = 1;
        this.unidadMedida = UnidadMedida.SEMANA;
        this.estadoCampana = EstadoCampana.PRELIMINAR;
        this.mensaje = new Mensaje();
        this.tags = new ArrayList<Tag>();
        this.acciones = new ArrayList<>();

        posts=new ArrayList<Post>();
    }


    public boolean incompleta(){
        if(this.acciones.size()>0)
            return false;
        return true;



    }

    public void actualizarEstado(){
        if(this.incompleta()){
            this.setEstadoCampana(EstadoCampana.PRELIMINAR);
            //Si no tiene acciones no puede cambiar de estado
            return;
        }
        Instant NOW=Instant.now();
        long nowinSeconds=NOW.getEpochSecond();
        boolean cambios=false;
        EstadoCampana estadoanterior=this.getEstadoCampana();



        if (this.getFechaInicio().after(Date.from(NOW))) {
            this.setEstadoCampana(EstadoCampana.PRELIMINAR);
            if(!estadoanterior.equals(estadoCampana)) {
                System.out.println("CAMPAÑA: "+this.nombre+" --->Estado actualizado: "+this.estadoCampana);
                cambios = true;
            }

        }

        if (this.getFechaInicio().before(Date.from(NOW))) {
                this.setEstadoCampana(EstadoCampana.ACTIVA);

                if(!estadoanterior.equals(estadoCampana)) {
                System.out.println("CAMPAÑA: "+this.nombre+" --->Estado actualizado: "+this.estadoCampana);
                cambios = true;
            }

    }

            if(this.calcularCaducidad().before(Date.from(NOW))){
                this.setEstadoCampana(EstadoCampana.FINALIZADA);

                if(!estadoanterior.equals(estadoCampana)) {
                    System.out.println("CAMPAÑA: "+this.nombre+" --->Estado actualizado: "+this.estadoCampana);
                    cambios = true;
                }



    }

    if(cambios)
        System.out.println("CAMPAÑA: "+this.nombre+" --->Estado anterior: "+estadoanterior);

}

    public Date calcularCaducidad(){

        Calendar c = Calendar.getInstance();
        c.setTime(fechaInicio);
        c.add(Calendar.SECOND, duracion*unidadMedida.unidadASegundos());

     return  c.getTime();
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public boolean borrarPost(Post post){
      return  this.posts.remove(post);
    }

    public ArrayList<Post> getPosts(){
       return this.posts;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public void addTags(Tag tag) {
        this.tags.add(tag);
    }

    public ArrayList<AccionPublicitaria> getAcciones() {
        return acciones;
    }

    public void addAccion(AccionPublicitaria accion) {
        this.acciones.add(accion);

    }


    public void removeAccion(AccionPublicitaria accion){
        this.acciones.remove(accion);
    }

    public EstadoCampana getEstadoCampana() {
        return estadoCampana;
    }

    public void setEstadoCampana(EstadoCampana estadoCampana) {
        this.estadoCampana = estadoCampana;
    }

    public UnidadMedida getUnidadMedida() { return unidadMedida; }

    public void setUnidadMedida(UnidadMedida unidadMedida) { this.unidadMedida = unidadMedida; }

    @Override
    public String toString(){
        return "Nombre Campaña "+this.nombre+"\n"+
                "Descripcion "+ this.descripcion+"\n"+
                "Duracion "+ this.duracion+"\n"+
                "Mensaje "+this.mensaje.toString()+"\n"+
                "Inicio "+ this.fechaInicio.toString()+"\n"+
                "Estado "+ this.estadoCampana.toString()+"\n"+
                "Tags "+ this.tags+"\n"+
                "Acciones "+ this.acciones+"\n"
                ;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campana campana = (Campana) o;

        if (duracion != campana.duracion) return false;
        if (nombre != null ? !nombre.equals(campana.nombre) : campana.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(campana.descripcion) : campana.descripcion != null) return false;
        if (unidadMedida != campana.unidadMedida) return false;
        if (fechaInicio != null ? !fechaInicio.equals(campana.fechaInicio) : campana.fechaInicio != null) return false;
        if (mensaje != null ? !mensaje.equals(campana.mensaje) : campana.mensaje != null) return false;
        if (tags != null ? !tags.equals(campana.tags) : campana.tags != null) return false;
        if (acciones != null ? !acciones.equals(campana.acciones) : campana.acciones != null) return false;
        if (posts != null ? !posts.equals(campana.posts) : campana.posts != null) return false;
        return id != null ? id.equals(campana.id) : campana.id == null;
    }

    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + duracion;
        result = 31 * result + (unidadMedida != null ? unidadMedida.hashCode() : 0);
        result = 31 * result + (fechaInicio != null ? fechaInicio.hashCode() : 0);
        result = 31 * result + (mensaje != null ? mensaje.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (acciones != null ? acciones.hashCode() : 0);
        result = 31 * result + (posts != null ? posts.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public Campana clone() throws CloneNotSupportedException {
        try {
            return (Campana) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
