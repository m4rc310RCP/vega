 
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.rcp.mercado.livre.ml.dialogs.DialogAddRisco;

public class DynamicAddRiscoHandler {
	@Execute
	public void execute(IEclipseContext context) {
		DialogAddRisco dialog = ContextInjectionFactory.make(DialogAddRisco.class, context);
		dialog.open();
	}	
}