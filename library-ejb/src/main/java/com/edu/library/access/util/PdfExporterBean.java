package com.edu.library.access.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.edu.library.business.exception.BusinessException;
import com.edu.library.business.publicationManagement.AuthorManagementBusiness;
import com.edu.library.business.publicationManagement.BorrowManagementBusiness;
import com.edu.library.business.publicationManagement.PublicationManagementBusiness;
import com.edu.library.business.publicationManagement.PublisherManagementBusiness;
import com.edu.library.business.userManagement.RoleManagementBusiness;
import com.edu.library.business.userManagement.UserManagementBusiness;
import com.edu.library.model.Author;
import com.edu.library.model.Borrow;
import com.edu.library.model.Publication;
import com.edu.library.model.Publisher;
import com.edu.library.model.Role;
import com.edu.library.model.UnifiedModel;
import com.edu.library.model.User;
import com.edu.library.porting.IPdfExporter;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

/**
 * Implements a universal data exporter to PDF format, using the UnifiedModel as
 * the object to export.
 *
 * @author kiska
 *
 */
@Stateless
public class PdfExporterBean implements IPdfExporter, Serializable {
	private static final long serialVersionUID = -1976534651096934980L;
	@EJB
	PublicationManagementBusiness publicationBean;
	@EJB
	UserManagementBusiness userBean;
	@EJB
	BorrowManagementBusiness borrowBean;
	@EJB
	PublisherManagementBusiness publisherBean;
	@EJB
	AuthorManagementBusiness authorBean;
	@EJB
	RoleManagementBusiness roleBean;

	private String pdfName;

	final DataExctractor de = new DataExctractor();

	private final Logger logger = Logger.getLogger(PdfExporterBean.class);

	private final Map<String, Integer> pageByTitle = new HashMap<>();

	/**
	 * Inner class that implements page numbering
	 *
	 * @author kiska
	 *
	 */
	class PageNumberFooter extends PdfPageEventHelper {
		Font ffont = new Font(Font.FontFamily.UNDEFINED, 10, Font.ITALIC);

		@Override
		public void onEndPage(final PdfWriter writer, final Document document) {
			final PdfContentByte cb = writer.getDirectContent();
			final Phrase footer = new Phrase(Integer.toString(writer.getPageNumber()), this.ffont);

			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
		}

		@Override
		public void onChapter(final PdfWriter writer, final Document document, final float paragraphPosition,
				final Paragraph title) {
			PdfExporterBean.this.pageByTitle.put(title.getContent(), writer.getPageNumber());
		}
	}

	private java.util.List<Publication> getPublications() {
		return this.publicationBean.getAll();
	}

	private java.util.List<User> getUsers() {
		return this.userBean.getAll();
	}

	private java.util.List<Borrow> getBorrowings() {
		return this.borrowBean.getAll();
	}

	private java.util.List<Publisher> getPublishers() {
		return this.publisherBean.getAll();
	}

	private java.util.List<Author> getAuthors() {
		return this.authorBean.getAll();
	}

	private java.util.List<Role> getRoles() {
		return this.roleBean.getAll();
	}

	@Override
	public void writeUsers() {
		this.pdfName = "msgLibrary_user";
		writeToPdf(this.de.userExtractor(getUsers()), this.pdfName);
	}

	@Override
	public void writePublications() {
		this.pdfName = "msgLibrary_publication";
		writeToPdf(this.de.publicationExtractor(getPublications()), this.pdfName);
	}

	@Override
	public void writePublishers() {
		this.pdfName = "msgLibrary_publisher";
		writeToPdf(this.de.publisherExtractor(getPublishers()), this.pdfName);
	}

	@Override
	public void writeAuthors() {
		this.pdfName = "msgLibrary_author";
		writeToPdf(this.de.authorExtractor(getAuthors()), this.pdfName);
	}

	@Override
	public void writeRole() {
		this.pdfName = "msgLibrary_role";
		writeToPdf(this.de.roleExtractor(getRoles()), this.pdfName);
	}

	@Override
	public void writeBorrows() {
		this.pdfName = "msgLibrary_borrow";
		writeToPdf(this.de.borrowExtractor(getBorrowings()), this.pdfName);
	}

	/**
	 * Writes the list given by {@code listOfConvertedEntities} to a PDF with
	 * the filename given by {@code filename}.
	 *
	 * @param listOfConvertedEntities
	 *            - a list of UnifiedModel objects (it can be user, publication,
	 *            etc.)
	 * @param filename
	 *            - the filename which the PDF will have
	 */
	public void writeToPdf(final java.util.List<UnifiedModel> listOfConvertedEntities, final String filename) {
		final String coverText = ".msg library";
		final Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			final PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("../welcome-content/" + filename + ".pdf"));

			writer.setPageEvent(new PageNumberFooter());

			document.open();
			document.setPageCount(0);
			/** Create paragraph */
			createParagraph(document, coverText, listOfConvertedEntities.get(0).getDescription());
			/** End paragraph */

