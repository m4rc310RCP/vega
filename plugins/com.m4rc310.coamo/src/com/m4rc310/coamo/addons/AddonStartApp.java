
package com.m4rc310.coamo.addons;

import javax.inject.Inject;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.MConstants;
import com.m4rc310.coamo.actions.ActionCoamo;
import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.controllers.LocalidadeController;
import com.m4rc310.rcp.graphql.MGraphQL;

import reports.utils.R;

public class AddonStartApp implements MConstants, IActionCoamoConsts {

	@Inject
	IEventBroker eventBroker;
	
	@Inject
	LocalidadeController localidadeController;
	
	@Inject @Optional Shell shell;

	
	@Inject
	public void compileReports() {
		R.compileReports(PLUGIN_ID, REPORTS_DIR);
	}

	@Inject
	public void start(ActionCoamo action, MGraphQL graphQL, MApplication app) {
		eventBroker.subscribe(APP_STARTUP_COMPLETE, e -> {
			Job.create("check for Unidade", monitor -> {
				
				graphQL.subscribe(SUBSCRIBE$data_change, ev -> {
					eventBroker.send(EVENT_TOPIC$update, "update");
				});

				graphQL.connectWebSocket();
				
				localidadeController.init();
				
				
//				action.addListener(AddonStartApp.this, FIRE$show_perspective, event -> {
//					navigation.switchPerspective("com.m4rc310.coamo.perspective.coamo");
////					navigation.switchPerspective("com.m4rc310.coamo.perspective.no.connection");
//					navigation.showPart("com.m4rc310.coamo.partdescriptor.unidade");
//				});
//				
//				action.addListener(AddonStartApp.this, FIRE$report_connection_success, event->{
//					if(event.getValue(boolean.class)) {
//						action.monitoreServerChanges();
//						action.searchForUnidade();
//					}else {
//						navigation.switchPerspective("com.m4rc310.coamo.perspective.no.connection");
//					}
//				});
//
				action.verifyConnectionAvailable();
				
//				
			}).schedule();
		});
	}

	
}
