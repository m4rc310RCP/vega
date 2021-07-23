package com.m4rc310.coamo.dialogs.atividades;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.coamo.actions.m.MActionDefault;
import com.m4rc310.coamo.models.Atividade;
import com.m4rc310.coamo.models.EPI;
import com.m4rc310.rcp.ui.utils.MD5Utils;

@Creatable
public class ActionAtividade extends MActionDefault implements ConstAtividade {
	private Atividade atividade;
	private Object hashMD5;

	public void writingId(String sid) {
		if (!isLock()) {
			sfire(ATIVIDADE$prepare_to_advance, !sid.isEmpty());
		}
	}
	public void writingEpis(String sepis) {
		if (!isLock()) {
			boolean valid = sepis.matches("([\\d,]+)$");
			sfire(ATIVIDADE$valid_epis, valid);
		}
	}

	public void advance(String sid) {
		Job.create("", monitor -> {
			setLock(true);
			sfire(ATIVIDADE$wait_for_action);

			String query = "{atividade(id:%d){id descricao epis  listEpis{id descricao}}}";
			Optional<Atividade> data = graphQL.query(query, Long.parseLong(sid)).getData("atividade", Atividade.class);
			if (data.isPresent()) {
				loadAtividade(data.get());
				sfire(ATIVIDADE$in_edition);
			}
			setLock(false);
		}).schedule();
	}
	
	public void change(Atividade atividade) {
		if (!isLock()) {
			String md5 = MD5Utils.getChecksum(atividade);
			boolean changed = !hashMD5.equals(md5);
			sfire(ATIVIDADE$changed_atividade, changed);
		}
	}
	
	public void searchForAtividade(String descricao) {
		Job.create("", monitor->{
			String query = "{searchAtividade(descricao:\"%s\"){id descricao epis  listEpis{id descricao}}}";
			List<Atividade> list = graphQL.query(query, descricao).getDataList("searchAtividade", Atividade.class);
			sfire(ATIVIDADE$return_list_search_results, list);
		}).schedule();
	}
	
	public void searchAtividade() {
		pc.showDialog(DialogSearchAtividade.class, this);
	}

	public void cancel() {
		if (atividade != null) {
			loadAtividade(null);
			init();
		} else {
			sfire(ATIVIDADE$close_dialog);
		}
	}
	
	public void save() {
		Job.create("", monitor->{
			String query = "mutation{atividade(atividade:%s){id descricao epis  listEpis{id descricao}}}";
			Optional<Atividade> data = graphQL.query(query, graphQL.toGraph(atividade)).getData("atividade", Atividade.class);
			if(data.isPresent()) {
				cancel();
			}
		}).schedule();
	}
	
	public void saveAndLoad() {
		Job.create("", monitor->{
			String query = "mutation{atividade(atividade:%s){id descricao epis  listEpis{id descricao}}}";
			Optional<Atividade> data = graphQL.query(query, graphQL.toGraph(atividade)).getData("atividade", Atividade.class);
			if(data.isPresent()) {
				loadAtividade(data.get());
			}
		}).schedule();
	}

	public void returnAtividadeFronSearch(Atividade atividade) {
		setLock(true);
		loadAtividade(atividade);
		fire(ATIVIDADE$in_edition);
		setLock(false);
	}
	
	public void loadAtividade(Atividade atividade) {
		setLock(true);
		this.atividade = atividade;
		this.hashMD5 = atividade == null ? "" : MD5Utils.getChecksum(atividade);
		sfire(ATIVIDADE$load_atividade, atividade);
		
		List<EPI> epis = atividade==null?new ArrayList<>(): atividade.getListEpis();
		epis = epis == null? new ArrayList<>(): epis;
		sfire(ATIVIDADE$load_epis, epis);
		
		setLock(false);
	}

	
	
	public void init() {
		sfire(ATIVIDADE$reset_dialog);
	}
}
