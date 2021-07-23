 
package com.m4rc310.coamo.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;

import com.m4rc310.coamo.dialogs.setores.MDialogSetor;
import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerSetor {
	@Execute
	public void execute(PartControl pc) {
		pc.showDialog(MDialogSetor.class);
	}
	
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		
		return true;
	}
		
}