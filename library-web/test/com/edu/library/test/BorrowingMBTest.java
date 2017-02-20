package com.edu.library.test;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.edu.library.BorrowMB;
import com.edu.library.access.publicationManagement.BorrowManagementFacade;
import com.edu.library.model.Borrow;

public class BorrowingMBTest {
	@InjectMocks
	BorrowMB borrowingMB;

	@Mock
	BorrowManagementFacade oBorrow;

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
