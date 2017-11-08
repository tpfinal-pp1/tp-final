package com.TpFinal.UnitTests.dto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.gargoylesoftware.htmlunit.javascript.host.file.Blob;

public class ContratoAlquilerTest {

	@Test(expected = IllegalArgumentException.class)
	public void test() {
		ContratoAlquiler ca = new ContratoAlquiler();
		ca.setDocumento(null, "dasd", "sdad");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test1() {
		ContratoAlquiler ca = new ContratoAlquiler();
		ca.setDocumento(null, "dasd", "sdad");
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void test2() {
//		ContratoAlquiler ca = new ContratoAlquiler();
//		Blob b= Blob.
//		ca.setDocumento(new Blo, "dasd", "sdad");
//	}

}
