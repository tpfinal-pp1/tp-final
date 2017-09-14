package com.TpFinal.Services;


import com.TpFinal.DM.Post;

public class Sender extends Thread{
    Tasker Tasker;
    private boolean run=false;

    public Sender(Tasker Tasker){
        this.Tasker = Tasker;
    }


    @Override
    public void run() {
        super.run();
        run=true;

        while (run){
            Post Post= Tasker.giveMeaPost();
            if(Post!=null) {
                System.out.println("Sender: Post Recibida y Ejecutada");
                Post.execute();
            }
            else{

                run=false;
            }

        }
    }


    public void stopSender(){
        this.run=false;
    }

}
