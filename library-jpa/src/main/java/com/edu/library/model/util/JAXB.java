package com.edu.library.model.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.edu.library.model.BaseEntity;
import com.edu.library.model.Book;
import com.edu.library.model.Borrow;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;
import com.edu.library.model.User;

/**
 * Marshall and unmarshall entities and list of entities
 *
 * @author sipost
 *
 */
public class JAXB {

	private JAXB() {

	}

	/**
	 * Marshall one entity
	 *
	 * @param entity
	 *            extends BaseEntity
	 */
	public static <T extends BaseEntity> void marshall(final T entity) {
		try {
			JAXBContext context = JAXBContext.newInstance(entity.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			System.out.println(entity.getClass());
			System.out.println("id: " + entity.getUuid());
			marshaller.marshal(entity, System.out);
			marshaller.marshal(entity, new File("C:\\" + entity.getClass().getSimpleName() + ".xml"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Marshal one user with borrows
	 *
	 * @param entity
	 *            -User
	 */
	public static void marshall(final User entity) {
		try {
			JAXBContext context = JAXBContext.newInstance(entity.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			System.out.println(entity.getClass());
			System.out.println("user id: " + entity.getUuid());
			List<Borrow> borrows = entity.getBorrows();
			for (Borrow b : borrows) {
				b.setUser(null);
				Publication p = b.getPublication();
				p.setBorrows(null);
				b.setPublication(p);
			}
			entity.setBorrows(borrows);
			marshaller.marshal(entity, System.out);
			marshaller.marshal(entity, new File("C:\\" + entity.getClass().getSimpleName() + ".xml"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Marshal one publication with borrows
	 *
	 * @param entity
	 *            - Book, Magazine or Newspaper
	 */
	public static void marshall(final Publication entity) {
		try {
			JAXBContext context = JAXBContext.newInstance(entity.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			System.out.println(entity.getClass());
			System.out.println("publication id: " + entity.getUuid());
			List<Borrow> borrows = entity.getBorrows();
			for (Borrow b : borrows) {
				b.setPublication(null);
				User u = b.getUser();
				u.setBorrows(null);
				b.setUser(u);
			}
			entity.setBorrows(borrows);
			marshaller.marshal(entity, System.out);
			marshaller.marshal(entity, new File("C:\\" + entity.getClass().getSimpleName() + ".xml"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Marshal list of elements
	 *
	 * @param entities
	 *            - List of elements extend BaseEntity
	 * @param type
	 *            - Class of elements
	 */
	public static <T extends BaseEntity> void marshall(final List<T> entities, final Class<T> type) {
		try {
			System.out.println("class: " + entities.getClass() + "  " + type);
			JAXBContext context = JAXBContext.newInstance(EntityList.class, type);
			if (Publication.class.equals(type)) {
				context = JAXBContext.newInstance(EntityList.class, type, Book.class, Magazine.class, Newspaper.class);
			}
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			EntityList<T> list = new EntityList<>(entities);
			System.out.println(entities.getClass());
			marshaller.marshal(list, System.out);
			marshaller.marshal(list, new File("C:\\" + type.getSimpleName() + "List.xml"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Unmarshall one Publication
	 *
	 * @return
	 */
	public static List<Publication> unmarshallPublication() {
		try {
			JAXBContext context = JAXBContext.newInstance(EntityList.class, Book.class, Magazine.class, Newspaper.class,
					Publication.class);
			Unmarshaller unmarhsaller = context.createUnmarshaller();
			Publication listOfPublications = (Publication) unmarhsaller.unmarshal(new File("C:\\Book.xml"));
			List<Publication> pubs = new ArrayList<>();
			pubs.add(listOfPublications);
			return pubs;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("can't unmarchal");
			throw new IllegalArgumentException("can't unmarchal");
		}
	}

	/**
	 * Unmarshal List of entityes
	 *
	 * @return
	 */
	public static <T extends BaseEntity> List<T> unmarshallList() {
		try {
			JAXBContext context = JAXBContext.newInstance(EntityList.class, Book.class, Magazine.class, Newspaper.class,
					Publication.class);
			Unmarshaller unmarhsaller = context.createUnmarshaller();
			@SuppressWarnings("unchecked")
			EntityList<T> listOfPublications = (EntityList<T>) unmarhsaller.unmarshal(new File("C:\\ArrayList.xml"));
			return listOfPublications.getEntities();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("can't unmarchal");
			throw new IllegalArgumentException("can't unmarchal");
		}
	}
}
