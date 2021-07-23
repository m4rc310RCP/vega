 
package com.m4rc310.coamo.assistente.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.coamo.assistente.dialogs.DialogFuncionario;
import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerNewFuncionario {
	
	@Execute
	public void execute(PartControl pc) {
		pc.showDialog(DialogFuncionario.class);
	}
		
}