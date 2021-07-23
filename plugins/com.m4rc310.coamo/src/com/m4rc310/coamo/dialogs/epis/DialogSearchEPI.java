package com.m4rc310.coamo.dialogs.epis;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.coamo.actions.m.MDialogSearch;
import com.m4rc310.coamo.models.EPI;

public class DialogSearchEPI extends MDialogSearch implements ConstEpi {

	private ActionEpi action;
	private TableViewer tableViewer;
	private Text textSearch;

	@Inject
	public DialogSearchEPI(Shell parentShell, ActionEpi action) {
		super(parentShell);
		this.action = action;
	}

	@Override
	public void search(Object... args) {
		String txt = (String) args[0];
		action.addListener(this, EPI$search_list_result, e->{
			List<EPI> results = e.getListValue(0, EPI.class);
			searchResults(results);
		});
		action.searchForEpi(txt);
	}

	@Override
	public void searchResults(List<?> results) {
		tableViewer.setInput(results);
	}

	@Override
	public void buildSearchTopSide(Composite parent) {
		this.textSearch = pc.getText(parent, "", SWT.BORDER | SWT.ICON_SEARCH |SWT.SEARCH );
		textSearch.setMessage("Informe o nome para pesquisa");
		pc.setWidth(textSearch, 45);
		
		textSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.keyCode == SWT.ARROW_DOWN) {
					Table table = tableViewer.getTable();
					if(table.getItemCount() > 0) {
						table.setFocus();
						table.setSelection(0);
					}
				}
			}
		});
		
	}

	@Override
	public void buildResultSide(Composite parent) {
		pc.fillHorizontalComponent(parent);
		this.tableViewer = pc.getTableViewer(parent, SWT.BORDER|SWT.V_SCROLL | SWT.H_SCROLL);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		Table table = tableViewer.getTable();
		GridData gd = pc.fillHorizontalComponent(table);
		gd.heightHint = 100;
		
		pc.createCollumn(tableViewer, SWT.NONE, "", 0, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof EPI) {
					EPI epi = (EPI) element;
					String text = "{0,number,000} - {1}" ;
					return MessageFormat.format(text,epi.getId(), epi.getDescricao());
				}
				return super.getText(element);
			}
			
			@Override
			public Image getImage(Object element) {
				return pc.getImage("com.m4rc310.coamo", "icons/applications-development.png");
			}
		});
		
		tableViewer.addDoubleClickListener(e->{
			StructuredSelection selection = (StructuredSelection)e.getSelection();
			selectedValue = selection.getFirstElement();
			okPressed();
		});
		
		tableViewer.addSelectionChangedListener(e->{
			selectedValue = e.getStructuredSelection().getFirstElement();
		});
		
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(table.getItemCount()>0) {
					Object value = table.getItem(0).getData();
					tableViewer.setSelection(new StructuredSelection(value));
				}
			}
		});
		
		tableViewer.addOpenListener(e->{
			okPressed();
		});
		
		pc.widtCollumn( tableViewer,0, 100f);
	}

	@Override
	public void returnValue(Object value) {
		action.resultSearch((EPI) value);
	}
	
	@Override
	protected void cancelPressed() {
		
		super.cancelPressed();
	}
	
	@Override
	protected void okPressed() {
		if(selectedValue==null) {
			search(textSearch.getText());
		}else {
			returnValue(selectedValue);
			action.removeListeners(this);
			super.okPressed();
		}
	}
}
