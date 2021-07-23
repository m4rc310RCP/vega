 
package com.m4rc310.coamo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.coamo.dialogs.pessoa.fisica.n.DialogPF;
import com.m4rc310.rcp.ui.utils.PartControl;

public class HandlerPessoaFisica {
	@Execute
	public void execute(PartControl pc) {
//		pc.showDialog(DialogPessoaFisica.class);
//		pc.showDialog(MDialogPF.class);
		pc.showDialog(DialogPF.class);
	}
	
	
//	@CanExecute
//	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
//		Object selected = ((IStructuredSelection) sel).getFirstElement();
//		if (selected instanceof Funcionario) {
//			return true;
//		}
//		return false; 
//	}
		
}