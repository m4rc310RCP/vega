 
package com.m4rc310.financas.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

public class HandlerUpdate {
	@Execute
	public void execute() {
	}
	
	
	@CanExecute
	public boolean canExecute() {
		
		return false;
	}
		
}