package com.m4rc310.coamo.dialogs.grupos;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.actions.ActionGrupo;
import com.m4rc310.rcp.ui.utils.PartControl;

public class DialogGrupo extends Dialog {
	
	@Inject ActionGrupo action;
	@Inject PartControl pc;

	@Inject
	public DialogGrupo(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("Cadastro de Grupos de Serviço ou Certificações");
		
		return parent;
	}
}
