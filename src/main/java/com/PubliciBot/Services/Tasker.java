package com.PubliciBot.Services;
import com.PubliciBot.DAO.Neodatis.DAONeodatis;
import com.PubliciBot.DM.Campana;
import com.PubliciBot.DM.EstadoCampana;
import com.PubliciBot.DM.Post;

import java.time.Instant;
import java.util.*;

public class Tasker extends Thread{

//SINGLETON
    private static Tasker Tasker;
    private static Stack<Post> Posts;
    private static boolean run=false;
    private static ArrayList<Sender> senders;
    private static ArrayList<Post> dbPosts;
    private static ArrayList<Post> tobeErasedPosts;


    private Tasker (){
        Posts=new Stack<>();
        senders =new ArrayList<>();
        dbPosts=new ArrayList<>();
        tobeErasedPosts=new ArrayList<>();
        int cantsenders=1;
        for(int i=0; i<1;i++) {
            Sender sender = new Sender(this);
            senders.add(sender);
        }
        DAONeodatis daoNeodatis= new DAONeodatis();

        ArrayList<Campana> campanas=(ArrayList<Campana>)daoNeodatis.obtenerTodos(Campana.class);

        Instant NOW=Instant.now();
        long nowinSeconds=NOW.getEpochSecond();

        for( Campana campana : campanas) {
            campana.actualizarEstado();
        }

        for( Campana campana : campanas) {
            if (campana.getEstadoCampana().equals(EstadoCampana.ACTIVA)) {
                    for(Post post :campana.getPosts()) {
                        dbPosts.add(post);
                    }
            }
        }



}

    @Override
    public void run() {

        boolean run=true;

        while (run) {
            System.out.println("\n\n");
            findAndDestroyTobeErasedPosts();
            System.out.println("/////////Tasker: Buscando Tareas...//////////");
            buscaTareas();
            for(Sender sender : senders){
                sender.run();
            }
            System.out.println("////////////Tasker: sleep por 10 segundos///////////////");
            System.out.println("\n\n");

            try {
                Thread.sleep(10000); //se duerme y vuelve a mandar cada 1 minuto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }




    public void buscaTareas(){
        DAONeodatis daoNeodatis= new DAONeodatis();
        Instant NOW=Instant.now();
        long nowinSeconds=NOW.getEpochSecond();

        System.out.println(dbPosts);

        for (Post p:dbPosts
             ) {

           if(p!=null) {
               System.out.println("\n\n");
               System.out.println("Tarea encontrada: " + p.getAccion().getNombreAccion());

               System.out.print("Fecha inicio: " + p.getFechaInicio() + " es antes que ahora?: " + Date.from(NOW));
               if (p.getFechaInicio().before(Date.from(NOW))) { //Si el Post Todavia Esta vigente (supero la fecha de inicio)
                   System.out.println("  SI");

                   System.out.print("FechaCaducidad: " + p.getFechaCaducidad() + " es despues que ahora?: " + Date.from(NOW));
                   if (p.getFechaCaducidad().after((Date.from(NOW)))) {  //Si no supero la fecha de caducidad
                       System.out.println(" SI");


                       if (p.getFechaUltimaEjecucion() != null) {
                           long ultimaejSeconds = p.getFechaUltimaEjecucion().toInstant().getEpochSecond();   //Convertir ultima ejecucion a segundos

                           System.out.print("Cooldown: Supero el Cooldown? ");
                           if (Math.abs(nowinSeconds - ultimaejSeconds) > p.getAccion().getPeriodicidadSegundos()) {  //SI LE TOCA EJECUTARSE (PASO EL COOLDOWN )
                               System.out.println("Cooldown: SI, agregada a col de ejecucion");
                               Posts.add(p);
                               return;
                           }
                       } else {
                           System.out.println("Cooldown: Nunca ejecutada antes, agegada a cola de ejecucion");
                           System.out.println("\n");
                           Posts.add(p);
                           return;
                       }


                   }


               }

               System.out.println(" NO: NO CALIFICA PARA EJECUCION: " + p.getAccion().getNombreAccion());
               System.out.println("\n\n");
           }
        }



    }



    public Post giveMeaPost(){
        if(Posts.size()!=0) {
            return Posts.pop();
        }
        return null;
    }


    public static void addPost(Post Post){
       dbPosts.add((Post)Post);
    }

    public static void findAndDestroyTobeErasedPosts(){

        for (int i=0;i<tobeErasedPosts.size();i++){
            Post post=tobeErasedPosts.get(i);
            for (int j=0;j<dbPosts.size();j++){
                Post p=dbPosts.get(j);
                if(p.getID()==post.getID()) {
                    dbPosts.remove(p);

                }
            }
        }



    }



    public static void removePost(Post Post){

        for (Post p:dbPosts
             ) {

            if(p.getID()==Post.getID()){
                System.out.println("Borrado"+ p );
                tobeErasedPosts.add(p);
            }

        }


    }



    public static Tasker getTasker(){
        if(Tasker==null){
            Tasker=new Tasker();
        }
        return Tasker;
    }



}
