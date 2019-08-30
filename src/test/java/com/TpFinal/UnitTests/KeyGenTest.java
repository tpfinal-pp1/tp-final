package com.TpFinal.UnitTests;

import com.TpFinal.utils.Cipher;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;

public class KeyGenTest {


    @Test
    public void generateKey(){
        System.err.println("SERIAL KEY (3 Days valid)= " + Cipher.encrypt(String.valueOf(Instant.now().toEpochMilli())));;
        }



}
