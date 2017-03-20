package com.edu.library.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang.StringEscapeUtils;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@ManagedBean(name = "notifyView")
@RequestScoped
public class NotifyView {

	private final static String CHANNEL = "/notify";

	private String summary;

	private String detail;

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(final String detail) {
		this.detail = detail;
	}

	public void send() {
		System.out.println("send notification");
		System.out.println(this.summary + "       ++++" + this.detail);
		EventBus eventBus = EventBusFactory.getDefault().eventBus();
		eventBus.publish(CHANNEL, new FacesMessage(StringEscapeUtils.escapeHtml(this.summary),
				StringEscapeUtils.escapeHtml(this.detail)));
	}
}