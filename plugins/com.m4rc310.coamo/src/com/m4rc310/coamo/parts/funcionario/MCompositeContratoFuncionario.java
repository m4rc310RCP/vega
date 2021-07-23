package com.m4rc310.coamo.parts.funcionario;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.jasperassistant.designer.viewer.IReportViewer;
import com.jasperassistant.designer.viewer.ViewerComposite;
import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.parts.IComposite;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;

import net.sf.jasperreports.engine.JasperPrint;

@Creatable
public class MCompositeContratoFuncionario implements IComposite, IActionCoamoConsts {
	@Inject
	PartControl pc;


	@Override
	public Composite make(Composite parent) {
		return null;
	}

	@Override
	public Composite make(MAction action, Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout());
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		ViewerComposite viewer = new ViewerComposite(parent, SWT.NONE);
		viewer.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		action.addListener(action, FIRE$load_contrato_trabalho, e -> {
			JasperPrint print = e.getValue(JasperPrint.class);
			IReportViewer rv = viewer.getReportViewer();
			rv.setDocument(print);
		});

		return parent;
	}

}
