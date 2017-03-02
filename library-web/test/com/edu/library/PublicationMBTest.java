package com.edu.library;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.edu.library.exception.ErrorLevel;
import com.edu.library.exception.LibraryException;
import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class PublicationMBTest {

	@Mock
	MessageService message;

	@Mock
	IPublicationService publicationBean;

	@Mock
	ExceptionHandler exceptionHandler;

	@InjectMocks
	PublicationMB publicationMB;

	@Test
	public void testStore_invalid() {
		this.publicationMB.store("", "");
		Mockito.verify(this.publicationMB.message, times(1)).warn("managedbean.required");
		this.publicationMB.store("title", "invalidNumber");
		Mockito.verify(this.publicationMB.message, times(1)).warn("managedbean.numberFormatExeption");
	}

	@Test
	public void testStore_Newspaper() {
		this.publicationMB.setCurrentPublication(new Newspaper());
		this.publicationMB.setType("Newspaper");
		this.publicationMB.setCurrentPublisher(new Publisher());
		this.publicationMB.setDate(new Date());
		Mockito.doNothing().when(this.publicationBean).store(Mockito.any());
		this.publicationMB.store("test", "3");
		Mockito.verify(this.publicationBean, times(1)).store(Mockito.any());
	}

	@Test
	public void testStore_Book_invalidAuthors() {
		this.publicationMB.setCurrentPublication(new Book());
		this.publicationMB.setType("Book");
		this.publicationMB.setCurrentPublisher(new Publisher());
		this.publicationMB.setDate(new Date());
		this.publicationMB.store("test", "3");
		Mockito.verify(this.publicationMB.message, times(1)).warn("managedbean.required");
	}

	@Test
	public void testStore_Book_Exception() {
		this.publicationMB.setCurrentPublication(new Book());
		this.publicationMB.setType("Book");
		this.publicationMB.setCurrentPublisher(new Publisher());
		this.publicationMB.setDate(new Date());
		List<Author> listAuthor = new ArrayList<>();
		listAuthor.add(new Author());
		this.publicationMB.setCurrentAuthors(listAuthor);
		Mockito.doThrow(new LibraryException("mess", ErrorLevel.ERROR)).when(this.publicationBean).store(Mockito.any());
		this.publicationMB.store("test", "3");
		Mockito.verify(this.publicationMB.exceptionHandler, times(1)).showMessage(Mockito.any());
	}

	@Test
	public void testStore_Magazine() {
		this.publicationMB.setCurrentPublication(new Magazine());
		this.publicationMB.setType("Magazine");
		this.publicationMB.setCurrentPublisher(new Publisher());
		this.publicationMB.setDate(new Date());
		List<Author> listAuthor = new ArrayList<>();
		listAuthor.add(new Author());
		this.publicationMB.setCurrentAuthors(listAuthor);
		this.publicationMB.store("test", "3");
		Mockito.verify(this.publicationMB.publicationBean, times(1)).store(Mockito.any());
	}

	@Test
	public void testUpdate_Newspaper() {
		Newspaper news = new Newspaper();
		news.setTitle("title");
		news.setPublisher(new Publisher());
		this.publicationMB.setCurrentPublication(news);
		this.publicationMB.setCurrentPublisher(new Publisher());
		Mockito.doNothing().when(this.publicationBean).update(Mockito.any());
		Mockito.when(this.publicationBean.getAll()).thenReturn(new ArrayList<Publication>());
		this.publicationMB.update();
		Mockito.verify(this.publicationBean, times(1)).update(Mockito.any());
	}

	@Test
	public void testUpdate_Book() {
		Book book = new Book();
		book.setTitle("title");
		book.setPublisher(new Publisher());
		List<Author> listAuthor = new ArrayList<>();
		listAuthor.add(new Author());
		book.setAuthors(listAuthor);
		this.publicationMB.setCurrentPublication(book);
		this.publicationMB.setCurrentPublisher(new Publisher());
		this.publicationMB.setAuthors(listAuthor);
		Mockito.doNothing().when(this.publicationBean).update(Mockito.any());
		Mockito.when(this.publicationBean.getAll()).thenReturn(new ArrayList<Publication>());
		this.publicationMB.update();
		Mockito.verify(this.publicationBean, times(1)).update(Mockito.any());
	}

	@Test
	public void testUpdate_Magazine() {
		Magazine magazine = new Magazine();
		magazine.setTitle("title");
		magazine.setPublisher(new Publisher());
		List<Author> listAuthor = new ArrayList<>();
		listAuthor.add(new Author());
		magazine.setAuthors(listAuthor);
		this.publicationMB.setCurrentPublication(magazine);
		this.publicationMB.setCurrentPublisher(new Publisher());
		this.publicationMB.setAuthors(listAuthor);
		Mockito.doThrow(new LibraryException()).when(this.publicationBean).update(Mockito.any());
		Mockito.when(this.publicationBean.getAll()).thenReturn(new ArrayList<Publication>());
		this.publicationMB.update();
		Mockito.verify(this.publicationMB.exceptionHandler, times(1)).showMessage(Mockito.any());
	}

	@Test
	public void testUpdate_Magazine_noTitle() {
		Magazine magazine = new Magazine();
		magazine.setPublisher(new Publisher());
		this.publicationMB.update();
		Mockito.verify(this.publicationMB.message, times(1)).warn(Mockito.any());
	}
}
