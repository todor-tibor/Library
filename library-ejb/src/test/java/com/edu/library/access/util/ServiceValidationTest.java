package com.edu.library.access.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.edu.library.access.util.ServiceValidation;
import com.edu.library.model.Book;
import com.edu.library.model.Publication;
import com.edu.library.model.Role;

@RunWith(MockitoJUnitRunner.class)
public class ServiceValidationTest {

	/**
	 * Test if the exception is thrown correctly by 'checkString' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkStringException() {
		String string = ".../,kjuh";
		ServiceValidation.checkString(string);
	}

	/**
	 * Test if accept a correct String
	 */
	@Test
	public void checkString() {
		String string = "String";
		ServiceValidation.checkString(string);
	}

	/**
	 * Test if the exception is thrown correctly by 'checkPassword' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkPasswordException() {
		String password = "12345";
		ServiceValidation.checkPassword(password);
	}

	/**
	 * Test if accept a correct password
	 */
	@Test
	public void checkPassword() {
		String password = "sdedeJAK123.";
		ServiceValidation.checkPassword(password);
	}

	/**
	 * Test if the exception is thrown correctly by 'checkUuid' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkUuidExceptionNull() {
		String uuid = null;
		ServiceValidation.checkUuid(uuid);
	}

	/**
	 * Test if the exception is thrown correctly by 'checkUuid' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkUuidException() {
		String uuid = "";
		ServiceValidation.checkUuid(uuid);
	}

	/**
	 * Test if accept a correct id
	 */
	@Test
	public void checkUuid() {
		String uuid = "29159ec5-66e2-4b79-bd02-afe382388b48";
		ServiceValidation.checkUuid(uuid);
	}

	/**
	 * Test if the exception is thrown correctly by 'checkNotNull' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkNotNullException() {
		Publication p = null;
		ServiceValidation.checkNotNull(p);
	}

	/**
	 * Test if accept a not null object
	 */
	@Test
	public void checkNotNull() {
		Publication p = new Book();
		ServiceValidation.checkNotNull(p);
	}

	/**
	 * Test if the exception is thrown correctly by 'checkNotEmpty' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkNotEmptyExceptionEmpty() {
		ServiceValidation.checkNotEmpty(new ArrayList<Publication>());
	}

	/**
	 * Test if the exception is thrown correctly by 'checkNotEmpty' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkNotEmptyExceptionNull() {
		ServiceValidation.checkNotEmpty(null);
	}

	/**
	 * Test if accept a list
	 */
	@Test
	public void checkNotEmpty() {
		List<Role> roleList = new ArrayList<>();
		roleList.add(new Role());
		ServiceValidation.checkNotEmpty(roleList);
	}

	/**
	 * Test if the exception is thrown correctly by 'checkIfNumberInRange'
	 * method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkIfNumberInrangeException1() {
		ServiceValidation.checkIfNumberInRange(-10, -30, -20);
	}

	/**
	 * Test if the exception is thrown correctly by 'checkIfNumberInRange'
	 * method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkIfNumberInrangeException2() {
		ServiceValidation.checkIfNumberInRange(-10, 0, -10);
	}

	/**
	 * Test if accept a correct order of numbers
	 */
	@Test
	public void checkIfNumberInRange() {
		ServiceValidation.checkIfNumberInRange(5, 0, 5);
	}

	/**
	 * Test if the exception is thrown correctly by 'checkDateOrder' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkDateOrderException() {
		ServiceValidation.checkDateOrder(new Date(), new Date(1220227200L * 1000));
	}

	/**
	 * Test if accept the correct date order
	 */
	@Test
	public void checkDateOrder() {
		ServiceValidation.checkDateOrder(new Date(1220227200L * 1000), new Date());
	}

	/**
	 * Test if the exception is thrown correctly by 'checEmail' method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailException() {
		ServiceValidation.checEmail("alma@fa@com");
	}

	/**
	 * Test if accept a correct email
	 */
	@Test
	public void checkEmailOrder() {
		ServiceValidation.checEmail("alma@fa.com");
	}

}
