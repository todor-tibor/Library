package com.edu.library.util;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.logging.Logger;

import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.model.BaseEntity;

/***
 * 
 * @author kiska
 *
 *         Serves as a data validator class for different data input types.
 */
public class ServiceValidation {

	private static Logger oLogger = Logger.getLogger(ServiceValidation.class);
	/**
	 * STRING_PATTERN - the restriction for a correct user name
	 * 
	 * [a-zA-Z]+.{3,} - has only letters and is at least 3 characters long
	 */
	private static final String STRING_PATTERN = "[a-zA-Z\\s,]+.{3,}";
	/**
	 * PASSWORD_PATTERN - the restriction for a correct password ((?=.*\\d) -
	 * must have at least one number,
	 * 
	 * (?=.*[a-z]) - one lowercase letter ,
	 * 
	 * (?=.*[A-Z]) - one uppercase letter,
	 * 
	 * (?=.*[@#.$%]) - one of these symbols, it's length is minimum 6
	 * characters, but can't be longer than 20 character
	 */
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#.$%]).{6,20})";
	/**
	 * UUID_PATTERN - the restriction for a correct uuid
	 * 
	 * [0-9A-Fa-f] - characters in range of 0-9 or A-F upper or lower case
	 * (basically hexadecimal character) the above constraint repeats as
	 * folllows: sequence of length 8 followed by a three of length of 4 and
	 * finally one with a length of 12 *
	 * 
	 * ^ - start of line
	 * 
	 * $ - end of line
	 */

	public static final String UUID_PATTERN = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";

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
	public static void checkString(String inputString) {
		Pattern pattern = Pattern.compile(STRING_PATTERN);
		Matcher matcher = pattern.matcher(inputString);
		if (!matcher.matches()) {
			oLogger.error(ErrorMessages.ERROR_MESSAGE);
			throw new IllegalArgumentException(ErrorMessages.ERROR_MESSAGE);
		}
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
		if (!matcher.matches()) {
			oLogger.error(ErrorMessages.ERROR_MESSAGE);
			throw new IllegalArgumentException(ErrorMessages.ERROR_MESSAGE);
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
		if (uuid == null || uuid.length() == 0) {
			oLogger.error(ErrorMessages.ERROR_MESSAGE);
			throw new IllegalArgumentException(ErrorMessages.ERROR_MESSAGE);
		}
	}

	public static void checkNotNull(BaseEntity entity) {
		if (entity == null) {
			oLogger.error(ErrorMessages.ERROR_MESSAGE);
			throw new IllegalArgumentException(ErrorMessages.ERROR_MESSAGE);
		}
	}

	public static void checkNotEmpty(List<? extends BaseEntity> entityList) {
		if (entityList == null || entityList.isEmpty()) {
			oLogger.error(ErrorMessages.ERROR_MESSAGE);
			throw new IllegalArgumentException(ErrorMessages.ERROR_MESSAGE);
		}
	}

	public static void checkIfNumberInRange(int number, int minRange, int maxRange) {
		if (!(number <= maxRange && number > minRange)) {
			oLogger.error(ErrorMessages.ERROR_MESSAGE);
			throw new IllegalArgumentException(ErrorMessages.ERROR_MESSAGE);
		}
	}

	/*
	 * check if the two dates are in the correct chronological order.
	 */
	public static void checDateOrder(Date from, Date until) {
		if (from.after(until)) {
			oLogger.error(ErrorMessages.ERROR_MESSAGE);
			throw new IllegalArgumentException(ErrorMessages.ERROR_MESSAGE);
		}
	}
}
