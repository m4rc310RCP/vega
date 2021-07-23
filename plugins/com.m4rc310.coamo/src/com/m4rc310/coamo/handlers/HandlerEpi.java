 
package com.m4rc310.coamo.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.m4rc310.coamo.dialogs.epis.MDialogEpi;
import com.m4rc310.coamo.models.Funcionario;
import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerEpi {
	@Execute
	public void execute(PartControl pc) {
		pc.showDialog(MDialogEpi.class);
	}
	
//	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		Object selected = ((IStructuredSelection) sel).getFirstElement();
		if (selected instanceof Funcionario) {
			return true;
		}
		return false;
	}
		
}