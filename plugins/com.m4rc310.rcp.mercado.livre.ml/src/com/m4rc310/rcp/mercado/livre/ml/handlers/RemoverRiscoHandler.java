
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.m4rc310.rcp.mercado.livre.ml.addons.actions.CipaAction;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Risco;

public class RemoverRiscoHandler {

	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		Object element = ((StructuredSelection) sel).getFirstElement();
		if (element instanceof Risco) {
//			Risco local = (Risco) element;
//			return local.get
		}

		return true;
	}

	@Execute
	public void execute(
			@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel,
			CipaAction action) {
		Object element = ((StructuredSelection)sel).getFirstElement();
		Risco risco = (Risco) element;
		action.removerRisco(risco);
	}
	

}