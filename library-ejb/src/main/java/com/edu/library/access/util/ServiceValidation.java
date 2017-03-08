package com.edu.library.access.util;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.logging.Logger;

import com.edu.library.model.BaseEntity;

/**
 * Serves as a Singleton data validator class for different data input types.
 *
 * @author kiska
 * @author gallb
 */
public class ServiceValidation {

	private static Logger logger = Logger.getLogger(ServiceValidation.class);

	private ServiceValidation() {

	}

	private static final String ERROR_MESSAGE = "access.error.illegalArgument";
	/**
	 * STRING_PATTERN - the restriction for a correct user name
	 *
	 * [a-zA-Z]+.{3,} - has only letters and is at least 3 characters long
	 */
	private static final String STRING_PATTERN = "[a-zA-Z\\s,]+.{2,}";
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
	 *
	 */
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Check whether the given string matches a set of constraints defined in
	 * the STRING_PATTERN constant Throws exception if it doesn't match.
	 *
	 * @param inputString
	 *            - a string representation of the input data (can be a user
	 *            name, publication name, etc.)
	 */
	public static void checkString(final String inputString) {
		final Pattern pattern = Pattern.compile(STRING_PATTERN);
		final Matcher matcher = pattern.matcher(inputString);
		if (!matcher.matches()) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	/**
	 * * Check whether the given password matches a set of constraints defined
	 * in the PASSWORD_PATTERN constant Throws exception if it doesn't match.
	 *
	 * @param password
	 *            - the password the user gave
	 */
	public static void checkPassword(final String password) {
		if (password == null) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
		final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		final Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	/**
	 * Checks if the given uuid matches the constraints defined by UUID_PATTERN
	 * Throws exception if it doesn't match.
	 *
	 * @param uuid
	 *            - the unique identification string of an object
	 */
	public static void checkUuid(final String uuid) {
		if (uuid == null || uuid.length() == 0) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	/**
	 * Checks whether the given entity exists. Throws exception if entity
	 * doesn't exist
	 *
	 * @param entity
	 *            - the input entity for which the not null validation is
	 *            checked
	 *
	 */
	public static void checkNotNull(final BaseEntity entity) {
		if (entity == null) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	/**
	 * Checks whether the given list is empty. Throws an error if it is.
	 *
	 * @param entityList
	 *            - the list for which the empty check is done
	 */
	public static void checkNotEmpty(final List<? extends BaseEntity> entityList) {
		if (entityList == null || entityList.isEmpty()) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	/**
	 * Checks whether the number given by {@code number} is between the
	 * thresholds given by {@code minRange}, {@code maxRange} Throws an
	 * exception if condition are not met.
	 *
	 * @param number
	 *            - the number on which the check is done
	 * @param minRange
	 *            - the minimum value the {@code number} can have
	 * @param maxRange
	 *            - the maximum value the {@code number} can have
	 */
	public static void checkIfNumberInRange(final int number, final int minRange, final int maxRange) {
		if (!(number <= maxRange && number > minRange)) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	/**
	 * Checks if the two dates are in chronological order. Throws an exception
	 * if they aren't.
	 *
	 * @param from
	 *            - the start date
	 * @param until
	 *            - the end date
	 */
	public static void checkDateOrder(final Date from, final Date until) {
		if (from.after(until)) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}

	/**
	 * * Check whether the given email matches a set of constraints defined in
	 * the EMAIL_PATTERN constant Throws exception if it doesn't match.
	 *
	 * @param email
	 *            - the email of the user
	 */
	public static void checEmail(final String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			logger.error(ERROR_MESSAGE);
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}
}
