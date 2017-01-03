package com.lujunyu.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class TrustAnyHostnameVerifier implements HostnameVerifier{
	@Override
	public boolean verify(String hostname, SSLSession session) {
		System.err.println("trust "+ hostname);
		return true;
	}
}
