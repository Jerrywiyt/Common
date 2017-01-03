package com.lujunyu.https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * 
 * @author lujunyu
 *
 */
public class HttpsSender {
	public static void main(String[] args) throws IOException {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("cipher/https.txt");
		byte[] b = new byte[in.available()];
		in.read(b);
		String param = new String(b);
		System.out.println(param);
		String url = "https://218.17.179.67:8443/QhicPos/doService?wsdl";
		send(param,url);
	}
	
	public static void send(String param,String url){
		try{
			initHttpsURLConnection();
			HttpsURLConnection urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
			urlCon.setHostnameVerifier(new TrustAnyHostnameVerifier());
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			byte[] reqByte = param.getBytes("utf-8");
			urlCon.setRequestProperty("Content-Length", String.valueOf(reqByte.length));
			urlCon.setRequestProperty("Content-Type ", "application/xop+xml; charset=UTF-8; type=\"text/xml\"");
			urlCon.setRequestProperty("SOAPAction", "soap_action");
			urlCon.setUseCaches(false);
			urlCon.connect();
			urlCon.getOutputStream().write(reqByte);
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String line = "";
			StringBuffer returnxml = new StringBuffer("");
			while ((line = in.readLine()) != null) {
				returnxml.append(line);
			}
			System.out.println(returnxml);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private static void initHttpsURLConnection() throws Exception {
		HttpsURLConnection.setDefaultHostnameVerifier(new TrustAnyHostnameVerifier());
		trustAllHttpsCertificates();
	}

	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}
}
