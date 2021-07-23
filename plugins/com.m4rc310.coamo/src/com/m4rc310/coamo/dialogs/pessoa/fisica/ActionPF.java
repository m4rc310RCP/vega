package com.m4rc310.coamo.dialogs.pessoa.fisica;

import java.util.Date;
import java.util.Optional;

import org.brazilutils.br.cpfcnpj.CpfCnpj;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.coamo.actions.ConstsCoamo;
import com.m4rc310.coamo.actions.m.MActionDefault;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.DialogSearchPF;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.DateUtils;

@Creatable
public class ActionPF extends MActionDefault implements ConstsCoamo{

	public static final String QUERY_RESULT_PF = "{id nome ativo bloqueado funcionario nascimento cpf sexo{id sigla descricao} "
			+ "rg{id numero emissor dataEmissao uf} "
			+ "cnh{id numero  categoria validade}  "
			+ "ctps{id numero serie uf digital dataCadastro dataExpedicao }  }";

	private boolean lock = false;

	private Object pf;

	public void advance(String sid, String scpf, boolean noCpf) {
		Job.create("Advance", monitor -> {
			setLock(true);

			sfire(FIRE$report_wait);

			Optional<PessoaFisica> data;

			if (!sid.isEmpty()) {
				String query = "{pessoaFisicaFromId(id:%d)%s}";
				data = graphQL.query(query, Long.parseLong(sid), QUERY_RESULT_PF).getData("pessoaFisicaFromId",
						PessoaFisica.class);

				if (data.isPresent()) {
					load(data.get());
				} else {
					sfire(FIRE$not_found_pf);
				}

			} else {
				CpfCnpj cc = new CpfCnpj(scpf);
				String query = "{pessoaFisica(cpf:\"%s\" semCPF:%s)%s}";
				data = graphQL.query(query, cc.getNumber(), noCpf, QUERY_RESULT_PF).getData("pessoaFisica",
						PessoaFisica.class);
				sfire(FIRE$load_pf, data.get());
			}

			setLock(false);
		}).schedule();

	}
	
	private PessoaFisica pfToPersist;
	public void prepareToPersiste(PessoaFisica pf) {
		this.pfToPersist = pf;
	}
	
	public void save() {
		
		Job.create("", monitor->{
		String query = "mutation{cadastrarPessoaFisica(pessoaFisica:%s)%s}";
		query = String.format(query, graphQL.toGraph(pfToPersist), QUERY_RESULT_PF);
		
		Optional<PessoaFisica> data = graphQL.query(query).getData("cadastrarPessoaFisica", PessoaFisica.class);
		if(data.isPresent()) {
			load(data.get());
		}
		
		}).schedule();
		
	}

	public void load(Object pf) {
		this.pf = pf;
		setLock(pf != null);
		afire(FIRE$load_pf, pf);
		afire(FIRE$lock_form_pf, pf == null);
	}

	public void searchPessoaFisica() {
		pc.showDialog(DialogSearchPF.class, this);
	}

	public void cancelPf() {
		if (pf == null) {
			sfire(FIRE$close_dialog_pf);
		} else {
			load(null);
			prepareToPersiste(null);
		}
	}

	public void writingId(String sid) {
		if (!getLock()) {
			sfire(FIRE$prepare_to_advance_id, !sid.isEmpty());
		}
	}

	public void writingCpf(String scpf) {
		if (!getLock()) {
			CpfCnpj cc = new CpfCnpj(scpf);
			sfire(FIRE$prepare_to_advance_cpf, cc.isValid());
		}
	}

	public void resetDialog() {
		load(null);
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	private boolean getLock() {
		return lock;
	}

	public void noCPF(boolean selection) {
		setLock(true);
		afire(FIRE$report_no_cpf, selection);
		setLock(false);
	}


	public void changeBirthDate(String text) {
		
		String age;
		
		try {
			Date date = DateUtils.getDate(text);
			age = "%d anos de idade.";
			age = String.format(age, DateUtils.getIdade(date));
		} catch (Exception e) {
			age = "Idade não definida";
		}
		
		sfire(FIRE$inform_age, age);
	}

}
