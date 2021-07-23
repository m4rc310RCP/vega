package com.m4rc310.coamo.parts;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class TreeUnidadeContentProvider implements ITreeContentProvider {
	
	private final ArrayContentProvider contentProvider;
	
	public TreeUnidadeContentProvider() {
		this.contentProvider = ArrayContentProvider.getInstance();
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return contentProvider.getElements(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

}
