 
package com.m4rc310.coamo.assistente.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;

public class HandlerMenuShowFuncionarios extends HandlerShowFuncionarios{
	
	
	@Execute
	public void execute(MItem toolItem) {
		
//		action.addListener(action,FUNCIONARIO$is_show_funcionarios, e->{
//			boolean isshow = e.getValue(boolean.class);
//			toolItem.setSelected(isshow);
//			
//			System.out.println(isshow);
//		});
		
		super.execute(toolItem);
	}
	
		
}