package com.edu.library.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.edu.library.PublicationMB;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Named("pdfExporterBean")
@SessionScoped
public class PdfExporter implements Serializable {
	@Inject
	PublicationMB publicationMB;
	/**
	 *
	 */
	private static final long serialVersionUID = -1976534651096934980L;
	private static boolean written = false;

	public java.util.List<Publication> getPublications() {
		return this.publicationMB.getAll();
	}

	public void writeToPdf() {
		System.out.println("******   pdf exporter started");

		final java.util.List<Publication> pubs = getPublications();

		final Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			PdfWriter.getInstance(document, new FileOutputStream("../welcome-content/library.pdf"));

			document.open();
			/** Create paragraph */
			createParagraph(document, "Publications", "Lists all publications.");
			/** End paragraph */
			Chapter chapter1 = null;
			Section section1 = null;
			for (final Publication publication : pubs) {

				/** Create chapter */
				chapter1 = createChapter(publication.getTitle());
				/** End chapter */

				/** Section object */
				final String publicationInfo = "Nr of copies: " + Integer.toString(publication.getNrOfCopys())
						+ ", on stock are: " + Integer.toString(publication.getOnStock()) + ", published on: "
						+ publication.getPublicationDate().toString() + ", by: " + publication.getPublisher().getName();
				section1 = createSection(chapter1, publicationInfo);
				/** End section */

				/** Table object */
				createTable(section1, publication.getBorrows());
				/** End table */

				/** List object */
				addList(section1);
				/** End list */

				/** Adding image */
				// addImage(section1);
				/** End image */

				document.add(chapter1);

			}
			/** Main document anchor */
			addMainAnchor(section1);
			/** End anchor */

			System.out.println("******   pdf should b written");

			written = true;

			document.close();

		} catch (final DocumentException e) {
			e.printStackTrace();
		} catch (final FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	private void addMainAnchor(final Section section1) {
		final Paragraph title2 = new Paragraph("Using Anchor",

				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,

						new CMYKColor(0, 255, 0, 0)));

		section1.add(title2);

		title2.setSpacingBefore(5000);

		final Anchor anchor2 = new Anchor("Back To Top");

		anchor2.setReference("#BackToTop");

		section1.add(anchor2);
	}

	private void addList(final Section section) {
		/** List object */
		final List l = new List(true, false, 10);
		l.add(new ListItem("First item of list"));
		l.add(new ListItem("Second item of list"));
		section.add(l);
		/** End list */
	}

	private void createTable(final Section section, final java.util.List<Borrow> list) {
		/** Table object */
		final PdfPTable t = new PdfPTable(3);
		t.setSpacingBefore(5);
		t.setSpacingAfter(15);
		for (final Borrow borrow : list) {
			final PdfPCell c1 = new PdfPCell(new Phrase("Borrowed by:"));
			t.addCell(c1);
			final PdfPCell c2 = new PdfPCell(new Phrase("from"));
			t.addCell(c2);
			final PdfPCell c3 = new PdfPCell(new Phrase("until"));
			t.addCell(c3);
			t.addCell(borrow.getUser().getUserName());
			t.addCell(borrow.getBorrowFrom().toString());
			t.addCell(borrow.getBorrowUntil().toString());
		}
		section.add(t);
		/** End table */
	}

	private Section createSection(final Chapter chapter, final String paragraphText) {
		/** Section object */
		final Paragraph title11 = new Paragraph(paragraphText,
				FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));

		final Section section1 = chapter.addSection(title11);
		section1.setNumberDepth(0);
		section1.setChapterNumber(0);
		final Paragraph someSectionText = new Paragraph("Details:");
		section1.add(someSectionText);

		/** End section */
		return section1;
	}

	private Chapter createChapter(final String chapterHeader) {
		/** Create chapter */
		final Paragraph title1 = new Paragraph(chapterHeader,
				FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new CMYKColor(0, 255, 255, 17)));

		final Chapter chapter1 = new Chapter(title1, 1);
		chapter1.setChapterNumber(0);
		chapter1.setNumberDepth(0);

		/** End chapter */
		return chapter1;
	}

	private void createParagraph(final Document document, final String firstPageText, final String paragraphText) {
		/** Create paragraph */
		final Anchor anchorTarget = new Anchor(firstPageText);
		anchorTarget.setName("BackToTop");
		final Paragraph paragraph1 = new Paragraph();

		paragraph1.setSpacingBefore(50);

		paragraph1.add(anchorTarget);
		try {
			document.add(paragraph1);
			document.add(new Paragraph(paragraphText,
					FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 255, 0, 0))));
		} catch (final DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** End paragraph */

	}

	public boolean isWritten() {
		return written;
	}
}
