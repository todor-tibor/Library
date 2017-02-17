package com.edu.library.util;

import com.edu.library.LibraryException;
import com.edu.library.MessageService;

public class ExceptionHandler {

	public ExceptionHandler(Exception e) {
		Throwable t=e;
		while (t instanceof LibraryException|| t instanceof IllegalArgumentException) {
			t=t.getCause();
			System.out.println(t.getClass().getSimpleName());
		}
		if (t instanceof LibraryException) {
			int level = ((LibraryException) e).getLevel();
			if (level == 1) {
				MessageService.error(PropertyProvider.INSTANCE.getProperty(e.getMessage()));
			}
			if (level == 2) {
				MessageService.warn(PropertyProvider.INSTANCE.getProperty(e.getMessage()));
			}
		}else if(t instanceof IllegalArgumentException){
			MessageService.warn(e.getMessage());
		}
		else {
			MessageService.error(e.getMessage());
		}
	}
}
