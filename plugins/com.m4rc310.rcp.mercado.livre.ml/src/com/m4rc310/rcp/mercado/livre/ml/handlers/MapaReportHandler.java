
package com.m4rc310.rcp.mercado.livre.ml.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Data;
import com.m4rc310.rcp.mercado.livre.ml.reports.Page;
import com.m4rc310.rcp.ui.utils.PartControl;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import reports.utils.R;

public class MapaReportHandler {
	@Execute
	public void execute(PartControl pc, MGraphQL gql) {

		Job job = Job.create("Carregando o relatório...", monitor -> {
			

			try {
				pc.show("com.m4rc310.rcp.mercado.livre.ml.partdescriptor.report", null);
				String query = "{unidade(numero:39){nome grupos{id descricao numero  locais{descricao numeroPessoas  riscos{descricao grupoRisco tamanho}}} cipa{gestao{id gestao atribuicoes{descricao nome membros{nomeCompleto}}}} setores{grupos{descricao}}}}";
//				String query = "";

				Data res = gql.query(query, Data.class);

				R.compileReports("com.m4rc310.rcp.mercado.livre.ml", "sreports");

				Page page = new Page();
				page.setUnidade(res.getUnidade());

				Map<String, Object> params = new HashMap<>();

				JasperReport report = R.getReport("com.m4rc310.rcp.mercado.livre.ml.mapa2");
				
				JasperPrint print = R.getJasperPrint(report, params, Arrays.asList(page));
				
				

				pc.show("com.m4rc310.rcp.mercado.livre.ml.partdescriptor.report", print);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		job.schedule();

	}

}