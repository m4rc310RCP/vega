 
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;

public class DynamicRemoverSetorHandler {
	@Execute
	public void execute(MDirectMenuItem item) {
		System.out.println(item.getTags());
	}	
}