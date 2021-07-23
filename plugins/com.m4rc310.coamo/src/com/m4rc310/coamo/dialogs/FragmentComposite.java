package com.m4rc310.coamo.dialogs;

import javax.inject.Inject;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.m4rc310.coamo.actions.ConstsCoamo;
import com.m4rc310.coamo.parts.IComposite;
import com.m4rc310.rcp.ui.utils.PartControl;

public abstract class FragmentComposite implements IComposite, ConstsCoamo {
	
	@Inject protected PartControl pc;
	

	@Override
	public Composite make(Composite parent) {
		return make(null, parent);
	}
	
	protected Composite getComposite(Composite parent_, int numColluns) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(numColluns, false));
		pc.clearMargins(parent);
		return parent;
	}


}
