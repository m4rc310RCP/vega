package com.m4rc310.coamo.parts;

import org.eclipse.swt.widgets.Composite;

import com.m4rc310.rcp.ui.utils.MAction;

public interface IComposite {
	Composite make(Composite parent) ;
	Composite make(MAction action, Composite parent) ;
}
