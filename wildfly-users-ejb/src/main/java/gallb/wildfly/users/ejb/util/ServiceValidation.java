package gallb.wildfly.users.ejb.util;
/***
 * 
 * @author kiska
 *
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
	public static boolean checkString(String inputString){		
		return STRING_PATTERN.matches(inputString);
	}
	
	public static boolean checkPassword(String password){
		return  PASSWORD_PATTERN.matches(password);
	}
}
