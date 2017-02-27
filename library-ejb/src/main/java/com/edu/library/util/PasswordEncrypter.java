/**
 *
 */
package com.edu.library.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.edu.library.data.exception.TechnicalException;

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
	 * Encrypts the given password {@code password} utilizing the salt value
	 * {@code salt}
	 * 
	 * @param password
	 *            - the raw password
	 * @param salt
	 *            - a random data used for hashing
	 * @return - the hashed password generated from the raw password and salt
	 *         value
	 * @throws TechnicalException
	 *             when the given algorithm or the encoding is not supported
	 */
	public static String encypted(final String password, final String salt) {
		try {
			byte[] initialBytes;
			initialBytes = (password + salt).getBytes("utf-8");
			final MessageDigest algorithm = MessageDigest.getInstance("SHA");
			algorithm.reset();
			algorithm.update(initialBytes);
			final byte[] hashBytes = algorithm.digest();
			return new String(hashBytes);
		} catch (final UnsupportedEncodingException e) {
			throw new TechnicalException(ENCRYPTER_ERROR, e);
		} catch (final NoSuchAlgorithmException e) {
			throw new TechnicalException(ENCRYPTER_ERROR, e);
		}
	}
}
