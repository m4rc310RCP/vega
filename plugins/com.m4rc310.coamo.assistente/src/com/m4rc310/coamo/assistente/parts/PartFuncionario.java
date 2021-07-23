 
package com.m4rc310.coamo.assistente.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.m4rc310.coamo.assistente.actions.ActionFuncionario;
import com.m4rc310.coamo.assistente.constants.ConstFuncionario;
import com.m4rc310.coamo.assistente.models.Setor;

public class PartFuncionario extends PartDefault implements ConstFuncionario {
	
	@Inject MPart part;
	
	@Inject ActionFuncionario action;
	
	@PostConstruct
	public void post(Composite parent) {
		parent.setLayout(new GridLayout());
		ScrolledComposite sc = pc.getScrolledComposite(parent);
		
		Composite content = getComposite(sc, 1);
		
		buildPartContent(content);
		
		pc.setContentToScrolled(content);
		
		action.init();
	}
	
	
	private void buildPartContent(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		Composite line = getComposite(parent, 2);
		pc.fillHorizontalComponent(line);
		makeIdentifyContent(line);
		makeSetorContent(line);
	}

	private void makeIdentifyContent(Composite content) {
		Group parent = getGroup(content, 4);
		pc.clearMargins(parent);
		
		Label label = pc.getLabel(parent, m.labelMatricula);
		Text textMatricula = pc.getText(parent, m.empty, SWT.BORDER | SWT.CENTER);
		pc.setWidth(textMatricula, 8);
		pc.groupControl(textMatricula, label);
		
		ToolBar tool = new ToolBar(parent, SWT.FLAT);
		ToolItem itemSearch = getToolItem(tool, ICONS$search);
		
		Composite stack = pc.getStackComposite(parent);
		Button buttonAdvance = pc.getButton(stack, m.textAdvance);
		Button buttonCancel = pc.getButton(stack, m.textCancel);
		Button buttonLoading = pc.getButton(stack, m.textLoading);
		
		pc.toTopControl(buttonAdvance);
		
	}
	
	private void makeSetorContent(Composite content) {
		Group parent = getGroup(content, 2);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		
		Label label = pc.getLabel(parent, m.labelSetor);
		ComboViewer comboViewerSetor = pc.getComboViewer(parent, SWT.READ_ONLY);
		comboViewerSetor.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Setor) {
					Setor setor = (Setor) element;
					return String.format("%04d - %s", setor.getLotacao(), setor.getNome());
				}
				return super.getText(element);
			}
		});
		
		pc.setWidth(comboViewerSetor.getControl(), 32);
		pc.groupControl(comboViewerSetor.getCombo(), label);
		
		
		
		action.addListener(this, FUNCIONARIO$dialog_load_setores, e->{
			List<Setor> setores = e.getListValue(0, Setor.class);
			comboViewerSetor.setInput(setores);
		});
		
	}
	
	

	@PreDestroy
	public void preDestroy() {
		
	}
	
	
	
	@Persist
	public void save() {
		
	}
	
}