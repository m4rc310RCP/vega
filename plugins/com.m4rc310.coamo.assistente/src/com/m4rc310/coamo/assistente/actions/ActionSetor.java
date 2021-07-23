package com.m4rc310.coamo.assistente.actions;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;

import com.m4rc310.coamo.assistente.constants.ConstSetor;
import com.m4rc310.coamo.assistente.models.Funcionario;
import com.m4rc310.coamo.assistente.models.Setor;

@Creatable
public class ActionSetor extends ActionDefault implements ConstSetor {

	@Inject
	@Preference(nodePath = GLOBAL$plugin_id)
	IEclipsePreferences prefs;

	public void init() {
		sfire(SETOR$partReset);
	}

	public void updateListSetores() {
		Job.create("", monitor -> {
			sfire(SETOR$partLoading, m.textLoading);
			String query = "{find_allSetores{id nome lotacao  numeroFuncionarios  funcionarios{id nomeCompleto apelido matricula{matricula} }}}";
			List<Setor> list = graphQL.query(query).getDataList("find_allSetores", Setor.class);
			sfire(SETOR$partLoadSetores, list);
			query = "{status}";
			String status = graphQL.query(query).getData("status", String.class).get();
			sfire(SETOR$partLoading, status);
		}).schedule();
	}
	
	public void openInEditor(Object selection) {
		if (selection instanceof Funcionario) {
			Funcionario funcionario = (Funcionario) selection;
			String key = String.format("FUN_%05d", funcionario.getId());
			pc.show(SETOR$part_funcionario_url, key, funcionario.getNomeCompleto(), funcionario);
		}
//		String key = MD5Utils.getChecksum(funcionario);
//		pc.show(SETOR$part_funcionario_url, key, funcionario.getNomeCompleto(), funcionario);
		
	}

}
