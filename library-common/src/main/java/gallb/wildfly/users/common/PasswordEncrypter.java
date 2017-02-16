/**
 * 
 */
package gallb.wildfly.users.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author kiska
 *
 */
public class PasswordEncrypter {
	private final static String ENCRYPTER_ERROR = "password.error.message";

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
			throw new LibraryException(ENCRYPTER_ERROR, e);
		} catch (NoSuchAlgorithmException e) {
			throw new LibraryException(ENCRYPTER_ERROR, e);
		}
	}
}
