
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.m4rc310.rcp.mercado.livre.ml.addons.actions.CipaAction;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;

public class RemoverLocalHandler {

	@Inject
	@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel;
	
	@Inject CipaAction action;
	
	@CanExecute
	public boolean canExecute() {
		Object element = ((StructuredSelection) sel).getFirstElement();
		if (element instanceof Local) {
			Local local = (Local) element;
			return local.getRiscos().isEmpty();
		}

		return true;
	}

	@Execute
	public void execute() {
		Object element = ((StructuredSelection)sel).getFirstElement();
		Local local = (Local) element;
		action.removerLocal(local);
	}
	

}