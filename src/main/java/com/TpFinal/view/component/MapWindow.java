package com.TpFinal.view.component;

import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.properties.Parametros;
import com.TpFinal.services.UbicacionService;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.io.FileExistsException;
import org.apache.log4j.Logger;
import org.apache.tika.config.Param;
import org.tepi.imageviewer.ImageViewer;
import org.tepi.imageviewer.ImageViewer.ImageSelectionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@Theme("demotheme")
public class MapWindow extends Window {
    private final static Logger logger = Logger.getLogger(MapWindow.class);

    private VerticalLayout mainLayout;
    private Button recalcularDir=new Button("Geocode");



    public MapWindow(Inmueble inmueble) {
	super();
	if(inmueble==null||inmueble.getDireccion()==null||inmueble.getDireccion().getCoordenada()==null){
		return;
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
	GoogleMap gmaps = new GoogleMap(key,null,null);

	gmaps.setSizeFull();

	if(inmueble.getDireccion().getCoordenada().equals(new Coordenada(null,null))){
		System.out.println("son nul "+inmueble.getDireccion().getCoordenada());
		if(inmueble.getDireccion().getCalle().equals("")){
			return;
		}
		UbicacionService us=new UbicacionService();
		Coordenada coords=us.geoCode(inmueble.getDireccion());
		Direccion dir=inmueble.getDireccion();
		dir.setCoordenada(coords);
		inmueble.setDireccion(dir);


	}

	Double lat=inmueble.getDireccion().getCoordenada().getLat();
	Double lng=inmueble.getDireccion().getCoordenada().getLon();
	LatLon latLon=new LatLon(lat,lng);
	GoogleMapMarker marker=new GoogleMapMarker("Inmueble",latLon,false);
	gmaps.addMarkerDragListener(new MarkerDragListener() {
		@Override
		public void markerDragged(GoogleMapMarker googleMapMarker, LatLon latLon) {
			googleMapMarker.setPosition(latLon);
			Direccion dir=inmueble.getDireccion();
			dir.setCoordenada(new Coordenada(latLon.getLat(),latLon.getLon()));
			inmueble.setDireccion(dir);
			gmaps.setCenter(latLon);
		}
	});
	gmaps.setCenter(latLon);
	gmaps.setZoom(19);
	gmaps.addMarker(marker);
	mainLayout.addComponents(gmaps);
	setContent(mainLayout);
	recalcularDir.addClickListener(new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent clickEvent) {
			UbicacionService us=new UbicacionService();
			Coordenada coords=us.geoCode(inmueble.getDireccion());
			if(coords!=null&&!coords.equals(new Coordenada(null,null))){
			latLon.setLat(coords.getLat());
			latLon.setLon(coords.getLon());
			marker.setPosition(latLon);
			Direccion dir=inmueble.getDireccion();
			dir.setCoordenada(coords);
			inmueble.setDireccion(dir);
			gmaps.setCenter(latLon);
			gmaps.removeMarker(marker);
			gmaps.addMarker(marker);
			}

		}
	});
	mainLayout.setExpandRatio(gmaps,1);
	recalcularDir.setIcon(VaadinIcons.LOCATION_ARROW_CIRCLE_O);
	recalcularDir.setStyleName(ValoTheme.BUTTON_PRIMARY);
	UI.getCurrent().addWindow(this);
	this.setResizable(false);
	this.setSizeFull();
	this.center();
    }


}