package com.m4rc310.coamo.assistente.parts;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.m4rc310.coamo.assistente.constants.ConstSetor;
import com.m4rc310.coamo.assistente.models.Setor;

public class SetoresContentProvider implements ITreeContentProvider, ConstSetor {
	

	final ArrayContentProvider provider = ArrayContentProvider.getInstance();

	@Override
	public Object[] getElements(Object element) {
		
		return provider.getElements(element);
	}

	@Override
	public Object[] getChildren(Object element) {
		if (element instanceof Setor) {
			Setor setor = (Setor) element;
			return provider.getElements(setor.getFuncionarios());
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Setor) {
			Setor setor = (Setor) element;
			return setor.getFuncionarios()!=null;
		}
		return false;
	}

}
