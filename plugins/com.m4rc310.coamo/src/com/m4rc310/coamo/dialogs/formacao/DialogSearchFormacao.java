package com.m4rc310.coamo.dialogs.formacao;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.dialogs.MDialog;
import com.m4rc310.coamo.models.Formacao;

public class DialogSearchFormacao extends MDialog implements ConstFormacao {

	private ActionFormacao action;
	
	private final int OK_ID = IDialogConstants.OK_ID;

	private Formacao selectedFormacao;
	
	@Inject
	public DialogSearchFormacao(Shell parentShell, ActionFormacao action) {
		super(parentShell);
		this.action = action;
	}

	@Override
	protected Control createDialogArea(Composite parent_) {

		Composite parent = pc.getComposite(parent_);
		parent.setLayoutData(new GridData());

		TableViewer tv = pc.getTableViewer(parent, SWT.READ_ONLY);
		tv.getTable().setHeaderVisible(true);
		tv.getTable().setLinesVisible(true);
		tv.addSelectionChangedListener(e->{
			selectedFormacao = (Formacao)e.getStructuredSelection().getFirstElement();
			action.loadFormacao(selectedFormacao);
		});
		tv.addDoubleClickListener(e->{
			okPressed();
		});

		GridData gd = pc.fillHorizontalComponent(tv.getControl());
		gd.heightHint = 120;
		gd.widthHint = 370;

		pc.createCollumn(tv, SWT.CENTER, "Código", 0, new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Formacao f = (Formacao) cell.getElement();
				String svalue = String.format("%03d", f.getId());
				cell.setText(svalue);
				super.update(cell);
			}
		});
		pc.createCollumn(tv, SWT.CENTER, "Sigla", 0, new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Formacao f = (Formacao) cell.getElement();
				String svalue = String.format("%s", f.getSigla());
				cell.setText(svalue);
				super.update(cell);
			}
		});
		pc.createCollumn(tv, SWT.NONE, "Descrição", 0, new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Formacao f = (Formacao) cell.getElement();
				String svalue = String.format("%s", f.getDescricao());
				cell.setText(svalue);
				super.update(cell);
			}
		});

		pc.widtCollumn(tv, 0, 15f);
		pc.widtCollumn(tv, 1, 15f);
		pc.widtCollumn(tv, 2, 70f);
		
		

		action.addListener(this, FORMACAO$search_result, e -> {
			List<Formacao> list = e.getListValue(0, Formacao.class);
			tv.setInput(list);
		});

		getShell().addListener(SWT.Show, e -> {
			action.initSearchDialog();
		});

		return parent;
	}
	
	@Override
	protected void okPressed() {
		
		super.okPressed();
	}
	

	@Override
	public boolean close() {
		action.removeListeners(this);
		return super.close();
	}

	@Override
	protected Control createButtonBar(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(4, false));
		Label label = pc.getLabel(parent, "");
		pc.fillHorizontalComponent(parent);
		pc.fillHorizontalComponent(label);
		
		createButton(parent, OK_ID, "Voltar", true);

		pc.clearMargins(parent);

		return parent;
	}

}
