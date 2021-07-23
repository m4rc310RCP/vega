package com.m4rc310.rcp.mercado.livre.ml.parts;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;

public class SearchableTreeViewer extends TreeViewer {

	@SuppressWarnings("unused")
	private final Composite parent;

	public SearchableTreeViewer(Composite parent, TreeSearchAlgorithm algorithm) {
		super(parent);
		this.parent = parent;
		
		parent.addKeyListener(KeyListener.keyPressedAdapter(event->{
			
			System.out.println(event.keyCode);
			
			System.out.println(parent.toString());
			
//			if(event.keyCode == SWT.F5) {
//				System.out.println("----");
//			}
			
			
//			if (event.stateMask == SWT.CTRL && event.keyCode == 'f') {
//                startSearchDialog(algorithm);
//            }
		}));
		
		
	}

//	private void startSearchDialog(TreeSearchAlgorithm algorithm) {
//		SearchDialog searchDialog = new SearchDialog(parent.getShell(), this, algorithm);
//        searchDialog.open();
//	}

}
