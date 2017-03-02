package com.edu.library;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.edu.library.model.Book;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.User;
import com.edu.library.util.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class BorrowingMBTest {

	@InjectMocks
	BorrowMB borrowingMB;

	@Mock
	IBorrowService oBorrow;

	@Test
	public void testGetAll() {
		final List<Borrow> borr = new ArrayList<>();
		final Borrow bbb = new Borrow();
		borr.add(bbb);
		Mockito.when(this.oBorrow.getAll()).thenReturn(borr);
		final List<Borrow> listB = this.borrowingMB.getAll();
		Assert.assertEquals(listB, borr);
	}

	@Mock
	Publication currentPublication;
	@Mock
	User currentUser;
	@Mock
	Date date2;
	@Mock
	MessageService message;

	@Test
	public void testStoreNull() {
		final Borrow bbb = new Borrow();

		this.currentPublication = null;
		this.currentUser = new User();
		this.date2 = new Date();
		bbb.setPublication(this.currentPublication);
		bbb.setUser(this.currentUser);
		bbb.setBorrowUntil(this.date2);
		this.borrowingMB.store();

		this.currentPublication = new Book();
		this.currentUser = null;
		this.date2 = new Date();
		bbb.setPublication(this.currentPublication);
		bbb.setUser(this.currentUser);
		bbb.setBorrowUntil(this.date2);
		this.borrowingMB.store();

		this.currentPublication = new Book();
		this.currentUser = new User();
		this.date2 = null;
		bbb.setPublication(this.currentPublication);
		bbb.setUser(this.currentUser);
		bbb.setBorrowUntil(this.date2);
		this.borrowingMB.store();

		Mockito.verify(this.borrowingMB.message, times(3)).warn("managedbean.required");
	}
}
