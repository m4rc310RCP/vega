 
package com.m4rc310.coamo.handlers.imperative;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.m4rc310.coamo.models.Unidade;

public class ImperativeSetorExpression {
	@Evaluate
	public boolean evaluate(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		Object selected = ((IStructuredSelection) sel).getFirstElement();
		if (selected instanceof Unidade) {
			return true;
		}
		return false;
	}
}
