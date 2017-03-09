package com.edu.library.business.publicationManagement;

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
import com.edu.library.data.publicationManagement.PublicationBean;
import com.edu.library.exception.LibraryException;
import com.edu.library.filter.PublicationFilter;
import com.edu.library.model.Book;
import com.edu.library.model.Borrow;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;

@RunWith(MockitoJUnitRunner.class)
public class PublicationManagementBusinessTest {

	@InjectMocks
	PublicationManagementBusiness publicationManagementBusiness;
	@Mock
	PublicationBean dataAcces;

	@Test
	public void testGetAll() {
		final List<Publication> publications = new ArrayList<>();
		Mockito.when(this.dataAcces.getAll()).thenReturn(publications);
		final List<Publication> publicationsFromMB = this.publicationManagementBusiness.getAll();
		Assert.assertEquals(publicationsFromMB, publications);
	}

	@Test
	public void testGetAllPaginate() {
		final List<Publication> publications = new ArrayList<>();
		Mockito.when(this.dataAcces.getAll(1, 5)).thenReturn(publications);
		final List<Publication> publicationsFromMB = this.publicationManagementBusiness.getAll(1, 5);
		Assert.assertEquals(publicationsFromMB, publications);
	}

	@Test
	public void testSearch() {
		final List<Publication> publications = new ArrayList<>();
		Mockito.when(this.dataAcces.search("test")).thenReturn(publications);
		final List<Publication> publicationsFromMB = this.publicationManagementBusiness.search("test");
		Assert.assertEquals(publicationsFromMB, publications);
	}

	@Test
	public void testGetById() {
		final Publication publication = new Magazine();
		publication.setUuid("123");
		Mockito.when(this.dataAcces.getById("123")).thenReturn(publication);
		final Publication publicationFromMB = this.publicationManagementBusiness.getById("123");
		Assert.assertEquals(publicationFromMB, publication);
	}

	@Test
	public void testStore() {
		Mockito.doNothing().when(this.dataAcces).store(Mockito.any());
		this.publicationManagementBusiness.store(new Book());
		Mockito.verify(this.dataAcces, times(1)).store(Mockito.any());
	}

	@Test(expected = LibraryException.class)
	public void testUpdate_Exception() {
		Mockito.doThrow(new LibraryException()).when(this.dataAcces).getById(Mockito.anyString());
		this.publicationManagementBusiness.update(new Newspaper());
		Mockito.verify(this.dataAcces, times(0)).update(Mockito.any());
	}

	@Test
	public void testUpdate() {
		Publication publication = new Book();
		publication.setUuid("123");
		Mockito.when(this.dataAcces.getById("123")).thenReturn(publication);
		this.publicationManagementBusiness.update(publication);
		Mockito.verify(this.dataAcces, times(1)).update(Mockito.any());
	}

	@Test
	public void testRemove() {
		Publication publication = new Book();
		publication.setUuid("123");
		publication.setBorrows(new ArrayList<>());
		Mockito.when(this.dataAcces.getById("123")).thenReturn(publication);
		Mockito.doNothing().when(this.dataAcces).remove(publication);
		this.publicationManagementBusiness.remove(publication.getUuid());
		Mockito.verify(this.dataAcces, times(1)).remove(publication);
	}

	@Test(expected = BusinessException.class)
	public void testRemove_Exception() {
		Publication publication = new Book();
		publication.setUuid("123");
		Borrow borrow = new Borrow();
		List<Borrow> borrows = new ArrayList<Borrow>();
		borrows.add(borrow);
		publication.setBorrows(borrows);
		Mockito.when(this.dataAcces.getById("123")).thenReturn(publication);
		this.publicationManagementBusiness.remove(publication.getUuid());
	}

	@Test
	public void testFilterPublication() {
		PublicationFilter filter = new PublicationFilter();
		List<Publication> expected = new ArrayList<>();
		expected.add(new Book());
		Mockito.when(this.dataAcces.filterPublication(filter, 1, 5)).thenReturn(expected);
		List<Publication> pubs = this.publicationManagementBusiness.filterPublication(filter, 1, 5);
		Assert.assertEquals(expected, pubs);
	}

}