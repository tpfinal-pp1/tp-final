package com.TpFinal.UnitTests;

import org.apache.commons.io.FileExistsException;
import org.junit.Test;

import com.TpFinal.services.MailSender;

public class MailSenderTest {

	@Test
	public void test() throws IllegalArgumentException, FileExistsException {
		new MailSender().enviarMail("inmobi@mailinator.com", "Se que fuiste vos", "Me enteré que uno de los puristas del reglamento eras vos, realmente estoy muy triste\n"
				+ ""
				+ " ");
	}

}
