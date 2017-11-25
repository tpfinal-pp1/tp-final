package com.TpFinal.view.inmuebles;

import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.properties.Parametros;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.UbicacionService;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.io.FileExistsException;
import org.apache.log4j.Logger;

@SuppressWarnings("serial")
@Theme("demotheme")
public class MapWindow extends Window {
    private final static Logger logger = Logger.getLogger(MapWindow.class);

    private VerticalLayout mainLayout;
	private Button recalcularDir=new Button("Ubicación Original");
	private InmuebleService inmuebleService=new InmuebleService();




    public MapWindow(Inmueble inmueble) {
	super();
	if(inmueble==null||inmueble.getDireccion()==null||inmueble.getDireccion().getCoordenada()==null){
		return;
	}
	if(inmueble.getDireccion().getCoordenada().equals(new Coordenada(null,null))){
		UbicacionService us=new UbicacionService();
		Coordenada coords=us.geoCode(inmueble.getDireccion());
		Direccion dir=inmueble.getDireccion();
		dir.setCoordenada(coords);
		inmueble.setDireccion(dir);
	}
	mainLayout = new VerticalLayout();
	mainLayout.setSizeFull();
	mainLayout.setMargin(true);
	mainLayout.setSpacing(true);
	String key="";
		try {
			key=Parametros.getProperty("mapsKey");

		} catch (FileExistsException e) {
			e.printStackTrace();
		}
	GoogleMap gmaps = new GoogleMap(key,null,"spanish");

	gmaps.setSizeFull();




	Coordenada coords=inmueble.getDireccion().getCoordenada();
	if(coords.getLat()==null||coords.getLon()==null) {
		coords=new Coordenada(0D,0D);
		Direccion direccion=inmueble.getDireccion();
		direccion.setCoordenada(coords);
		inmueble.setDireccion(direccion);
	}

	Double lat=inmueble.getDireccion().getCoordenada().getLat();
	Double lng=inmueble.getDireccion().getCoordenada().getLon();
	LatLon latLon=new LatLon(lat,lng);
	GoogleMapMarker marker=new GoogleMapMarker("Inmueble",latLon,true);
	gmaps.addMarkerDragListener(new MarkerDragListener() {
		@Override
		public void markerDragged(GoogleMapMarker googleMapMarker, LatLon latLon) {
			//googleMapMarker.setPosition(latLon);
			Direccion dir=inmueble.getDireccion();
			System.out.println("Coordenadas Anteriores: "+dir.getCoordenada());
			LatLon nueva=googleMapMarker.getPosition();
			dir.setCoordenada(new Coordenada(nueva.getLat(),nueva.getLon()));
			inmueble.setDireccion(dir);
			InmuebleService inmuebleService=new InmuebleService();
			System.out.println("Inmueble Actualizado, coords actuales "+dir.getCoordenada());
			inmuebleService.merge(inmueble);

		}
	});
	recalcularDir.addStyleName(ValoTheme.BUTTON_PRIMARY);
	recalcularDir.setIcon(VaadinIcons.LOCATION_ARROW_CIRCLE_O);
	recalcularDir.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				UbicacionService us=new UbicacionService();
				Coordenada coords=us.geoCode(inmueble.getDireccion());
				if(coords!=null&&!coords.equals(new Coordenada(null,null))
						&&!coords.equals(new Coordenada(0D,0D))){
					latLon.setLat(coords.getLat());
					latLon.setLon(coords.getLon());
					marker.setPosition(latLon);
					marker.setAnimationEnabled(true);
					Direccion dir=inmueble.getDireccion();
					dir.setCoordenada(coords);
					inmueble.setDireccion(dir);
					gmaps.setCenter(latLon);
					inmuebleService.merge(inmueble);
					close();
					new MapWindow(inmueble);


				}

			}
		});
	gmaps.setCenter(latLon);
	gmaps.setZoom(16);
	gmaps.addMarker(marker);
	mainLayout.addComponents(recalcularDir,gmaps);
	setContent(mainLayout);


	mainLayout.setExpandRatio(gmaps,1);

	UI.getCurrent().addWindow(this);
	this.setResizable(false);
	this.setSizeFull();
	this.center();
    }


}