package com.edu.library;

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

@RunWith(MockitoJUnitRunner.class)
public class BorrowingMBTest {
	
	@InjectMocks
	BorrowMB borrowingMB;

	@Mock
	IBorrowService oBorrow;

	@Test
	public void testGetAll() {
		List<Borrow> borr = new ArrayList<>();
		Borrow bbb=new Borrow();
		borr.add(new Borrow());
		Mockito.when(oBorrow.getAll()).thenReturn(borr);
		List<Borrow> listB = borrowingMB.getAll();
		Assert.assertEquals(listB, borr);
	}

}
