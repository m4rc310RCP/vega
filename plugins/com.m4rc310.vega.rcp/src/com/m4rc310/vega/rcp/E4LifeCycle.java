package com.m4rc310.vega.rcp;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class E4LifeCycle {

	@Inject
	IEventBroker eventBroker;

//	@Inject
//	EModelService model;
//
//	@Inject
//	MApplication app;

	public static final String CATEGORY_PERSPECTIVES = "org.eclipse.e4.ui.perspectives";
	public static final String CATEGORY_PERSPECTIVES$_NAME = "Perspectives"; //$NON-NLS-1$

	@Inject
	@Optional
	private void appCompleted(@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Object event,
			IWorkbench workbench) {
	}

	@PostContextCreate
	void postContextCreate(IEventBroker eventBroker, final IApplicationContext context) {

		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new EventHandler() {
			@Override
			public void handleEvent(Event event) {
	    	  eventBroker.send("m_start", event);
	    	  eventBroker.unsubscribe(this);
			}
		});
		
		context.applicationRunning();
	}

//	@PostContextCreate
	void postContextCreate(IEclipseContext workbenchContext) {
	}

	@PreSave
	void preSave(IEclipseContext workbenchContext) {
	}

	@ProcessAdditions
	void processAdditions(IEclipseContext workbenchContext) {
	}

	@ProcessRemovals
	void processRemovals(IEclipseContext workbenchContext) {
		eventBroker.send("processRemovals", workbenchContext);
	}
}
