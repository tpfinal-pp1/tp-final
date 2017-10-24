package com.TpFinal.utils;

import com.vaadin.data.ValidationException;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.util.CurrentInstance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {

    public String resourcesPath() {

	return CurrentInstance.get(VaadinRequest.class)
		.getService().getBaseDirectory() + File.separator;
    }

    public static ArrayList<Object> Search(List<Object> Objects, String keyword, int threshold) {
	ArrayList<Object> ret = new ArrayList<Object>();
	keyword = keyword.toUpperCase();
	for (Object op : Objects) {

	    if (op.toString() != null) {
		if (Utils.isPercentageMatch(op.toString().toUpperCase(), keyword, threshold)) {
		    ret.add(op);
		}
	    }

	}

	return ret;

    }

    public static boolean isPercentageMatch(String candidate, String keyword, int threshold) {
	// String trimmedCandidate=trimCandidate(candidate,keyword,4);

	// if(trimmedCandidate==""){
	// return false;
	// }

	int percentage = percentageOfTextMatch(keyword, candidate);
	System.out.println("Busqueda: " + keyword + " Resultado: " + candidate + " Match: " + percentage + "%");

	if (percentage >= threshold) {

	    return true;
	}
	return false;
    }

    public static StreamResource fromPathtoSR(String filename) {

	return new StreamResource(new StreamResource.StreamSource() {
	    public InputStream getStream() {
		InputStream is = null;
		try {
		    is = new FileInputStream("Files" + File.separator + filename);
		} catch (FileNotFoundException e) {
		    System.err.println("No se ha encontrado el archivo a descargar Files/" + filename);
		}
		return is;
	    }
	}, filename);

    }

    public static int percentageOfTextMatch(String s0, String s1) { // Trim and remove duplicate spaces
	int percentage = 0;
	s0 = s0.trim().replaceAll("\\s+", " ");
	s1 = s1.trim().replaceAll("\\s+", " ");
	percentage = (int) (100 - (float) LevenshteinDistance(s0, s1) * 100 / (float) (s0.length() + s1.length()));
	return percentage;
    }

    public static int LevenshteinDistance(String s0, String s1) {

	int len0 = s0.length() + 1;
	int len1 = s1.length() + 1;
	// the array of distances
	int[] cost = new int[len0];
	int[] newcost = new int[len0];

	// initial cost of skipping prefix in String s0
	for (int i = 0; i < len0; i++)
	    cost[i] = i;

	// dynamically computing the array of distances

	// transformation cost for each letter in s1
	for (int j = 1; j < len1; j++) {

	    // initial cost of skipping prefix in String s1
	    newcost[0] = j - 1;

	    // transformation cost for each letter in s0
	    for (int i = 1; i < len0; i++) {

		// matching current letters in both strings
		int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

		// computing cost for each transformation
		int cost_replace = cost[i - 1] + match;
		int cost_insert = cost[i] + 1;
		int cost_delete = newcost[i - 1] + 1;

		// keep minimum cost
		newcost[i] = Math.min(Math.min(cost_insert, cost_delete),
			cost_replace);
	    }

	    // swap cost/newcost arrays
	    int[] swap = cost;
	    cost = newcost;
	    newcost = swap;
	}

	// the distance is the cost for transforming all letters in both strings
	return cost[len0 - 1];
    }

    public static void mostarErroresValidator(ValidationException e) {
	System.out.println(e.getMessage());
	e.getFieldValidationErrors().forEach(err -> System.out.println("Campo invalido "+err.getField()));
	e.getValidationErrors().forEach(err -> System.out.println(err.getErrorMessage()));
    }

    public static BigDecimal StringToBigDecimal(String val) {
	Locale locale = new Locale("es", "AR");
	DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(locale);
	nf.setParseBigDecimal(true);
	BigDecimal bd = (BigDecimal) nf.parse(val, new ParsePosition(0));
	return bd;
    }
}