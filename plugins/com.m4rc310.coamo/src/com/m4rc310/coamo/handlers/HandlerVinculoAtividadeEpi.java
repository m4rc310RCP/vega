 
package com.m4rc310.coamo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.coamo.dialogs.atividades.DialogVinculoAtividadeEpi;
import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerVinculoAtividadeEpi {
	@Execute
	public void execute(PartControl pc) {
		pc.showDialog(DialogVinculoAtividadeEpi.class);
	}
	
		
}