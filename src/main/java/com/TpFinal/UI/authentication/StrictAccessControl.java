package com.TpFinal.UI.authentication;

import com.TpFinal.DM.Privilegio;
import com.TpFinal.DM.Rol;
import com.TpFinal.DM.Usuario;
import com.TpFinal.Services.UsuarioService;

import com.TpFinal.UI.Vistas.ABMTagsView;
import com.TpFinal.UI.Vistas.EdicionCampanasView;

/**
 * Created by Max on 6/4/2017.
 */

public class StrictAccessControl implements AccessControl {

    private UsuarioService usuarioService ;
    private Usuario recoveredUser;


    public StrictAccessControl(){
        usuarioService = new UsuarioService();

    }

    @Override
    public boolean signIn(String username, String password) {
        crearTecnico();
        crearCliente();
        crearAdmin();
        recoveredUser = this.usuarioService.buscarUsuario(username,password);
        if(recoveredUser == null)
           return false;
        String recoveredName = recoveredUser.getMail();
        String recoveredPassword = recoveredUser.getContrasena();
        if(recoveredName.equals(username) && recoveredPassword.equals(password)){
           CurrentUser.set(username);
           return true;
       }
       return false;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if(recoveredUser!=null)
            if(recoveredUser.getRol()!=null)
                if(recoveredUser.getRol().getDescripcion()!=null)
                    if(role!=null)
                        return recoveredUser.getRol().getDescripcion().equals(role);

        return false;

    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }


    //METODOS TEMPORALES ES PARA QUE CREEN UN USUARIO DE CADA ROL EN SUS DBS DESPUES SE ELIMINARAN
    private void crearTecnico(){
        Privilegio<ABMTagsView> tecnico = new Privilegio<>(ABMTagsView.class);
        Rol rolTecnico = new Rol("Tecnico");
        rolTecnico.add(tecnico);
        Usuario tecnicoUser = new Usuario("tecnico","tecnico",rolTecnico);
        Usuario recup = usuarioService.buscarUsuario("tecnico","tecnico");
        if(recup == null || !recup.equals(tecnicoUser))
            usuarioService.guardarUsuario(tecnicoUser);
    }

    private void crearCliente(){
        Privilegio<EdicionCampanasView> cliente = new Privilegio<>(EdicionCampanasView.class);
        Rol rolCliente = new Rol("Cliente");
        rolCliente.add(cliente);
        Usuario clientUser =  new Usuario("cliente","cliente",rolCliente);
        Usuario recup = usuarioService.buscarUsuario("cliente","cliente");
        if(recup == null || !recup.equals(clientUser))
            usuarioService.guardarUsuario(clientUser);
    }

    private void crearAdmin(){
        Privilegio<ABMTagsView> admin = new Privilegio<>(ABMTagsView.class);
        Privilegio<ABMTagsView> tecnico = new Privilegio<>(ABMTagsView.class);
        Privilegio<EdicionCampanasView> cliente = new Privilegio<>(EdicionCampanasView.class);
        Rol rolAdmin = new Rol("admin");
        rolAdmin.add(admin);
        rolAdmin.add(tecnico);
        rolAdmin.add(cliente);
        Usuario retAdmin = new Usuario("admin","admin",rolAdmin);
        Usuario recup = usuarioService.buscarUsuario("admin","admin");
        if(recup == null || !recup.equals(retAdmin))
            usuarioService.guardarUsuario(retAdmin);

    }

    public Usuario getRecoveredUser(){
        return this.recoveredUser;
    }

    public void setRecoveredUser (Usuario user){
        this.recoveredUser = user;
    }
}
