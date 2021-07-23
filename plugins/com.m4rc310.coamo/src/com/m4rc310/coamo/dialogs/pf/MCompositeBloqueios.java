package com.m4rc310.coamo.dialogs.pf;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.widgets.Composite;

import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.parts.IComposite;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;

@Creatable
public class MCompositeBloqueios  implements IComposite, IActionCoamoConsts{
	
	@Inject PartControl pc;

	@Override
	public Composite make(Composite parent) {
		return null;
	}

	@Override
	public Composite make(MAction action, Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		
		return parent;
	}

}
