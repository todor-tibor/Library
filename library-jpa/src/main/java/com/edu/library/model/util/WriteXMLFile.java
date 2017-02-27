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
import com.edu.library.model.Publication;

public class WriteXMLFile {

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
				} else {
					publicationType = "Newspaper";
				}
				publicationElement = new Element(publicationType);
				publicationElement.setAttribute(new Attribute("uuid", publication.getUuid()));
				publicationElement.addContent(new Element("title").setText(publication.getTitle()));
				publicationElement
						.addContent(new Element("nrOfCopies").setText(Integer.toString(publication.getNrOfCopys())));
				publicationElement
						.addContent(new Element("onStock").setText(Integer.toString(publication.getOnStock())));
				publicationElement.addContent(new Element("publisher").setText(publication.getPublisher().getName()));

				if (publication instanceof Book) {

					for (final Author au : ((Book) publication).getAuthors()) {
						final Element author = new Element("author");
						author.setAttribute(new Attribute("uuid", au.getUuid()));
						author.addContent(new Element("name").setText(au.getName()));
						publicationElement.addContent(author);
					}

				} else if (publication instanceof Magazine) {

					for (final Author au : ((Magazine) publication).getAuthors()) {
						final Element author = new Element("author");
						author.setAttribute(new Attribute("uuid", au.getUuid()));
						author.addContent(new Element("name").setText(au.getName()));
						publicationElement.addContent(author);
					}
				}
				doc.getRootElement().addContent(publicationElement);
			}
			final XMLOutputter xmlOutput = new XMLOutputter();

			xmlOutput.setFormat(Format.getPrettyFormat());
			System.out.println("C:\\" + filename + ".xml");
			xmlOutput.output(doc, new FileWriter("C:\\" + filename + ".xml"));

			System.out.println("File Saved!");
		} catch (final IOException io) {
			io.printStackTrace();
			throw new RuntimeException("Cannot save to xml");
		}

	}

}
