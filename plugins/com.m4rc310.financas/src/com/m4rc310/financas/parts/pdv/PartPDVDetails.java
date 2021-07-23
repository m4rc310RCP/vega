 
package com.m4rc310.financas.parts.pdv;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.m4rc310.financas.parts.PartDefault;

import javax.annotation.PreDestroy;

public class PartPDVDetails extends PartDefault{
	@Inject
	public PartPDVDetails() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite content) {
		Composite parent = getComposite(content, 1);
		pc.fillHorizontalComponent(parent);
		
		Label labelPvdStatus = pc.getLabel(parent, m.textPdvFree, SWT.CENTER);
		pc.setStyledClassname(labelPvdStatus, "LabelPdvStatus");
		pc.fillHorizontalComponent(labelPvdStatus);
		
	}
	
	
	@PreDestroy
	public void preDestroy() {
		
	}
	
	
	
}