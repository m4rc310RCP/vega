package com.m4rc310.coamo.assistente.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.m4rc310.coamo.assistente.constants.ConstFuncionario;

public class PartFuncionarios extends PartDefault implements ConstFuncionario{
	
//	@Inject
//	private ActionFuncionario action;
	
	@Inject MPart part;


//	@Inject
//	public PartFuncionarios(ActionFuncionario action) {
//		this.action = action;
//	}
	
//	public void setActionFuncionario(ActionFuncionario action) {
//		this.action = action;
//	}
	
	
	@PostConstruct
	public void post(Composite parent) {
		TreeViewer tree = new TreeViewer(parent);
		pc.fillHorizontalComponent(tree.getControl()).heightHint = SWT.FILL;
		
		tree.getTree().setHeaderVisible(true);
	}
	
	
	
	public void postConstruct(Composite parent) {
		
//		action = (ActionAddFuncionario) part.getObject();
//		
//		
//		TableViewer tableViewer = pc.getTableViewer(parent);
//		pc.fillHorizontalComponent(tableViewer.getControl()).heightHint = SWT.FILL;
////		tableViewer.getTable().setLinesVisible(true);
////		tableViewer.getTable().setHeaderVisible(true);
//		
//		
//		pc.createCollumn(tableViewer, SWT.RIGHT, "", 0, new StyledCellLabelProvider() {
//			@Override
//			public void update(ViewerCell cell) {
//				cell.setImage(pc.getImage(GLOBAL$plugin_id, "icons/user.png"));
//				super.update(cell);
//			}
//		});
//		pc.createCollumn(tableViewer, SWT.RIGHT, "", 1, new StyledCellLabelProvider() {
//			@Override
//			public void update(ViewerCell cell) {
//				
//				Funcionario fun = (Funcionario) cell.getElement();
//				String text = String.format("%s", fun.getMatricula().getMatricula(), fun.getNomeCompleto());
//				cell.setText(text);
//				super.update(cell);
//			}
//		});
//		
//		pc.createCollumn(tableViewer, SWT.NONE, "", 2, new StyledCellLabelProvider() {
//			@Override
//			public void update(ViewerCell cell) {
//				
//				Funcionario fun = (Funcionario) cell.getElement();
//				String text = String.format("%s", fun.getNomeCompleto());
//				cell.setText(text);
//				
//				super.update(cell);
//			}
//		});
//		
//		pc.widtCollumn(tableViewer, 0, 22); //27.088-1
//		pc.widtCollumnCharacter(tableViewer, 1, 7); //27.088-1
//		pc.widtCollumn(tableViewer, 2, 80f);
//		
//		
//		action.addListener(this, FUNCIONARIO$report_loaded_part, e->{
//			update("");
//		});
//		
//		action.addListener(this, FUNCIONARIO$load_funcionarios, e->{
//			tableViewer.setInput(e.getListValue(0, Funcionario.class));
//		});
//		
//		action.initFuncionarioPart();
	}
	
	@Inject
	@Optional
	private void update(@EventTopic(GLOBAL$refresh) String log) {
//		action.updateListFuncinarios();
	}
	
	
	@PreDestroy
	public void preDestroy() {
//		action.showFuncionarioPart(false);
//		action.removeListeners(this);
//		action.removeListeners(action);
	}
	
	
	
	@Persist
	public void save() {
		
	}
	
}