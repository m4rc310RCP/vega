package com.m4rc310.coamo.assistente.parts;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.m4rc310.coamo.assistente.constants.ConstGlobal;
import com.m4rc310.coamo.assistente.i18n.M;
import com.m4rc310.rcp.ui.utils.PartControl;

public class PartDefault implements ConstGlobal{
	@Inject protected PartControl pc;
	@Inject protected IEventBroker eventBroker;
	@Inject protected MPart part;
	@Inject @Translation M m ;
	
	
	protected Label getLabel(Composite parent, String text) {
		Label label = pc.getLabel(parent, text, SWT.CENTER);
		label.setBackground(pc.getColor(SWT.COLOR_DARK_GRAY));
		label.setForeground(pc.getColor(SWT.COLOR_WHITE));
		pc.fillHorizontalComponent(label);
		return label;
	}

	protected ToolItem getToolItem(ToolBar parent, String pathIcon) {
		ToolItem item = new ToolItem(parent, SWT.PUSH);
		item.setImage(pc.getImage(GLOBAL$plugin_id, pathIcon));
		return item;
	}
	
	protected Group getGroup(Composite parent_, int numColluns) {
		Group parent = pc.getGroup(parent_);
		parent.setLayout(new GridLayout(numColluns, false));
		pc.clearMargins(parent);
		return parent;
	}

	
	protected Composite getComposite(Composite parent_, int numColluns) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(numColluns, false));
		pc.clearMargins(parent);
		return parent;
	}
}
