                                                     
package com.m4rc310.coamo.assistente.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class HandlerSave {
	@Execute
	public void execute(EPartService partService,MPart part) {
		partService.savePart(part, false);
	}
	
	@CanExecute
	public boolean canExecute(EPartService partService) {
		MPart activePart = partService.getActivePart();
		if(activePart!=null) {
			return activePart.isDirty();
		}
		
		return false;
	}
		
}