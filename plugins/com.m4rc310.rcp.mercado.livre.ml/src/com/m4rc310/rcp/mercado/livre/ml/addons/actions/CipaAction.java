package com.m4rc310.rcp.mercado.livre.ml.addons.actions;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.nls.Translation;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Data;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.GrupoLocais;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Risco;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Setor;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Unidade;
import com.m4rc310.rcp.mercado.livre.ml.i18n.Messages;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.streaming.MEvent;

@Creatable
@Singleton
public class CipaAction extends MAction {
	
	@Inject @Translation
	private Messages m;
	
	public static final String UPDATE_VALUES = "update_values";
	public static final String INFORME_ERRO = "informe_erro";
	public static final String LOAD_UNIDADES = "load_unidades";

	public static final String UNIDADE_QUERY = "{id nome numero setores{id descricao lotacao grupos{id descricao numero numeroPessoas locais{id descricao numeroPessoas riscos{id descricao grupoRisco tamanho}}}}}";
	
//	public static final String ACTION_PERSIST = "action_persist";
//	public static final String ACTION_REMOVE = "action_remove";
//	
	@Inject
	private MGraphQL graphQL;
	
	
	public void novoSetor(Unidade unidade,int lotacao, String descricao) {
		try {
			String query ="mutation{addSetor(lotacao:%d numUnidade:%d descricao:\"%s\"){%s}}";
			query = String.format(query, lotacao, unidade.getNumero(), descricao, UNIDADE_QUERY);
			
			System.out.println(query);
			
			
		} catch (Exception e) {
			stream.fireListener(MEvent.event(this, INFORME_ERRO, e));
		}
	}
	
	public void removerSetor(Setor setor) {
		try {
//			Assert.assertNotEmpty(setor.getGrupos(), m.erroRemoverSetorExisteGrupos);
			
			
		} catch (Exception e) {
			stream.fireListener(MEvent.event(this, INFORME_ERRO, e));
		}
	}
	
	
	
	public void addRisco(Local local, String descricao, String srisco) {
		try {
			String query ="mutation{risco(idLocal:%d descricao:\"%s\" srisco:\"%s\")%s}";
			query = String.format(query,local.getId(), descricao, srisco, UNIDADE_QUERY);
			
			Data data = graphQL.query(query, Data.class);
			List<Unidade> unidades = data.getUnidadesFromRisco();
			stream.fireListener(MEvent.event(this, LOAD_UNIDADES, unidades ));
		} catch (Exception e) {
			stream.fireListener(MEvent.event(this, INFORME_ERRO, e));
		}
	}
	
	
	
	public void addGrupoLocal(int numero, Setor setor, String descricao) {
		try {
//			Assert.assertNotNull(setor, m.erroLotacaoNaoSelecionada);
			String query = "mutation{grupoLocal(numero:%d lotacao:%d descricao:\"%s\")%s}";
			query = String.format(query, numero, setor.getLotacao(), descricao, UNIDADE_QUERY);
			
			System.out.println(query);
			
			Data data = graphQL.query(query, Data.class);
			List<Unidade> unidades = data.getUnidadeGrupoLocal();
			stream.fireListener(MEvent.event(this, LOAD_UNIDADES, unidades ));
		} catch (Exception e) {
			
			e.printStackTrace();
			
			stream.fireListener(MEvent.event(this, INFORME_ERRO, e));
		}
	}
	
	public void removerGrupoLocal(GrupoLocais grupo) {
		try {
//			Assert.assertTrue(grupo.getLocais().isEmpty(), m.errorRemoverGrupoLocalExisteLocais);
			
			String query = "mutation{removerGrupoLocal(id:%d)%s}";
			query = String.format(query, grupo.getId(),UNIDADE_QUERY);
			Data data = graphQL.query(query, Data.class);
			stream.fireListener(MEvent.event(this, LOAD_UNIDADES, data.getUnidadeResp()));
		} catch (Exception e) {
			
			stream.fireListener(MEvent.event(this, INFORME_ERRO, e));
		}
	}
	
	public void removerLocal(Local local) {
		try {
			String query = "mutation{removerLocal(id:%d)%s}";
			query = String.format(query, local.getId(),UNIDADE_QUERY);
			Data data = graphQL.query(query, Data.class);
			stream.fireListener(MEvent.event(this, LOAD_UNIDADES, data.getRemoverLocal()));
		} catch (Exception e) {
			stream.fireListener(MEvent.event(this, INFORME_ERRO, e));
		}
	}
	
	

	public void addLocal(GrupoLocais grupo, Local local) {
		try {
			String query = "mutation{addLocal(pessoas:%d, descricao:\"%s\" idGrupo:%d)%s}";
			query = String.format(query, 
					local.getNumeroPessoas(), 
					local.getDescricao(),
					grupo.getId(),UNIDADE_QUERY);
			Data data = graphQL.query(query, Data.class);
			stream.fireListener(MEvent.event(this, LOAD_UNIDADES, data.getAddLocal()));
		} catch (Exception e) {
			stream.fireListener(MEvent.event(this, INFORME_ERRO, e));
		}
	}

	public void removerRisco(Risco risco) {
		try {
			String query = "mutation{removerRisco(riscoId:%d)%s}";
			query = String.format(query, risco.getId(),UNIDADE_QUERY);
			
			Data data = graphQL.query(query, Data.class);
			stream.fireListener(MEvent.event(this, LOAD_UNIDADES, data.getRemoverRisco()));
		} catch (Exception e) {
			stream.fireListener(MEvent.event(this, INFORME_ERRO, e));
		}
	}
}
