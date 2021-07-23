 
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.m4rc310.rcp.mercado.livre.ml.addons.actions.CipaAction;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.GrupoLocais;

public class RemoverItemHandler {
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel, CipaAction action,IEventBroker broker) {
		Object selected = ((StructuredSelection) sel).getFirstElement();
		if (selected instanceof GrupoLocais	) {
			GrupoLocais grupo = (GrupoLocais) selected;
			action.getStream().addListener(CipaAction.UPDATE_VALUES, e->{
				broker.send("refresh", "Start Refresh Handler's");
			});
			action.removerGrupoLocal(grupo);
		}
	}
	
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		Object element = ((StructuredSelection)sel).getFirstElement();
		if (element instanceof GrupoLocais) {
			GrupoLocais grupo = (GrupoLocais) element;
			return grupo.getLocais().isEmpty();
		}
		return true;
	}
		
}