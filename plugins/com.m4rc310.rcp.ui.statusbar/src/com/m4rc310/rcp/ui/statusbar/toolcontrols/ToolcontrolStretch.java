package com.m4rc310.rcp.ui.statusbar.toolcontrols;


import javax.annotation.PostConstruct;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import com.m4rc310.rcp.ui.utils.PartControl;

public class ToolcontrolStretch {
	
	@PostConstruct
	public void createGui(Composite parent_, PartControl  pc) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout());

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 22;
		
		pc.getLabel(parent, "").setLayoutData(gd);
	}
}