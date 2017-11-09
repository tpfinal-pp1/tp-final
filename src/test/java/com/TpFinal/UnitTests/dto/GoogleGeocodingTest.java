package com.TpFinal.UnitTests.dto;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GoogleGeocodingTest {

    @Ignore
    @Test
    public void test() {
	GeoApiContext context = new GeoApiContext.Builder()
		.apiKey("AIza...Colocar la key aca")
		.build();
	try {
	    GeocodingResult[] results = GeocodingApi.geocode(context,
		    "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    System.out.println(gson.toJson(results[0].addressComponents));
	    System.out.println(gson.toJson(results[0].geometry.location));// para obtener lat y long.
	} catch (ApiException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
