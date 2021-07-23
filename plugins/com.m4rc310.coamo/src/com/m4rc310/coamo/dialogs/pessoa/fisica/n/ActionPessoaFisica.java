package com.m4rc310.coamo.dialogs.pessoa.fisica.n;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.brazilutils.br.cpfcnpj.CpfCnpj;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.coamo.actions.m.MActionDefault;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.DateUtils;
import com.m4rc310.rcp.ui.utils.MD5Utils;

@Creatable
public class ActionPessoaFisica extends MActionDefault implements ConstPF {

	public static final String QUERY_RESULT_PF = "{id nome ativo bloqueado nascimento cpf sexo{id sigla descricao} "
			+ "rg{id numero emissor dataEmissao uf} " + "cnh{id numero  categoria validade}  "
			+ "ctps{id numero serie uf digital dataCadastro dataExpedicao } funcionario }";
	private PessoaFisica pf;
	private String md5;

	public void writeId(String sid) {
		boolean valid = sid.isEmpty();
		sfire(PF$prepare_to_advance, !valid);
	}

	public void writeCPF(String scpf) {
		boolean valid = CpfCnpj.isValid(scpf);
		sfire(PF$prepare_to_advance, valid);
	}

	public void advance(String sid, String scpf) {
		Job.create("", monitor -> {
			sfire(PF$mode_loading);
			Optional<PessoaFisica> data;

			if (CpfCnpj.isValid(scpf)) {
				CpfCnpj cc = new CpfCnpj(scpf);
				String query = "{pessoaFisica(cpf:\"%s\" semCPF:false)%s}";
				data = graphQL.query(query, cc.getNumber(), QUERY_RESULT_PF).getData("pessoaFisica",
						PessoaFisica.class);
			} else {
				String query = "{pessoaFisicaFromId(id:%d)%s}";
				data = graphQL.query(query, Long.parseLong(sid), QUERY_RESULT_PF).getData("pessoaFisicaFromId",
						PessoaFisica.class);
			}

			if (data.isPresent()) {
				loadPessoaFisica(data.get());
				sfire(PF$dialog_in_edition);
			} else {
				sfire(PF$report_search_unsuccess);
			}

		}).schedule();
	}

	public void cancel() {
		if (pf != null) {
			loadPessoaFisica(null);
			initDialog();
		} else {
			sfire(PF$dialog_dispose);
		}
	}

	public void loadPessoaFisica(PessoaFisica pf) {
		this.pf = pf;
		sfire(PF$load_pessoa_fisica, pf);
		sfire(PF$init_change_listener, pf);
		
		this.md5 = pf == null? "" : MD5Utils.getChecksum(pf);
		
		changePF();
	}

	public void calculeIdade(PessoaFisica pf) {
		if (pf == null) {
			sfire(PF$load_idade, "Não informada.");
		} else {
			Calendar nascimento = pf.getNascimento();
			String sidade = "Não informada.";
			if (nascimento != null) {
				sidade = String.format("Idade: %d anos", DateUtils.getIdade(nascimento.getTime()));
			}
			sfire(PF$load_idade, sidade);
		}
	}
	
	public void salvar() {
		Job.create("", monitor->{
			String query = "mutation{cadastrarPessoaFisica(pessoaFisica:%s)%s}";
			Optional<PessoaFisica> data = graphQL.query(query, graphQL.toGraph(pf),QUERY_RESULT_PF).getData("cadastrarPessoaFisica", PessoaFisica.class);
			if(data.isPresent()) {
				cancel();
			}
		}).schedule();
		
	}
	
	public void searchPF() {
		pc.showDialog(DialogSearchPF.class, this);
	}

	public void initDialog() {
		sfire(PF$reset_dialog);
	}

	public void changePF() {
		boolean changeded = pf == null?false:! md5.equals(MD5Utils.getChecksum(pf));
		sfire(PF$changeded, changeded);
	}

	public void newPF() {
		PessoaFisica pf = new PessoaFisica();
		pf.setAtivo(true);
		loadPessoaFisica(pf);
		sfire(PF$dialog_in_edition);
	}
	
	
	public void searchFor(String nome) {
		Job.create("", monitor->{
			String query = "{buscarForPessoaFisica(nome:\"%s\" pesquisaFonetica:true)%s}";
			List<PessoaFisica> results = graphQL.query(query, nome, QUERY_RESULT_PF).getDataList("buscarForPessoaFisica", PessoaFisica.class);
			sfire(PF$load_list_search_results, results);
		}).schedule();
	}

	public void loadFromSearch(Object value) {
		loadPessoaFisica((PessoaFisica) value);
		sfire(PF$dialog_in_edition);
	}
}
