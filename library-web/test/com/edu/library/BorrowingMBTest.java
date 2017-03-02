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

import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
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
	Publication currentPublication = null;
	@Mock
	MessageService message;

	@Test
	public void testStorePublicationNull() {
		final Borrow bbb = new Borrow();
		bbb.setPublication(this.currentPublication);
		this.borrowingMB.store();
		Mockito.verify(this.borrowingMB.message, times(1)).warn("managedbean.required");
	}
}
