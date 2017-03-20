package com.edu.library;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * Publication manager.
 *
 * @author sipost
 * @author kiska
 *
 */
@Named("jasperBean")
@SessionScoped
public class JasperControllerMB implements Serializable {

	private final Logger logger = Logger.getLogger(JasperControllerMB.class);
	private static final long serialVersionUID = -4702598250751689454L;

	@Inject
	IUserService userBean;

	@Inject
	IBorrowService borrowBean;
	@Inject
	ExceptionHandler exceptionHandler;
	@Inject
	MessageService message;
	private JasperPrint jasperPrint;

	/**
	 * Creates a JASPER printer based on the given template and data collection
	 */
	private <T> void initReport(final Collection<T> collection, final String templateName) {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(collection);
		InputStream reportPath = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/WEB-INF/classes/reports/jasper/" + templateName + ".jasper");
		try {
			this.jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap<String, Object>(),
					beanCollectionDataSource);
		} catch (JRException e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	/**
	 * Saves a JASPER report to the file given by {@code filename}.
	 *
	 * @param filename
	 *            - the name of the file to which the report will be saved
	 */
	public void savePdf(final String filename) {
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.reset();
		httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + filename + ".pdf");
		httpServletResponse.setContentType("application/pdf");
		try {
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(this.jasperPrint, servletOutputStream);
		} catch (JRException | IOException e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * Saves the users to the PDF file.
	 */
	public void toPdf() {
		initReport(this.userBean.getAll(), "template");
		savePdf("userReport");
	}

	/**
	 * Saves the borrowings to the PDF file.
	 */
	public void toPdfChart() {
		initReport(this.borrowBean.borrowLateStatistic().entrySet(), "pieChart_template");
		savePdf("borrowReport");
	}
}
