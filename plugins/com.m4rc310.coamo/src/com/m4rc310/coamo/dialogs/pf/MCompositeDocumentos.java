package com.m4rc310.coamo.dialogs.pf;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.parts.IComposite;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;

@Creatable
public class MCompositeDocumentos  implements IComposite, IActionCoamoConsts{
	
	@Inject PartControl pc;
	@Inject CompositeRG compositeRG;
	@Inject CompositeCNH compositeCNH;
	@Inject CompositeCTPS compositeCTPS;

	@Override
	public Composite make(Composite parent) {
		return null;
	}

	@Override
	public Composite make(MAction action, Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		parent.setLayoutData(gd);
		
		Group g1 = pc.getGroup(parent);
		g1.setLayout(new GridLayout());
		Label label = pc.getLabel(g1, "Carteira de Identidade (RG):");
		pc.clearMargins(g1);
		pc.groupControl(g1,label);
		compositeRG.make(action, g1);
		
		g1 = pc.getGroup(parent);
		g1.setLayout(new GridLayout());
		pc.getLabel(g1, "Carteira Nacional de Habilitação (CNH):");
		pc.clearMargins(g1);
		compositeCNH.make(action, g1);
		g1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		g1 = pc.getGroup(parent);
		g1.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		g1.setLayoutData(gd);
		
		pc.getLabel(g1, "Carteira de Trabalho e Previdência Social (CTPS):");
		pc.clearMargins(g1);
		compositeCTPS.make(action, g1);
		
		
		return parent;
	}

}
