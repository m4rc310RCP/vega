package com.m4rc310.financas.dialogs;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.m4rc310.financas.i18n.M;
import com.m4rc310.rcp.ui.utils.PartControl;

public class DialogDefault extends Dialog {
	@Inject protected PartControl pc;
	@Inject protected UISynchronize sync;
	@Inject @Translation M m;
	@Inject IStylingEngine engine;
	
	private final String PLUGIN_ID = "com.m4rc310.financas";

	protected final int REMOVE_ID = "remove_id".hashCode();
	protected final int OK_ID = IDialogConstants.OK_ID;
	protected final int CANCEL_ID = IDialogConstants.CANCEL_ID;
	
	@Inject
	public DialogDefault(Shell parentShell) {
		super(parentShell);
		init();
	}
	
	protected void init() {
//		getShell().addListener(SWT.Show, e->{
//			initDilog();
//		});
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
	
	protected void initDilog() {}
	
	
	@Override
	protected Control createButtonBar(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(4, false));
		
//		pc.fillHorizontalComponent(pc.getLabel(parent, "", SWT.HORIZONTAL|SWT.SEPARATOR)).horizontalSpan= 4;

		createButton(parent, REMOVE_ID, "Remover", false);

		Label label = pc.getLabel(parent, "");
		pc.fillHorizontalComponent(parent);
		pc.fillHorizontalComponent(label);

		createButton(parent, CANCEL_ID, "Cancelar", false);
		createButton(parent, OK_ID, "Efetivar", true);

		pc.clearMargins(parent);

		return parent;
	}
	
	protected Label getLabel(Composite parent, String text) {
		Label label = pc.getLabel(parent, text, SWT.CENTER);
		label.setBackground(pc.getColor(SWT.COLOR_DARK_GRAY));
		label.setForeground(pc.getColor(SWT.COLOR_WHITE));
		pc.fillHorizontalComponent(label);
		return label;
	}

	protected ToolItem getToolItem(ToolBar parent, String pathIcon) {
		ToolItem item = new ToolItem(parent, SWT.PUSH);
		item.setImage(pc.getImage(PLUGIN_ID, pathIcon));
		return item;
	}

}
