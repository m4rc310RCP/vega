package com.m4rc310.rcp.mercado.livre.ml.parts;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class SearchDialog extends Dialog {
	public SearchDialog(Shell parent) {
        super(parent);
    }

    private Tree tree;
    private SearchableTreeViewer searchableTreeViewer;
    private Button searchButton;
    private Text searchField;
    private TreeSearchAlgorithm algorithm;

    public SearchDialog(Shell parent, SearchableTreeViewer searchableTreeViewer, TreeSearchAlgorithm algorithm) {
        super(parent);
        this.searchableTreeViewer = searchableTreeViewer;
        this.algorithm = algorithm;
        tree = searchableTreeViewer.getTree();
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        container.setLayout(new FillLayout());
        searchField = new Text(container, SWT.NONE);
        return container;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        searchButton = createButton(parent, IDialogConstants.PROCEED_ID, "Search", false);
        searchButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(this::search));
    }

    private void search(SelectionEvent event) {
        TreeItem found = algorithm.search(tree.getItems(), searchField.getText());
        if (found != null) {
            searchableTreeViewer.setSelection(new StructuredSelection(found.getData()), true);
            close();
        } else {
            System.out.println("Nothing Found");
        }
    }
}
