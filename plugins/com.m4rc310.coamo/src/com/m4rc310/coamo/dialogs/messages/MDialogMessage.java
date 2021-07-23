package com.m4rc310.coamo.dialogs.messages;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.dialogs.MDialog;

public class MDialogMessage extends MDialog {
	
	@Inject Display display;

	@Inject
	public MDialogMessage(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {

		Label icon = pc.getLabel(parent, "Label de Testes");
		icon.setImage(display.getSystemImage(SWT.ICON_INFORMATION));
		
		icon = pc.getLabel(parent, "Label de Testes");
		icon.setImage(display.getSystemImage(SWT.ICON_QUESTION));
		
		icon = pc.getLabel(parent, "Label de Testes");
		icon.setImage(display.getSystemImage(SWT.ICON_SEARCH));
		
		icon = pc.getLabel(parent, "Label de Testes");
		icon.setImage(display.getSystemImage(SWT.ICON_ERROR));
		
		return super.createDialogArea(parent);
	}
	

}
