 
package com.m4rc310.coamo.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.coamo.dialogs.messages.MDialogMessage;
import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerTest {
	@Execute
	public void execute(PartControl pc) {
		pc.showDialog(MDialogMessage.class);
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return true;
	}
		
}