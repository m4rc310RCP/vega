package com.m4rc310.coamo.actions;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.coamo.dialogs.pessoa.fisica.n.DialogSearchPF;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.PartControl;

@Creatable
public class ActionSearch extends ActionCoamo {
	
	@Inject PartControl pc;
	
	public void openSearch() {
//		pc.showDialog(DialogSearchPessoaFisica.class, this);
		pc.showDialog(DialogSearchPF.class, this);
	}
	
	
	public void searchPessoaFisica(String nome) {
		
		String query = "{buscarForPessoaFisica(nome:\"%s\" pesquisaFonetica:true){id nome nascimento cpf ativo bloqueado funcionario "
				+ "rg{id numero emissor uf dataEmissao} "
				+ "cnh{id categoria numero validade}  "
				+ "ctps{id numero digital serie dataCadastro dataExpedicao uf}}}";
		
		List<PessoaFisica> list = graphQL.query(query, nome).getDataList("buscarForPessoaFisica", PessoaFisica.class);
		fire(FIRE$load_search_results, list);
	}
}
