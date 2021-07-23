package com.m4rc310.rcp.mercado.livre.ml.parts;

import org.eclipse.swt.widgets.TreeItem;

public interface TreeSearchAlgorithm {
	public TreeItem search(TreeItem[] treeItems, String searchTerm);
}
