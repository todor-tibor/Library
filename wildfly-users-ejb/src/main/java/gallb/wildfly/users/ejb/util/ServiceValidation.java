package gallb.wildfly.users.ejb.util;
/***
 * 
 * @author kiska
 *
 *Serves as a data validator class for different data input types.
 */
public class ServiceValidation {
	/**
	 * STRING_PATTERN - the restriction for a correct user name
	 * (.*[a-z]) - has only letters
	 * {3} - is at least 3 characters long
	 */
	private static final String STRING_PATTERN = "(.*[a-z]){3}";
	/**
	 * PASSWORD_PATTERN - the restriction for a correct password
	 * (?=.*[0-9]) - must have at least one number,
	 * (?=.*[a-z]) - one lowercase letter
	 * (?=.*[A-Z]) - one uppercase letter
	 * (?=.*[@#$%^&+=]) - one special symbol
	 * (?=\\S+$) - can't have any whitespaces
	 * .{8,} - the length should be at least 5 characters
	 */
	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}";
	/**
	 * Check whether the given string matches a set of constraints defined in the STRING_PATTERN constant
	 * @param inputString - a string representation of the input data (can be a user name, publication name, etc.)
	 * @return - true if the input data satisfies the given constraints, otherwise returns false
	 */
	public static boolean checkString(String inputString){		
		return STRING_PATTERN.matches(inputString);
	}
	
	/**
	 *  * Check whether the given password matches a set of constraints defined in the PASSWORD_PATTERN constant
	 * @param password - the password the user gave
	 * @return - true if the password satisfies the given constraints, otherwise returns false
	 */
	public static boolean checkPassword(String password){
		return  PASSWORD_PATTERN.matches(password);
	}
}
