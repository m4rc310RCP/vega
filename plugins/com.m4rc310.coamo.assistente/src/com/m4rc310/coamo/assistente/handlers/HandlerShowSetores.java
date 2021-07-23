 
package com.m4rc310.coamo.assistente.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.coamo.assistente.constants.ConstSetor;
import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerShowSetores implements ConstSetor{
	
//	@Inject ActionSetor action;
	
	@Inject PartControl pc;
	
	@Execute
	public void execute() {
		pc.show(SETOR$part_url);
//		action.showFuncionarioPart(toolItem.isSelected());
	}
	
//	@Inject
//	public void init() {
////		action.verify();
//	}
//	
//	@Inject @Optional
//	public void selectItem(@EventTopic("selectShowFuncionarios") boolean select) {
//		if(toolItem!=null) {
//			toolItem.setSelected(select);
//		}
//	}
//	
//	
//	@CanExecute
//	public boolean canExecute(MItem toolItem) {
//		this.toolItem = toolItem;
//		return true;
//	}
		
}