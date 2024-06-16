package goa.systems.commons.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

public class SecurityManager {

	public byte[] getSecurePassword(String password, byte[] salt) throws NoSuchAlgorithmException {
		byte[] bytes = null;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(salt);
		bytes = md.digest(password.getBytes());
		return bytes;
	}

	public String toHexString(byte[] bytes) {
		return HexFormat.of().withUpperCase().formatHex(bytes);
	}

	public byte[] getSalt() {
		return getSalt(16);
	}

	public byte[] getSalt(int lenght) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[lenght];
		random.nextBytes(salt);
		return salt;
	}
}
