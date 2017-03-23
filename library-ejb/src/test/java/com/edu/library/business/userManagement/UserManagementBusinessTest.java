package com.edu.library.business.userManagement;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.exception.ErrorMessages;
import com.edu.library.business.userManagement.UserManagementBusiness;
import com.edu.library.data.userManagement.UserDao;
import com.edu.library.model.Borrow;
import com.edu.library.model.Role;
import com.edu.library.model.User;

@RunWith(MockitoJUnitRunner.class)

public class UserManagementBusinessTest {
	@InjectMocks
	UserManagementBusiness userManagementBusiness;
	@Mock
	UserDao dataAcces;

	@Test
	public void testGetAll() {
		final List<User> users = new ArrayList<>();
		final User user = new User();
		users.add(user);
		Mockito.when(this.dataAcces.getAll()).thenReturn(users);
		final List<User> usersFromMB = this.userManagementBusiness.getAll();
		Assert.assertEquals(usersFromMB, users);
	}

	@Test
	public void testSearch() {
		final List<User> users = new ArrayList<>();
		final User user = new User();
		user.setUserName("test");
		users.add(user);
		Mockito.when(this.dataAcces.search("test")).thenReturn(users);
		final List<User> usersFromMB = this.userManagementBusiness.search("test");
		Assert.assertEquals(usersFromMB, users);
	}

	@Test
	public void testGetById() {
		final List<User> users = new ArrayList<>();
		final User user = new User();
		user.setUuid("123");
		users.add(user);
		Mockito.when(this.dataAcces.getById("123")).thenReturn(user);
		final User userFromMB = this.userManagementBusiness.getById("123");
		Assert.assertEquals(userFromMB, user);
	}

	@Test
	public void testStore() {
		final String name = "KisKate";
		final String password = "Almafa.123";
		final String email = "kiskate@gmail.com";
		final int loyaltyIndex = 10;
		final User tmpUser = new User();
		tmpUser.setUserName(name);
		tmpUser.setLoyaltyIndex(loyaltyIndex);
		tmpUser.setPassword(password);
		tmpUser.setEmail(email);
		final List<Role> roles = new ArrayList<>();
		final Role role = new Role();
		role.setUuid("1423");
		role.setRole("LIBRARIAN");
		roles.add(role);
		tmpUser.setRoles(roles);

		Mockito.doNothing().when(this.dataAcces).store(tmpUser);
		this.userManagementBusiness.store(tmpUser);
		Mockito.verify(this.dataAcces, times(1)).store(Mockito.any());
	}

	@Test
	public void testUpdate() {
		final String password = "Almafa.123";
		final User tmpUser = new User();
		tmpUser.setPassword(password);

		Mockito.doNothing().when(this.dataAcces).update(tmpUser);
		this.userManagementBusiness.update(tmpUser);
		Mockito.verify(this.dataAcces, times(1)).update(Mockito.any());
	}

	@Test
	public void testRemove() {

		final User user = new User();
		user.setUuid("123");
		final List<Borrow> borrows = new ArrayList<>();
		user.setBorrows(borrows);
		Mockito.when(this.dataAcces.getById("123")).thenReturn(user);
		Mockito.doNothing().when(this.dataAcces).remove(user);
		this.userManagementBusiness.remove(user.getUuid());
		Mockito.verify(this.dataAcces, times(1)).remove(user);
	}

	@Test(expected = BusinessException.class)
	public void testRemove_ThrowError() {
		final BusinessException e = new BusinessException(ErrorMessages.ERROR_BOUND);

		final User user = new User();
		user.setUuid("123");
		final List<Borrow> borrows = new ArrayList<>();
		final Borrow borrow = new Borrow();
		borrows.add(borrow);
		user.setBorrows(borrows);
		Mockito.when(this.dataAcces.getById("123")).thenReturn(user);
		Mockito.doThrow(e).when(this.dataAcces).remove(user);
		this.userManagementBusiness.remove(user.getUuid());
		Mockito.verify(this.dataAcces, times(1)).remove(user);
	}

	@Test
	public void testSearchForUserName() {

		final User user = new User();
		user.setUserName("test");

		Mockito.when(this.dataAcces.getByUserName("test")).thenReturn(user);
		final User userFromMB = this.userManagementBusiness.searchForUserName("test");
		Assert.assertEquals(userFromMB, user);
	}

}
