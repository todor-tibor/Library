package com.edu.library.model.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;

import org.jboss.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * JasperReports making report using template files
 *
 * @author sipost
 *
 */
public class JasperReports {
	private static final Logger logger = Logger.getLogger(JasperReports.class);

	/**
	 * Save data given by {@code collection} to the {@link OutputStream} using
	 * template {@link InputStream}
	 *
	 * @param collection
	 *            - data to be saved
	 * @param inputFile
	 *            - template file for JASPER
	 * @param outputFile
	 *            - output stream where data will be saved
	 */
	public static <T> void savePdf(final Collection<T> collection, final InputStream inputFile,
			final OutputStream outputFile) {
		try {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(collection);
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputFile, new HashMap<String, Object>(),
					beanCollectionDataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputFile);
		} catch (JRException e) {
			logger.error(e);
			throw new JpaException("something went wrong!!!!");
		}
	}

}
