package com.edu.library.util;

import javax.faces.application.FacesMessage;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/notify")
public class NotifyResource {

	@OnMessage(encoders = { JSONEncoder.class })
	public FacesMessage onMessage(final FacesMessage message) {
		System.out.println("message:" + message.getDetail());
		return message;
	}

}