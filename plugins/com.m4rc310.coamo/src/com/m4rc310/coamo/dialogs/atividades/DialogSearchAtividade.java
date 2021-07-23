package com.m4rc310.coamo.dialogs.atividades;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.actions.m.MDialogSearch;
import com.m4rc310.coamo.models.Atividade;

public class DialogSearchAtividade extends MDialogSearch implements ConstAtividade{

	private ActionAtividade action;

	@Inject
	public DialogSearchAtividade(Shell parentShell, ActionAtividade action) {
		super(parentShell);
		this.action = action;
	}
	
	@Override
	protected Control createDialogArea(Composite parent_) {
		Control ret = super.createDialogArea(parent_);
		
		getShell().addListener(SWT.Show, event->{
			action.addListener(this, ATIVIDADE$return_list_search_results, e->{
				List<Atividade> list = e.getListValue(0, Atividade.class);
				searchResults(list);
			});
		});
		
		return ret;
	}
	
//	

	@Override
	public void search(Object... args) {
		String txt = (String) args[0];
		action.searchForAtividade(txt);
	}

	@Override
	public void makeCollums(TableViewer tableViewer) {
		pc.createCollumn(tableViewer, SWT.NONE, "", 0, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Atividade) {
					Atividade a = (Atividade) element;
					return String.format("%03d - %s", a.getId(), a.getDescricao());
				}
				return super.getText(element);
			}
		});
		pc.widtCollumn(tableViewer, 0, 100f);
	}
	
	@Override
	public void returnValue(Object value) {
		action.returnAtividadeFronSearch((Atividade) value);
	}
	
	@Override
	protected void okPressed() {
		if(selectedValue==null) {
			search(textSearch.getText());
		}else {
			action.removeListeners(this);
			returnValue(selectedValue);
			super.okPressed();
		}
	}

}
