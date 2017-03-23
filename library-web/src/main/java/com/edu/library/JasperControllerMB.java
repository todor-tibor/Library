package com.edu.library;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.edu.library.model.util.JasperReports;
import com.edu.library.model.util.JpaException;
import com.edu.library.util.ExceptionHandler;
import com.edu.library.util.MessageService;

/**
 *
 * Jasper controller for making reports.
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

	/**
	 * Saves the users to the PDF file.
	 */
	public void toPdf() {
		makeReport("template", "userReport", this.userBean.getAll());
	}

	/**
	 * Saves the borrowings to the PDF file.
	 */
	public void toPdfChart() {
		makeReport("pieChart_template", "borrowReport", this.borrowBean.borrowLateStatistic().entrySet());
	}

	/**
	 * Get input stream for template, get output stream for saving the file and
	 * call static function JasperReport savePdf
	 *
	 * @param template
	 *            -name of the template file
	 * @param output
	 *            -name of the output file
	 * @param collection
	 *            - collection of entities for report
	 */
	private <T> void makeReport(final String template, final String output, final Collection<T> collection) {
		InputStream reportFile = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/WEB-INF/classes/reports/jasper/" + template + ".jasper");
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.reset();
		httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + output + ".pdf");
		httpServletResponse.setContentType("application/pdf");
		try {
			JasperReports.savePdf(collection, reportFile, httpServletResponse.getOutputStream());
		} catch (IOException | JpaException e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		} finally {
			FacesContext.getCurrentInstance().responseComplete();
		}
	}
}
