package com.m4rc310.coamo.assistente.parts;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import com.m4rc310.coamo.assistente.actions.ActionSetor;
import com.m4rc310.coamo.assistente.constants.ConstSetor;
import com.m4rc310.coamo.assistente.models.Funcionario;
import com.m4rc310.coamo.assistente.models.Matricula;
import com.m4rc310.coamo.assistente.models.Setor;

public class PartSetores extends PartDefault implements ConstSetor {

	@Inject
	private ActionSetor action;

	@Inject
	MPart part;

	@PostConstruct
	public void post(Composite p) {

		Composite parent = getComposite(p, 1);
		Composite top = getComposite(parent, 1);
		pc.fillHorizontalComponent(top);

		Label labelStatus = pc.getLabel(top, " ", SWT.CENTER);
		pc.fillHorizontalComponent(labelStatus);

		Composite clist = getComposite(parent, 1);
		clist.setLayoutData(new GridData(GridData.FILL_BOTH));

		TreeViewer tv = new TreeViewer(clist);
		tv.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		tv.setContentProvider(new SetoresContentProvider());

		tv.setLabelProvider(new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {

				Object element = cell.getElement();
				if (element instanceof Setor) {
					Setor s = (Setor) element;
					cell.setText(MessageFormat.format(m.textSetorLabel, s.getLotacao(), s.getNome(), s.getNumeroFuncionarios()));
				}

				if (element instanceof Funcionario) {
					Funcionario f = (Funcionario) element;
					String text = "[%s] %s";

					Matricula matricula = f.getMatricula();
					String smatricula = matricula == null ? "" : matricula.getMatricula();

					text = String.format(text, smatricula, f.getNomeCompleto());
					cell.setText(text);
				}

				if (element instanceof String) {
					cell.setText(String.valueOf(element));
				}

				super.update(cell);
			}
		});
		
		tv.addSelectionChangedListener(e->{
//			Funcionario fun = (Funcionario) e.getStructuredSelection().getFirstElement();
//			action.openEditorFuncionario(fun);
		});
		
		tv.addOpenListener(event -> {
			Object selection = ((IStructuredSelection) event.getSelection()).getFirstElement();
			action.openInEditor(selection);
		});

		action.addListener(this, SETOR$partLoading, e -> {
			String status = e.getValue(String.class);
			labelStatus.setText(status);
		});

		action.addListener(this, SETOR$partLoadSetores, e -> {
			List<Setor> list = e.getListValue(0, Setor.class);
			setInput(tv, list);
		});

		
		update("");
	}

	public void setInput(TreeViewer tv, Object value) {
		Tree tree = tv.getTree();
		tree.setRedraw(false);

		TreePath[] expandedTreePaths = tv.getExpandedTreePaths();

		final ISelection selection = tv.getSelection();

		tv.setInput(value);

		tv.expandAll();
		tv.setSelection(selection);
		tv.setExpandedTreePaths(expandedTreePaths);
		tree.setRedraw(true);
	}

	@Inject
	@Optional
	private void update(@EventTopic(GLOBAL$refresh) String log) {
		action.updateListSetores();
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