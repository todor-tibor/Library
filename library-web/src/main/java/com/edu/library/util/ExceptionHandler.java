package com.edu.library.util;

import com.edu.library.LibraryException;
import com.edu.library.MessageService;

public class ExceptionHandler {

	public ExceptionHandler(Exception e) {	
		Throwable t = e;

		while ((t != null) && !(t instanceof LibraryException) && !(t instanceof IllegalArgumentException)) {
			t = t.getCause();
		}
		if (t instanceof LibraryException) {
			int level = ((LibraryException) t).getLevel();
			if (level == 1) {
				MessageService.error(PropertyProvider.getProperty(t.getMessage()));
			}
			if (level == 2) {
				MessageService.warn(PropertyProvider.getProperty(t.getMessage()));
			}
		} else if (t instanceof IllegalArgumentException) {
			MessageService.warn(PropertyProvider.getProperty(t.getMessage()));
		} else {
			MessageService.error(PropertyProvider.getProperty(t.getMessage()));
		}
	}
}
