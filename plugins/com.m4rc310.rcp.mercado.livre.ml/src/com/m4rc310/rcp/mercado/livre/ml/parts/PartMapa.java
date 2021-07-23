 
package com.m4rc310.rcp.mercado.livre.ml.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.jasperassistant.designer.viewer.IReportViewer;
import com.jasperassistant.designer.viewer.ViewerComposite;
import com.m4rc310.rcp.mercado.livre.ml.i18n.Messages;

import net.sf.jasperreports.engine.JasperPrint;

public class PartMapa {
	
	private ViewerComposite viewReportComposite;
	@Inject
	private MPart part;
	private JasperPrint print;

	@Inject @Translation Messages m;
	
	@Inject
	UISynchronize sync;

	
	
	@Inject
	public PartMapa() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent_) {
		
		final Composite parent = new Composite(parent_, SWT.NONE);
		final GridLayout layout = new GridLayout();
		parent.setLayout(layout);

		this.viewReportComposite = new ViewerComposite(parent, SWT.NONE);
		viewReportComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		updatePart(part);
		viewReportComposite.getReportViewer().setZoomMode(IReportViewer.ZOOM_MODE_FIT_WIDTH);

		
	}
	
	@Inject
	@Optional
	public void updatePart( @EventTopic("update_part_report") MPart part) {
		sync.asyncExec(() -> {
			if (this.part == part) {
				
				part.setLabel(m.textLoading);
				
				if (part.getObject() instanceof JasperPrint) {
					this.print = (JasperPrint) part.getObject();
					IReportViewer view = viewReportComposite.getReportViewer();
					view.setDocument(print);
					part.setLabel(m.textMapaRisco);
				}
			}
		});

	}
	
	
	@PreDestroy
	public void preDestroy() {
		
	}
	
	
	
}