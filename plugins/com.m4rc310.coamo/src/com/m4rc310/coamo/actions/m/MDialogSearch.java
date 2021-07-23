package com.m4rc310.coamo.actions.m;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.ui.utils.PartControl;

public abstract class MDialogSearch extends Dialog implements ISearch {

	@Inject
	protected PartControl pc;
	@Inject
	protected MGraphQL graphQL;

	protected final int REMOVE_ID = "remove_id".hashCode();
	protected final int OK_ID = IDialogConstants.OK_ID;
	protected final int CANCEL_ID = IDialogConstants.CANCEL_ID;

	protected Object selectedValue;
	protected Text textSearch;
	protected TableViewer tableViewer;

	@Inject
	public MDialogSearch(Shell parentShell) {
		super(parentShell);

	}

	@Override
	public void buildSearchTopSide(Composite parent) {
		this.textSearch = pc.getText(parent, "", SWT.BORDER | SWT.ICON_SEARCH | SWT.SEARCH);
		textSearch.setMessage("Informe o nome para pesquisa");
		pc.setWidth(textSearch, 45);

		textSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					Table table = tableViewer.getTable();
					if (table.getItemCount() > 0) {
						table.setFocus();
						table.setSelection(0);
					}
				}
			}
		});
		
		
		
	}

	@Override
	public void makeCollums(TableViewer tableViewer) {

	}
	
	@Override
	public void initListeners() {
	}

	@Override
	public void buildResultSide(Composite parent) {
		pc.fillHorizontalComponent(parent);
		this.tableViewer = pc.getTableViewer(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		Table table = tableViewer.getTable();
		GridData gd = pc.fillHorizontalComponent(table);
		gd.heightHint = 100;

		makeCollums(tableViewer);

		tableViewer.addDoubleClickListener(e -> {
			StructuredSelection selection = (StructuredSelection) e.getSelection();
			selectedValue = selection.getFirstElement();
			okPressed();
		});

		tableViewer.addSelectionChangedListener(e -> {
			selectedValue = e.getStructuredSelection().getFirstElement();
		});

		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (table.getItemCount() > 0) {
					Object value = table.getItem(0).getData();
					tableViewer.setSelection(new StructuredSelection(value));
				}
			}
		});

		tableViewer.addOpenListener(e -> {
			okPressed();
		});
	}

	@Override
	protected Control createDialogArea(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));

		Composite top = pc.getComposite(parent);
		buildSearchTopSide(top);

		Composite resultSide = pc.getComposite(parent);
		buildResultSide(resultSide);
		
		getShell().addListener(SWT.Show, e->{
			initListeners();
		});

		return parent;
	}

	@Override
	protected Control createButtonBar(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(3, false));

		Label label = pc.getLabel(parent, "");
		pc.fillHorizontalComponent(parent);
		pc.fillHorizontalComponent(label);

		createButton(parent, CANCEL_ID, "Cancelar", false);
		createButton(parent, OK_ID, "Pesquisar", true);

		pc.clearMargins(parent);

		return parent;
	}

	@Override
	public void searchResults(List<?> results) {
		if (!tableViewer.getTable().isDisposed()) {
			tableViewer.setInput(results);
		}
	}
	
	@Override
	protected void okPressed() {
		if(selectedValue==null) {
			search(textSearch.getText());
		}else {
			returnValue(selectedValue);
			super.okPressed();
		}
	}

	@Override
	public void selectValue(Object selected) {

	}

	@Override
	public void returnValue(Object value) {

	}

}
