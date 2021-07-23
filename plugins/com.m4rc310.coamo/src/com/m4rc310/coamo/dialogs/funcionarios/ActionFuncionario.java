package com.m4rc310.coamo.dialogs.funcionarios;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.coamo.actions.m.MActionDefault;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.DialogSearchPF;
import com.m4rc310.coamo.models.Lotacao;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.coamo.models.Unidade;
import com.m4rc310.coamo.models.VinculoEmprego;

@Creatable
public class ActionFuncionario extends MActionDefault implements ConstFuncionario {

	private boolean lock;
	private PessoaFisica pf;
	@Inject
	@Named("unidade")
	Unidade unidade;
	private Lotacao lotacao;
	private VinculoEmprego vinculo;

	public void init() {
		sfire(FUNCIONARIO$reset_dialog);
		sfire(FUNCIONARIO$load_lotacoes, unidade.getLotacoes());
	}

	public void writingPfId(String sid) {
		if (!isLock()) {
			boolean empty = sid.isEmpty();
			sfire(FUNCIONARIO$writing_id, empty);
		}
	}

	public void writingMatriculaFuncionario(String matricula) {
		if (!isLock()) {
			boolean valid = pc.validateMod11(matricula);
			sfire(FUNCIONARIO$valid_matricula, valid);
		}
	}

	public void findPf(String sid) {
		Job.create("Finding", monitor -> {
			setLock(true);
			sfire(FUNCIONARIO$searching_pf);

			String query = "{pessoaFisicaFromId(id:%d){id nome ativo bloqueado funcionario nascimento cpf}}";

			Optional<PessoaFisica> data = graphQL.query(query, Long.parseLong(sid)).getData("pessoaFisicaFromId",
					PessoaFisica.class);
			if (data.isPresent()) {
				loadPF(data.get());
			} else {
				sfire(FUNCIONARIO$informe_pf_not_found);
			}

			setLock(false);
		}).schedule();
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public void vinculoEmprego(String smatricula) {
		Job.create("vinculando", monitor -> {
			setLock(true);

			if (lotacao != null) {
				String matricula = smatricula.substring(0, smatricula.length() - 1);
				Long code = Long.valueOf(matricula);
				Integer numeroLotacao = lotacao.getNumero();
				
				String query = "mutation{vinculoEmprego(codigo:%d pessoaFisicaID:%d lotacao:%d){id matricula funcionario{id matricula dados{id}}}}";
				Optional<VinculoEmprego> data = graphQL.query(query, code, pf.getId(), numeroLotacao).getData("vinculoEmprego", VinculoEmprego.class);
				loadvinculo(data.get());
				sfire(FUNCIONARIO$edit_vinculo, false);
			}

			setLock(false);
		}).schedule();
	}

	public void searchPF() {
		pc.showDialog(DialogSearchPF.class, this);
	}

	public void loadPF(PessoaFisica pf) {
		setLock(true);
		this.pf = pf;
		sfire(FUNCIONARIO$edit_vinculo, false);
		
		if (pf != null) {
			sfire(FUNCIONARIO$pf_loaded);
			if (pf.isFuncionario()) {
				String query = "{vinculo(pf:%s){id matricula lotacao{id nome numero} funcionario{id dados{nome}}}}";
				Optional<VinculoEmprego> data = graphQL.query(query, graphQL.toGraph(pf)).getData("vinculo", VinculoEmprego.class);
				if(data.isPresent()) {
					VinculoEmprego vinculo = data.get();
					loadvinculo(vinculo);
					sfire(FUNCIONARIO$edit_vinculo, false);
				}
			}else {
				sfire(FUNCIONARIO$edit_vinculo, true);
			}
		} else {
			VinculoEmprego vinculo = null;
			loadvinculo(vinculo);
			sfire(FUNCIONARIO$reset_dialog);
		}
		sfire(FUNCIONARIO$load_pf, pf);
		setLock(false);
	}
	
	private void loadvinculo(VinculoEmprego vinculo) {
		this.vinculo = vinculo;
		sfire(FUNCIONARIO$load_vinculo, vinculo);
	}
	
	public void cancelVinculo() {
		Job.create("", monitor->{
			if(vinculo!=null) {
				String query = "mutation{removerVinculo(id:%d)}";
				Optional<Boolean> data = graphQL.query(query, vinculo.getId()).getData("removerVinculo", Boolean.class);
				if(data.isPresent()) {
					loadvinculo(null);
					sfire(FUNCIONARIO$edit_vinculo, true);
				}
			}
			
		}).schedule();
	}
	

	public void cancelPF() {
		setLock(true);
		if (pf != null) {
			loadPF(null);
		}
		setLock(false);
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean isLock() {
		return lock;
	}
}
