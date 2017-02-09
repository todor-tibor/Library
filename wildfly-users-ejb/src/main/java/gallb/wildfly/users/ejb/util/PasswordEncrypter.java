/**
 * 
 */
package gallb.wildfly.users.ejb.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jboss.logging.Logger;

import gallb.wildfly.users.ejb.EjbException;
import gallb.wildfly.users.ejb.RoleBean;

/**
 * @author kiska
 *
 */
public class PasswordEncrypter {
	private static Logger oLogger = Logger.getLogger(RoleBean.class);
	private final static String ENCRYPTER_ERROR = "Password encrypter error!";
	public static String encypted(String password, String salt) {
		try {
			byte[] initialBytes;
			initialBytes = (password + salt).getBytes("utf-8");
			MessageDigest algorithm = MessageDigest
					.getInstance("SHA");
			algorithm.reset();
			algorithm.update(initialBytes);
			byte[] hashBytes = algorithm.digest();
			return new String(hashBytes);
		} catch (UnsupportedEncodingException e) {
			oLogger.error(e);
			throw new EjbException(ENCRYPTER_ERROR, e);
		} catch (NoSuchAlgorithmException e) {
			oLogger.error(e);
			throw new EjbException(ENCRYPTER_ERROR, e);
		}
	}
}
