 
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

public class RefreshHandler {
	@Execute
	public void execute(IEventBroker broker) {
		broker.send("refresh", "Start Refresh Handler's");
	}
		
}