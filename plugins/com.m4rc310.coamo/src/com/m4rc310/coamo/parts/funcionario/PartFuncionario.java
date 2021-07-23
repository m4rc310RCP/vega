
package com.m4rc310.coamo.parts.funcionario;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.m4rc310.coamo.actions.ActionCoamo;
import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.models.Funcionario;
import com.m4rc310.rcp.ui.utils.PartControl;

public class PartFuncionario implements IActionCoamoConsts {

	@Inject
	PartControl pc;

	@Inject
	MPart part;

	@Inject
	Display display;

	@Inject
	MCompositeDadosFuncionario compositeDados;
	@Inject
	MCompositeContratoFuncionario compositeContrato;
	
	@Inject
	ActionCoamo action;
	
	@Inject UISynchronize sync;
	
	String defaultTitle;

	@Inject
	public PartFuncionario() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		
		Object object = part.getObject();
		
		
		defaultTitle = part.getLabel();
		
		parent.setLayout(new GridLayout());
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		

		CTabFolder folder = pc.createCTabFolder(parent);
		folder.setBorderVisible(true);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite itemA = pc.createCTabItemContainer(folder, "Dados Pessoais");
		compositeDados.make(action, itemA);
		pc.setContentToScrolled(itemA);

		Composite itemB = pc.createCTabItemContainer(folder, "Contrato de Trabalho");
		itemB.addListener(PartControl.TAB_ITEM_SELECTED, e->{
			Funcionario funcionario = (Funcionario) object;
			action.loadContratoTrabalho(funcionario);
		});
		
		compositeContrato.make(action, itemB);
		pc.setContentToScrolled(itemB);
		

		pc.ctabFolderSetSelection(itemA);
		
		
		action.addListener(action, FIRE$report_loading, e->{
			Boolean loading = e.getValue(boolean.class);
			sync.syncExec(()->part.setLabel(loading?"Carregando...":defaultTitle));
		});
		


		if (object instanceof Funcionario) {
			Funcionario funcionario = (Funcionario) object;
//			action.loadContratoTrabalho(funcionario);
			action.loadDadosFuncionario(funcionario);
		}
	}

	

	@PreDestroy
	public void preDestroy() {
		action.removeListeners(action);
	}

	@Persist
	public void save() {

	}

}