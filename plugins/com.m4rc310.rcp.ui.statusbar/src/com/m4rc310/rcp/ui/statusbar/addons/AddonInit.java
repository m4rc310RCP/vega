package com.m4rc310.rcp.ui.statusbar.addons;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.m4rc310.rcp.ui.statusbar.IConst;
import com.m4rc310.rcp.ui.statusbar.actions.ActionMessage;

public class AddonInit implements IConst{
	
	@Inject
	IEventBroker eventBroker;
	
	@Inject 
	ActionMessage message;
	
//	@Inject
	void init() {
		eventBroker.subscribe(APP_STARTUP_COMPLETE, event -> {
			message.message(APP_STARTUP_COMPLETE);
		});
	}
}
