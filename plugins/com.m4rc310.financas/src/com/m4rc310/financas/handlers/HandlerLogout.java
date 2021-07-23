 
package com.m4rc310.financas.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.di.annotations.CanExecute;

public class HandlerLogout {
	@Execute
	public void execute(IEventBroker event) {
		event.send("login", true);
	}
	
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	}
		
}