 
package com.m4rc310.financas.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Display;

import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerShowPDV {
	@Execute
	public void execute(PartControl pc) {
		pc.switchPerspective("com.m4rc310.financas.perspective.pdv");
		Display.getCurrent().beep();
	}
	
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	}
		
}