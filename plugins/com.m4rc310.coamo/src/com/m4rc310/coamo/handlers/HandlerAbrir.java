 
package com.m4rc310.coamo.handlers;

import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.m4rc310.coamo.actions.ActionCoamo;
import com.m4rc310.coamo.models.Funcionario;

public class HandlerAbrir {
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel, ActionCoamo action) {
		Object selected = ((IStructuredSelection) sel).getFirstElement();
		action.open(selected);
	}
	
//	@Execute
	public void execute2(@Named(IServiceConstants.ACTIVE_SELECTION) List<?> selections, ActionCoamo action) {
		
		System.out.println(selections);
		
//		Object selected = ((IStructuredSelection) sel).getFirstElement();
//		action.open(selected);
	}
	
	
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		Object selected = ((IStructuredSelection) sel).getFirstElement();
		if (selected instanceof Funcionario) {
			return true;
		}
		return false;
	}
		
}