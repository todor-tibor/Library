package com.edu.library.model.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.edu.library.model.Author;
import com.edu.library.model.Book;
import com.edu.library.model.Magazine;
import com.edu.library.model.Newspaper;
import com.edu.library.model.Publication;

/**
 * Class that implements the export of all the publications to an ".xml" file.
 *
 * @author kiska
 *
 */
public class WriteXMLFile {

	public WriteXMLFile() {
	}

	/**
	 * Writes the publications given by {@code publications} to the file given
	 * in {@code filename}.
	 *
	 * @param publications
	 *            - a list containing all the publications
	 * @param filename
	 *            - the name of the file to which the data will be written
	 *            Throws exception if it can't write to the ".xml" file.
	 */
	public static void exportData(final List<Publication> publications, final String filename) {

		try {
			final Element allPublications = new Element("allPublications");
			final Document doc = new Document();
			doc.setRootElement(allPublications);
			Element publicationElement;
			String publicationType = "";
			for (final Publication publication : publications) {
				if (publication instanceof Book) {
					publicationType = "Book";
				} else if (publication instanceof Magazine) {
					publicationType = "Magazine";
				} else if (publication instanceof Newspaper) {
					publicationType = "Newspaper";
				}
				publicationElement = new Element(publicationType);
				publicationElement.setAttribute(new Attribute("uuid", publication.getUuid()));
				publicationElement.addContent(new Element("title").setText(publication.getTitle()));
				publicationElement
						.addContent(new Element("nrOfCopies").setText(Integer.toString(publication.getNrOfCopys())));
				publicationElement
						.addContent(new Element("onStock").setText(Integer.toString(publication.getOnStock())));

				final Element publisher = new Element("publisher");
				publisher.setAttribute(new Attribute("uuid", publication.getPublisher().getUuid()));
				publisher.addContent(new Element("name").setText(publication.getPublisher().getName()));
				publicationElement.addContent(publisher);

				publicationElement.addContent(
						new Element("publicationDate").setText(publication.getPublicationDate().toString()));
				if (publication instanceof Book) {
					for (final Author au : ((Book) publication).getAuthors()) {
						publicationElement.addContent(addAuthorParam(au));
					}

				} else if (publication instanceof Magazine) {
					for (final Author au : ((Magazine) publication).getAuthors()) {
						publicationElement.addContent(addAuthorParam(au));
					}
				}
				doc.getRootElement().addContent(publicationElement);
			}
			final XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("C:\\" + filename + ".xml"));
			System.out.println("File Saved!");
		} catch (final IOException io) {
			io.printStackTrace();
			throw new RuntimeException("Cannot save to xml!");
		}

	}

	/**
	 * Sets the author attribute given by {@code au} to an XML type element.
	 *
	 * @param au
	 *            - the author to write to the XML type element
	 * @return - the XML element that contains the information of an Author
	 *         object given by {@code au}
	 */
	private static Element addAuthorParam(final Author au) {
		final Element author = new Element("author");
		author.setAttribute(new Attribute("uuid", au.getUuid()));
		author.addContent(new Element("name").setText(au.getName()));
		return author;
	}

}
