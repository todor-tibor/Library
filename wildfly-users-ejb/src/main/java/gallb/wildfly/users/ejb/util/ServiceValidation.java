package gallb.wildfly.users.ejb.util;

/***
 * 
 * @author kiska
 *
 *         Serves as a data validator class for different data input types.
 */
public class ServiceValidation {
	/**
	 * STRING_PATTERN - the restriction for a correct user name (.*[a-z]) - has
	 * only letters {3} - is at least 3 characters long
	 */
	private static final String STRING_PATTERN = "{3,}";
	/**
	 * PASSWORD_PATTERN - the restriction for a correct password (?=.*[0-9]) -
	 * must have at least one number, (?=.*[a-z]) - one lowercase letter	 * 
	 * least 5 characters
	 */
	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}";
	/**
	 * UUID_PATTERN - the restriction for a correct uuid (?=.*[0-9]) - must have
	 * at least one number, (?=.*[a-z]) - one lowercase letter (?=.*[@#$%^&+=])
	 * - one special symbol (?=\\S+$) - can't have any whitespaces .{36,} - the
	 * length should be at least 36 characters
	 */
	private static final String UUID_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{36,}";

	/**
	 * Check whether the given string matches a set of constraints defined in
	 * the STRING_PATTERN constant
	 * 
	 * @param inputString
	 *            - a string representation of the input data (can be a user
	 *            name, publication name, etc.)
	 * @return - true if the input data satisfies the given constraints,
	 *         otherwise returns false
	 */
	public static boolean checkString(String inputString) {
		return STRING_PATTERN.matches(inputString);
	}

	/**
	 * * Check whether the given password matches a set of constraints defined
	 * in the PASSWORD_PATTERN constant
	 * 
	 * @param password
	 *            - the password the user gave
	 * @return - true if the password satisfies the given constraints, otherwise
	 *         returns false
	 */
	public static boolean checkPassword(String password) {
		return PASSWORD_PATTERN.matches(password);
	}

	/**
	 * Checks if the given uuid matches the constraints defined by UUID_PATTERN
	 * 
	 * @param uuid
	 *            - the unique identification string of an object
	 * @return - true if the two id match, false otherwise
	 */
	public static boolean checkUuid(String uuid) {
		return UUID_PATTERN.matches(uuid);
	}
}
