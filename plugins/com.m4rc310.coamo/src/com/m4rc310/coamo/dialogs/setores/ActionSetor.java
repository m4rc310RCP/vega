package com.m4rc310.coamo.dialogs.setores;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;

import com.m4rc310.coamo.actions.m.MActionDefault;
import com.m4rc310.coamo.models.Lotacao;
import com.m4rc310.coamo.models.Unidade;

@Creatable
public class ActionSetor extends MActionDefault implements ConstSetor {
	@Inject
	@Named(IServiceConstants.ACTIVE_SELECTION)
	ISelection sel;
	@Inject
	@Named("list_unidades")
	List<Unidade> unidades;
	private Lotacao lotacao;

	public void initDialog() {
		sfire(SETOR$load_unidades, unidades);
		if (unidades.size() == 1) {
			sfire(SETOR$select_unidade, unidades.get(0));
		}
		sfire(SETOR$setor_dialog_reset);
	}

	public void loadSetor(Lotacao lotacao) {
		this.lotacao = lotacao;
		sfire(SETOR$setor_load, lotacao);
	}
	
	public void writeNumero(String snumero) {
		boolean valid = !snumero.isEmpty();
		sfire(SETOR$report_prepare_to_advance, valid);
	}
	
	
	public void searchSetor() {
		pc.showDialog(DialoSearchSetor.class, this);
	}
	
	public void search(String nome) {
		Job.create("", monitor -> {
//			sfire(FIRE$setor_report_edit);
			String query = "{searchLotacoes(nome:\"%s\"){id nome numero}}";
			List<Lotacao> results = graphQL.query(query, nome).getDataList("searchLotacoes", Lotacao.class);
			sfire(SETOR$load_list_search_lotacao, results);
		}).schedule();
	}

	public void salvar() {
		Job.create("", monitor -> {
			if (lotacao != null) {
				lotacao.setUnidade(unidades.get(0));
				String query = "mutation{cadastrarLotacao(lotacao:%s){id}}";
				Optional<Lotacao> data = graphQL.query(query, graphQL.toGraph(lotacao)).getData("cadastrarLotacao", Lotacao.class);
				if(data.isPresent()) {
					System.out.println("OK");
				}
			}
		}).schedule();
	}
	
	public void resetLotacao() {
		cancel();
	}
	

	public void advance(String snumero) {
		Job.create("", monitor -> {
			sfire(SETOR$loading);
			String query = "{lotacao(numero:%d){id nome numero}}";
			Optional<Lotacao> data = graphQL.query(query, Long.parseLong(snumero)).getData("lotacao", Lotacao.class);
			loadSetor(data.get()); 
			sfire(SETOR$setor_report_edit);
		}).schedule();
	}

	public void cancel() {
		sfire(SETOR$setor_dialog_reset);
		loadSetor(null);
	}

	public void writingNumeroSetor(String snumero) {
		sfire(SETOR$report_prepare_to_advance, !snumero.isEmpty());
	}

}
