package com.edu.library;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("fileContentManager")
@ManagedBean
@SessionScoped
public class FileContentManager implements Serializable {

	private static final long serialVersionUID = 931116985451213948L;
	private UploadedFile file;
	private String text;

	public UploadedFile getFile() {
		System.out.println("inside get   " + this.file.getFileName());
		return this.file;
	}

	public void setFile(final UploadedFile file) {
		System.out.println("inside set   " + this.file.getFileName());
		this.file = file;
		System.out.println("FFFIIIIIILLLLEEEEE   " + this.file.getFileName());
		if (this.file != null) {
			System.out.println("/*/*/*/*/       " + this.file.getFileName());

			this.text = new String(this.file.getContents(), StandardCharsets.UTF_8);
			System.out.println("*-*-*-*-*-*-*-*- +                 " + this.text);

			final FacesMessage message = new FacesMessage("Succesful", this.file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void handleFileContent(final FileUploadEvent event) {
		this.file = event.getFile();
	}

	public String content() {
		byte[] temp = null;
		if (this.file != null) {

			System.err.println("/*/*/*/*/       " + this.file.getFileName());
			try {
				temp = IOUtils.toByteArray(this.file.getInputstream());
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.text = new String(temp, StandardCharsets.UTF_8);
			System.err.println("*-*-*-*-*-*-*-*- +                 " + this.text);

			final FacesMessage message = new FacesMessage("Succesful", this.file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return this.text;
	}

	public String getText() {
		return this.text;
	}

}
