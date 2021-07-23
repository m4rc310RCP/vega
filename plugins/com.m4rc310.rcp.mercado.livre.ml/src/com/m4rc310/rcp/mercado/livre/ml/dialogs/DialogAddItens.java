package com.m4rc310.rcp.mercado.livre.ml.dialogs;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.mercado.livre.ml.addons.actions.CipaAction;
import com.m4rc310.rcp.mercado.livre.ml.i18n.Messages;
import com.m4rc310.rcp.ui.utils.PartControl;

public abstract class DialogAddItens extends Dialog {
	
	@Inject @Translation 
	protected Messages m;
	protected Button saveButton;
	protected Button cancelButton;
	
	@Inject
	PartControl pc;

	@Inject
	@Named(IServiceConstants.ACTIVE_SELECTION)
	ISelection sel;

	@Inject
	MGraphQL graphql;

	@Inject
	UISynchronize sync;
	
	@Inject Shell shell;
	
	
	@Inject CipaAction cipaAction;

	protected DialogAddItens(Shell parentShell) {
		super(parentShell);
	}
	
	
	@Override
    protected void createButtonsForButtonBar(Composite parent) {
		this.cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, m.textCancel, false);
		this.saveButton = createButton(parent, IDialogConstants.OK_ID, m.textOk, false);
		saveButton.setEnabled(false);
        saveButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(this::save));
        cancelButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(this::cancel));
    }
	
	public abstract void save(SelectionEvent event);
	public abstract void cancel(SelectionEvent event);

}
