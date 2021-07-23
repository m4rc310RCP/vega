 
package com.m4rc310.coamo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.coamo.dialogs.grupos.DialogGrupo;
import com.m4rc310.rcp.ui.utils.PartControl;

import org.eclipse.e4.core.di.annotations.CanExecute;

public class HandlerGrupo {
	@Execute
	public void execute(PartControl pc) {
		pc.showDialog(DialogGrupo.class);
	}
	
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	}
		
}