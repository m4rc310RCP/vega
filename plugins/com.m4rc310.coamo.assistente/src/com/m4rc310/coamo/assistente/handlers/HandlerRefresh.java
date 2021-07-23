 
package com.m4rc310.coamo.assistente.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

import com.m4rc310.coamo.assistente.constants.ConstGlobal;

public class HandlerRefresh implements ConstGlobal{
	@Execute
	public void execute(IEventBroker eventBroker) {
		eventBroker.send(GLOBAL$refresh, "Refreshing");
	}
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	}
		
}