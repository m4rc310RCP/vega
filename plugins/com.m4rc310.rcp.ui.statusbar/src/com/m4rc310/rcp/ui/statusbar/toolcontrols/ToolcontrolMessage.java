package com.m4rc310.rcp.ui.statusbar.toolcontrols;

import javax.annotation.PostConstruct;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.m4rc310.rcp.ui.utils.PartControl;

public class ToolcontrolMessage {
	
	@PostConstruct
	public void createGui(Composite parent_, PartControl pc) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));
		Label labelMessage = pc.getLabel(parent, "Message");
		pc.setGridDataWidthHint(labelMessage, 50);
	}
}