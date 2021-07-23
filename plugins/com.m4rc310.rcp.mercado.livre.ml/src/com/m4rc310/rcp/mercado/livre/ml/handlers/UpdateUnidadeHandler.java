
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Data;

public class UpdateUnidadeHandler {

	@Execute
	public void execute(MGraphQL gql, IEventBroker broker) {

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