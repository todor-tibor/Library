package com.edu.library.util;

import com.edu.library.LibraryException;
import com.edu.library.MessageService;

public class ExceptionHandler {
	
public ExceptionHandler(Exception e){
	if(e instanceof LibraryException){
		int level=((LibraryException) e).getLevel();
		if(level==1){
			MessageService.error(PropertyProvider.getProperty(e.getMessage()));
		}
		if(level==2){
			MessageService.warn(PropertyProvider.getProperty(e.getMessage()));
		}
	}
}
}
