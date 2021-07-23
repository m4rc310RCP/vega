 
package com.m4rc310.financas.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.CanExecute;

public class HandlerSaveAll {
	@Execute
	public void execute() {
		
	}
	
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	}
		
}