 
package com.m4rc310.coamo.assistente.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;

import com.m4rc310.coamo.assistente.actions.ActionSetor;
import com.m4rc310.coamo.assistente.constants.ConstFuncionario;

public class HandlerShowFuncionarios implements ConstFuncionario{
	
	@Inject ActionSetor action;
	
	private MItem toolItem;
	
	
	public void execute(MItem toolItem) {
//		action.showFuncionarioPart(toolItem.isSelected());
	}
	
	@Inject
	public void init() {
//		action.verify();
	}
	
	@Inject @Optional
	public void selectItem(@EventTopic("selectShowFuncionarios") boolean select) {
		if(toolItem!=null) {
			toolItem.setSelected(select);
		}
	}
	
	
	@CanExecute
	public boolean canExecute(MItem toolItem) {
		this.toolItem = toolItem;
		return true;
	}
		
}