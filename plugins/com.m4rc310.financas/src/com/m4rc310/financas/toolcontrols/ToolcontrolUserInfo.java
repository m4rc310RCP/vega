package com.m4rc310.financas.toolcontrols;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.m4rc310.financas.i18n.M;
import com.m4rc310.financas.models.Funcionario;
import com.m4rc310.rcp.ui.utils.PartControl;

public class ToolcontrolUserInfo {

	@Inject
	PartControl pc;
	@Inject
	@Translation
	M m;
	@Inject
	IEventBroker eventBroker;

	@PostConstruct
	public void createGui(Composite container) {

		Composite parent = pc.getComposite(container);
		parent.setLayout(new GridLayout(1, false));

		Label labelUser = pc.getLabel(parent, "---".toUpperCase(), SWT.CENTER);
		pc.setStyledClassname(labelUser, "LabelUser");
		pc.setWidth(labelUser, 10);

		eventBroker.subscribe("load_funcionario", new EventHandler() {
			public void handleEvent(Event e) {
//				MToolControl toolControl = pc.find("com.m4rc310.financas.toolcontrol.user.info", MToolControl.class);
				Funcionario f = (Funcionario) e.getProperty(IEventBroker.DATA);
				labelUser.setText(f.getNome().toUpperCase());
				labelUser.setToolTipText(f.getNomeCompleto());
				eventBroker.unsubscribe(this);
//				toolControl.setToBeRendered(false);
//				toolControl.setToBeRendered(true);
			}
		});
	}
}