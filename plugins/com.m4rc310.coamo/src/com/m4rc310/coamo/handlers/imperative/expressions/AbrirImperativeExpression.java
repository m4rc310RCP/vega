 
package com.m4rc310.coamo.handlers.imperative.expressions;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.m4rc310.coamo.models.Funcionario;
import com.m4rc310.coamo.models.Unidade;

public class AbrirImperativeExpression {
	@Evaluate
	public boolean evaluate(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		Object selected = ((IStructuredSelection) sel).getFirstElement();
		if (selected instanceof Funcionario) {
			return true;
		}
		
		return false;
	}
}
