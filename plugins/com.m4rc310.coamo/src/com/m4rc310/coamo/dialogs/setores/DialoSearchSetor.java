package com.m4rc310.coamo.dialogs.setores;

import javax.inject.Inject;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.actions.m.MDialogSearch;
import com.m4rc310.coamo.models.Lotacao;

public class DialoSearchSetor extends MDialogSearch implements ConstSetor {
	
	@Inject ActionSetor actionSetor;

	@Inject
	public DialoSearchSetor(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	public void initListeners() {
		actionSetor.addListener(this, SETOR$load_list_search_lotacao, e->{
			searchResults(e.getListValue(0, Lotacao.class));
		});
		
		getShell().addListener(SWT.Show, e->{
			search("");
		});
	}
	
	@Override
	public void makeCollums(TableViewer tableViewer) {
		pc.createCollumn(tableViewer, SWT.NONE, "", 0, new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Object element = cell.getElement();
				if (element instanceof Lotacao) {
					Lotacao lotacao = (Lotacao) element;
					String text = String.format("%04d - %s", lotacao.getNumero(),lotacao.getNome());
					cell.setText(text);
				}
				super.update(cell);
			}
		});
		
		pc.widtCollumn(tableViewer, 0, 100f);
	}

	@Override
	public void search(Object... args) {
		String nome = (String) args[0];
		actionSetor.search(nome);
	}

}
