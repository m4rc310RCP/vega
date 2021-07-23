package com.m4rc310.coamo.dialogs.formacao;

import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.coamo.actions.m.MActionDefault;
import com.m4rc310.coamo.models.Formacao;
import com.m4rc310.rcp.ui.utils.MD5Utils;

@Creatable
public class ActionFormacao extends MActionDefault implements ConstFormacao {

	private Formacao formacao;
	private Object hashMD5;

	public void init() {
		sfire(FORMACAO$reset_dialog);
	}

	public void writingFormacaoId(String sid) {
		if (!isLock()) {
			boolean isEmpty = sid.isEmpty();
			sfire(FORMACAO$empty_id, isEmpty);
		}
	}

	public void search() {
		pc.showDialog(DialogSearchFormacao.class, this);
	}

	public void initSearchDialog() {
		Job.create("", monitor -> {

			String query = "{formacoes{id sigla descricao}}";
			List<Formacao> list = graphQL.query(query).getDataList("formacoes", Formacao.class);
			sfire(FORMACAO$search_result, list);
			
		}).schedule();
	}

	public void newFormacao() {
		Job.create("", monitor -> {
			waitForJob();

			Formacao formacao = new Formacao();
			loadFormacao(formacao);

		}).schedule();
	}

	public void advance(String sid) {
		Job.create("", monitor -> {
			waitForJob();
			String query = "{formacao(id:%d){id sigla descricao}}";
			Optional<Formacao> data = graphQL.query(query, Long.parseLong(sid)).getData("formacao", Formacao.class);
			if (data.isPresent()) {
				loadFormacao(data.get());
			} else {
				reset();
			}
		}).schedule();
	}

	public void remover() {
		Job.create("", monitor -> {
			String query = "mutation{removerFormacao(id:%d)}";
			Optional<Boolean> data = graphQL.query(query, formacao.getId()).getData("removerFormacao", Boolean.class);
			if (data.isPresent()) {
				reset();
			}

		}).schedule();
	}

	public void save() {
		Job.create("", monitor -> {
			String query = graphQL.toGraph(formacao);
			Optional<Formacao> data = graphQL.query("mutation{formacao(formacao:%s){id sigla descricao}}", query)
					.getData("formacao", Formacao.class);
			if (data.isPresent()) {
				loadFormacao(data.get());
			}
		}).schedule();
	}

	public void reset() {
		loadFormacao(null);
	}

	public void changeFormacao(Formacao formacao) {
		if (!isLock()) {
			String md5 = MD5Utils.getChecksum(formacao);
			boolean changed = !hashMD5.equals(md5);
			sfire(FORMACAO$report_changed, changed);
		}
	}

	public void loadFormacao(Formacao formacao) {
		setLock(true);

		this.formacao = formacao;
		this.hashMD5 = formacao == null ? "" : MD5Utils.getChecksum(formacao);

		sfire(FORMACAO$load_formacao, formacao);
		sfire(formacao == null ? FORMACAO$reset_dialog : FORMACAO$in_edition);

		if (formacao != null) {
			sfire(FORMACAO$enable_delete_option, formacao.getId() != null);
		}
		setLock(false);
	}

	private void waitForJob() {
		sfire(FORMACAO$wait);
	}

}
