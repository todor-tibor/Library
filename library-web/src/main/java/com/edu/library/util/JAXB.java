package com.edu.library.util;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.jboss.logging.Logger;

import com.edu.library.model.BaseEntity;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.util.EntityList;
import com.edu.library.model.util.JaxbException;

/**
 * Marshall and unmarshall entities and list of entities
 *
 * @author sipost
 *
 */
@SessionScoped
public class JAXB implements Serializable {

	private static final long serialVersionUID = 6848520504203333083L;
	private static final Logger logger = Logger.getLogger(JAXB.class);

	private JAXB() {

	}

	/**
	 * Unmarshal XML to EntityList and return List value.
	 *
	 * @return List of entities
	 */
	public static <T extends BaseEntity> List<T> unmarshallList(final Class<T> type, final String filename) {
		try {
			JAXBContext context = JAXBContext.newInstance(EntityList.class, type, Book.class, Magazine.class,
					Newspaper.class);

			Unmarshaller unmarshaller = context.createUnmarshaller();
			StreamSource xml = new StreamSource("C:\\" + filename + ".xml");
			@SuppressWarnings("unchecked")
			EntityList<T> wrapper = unmarshaller.unmarshal(xml, EntityList.class).getValue();

			return wrapper.getEntities();
		} catch (JAXBException e) {
			logger.error(e);
			throw new JaxbException("import.fail");
		} finally {
			FacesContext.getCurrentInstance().responseComplete();
		}
	}

	/**
	 * Unmarshal XML to EntityList and return List value.
	 *
	 * @return List of entities
	 */
	public static <T extends BaseEntity> List<T> unmarshallList(final Class<T> type, final InputStream file) {
		try {
			JAXBContext context = JAXBContext.newInstance(EntityList.class, type, Book.class, Magazine.class,
					Newspaper.class);

			Unmarshaller unmarshaller = context.createUnmarshaller();
			StreamSource xml = new StreamSource(file);
			@SuppressWarnings("unchecked")
			EntityList<T> wrapper = unmarshaller.unmarshal(xml, EntityList.class).getValue();
			return wrapper.getEntities();
		} catch (JAXBException e) {
			logger.error(e);
			throw new JaxbException("import.fail");
		}
	}

	/**
	 * Wrap List in EntityList, then leverage JAXBElement to supply root element
	 * information.
	 *
	 * @param entities
	 *            - List entities
	 * @param type
	 *            - Class of entities
	 */
	public static <T extends BaseEntity> void marshall(final List<T> entities, final Class<T> type,
			final String filename) {
		try {
			JAXBContext context = JAXBContext.newInstance(EntityList.class, type, Book.class, Magazine.class,
					Newspaper.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			QName qName = new QName(type.getSimpleName() + "List");
			EntityList<T> wrapper = new EntityList<T>(entities);
			@SuppressWarnings("rawtypes")
			JAXBElement<EntityList> jaxbElement = new JAXBElement<EntityList>(qName, EntityList.class, wrapper);
			marshaller.marshal(jaxbElement, new File("C:\\" + filename + ".xml"));
			/*
			 * Get external context and show file as download
			 */
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
					.getExternalContext().getResponse();
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + filename + ".xml");
			httpServletResponse.setContentType("text/xml");
			marshaller.marshal(jaxbElement, httpServletResponse.getOutputStream());
		} catch (Exception e) {
			logger.error(e);
			throw new JaxbException("export.fail");
		} finally {
			FacesContext.getCurrentInstance().responseComplete();
		}
	}
}
