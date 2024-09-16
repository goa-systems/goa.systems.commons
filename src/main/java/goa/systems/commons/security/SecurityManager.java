package goa.systems.commons.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

public class SecurityManager {

	/**
	 * Generates a SHA-256 password hash from the given password and salt.
	 * 
	 * @param password Password to use
	 * @param salt Salt to use
	 * @return Password hash as byte array
	 * @throws NoSuchAlgorithmException
	 */
	public byte[] getSecurePassword(String password, byte[] salt) throws NoSuchAlgorithmException {
		return getSecurePassword(password, salt, "SHA-256");
	}

	/**
	 * Generates a password hash from the given password and salt.
	 * 
	 * @param password Password to use
	 * @param salt Salt to use
	 * @param algorithm Algorithm to use
	 * @return Password hash as byte array
	 * @throws NoSuchAlgorithmException If algorithm is not found
	 */
	public byte[] getSecurePassword(String password, byte[] salt, String algorithm) throws NoSuchAlgorithmException {
		byte[] bytes = null;
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(salt);
		bytes = md.digest(password.getBytes());
		return bytes;
	}

	/**
	 * Converts the given byte array into a HEX string.
	 * @param bytes Byte array to convert
	 * @return String in hexadecimal format
	 */
	public String toHexString(byte[] bytes) {
		return HexFormat.of().withUpperCase().formatHex(bytes);
	}

	/**
	 * Generates a salt of length 16
	 * @return Salt as byte array
	 */
	public byte[] getSalt() {
		return getSalt(16);
	}
	
	/**
	 * Generates a salt of the given length
	 * @param lenght length of salt
	 * @return Salt as byte array
	 */
	public byte[] getSalt(int lenght) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[lenght];
		random.nextBytes(salt);
		return salt;
	}
}
