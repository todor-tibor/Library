package com.edu.library;

import static org.mockito.Mockito.times;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.edu.library.model.Book;
import com.edu.library.util.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class PublicationMBTest {

	@Mock
	MessageService message;

	@Mock
	Book currentPublication;

	@InjectMocks
	PublicationMB publicationMB;

	@Test
	public void testStoreNull() {
		this.currentPublication = new Book();
		this.publicationMB.store("", "");
		Mockito.verify(this.publicationMB.message, times(1)).warn("managedbean.required");
		this.publicationMB.store("title", "invalidNumber");
		Mockito.verify(this.publicationMB.message, times(1)).warn("managedbean.numberFormatExeption");
		// this.publicationMB.setType("Book");
		// this.publicationMB.store("title", "2");
	}

}
