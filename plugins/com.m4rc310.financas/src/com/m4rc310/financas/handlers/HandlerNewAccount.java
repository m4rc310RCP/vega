 
package com.m4rc310.financas.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.rcp.ui.utils.PartControl;

import org.eclipse.e4.core.di.annotations.CanExecute;

public class HandlerNewAccount {
	@Execute
	public void execute(PartControl pc) {
		pc.show("com.m4rc310.financas.partdescriptor.partAccount");
	}
	
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	}
		
}