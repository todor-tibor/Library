/**
 * 
 */
package com.edu.library.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.edu.library.data.exception.TechnicalException;
import com.edu.library.exception.LibraryException;

/**
 * Implements the password hashing, utilizing the SHA algorithm. Implements the
 * singleton design pattern with static methods.
 * 
 * @author kiska
 *
 */
public class PasswordEncrypter {
	private final static String ENCRYPTER_ERROR = "password.error.message";

	private PasswordEncrypter() {
	}

	/**
	 * 
	 * @param password
	 *            - the raw password
	 * @param salt
	 *            - a random data used for hashing
	 * @return - the hashed password generated from the raw password and salt
	 *         value
	 * @throws EjbException
	 */
	public static String encypted(String password, String salt) throws LibraryException {
		try {
			byte[] initialBytes;
			initialBytes = (password + salt).getBytes("utf-8");
			MessageDigest algorithm = MessageDigest.getInstance("SHA");
			algorithm.reset();
			algorithm.update(initialBytes);
			byte[] hashBytes = algorithm.digest();
			return new String(hashBytes);
		} catch (UnsupportedEncodingException e) {
			throw new TechnicalException(ENCRYPTER_ERROR, e);
		} catch (NoSuchAlgorithmException e) {
			throw new TechnicalException(ENCRYPTER_ERROR, e);
		}
	}
}
