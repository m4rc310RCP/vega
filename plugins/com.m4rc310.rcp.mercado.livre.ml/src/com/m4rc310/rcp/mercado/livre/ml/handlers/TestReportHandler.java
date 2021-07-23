
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Data;
import com.m4rc310.rcp.mercado.livre.ml.reports.Page;
import com.m4rc310.rcp.ui.utils.PartControl;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import reports.utils.R;

public class TestReportHandler {
	
	
	
	@Execute
	public void execute(PartControl pc, MGraphQL gql, MHandledItem item) {
		
		MCommand command = item.getCommand();
		System.out.println(command.getCommandName());
		

		Job job = Job.create("Carregando o relatório...", monitor -> {

			try {
				String query = "{unidade(numero:39){nome setores{id lotacao descricao locais{id descricao numeroPessoas riscos{id descricao grupoRisco tamanho local{id} }}} cipa{descricao gestao{gestao  atribuicoes{nome descricao membros{nomeCompleto} }}}}}";

				Data res = gql.query(query, Data.class);

				R.compileReports("com.m4rc310.rcp.mercado.livre.ml", "sreports");

				Page page = new Page();
				page.setUnidade(res.getUnidade());

				Map<String, Object> params = new HashMap<>();

				JasperReport report = R.getReport("com.m4rc310.rcp.mercado.livre.ml.mapa");
				
				JasperPrint print = R.getJasperPrint(report, params, Arrays.asList(page));

				pc.show("com.m4rc310.rcp.mercado.livre.ml.partdescriptor.report", print);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		job.schedule();

	}

}