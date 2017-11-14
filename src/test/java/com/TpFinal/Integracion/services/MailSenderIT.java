package com.TpFinal.Integracion.services;

import org.apache.commons.io.FileExistsException;
import org.junit.Test;

import com.TpFinal.services.MailSender;

public class MailSenderIT {

	@Test
	public void test() throws IllegalArgumentException, FileExistsException {
		new MailSender().enviarMensaje("deidelson93@gmail.com", "Se que fuiste vos", "Me enteré que uno de los puristas del reglamento eras vos, realmente estoy muy triste\n"
				+ ""
				+ " ");
	}

}
