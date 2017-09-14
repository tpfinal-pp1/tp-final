package com.TpFinal.UI.Vistas;

import com.TpFinal.UI.MyUI;

import com.TpFinal.UI.Vistas.VistaCamapana.ABMCampanasView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends HorizontalLayout {
    private Menu menu;

    public MainScreen(MyUI ui) {

        setSpacing(false);
        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new Menu(navigator);
     //TODO CARGAR LOS BOTONES SEGUN EL ROL

        menu.addView(new HomeView(), HomeView.VIEW_NAME,"Inicio",
                VaadinIcons.HOME);

        boolean esAdmin = ui.getAccessControl().isUserInRole("admin");
        boolean esTecnico = ui.getAccessControl().isUserInRole("Tecnico");
        boolean esCliente = ui.getAccessControl().isUserInRole("Cliente");


        if(esAdmin) {
            menu.addView(new ABMTagsView(), ABMTagsView.VIEW_NAME,
                    ABMTagsView.VIEW_NAME, VaadinIcons.EDIT);


            menu.addView(new ABMCampanasView(),ABMCampanasView.VIEW_NAME, ABMCampanasView.VIEW_NAME,
                    VaadinIcons.FIRE);
            menu.addView(new AboutView(), AboutView.VIEW_NAME, AboutView.VIEW_NAME,
                    VaadinIcons.INFO_CIRCLE);
        }
        else if(esTecnico){
            menu.addView(new ABMTagsView(), ABMTagsView.VIEW_NAME,
                    ABMTagsView.VIEW_NAME, VaadinIcons.EDIT);
            menu.addView(new AboutView(), AboutView.VIEW_NAME, AboutView.VIEW_NAME,
                    VaadinIcons.INFO_CIRCLE);
        }

        else if(esCliente){
            menu.addView(new ABMCampanasView(),ABMCampanasView.VIEW_NAME, ABMCampanasView.VIEW_NAME,
                    VaadinIcons.SEARCH);

            menu.addView(new AboutView(), AboutView.VIEW_NAME, AboutView.VIEW_NAME,
                    VaadinIcons.INFO_CIRCLE);
        }
        else{
            VaadinSession.getCurrent().getSession().invalidate();
            VaadinSession.getCurrent().close();
            Page.getCurrent().reload();
        }

       /* menu.addView(new AddressbookUIView(),AddressbookUIView.VIEW_NAME, AddressbookUIView.VIEW_NAME,
                VaadinIcons.SEARCH);
*/


        navigator.addViewChangeListener(viewChangeListener);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
    }

    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            //TODO verficar que el usuario pueda acceder
        return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            menu.setActiveView(event.getViewName());
        }

    };
}
