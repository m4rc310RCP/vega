 
package com.m4rc310.coamo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

import com.m4rc310.coamo.actions.IActionCoamoConsts;

public class HandlerRefresh implements IActionCoamoConsts {
	@Execute
	public void execute(IEventBroker eventBroker ) {
		eventBroker.send(EVENT_TOPIC$update, "Update");
	}
		
}