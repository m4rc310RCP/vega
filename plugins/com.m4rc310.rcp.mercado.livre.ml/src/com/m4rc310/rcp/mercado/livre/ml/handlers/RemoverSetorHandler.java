
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Setor;

public class RemoverSetorHandler {

	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		Object element = ((StructuredSelection)sel).getFirstElement();
		if (element instanceof Setor) {
			Setor setor = (Setor) element;
			return setor.getGrupos().isEmpty();
		}
		
		return true;
	}
	
	@Execute
	public void execute(MDirectMenuItem item, IEclipseContext context) {
	}

}