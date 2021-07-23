package com.m4rc310.coamo.dialogs.setores;

import javax.inject.Inject;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.dialogs.MDialog;

public class MDialogSetor extends MDialog implements ConstSetor{
	
	@Inject FragmentSetorUnidade fragmentSetorUnidade;
	@Inject FragmentSetorDados fragmentSetorDados;
	@Inject ActionSetor actionSetor;

	@Inject
	public MDialogSetor(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		parent.setLayout(new GridLayout(1, false));
		fragmentSetorUnidade.make(actionSetor, parent);
		fragmentSetorDados.make(actionSetor, parent);
		
		actionSetor.initDialog();
		
		return parent;
	}
	
	@Override
	protected void okPressed() {
		actionSetor.salvar();
		super.okPressed();
	}

}
