 
package com.m4rc310.rcp.mercado.livre.ml.addons;

import javax.inject.Inject;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
//import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Data;

public class InitAddon {
	
	
	@Inject  MGraphQL gql;
	
	@Inject IEventBroker broker;
	

	@Inject
	@Optional
	public void reflesh(@UIEventTopic("refresh") String log) {
		System.out.println(log);
		applicationStarted(null);
	}
	
	@Inject
	@Optional
	public void applicationStarted(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {

		Job job = Job.create("Iniciando...", monitor -> {
			try {
				Data res = gql.queryInFile("com.m4rc310.rcp.mercado.livre.ml", "queries/unidade.query", Data.class);
				broker.send("load_unidades", res.getUnidades());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		job.schedule();
	}

}
