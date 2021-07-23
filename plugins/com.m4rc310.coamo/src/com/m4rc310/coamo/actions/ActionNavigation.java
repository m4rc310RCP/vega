package com.m4rc310.coamo.actions;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;

@Creatable
@Singleton
public class ActionNavigation extends MAction {

	@Inject
	IEventBroker eventBroker;

	@Inject
	EModelService modelService;

	@Inject
	EPartService partService;

	@Inject
	MApplication app;

	@Inject
	PartControl pc;

	@Inject
	UISynchronize sync;
	
	public void requestAtivation() {
		partService.requestActivation();
	}

	public void showPart(String partURI) {
		sync.syncExec(() -> pc.show(partURI));
	}
	

	public void showPart(String partURI, String partId, String title, Object value) {
		sync.syncExec(() -> pc.show(partURI, partId, title, value));
	}

	int tryCount = 0;
	
	public void switchPerspective(String perspectiveId) {
		try {
			MPerspective perspective = (MPerspective) modelService.find(perspectiveId, app);
			if (perspective != null) {
				partService.switchPerspective(perspective);
			}
		} catch (Exception e) {
			if(tryCount++<50) {
				switchPerspective(perspectiveId);
			}
		}
	}
}
