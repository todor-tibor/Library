package com.edu.library.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.edu.library.model.BaseEntity;

/***
 * 
 * @author kiska
 *
 *         Serves as a data validator class for different data input types.
 */
public class ServiceValidation {
	private static final String ERROR_MESSAGE="access.error.illegalArgument";
	/**
	 * STRING_PATTERN - the restriction for a correct user name [a-zA-Z]+ - has
	 * only letters
	 */
	private static final String STRING_PATTERN = "[a-z]+.{3,}";
	/**
	 * PASSWORD_PATTERN - the restriction for a correct password ((?=.*\\d) -
	 * must have at least one number, (?=.*[a-z]) - one lowercase letter ,
	 * (?=.*[A-Z]) - one uppercase letter, (?=.*[@#.$%]) - one of these symbols,
	 * it's length is* minimum 6 characters, but can't be longer than 20
	 * character
	 */
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#.$%]).{6,20})";
	/**
	 * UUID_PATTERN - the restriction for a correct uuid (?=.*[0-9]) - must have
	 * at least one number, (?=.*[a-z]) - one lowercase letter (?=.*[@#$%^&+=])
	 * - one special symbol (?=\\S+$) - can't have any whitespaces .{36,} - the
	 * length should be at least 36 characters
	 */
	private static final String UUID_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[-])";

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
		Pattern pattern = Pattern.compile(STRING_PATTERN);
		Matcher matcher = pattern.matcher(inputString);
		return matcher.matches();
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
	public static void checkPassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		if(!matcher.matches()){
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	/**
	 * Checks if the given uuid matches the constraints defined by UUID_PATTERN
	 * 
	 * @param uuid
	 *            - the unique identification string of an object
	 * @return - true if the two id match, false otherwise
	 */
	public static void checkUuid(String uuid) {
		if(uuid==null || uuid.length()==0){
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	public static void checkNotNull(BaseEntity entity) {
		if (entity == null) {			
				throw new IllegalArgumentException(ERROR_MESSAGE);			
		}
	}
	
	public static void checkIfNumberInRange(int number, int minRange, int maxRange){
		if (!(number <= maxRange && number > minRange)){
				throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}
}
