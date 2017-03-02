package com.edu.library;

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

import com.edu.library.exception.LibraryException;
import com.edu.library.model.Role;
import com.edu.library.model.User;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

@RunWith(MockitoJUnitRunner.class)

public class UserMBTest {

	@InjectMocks
	UserMB userMB;

	@Mock
	IUserService userService;

	@Mock
	ExceptionHandler exceptionHandler;

	@Mock
	MessageService message;

	@Test
	public void testGetAll() {
		final List<User> users = new ArrayList<>();
		final User user = new User();
		users.add(user);
		Mockito.when(this.userService.getAll()).thenReturn(users);
		final List<User> usersFromMB = this.userMB.getAll();
		Assert.assertEquals(usersFromMB, users);
	}

	@Test
	public void testGetAll_ThrowsException() {
		final LibraryException e = new LibraryException();
		Mockito.when(this.userService.getAll()).thenThrow(e);
		this.userMB.getAll();
		Mockito.verify(this.userMB.exceptionHandler, times(1)).showMessage(e);
	}

	@Test
	public void testSearch_ForUser() {
		final List<User> users = new ArrayList<>();
		final User user = new User();
		user.setUserName("test");
		users.add(user);
		Mockito.when(this.userService.search("test")).thenReturn(users);
		final List<User> usersFromMB = this.userMB.search("test");
		Assert.assertEquals(usersFromMB, users);
	}

	@Test
	public void testSearch_InvalidParameter_ReturnsEmptyList() {
		final List<User> users = new ArrayList<>();
		Mockito.when(this.userService.search("ts")).thenReturn(users);
		final List<User> usersFromMB = this.userMB.search("ts");
		Assert.assertEquals(usersFromMB, users);
	}

	@Test
	public void testSearch_InvalidParamter_ThrowsException() {
		final LibraryException e = new LibraryException();
		Mockito.when(this.userService.search("ts123")).thenThrow(e);
		this.userMB.search("ts123");
		Mockito.verify(this.userMB.exceptionHandler, times(1)).showMessage(e);
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
		this.userMB.setCurrentRoles(roles);

		Mockito.doNothing().when(this.userService).store(tmpUser);
		this.userMB.store(name, password, loyaltyIndex, email);
		Mockito.verify(this.userService, times(1)).store(Mockito.any());
	}

	@Test
	public void testStore_EmptyName() {
		final String name = "";

		Mockito.doNothing().when(this.userService).store(new User());
		this.userMB.store(name, "", 0, "");
		Mockito.verify(this.userMB.message, times(1)).warn("managedbean.empty");
	}

	@Test
	public void testStore_InvalidLoyaltyIndex() {
		final String name = "KisKate";
		final int loyaltyIndex = 11;
		final User user = new User();
		user.setUserName(name);
		user.setLoyaltyIndex(loyaltyIndex);
		Mockito.doNothing().when(this.userService).store(user);
		this.userMB.store(name, "", loyaltyIndex, "");
		Mockito.verify(this.userMB.message, times(1)).warn("managedbean.loyalty");

		final int loyaltyIndex2 = 0;
		final User user2 = new User();
		user2.setUserName(name);
		user2.setLoyaltyIndex(loyaltyIndex2);

		Mockito.doNothing().when(this.userService).store(user2);
		this.userMB.store(name, "", loyaltyIndex2, "");
		Mockito.verify(this.userMB.message, times(1)).warn("managedbean.loyalty");
	}

	@Test
	public void testStore_CurrentRolesIsEmpty() {
		final String name = "KisKate";
		final String password = "Almafa.123";
		final String email = "kiskate@gmail.com";
		final int loyaltyIndex = 10;
		final User tmpUser = new User();
		tmpUser.setUserName(name);
		tmpUser.setLoyaltyIndex(loyaltyIndex);
		tmpUser.setPassword(password);
		tmpUser.setEmail(email);

		Mockito.doNothing().when(this.userService).store(tmpUser);
		this.userMB.store(name, password, loyaltyIndex, email);
		Mockito.verify(this.userMB.message, times(1)).warn("managedbean.empty");
	}

	@Test
	public void testStore_ThrowException() {
		final LibraryException e = new LibraryException();

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
		this.userMB.setCurrentRoles(roles);

		Mockito.doThrow(e).when(this.userService).store(Mockito.any());
		this.userMB.store(name, password, loyaltyIndex, email);
		Mockito.verify(this.userMB.message, times(1)).error(e.getMessage());
	}

	@Test
	public void testRemove() {
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
		this.userMB.setCurrentRoles(roles);

		this.userMB.setCurrentUser(tmpUser);

		Mockito.doNothing().when(this.userService).remove(tmpUser.getUuid());
		this.userMB.remove();
		Mockito.verify(this.userService, times(1)).remove(tmpUser.getUuid());
	}

}