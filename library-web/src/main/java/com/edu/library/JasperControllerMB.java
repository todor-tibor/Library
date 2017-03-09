package com.edu.library;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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
	ExceptionHandler exceptionHandler;
	@Inject
	MessageService message;
	private JasperPrint jasperPrint;

	@SuppressWarnings("rawtypes")
	private void initReport() {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.userBean.getAll());
		InputStream reportPath = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/WEB-INF/classes/reports/jasper/template.jasper");
		try {
			this.jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
		} catch (JRException e) {
			this.logger.error(e);
			this.exceptionHandler.showMessage(e);
		}
	}

	public void toPdf() {
		initReport();

		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.reset();
		httpServletResponse.setHeader("Content-disposition", "attachment; filename=report.pdf");
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
}
