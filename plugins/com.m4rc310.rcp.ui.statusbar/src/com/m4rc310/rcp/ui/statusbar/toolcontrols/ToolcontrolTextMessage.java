package com.m4rc310.rcp.ui.statusbar.toolcontrols;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.m4rc310.rcp.ui.statusbar.IConst;
import com.m4rc310.rcp.ui.statusbar.actions.ActionMessage;
import com.m4rc310.rcp.ui.utils.PartControl;

public class ToolcontrolTextMessage implements IConst{
	
	
	
	@Inject
	IEventBroker eventBroker;
	@Inject
	ActionMessage actionMessage;
	@Inject
	UISynchronize sync;
	
	@PostConstruct
	public void createGui(Composite parent_, PartControl pc) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));
		Label labelMessage = pc.getLabel(parent, "...");
		pc.setGridDataWidthHint(labelMessage, 330);
		
		actionMessage.addListener(this, TEXT_MESSAGE_STATUS, e->{
			String message = e.getValue(String.class);
			labelMessage.setText(message);
		});
		
		
		sync.asyncExec(() -> {
			eventBroker.subscribe(APP_STARTUP_COMPLETE, event -> {
				System.out.println("--->");
			});
		});
		
	}
}