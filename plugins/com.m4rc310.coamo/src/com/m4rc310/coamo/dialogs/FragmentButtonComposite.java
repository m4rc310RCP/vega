package com.m4rc310.coamo.dialogs;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
public class FragmentButtonComposite extends FragmentComposite  {

	@Override
	public Composite make(MAction action, Composite parent) {
		
		pc.clearMargins(parent);
		
		Composite stack = pc.getStackComposite(parent);
		Button buttonAdvance = pc.getButton(stack, "Avançar");
		Button buttonCancel = pc.getButton(stack, "Cancelar");
		Button buttonLoading = pc.getButton(stack, "Aguarde...");
		
		pc.toTopControl(buttonAdvance);
		return parent;
	}
	

}
