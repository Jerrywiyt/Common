package com.lujunyu.https;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Test {

	public static void main(String[] args) throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException, UnrecoverableKeyException {
		
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+File.separator+"cipher"+File.separator+"cipher.pfx";
		System.out.println(path);
		
		String usrname = "qhicpos20160303";
		String psw = "qhic2022";
		
		
		KeyStore ks = KeyStore.getInstance("PKCS12");
		
		InputStream ksfis = new FileInputStream(new File(path));
		BufferedInputStream ksbufin = new BufferedInputStream(ksfis);

		ks.load(ksbufin, psw.toCharArray());
		PrivateKey priK = (PrivateKey) ks.getKey(usrname, psw.toCharArray());
		System.out.println(ks.getCertificate("qhicpos20160303"));
	}
	

}
