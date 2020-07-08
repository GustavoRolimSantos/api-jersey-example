package br.com.maxtercreations.tebonaro.authentication;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

public class UserAuthenticationService {
	
	public static boolean authenticate(String authCredentials) {

		if (null == authCredentials)
			return false;

		String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", ""), usernameAndPassword = null;

		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);

			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");

		String username = tokenizer.nextToken(), password = tokenizer.nextToken();

		return username.equals("tebonaro") && password.equals("12345");
	}

}