			Chapter chapter1 = null;
			Section section1 = null;
			for (final UnifiedModel unifiedModel : listOfConvertedEntities) {

				/** Create chapter */
				chapter1 = createChapter(unifiedModel.getDescriptor());
				/** End chapter */
				document.add(Chunk.NEWLINE);
				/** Section object */
				section1 = createSection(chapter1,
						unifiedModel.getDetails().toString().replace("{", "").replace("}", "").replace("=", ":"));
				/** End section */

				/** Table object */
				document.add(Chunk.NEWLINE);

				createTable(section1, unifiedModel.getAdditionalDetails());
				document.add(Chunk.NEWLINE);
				/** End table */

				document.add(chapter1);
			}
		} catch (final DocumentException | FileNotFoundException e) {
			this.logger.error(e);
			throw new BusinessException(e.getMessage());
		}

		/** Main document anchor */
		document.newPage();

		tableOfContents(document);
		document.newPage();
		addMainAnchor(document);
		document.close();
		/** End anchor */

		this.logger.info("PDF exported successfuly.");

	}

	/**
	 * Creates the table of content for the specified document.
	 *
	 * @param document
	 *            - the document for which the table of contents will be created
	 */
	private void tableOfContents(final Document document) {
		final Paragraph title = new Paragraph("Table of contents",
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
		try {
			document.add(title);
		} catch (final DocumentException e) {
			this.logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		final Paragraph paragraph = new Paragraph();

		this.pageByTitle.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue()).forEach(entry -> {

			final Chunk glue = new Chunk(new VerticalPositionMark());
			final Anchor anchor2 = new Anchor(entry.getKey());

			anchor2.setReference("#" + entry.getKey());
			paragraph.add(anchor2);
			paragraph.add(new Chunk(glue));
			paragraph.add(Integer.toString(entry.getValue()));

			paragraph.add(Chunk.NEWLINE);
		});
		this.pageByTitle.clear();
		try {
			document.add(paragraph);
		} catch (final DocumentException e) {
			this.logger.error(e);
			throw new BusinessException(e.getMessage());
		}

	}

	/**
	 * Adds the main, back to the top reference to the PDF's ending.
	 *
	 * @param document
	 */
	private void addMainAnchor(final Document document) {
		final Paragraph title2 = new Paragraph("References",
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 0, 0)));

		try {
			document.add(title2);
		} catch (final DocumentException e) {
			this.logger.error(e);
			throw new BusinessException(e.getMessage());
		}

		title2.setSpacingBefore(5000);

		final Anchor anchor2 = new Anchor("Back To Top");

		anchor2.setReference("#BackToTop");

		try {
			document.add(anchor2);
		} catch (final DocumentException e) {
			this.logger.error(e);
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * Creates a table with one column and {n} rows, where {n} is the number of
	 * elements in the list given by {@code list} that will be written to the
	 * table
	 *
	 * @param section
	 *            - the document section where the table will be created
	 * @param list
	 *            - a list containing the elements that will be written to the
	 *            table
	 */
	private void createTable(final Section section, final java.util.List<String> list) {
		/** Table object */
		final PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setSpacingBefore(5);
		table.setSpacingAfter(15);

		for (final String string : list) {
			final PdfPCell c1 = new PdfPCell(new Phrase(string.replace("[", "").replace("]", "").replace("=", ":")));
			c1.setFixedHeight(25f);
			table.addCell(c1);
		}
		section.add(table);
		/** End table */
	}

	/**
	 * Creates a new section with a paragraph and sets the reference for it.
	 *
	 * @param chapter
	 *            - the chapter in which the section will be created
	 * @param paragraphText
	 *            - the text that will be written to the paragraph
	 * @return - the created section
	 */
	private Section createSection(final Chapter chapter, final String paragraphText) {
		final Anchor anchorTarget = new Anchor(paragraphText);
		anchorTarget.setName(paragraphText);
		/** Section object */

		final Paragraph title11 = new Paragraph(anchorTarget.getName(),
				FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));

		final Section section = chapter.addSection(title11);
		section.setNumberDepth(0);
		section.setChapterNumber(0);
		/** End section */
		return section;
	}

	/**
	 * Creates a chapter with title provided by {@code chapterHeader}
	 *
	 * @param chapterHeader
	 *            - the title of the chapter
	 * @return - the created chapter
	 */
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

	/**
	 * Creates the paragraph of the first PDF's first page. Sets a reference to
	 * the start of the PDF.
	 *
	 * @param document
	 *            - the document to which the paragraph will be added
	 * @param firstPageText
	 *            - the text where the reference points to
	 * @param paragraphText
	 *            - the text to be shown after the {@code firstPageText}
	 */
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
			this.pdfName = null;
			this.logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		/** End paragraph */
	}

	@Override
	public String getPdfName() {
		return this.pdfName;
	}
}
