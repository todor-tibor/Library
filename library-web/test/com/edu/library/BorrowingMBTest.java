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

	// @Mock
	// Publication currentPublication;
	//
	// @Mock
	// User currentUser;
	//
	// @Mock
	// Date date2;
	//
	@Mock
	MessageService message;

	@Test
	public void testStoreNull() {
		final Borrow bbb = new Borrow();
		// Test nullcheck for publication
		this.borrowingMB.setCurrentPublication(null);
		this.borrowingMB.setCurrentUser(new User());
		this.borrowingMB.setDate2(new Date());

		this.borrowingMB.store();
		// Test nullcheck for user
		this.borrowingMB.setCurrentPublication(new Book());
		this.borrowingMB.setCurrentUser(null);
		this.borrowingMB.setDate2(new Date());

		this.borrowingMB.store();
		// Test nullcheck for borrow until date
		this.borrowingMB.setCurrentPublication(new Book());
		this.borrowingMB.setCurrentUser(new User());
		this.borrowingMB.setDate2(null);

		this.borrowingMB.store();

		Mockito.verify(this.borrowingMB.message, times(3)).warn("managedbean.required");

	}

	@Test
	public void testStore() {
		final Borrow bbb = new Borrow();

		Mockito.doNothing().when(this.oBorrow).store(bbb);

		this.borrowingMB.setCurrentPublication(new Book());
		this.borrowingMB.setCurrentUser(new User());
		this.borrowingMB.setDate2(new Date());

		this.borrowingMB.store();
	}
}
