package com.m4rc310.coamo.dialogs.grupos;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.coamo.actions.ActionSearch;
import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.PartControl;

public class DialogSearchPessoaFisica extends Dialog implements IActionCoamoConsts {

	private ActionSearch actionSearch;

	@Inject
	PartControl pc;
	
//	@Inject
//	ESelectionService selectionService;
	
	private PessoaFisica pessoaFisica;
	

	private Text textSearch;

	@Inject
	public DialogSearchPessoaFisica(Shell parentShell, ActionSearch actionSearch) {
		super(parentShell);
		this.actionSearch = actionSearch;
	}

	@Override
	protected Control createDialogArea(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(2, false));

		Label label = pc.getLabel(parent, "Pesquisar por:");
		this.textSearch = pc.getText(parent, "",
				SWT.BORDER | SWT.SEARCH | SWT.ICON_SEARCH | SWT.CANCEL | SWT.ICON_CANCEL);
		pc.setWidth(textSearch, 45);
		pc.groupControl(textSearch, label);

		TableViewer tableViewerResults = pc.getTableViewer(parent);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.heightHint = 250;
		tableViewerResults.getControl().setLayoutData(gd);
		
		tableViewerResults.addSelectionChangedListener(e->{
			pessoaFisica = (PessoaFisica) e.getStructuredSelection().getFirstElement();
		});

		tableViewerResults.setContentProvider(ArrayContentProvider.getInstance());

		Table table = tableViewerResults.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		pc.createCollumn(tableViewerResults, SWT.CENTER, "Id", 0, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PessoaFisica) {
					PessoaFisica pf = (PessoaFisica) element;
					return String.format("%03d", pf.getId());
				}
				return super.getText(element);
			}
		});
		pc.createCollumn(tableViewerResults, SWT.CENTER, "CPF", 0, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PessoaFisica) {
					PessoaFisica pf = (PessoaFisica) element;
					return pf.getCpf();
				}
				return super.getText(element);
			}
		});
		pc.createCollumn(tableViewerResults, SWT.NONE, "Nome", 0, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PessoaFisica) {
					PessoaFisica pf = (PessoaFisica) element;
					return pf.getNome();
				}
				return super.getText(element);
			}
		});

		pc.widtCollumn(tableViewerResults, 0, 10f);
		pc.widtCollumn(tableViewerResults, 1, 20f);
		pc.widtCollumn(tableViewerResults, 2, 70f);

		actionSearch.addListener(this, FIRE$load_search_results, e -> {
			List<PessoaFisica> results = e.getListValue(0, PessoaFisica.class);
			tableViewerResults.setInput(results);
		});

		return parent;
	}

	@Override
	public boolean close() {
		actionSearch.removeListeners(this);
		return super.close();
	}

	@Override
	protected void okPressed() {
		if (pessoaFisica == null) {
			actionSearch.searchPessoaFisica(textSearch.getText());
		} else {
			actionSearch.setPessoaFisica(pessoaFisica);
			super.okPressed();
		}
	}
}
