package com.m4rc310.coamo.assistente.actions;

import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.coamo.assistente.constants.ConstFuncionario;
import com.m4rc310.coamo.assistente.models.Funcionario;
import com.m4rc310.coamo.assistente.models.Matricula;
import com.m4rc310.coamo.assistente.models.Setor;
import com.m4rc310.coamo.assistente.utils.MValidateUtils;
import com.m4rc310.rcp.ui.utils.MD5Utils;

@Creatable
public class ActionFuncionario extends ActionDefault implements ConstFuncionario {

	private Funcionario funcionario;
	private String hash;
	private String matricula;

	public void writingMatricula(String smatricula) {
		sfire(FUNCIONARIO$dialog_prepare_to_advance, MValidateUtils.validateMod11(smatricula));
		this.matricula = smatricula;
	}

	public void init() {
		Job.create("", monitor -> {
			
			String query = "{find_allSetores{id lotacao nome }}";
			
			List<Setor> setores = graphQL.query(query).getDataList("find_allSetores", Setor.class);
			sfire(FUNCIONARIO$dialog_load_setores, setores);
			
			sfire(FUNCIONARIO$dialog_reset);
		}).schedule();
	}

	public void advance(String smatricula) {
		Job.create("", monitor -> {
			sfire(FUNCIONARIO$dialog_loading);

			matricula = smatricula.replaceAll(".-", "");

			String query = "mutation{search_funcionario(matricula:\"%s\"){id nome sobrenome nomeCompleto apelido  matricula{matricula} setor{id lotacao nome} }}";
			Optional<Funcionario> data = graphQL.query(query, matricula).getData("search_funcionario",
					Funcionario.class);

			if (data.isPresent()) {
				loadFuncionario(data.get());
			} else {
				Matricula m = new Matricula();
				m.setMatricula(matricula);

				Funcionario f = new Funcionario();
				f.setMatricula(m);
				loadFuncionario(f);
			}
			sfire(FUNCIONARIO$dialog_in_edition);
		}).schedule();
	}

	public void searchForSetor(String slotacao) {

		if (slotacao.isEmpty()) {
			return;
		}

		String query = "{find_setor(lotacao:%d){id lotacao nome}}";

		Optional<Setor> data = graphQL.query(query, Integer.parseInt(slotacao)).getData("find_setor", Setor.class);
		if (data.isPresent()) {
			loadSetor(data.get());
		} else {
			sfire(FUNCIONARIO$dialog_report_invalid_setor);
		}
	}

	private void loadSetor(Setor setor) {
		if (funcionario != null) {
			funcionario.setSetor(setor);
			loadFuncionario(funcionario);
			sfire(FUNCIONARIO$dialog_prepare_to_save, true);
		}
	}

	public void loadFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
		this.hash = funcionario == null ? "" : MD5Utils.getChecksum(funcionario);
		sfire(FUNCIONARIO$dialog_load_funcionario, funcionario);
	}

	public void changeFuncionario() {
		boolean ischanged = hash.equals(MD5Utils.getChecksum(funcionario));
		sfire(FUNCIONARIO$dialog_prepare_to_save, !ischanged);
	}

	public void save() {
		Job.create("", monitor -> {
			sfire(FUNCIONARIO$dialog_wait_saving, true);
			String query = "mutation{add_funcionario(matricula:\"%s\" funcionario:%s){id nomeCompleto apelido  matricula{numero dv matricula}}}";
			Optional<Funcionario> data = graphQL.query(query, matricula, graphQL.toGraph(funcionario))
					.getData("add_funcionario", Funcionario.class);

			if (data.isPresent()) {
				reset();
			}
		}).schedule();
	}

	public void reset() {
		if (funcionario != null) {
			loadFuncionario(null);
			sfire(FUNCIONARIO$dialog_reset);
		} else {
			sfire(FUNCIONARIO$dialog_dispose);
		}
	}

	public void putFuncionario(Funcionario funcionario2) {
		loadFuncionario(funcionario2);
		sfire(FUNCIONARIO$dialog_in_edition);
	}

//	public void updateListFuncionarios() {
//		Job.create("", monitor -> {
//			String query = "{find_allSetores{id nome lotacao funcionarios{id nomeCompleto apelido}}}";
//			List<Setor> list = graphQL.query(query).getDataList("", Setor.class);
//			
//		}).schedule();
//	}
}
