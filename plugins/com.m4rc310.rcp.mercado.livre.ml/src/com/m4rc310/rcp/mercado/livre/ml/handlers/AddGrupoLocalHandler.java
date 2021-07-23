
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;

import com.m4rc310.rcp.mercado.livre.ml.dialogs.DialogAddGrupoLocal;

public class AddGrupoLocalHandler {

	@Execute
	public void execute(IEclipseContext context) {
		DialogAddGrupoLocal dialog = ContextInjectionFactory.make(DialogAddGrupoLocal.class, context);
		dialog.open();
	}
	public void execute(MDirectMenuItem item, IEclipseContext context) {
	}

}