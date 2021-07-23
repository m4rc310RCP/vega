 
package com.m4rc310.financas.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import javax.annotation.PreDestroy;
import org.eclipse.e4.ui.di.Persist;

public class PartAccount extends PartDefault {
	@Inject
	public PartAccount() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite master) {
		master.setLayout(new GridLayout(1, false));
		
		ScrolledComposite sc = pc.getScrolledComposite(master);
		Composite parent = getComposite(sc, 1);
		
		
		
		
		
		pc.setContentToScrolled(parent);
	}
	
	
	@PreDestroy
	public void preDestroy() {
		
	}
	
	
	
	@Persist
	public void save() {
		
	}
	
}