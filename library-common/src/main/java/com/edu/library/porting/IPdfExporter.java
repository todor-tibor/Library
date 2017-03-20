package com.edu.library.porting;

public interface IPdfExporter {

	/**
	 * Write all users existent in the database to the PDF file.
	 */

	public void writeUsers();

	/**
	 * Write all publications existent in the database to the PDF file.
	 */

	public void writePublications();

	/**
	 * Write all publishers existent in the database to the PDF file.
	 */

	public void writePublishers();

	/**
	 * Write all authors existent in the database to the PDF file.
	 */

	public void writeAuthors();

	/**
	 * Write all roles existent in the database to the PDF file.
	 */

	public void writeRole();

	/**
	 * Write all borrows existent in the database to the PDF file.
	 */
	public void writeBorrows();

	/**
	 * Returns the name of the PDf file to which data was saved.
	 * 
	 * @return - the name of the PDF
	 */
	String getPdfName();

}
