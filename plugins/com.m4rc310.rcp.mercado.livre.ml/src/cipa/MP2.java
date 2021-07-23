package cipa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Data;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.GrupoLocais;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.NivelRisco;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Risco;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.TipoRisco;

@Creatable
@Singleton
public class MP2 {

	@Inject MGraphQL graphQL;

//	private final List<GrupoLocais> locais = new ArrayList<>();
	
	
	
	
	public List<GrupoLocais> local(Long id){
		return local_(id);
	}
	public List<GrupoLocais> local(int id){
		return local2_(id);
	}
	
	public String getRiscos(List<Risco> riscos) {
		Iterator<Risco> itrs = riscos.iterator();
		
		StringBuilder sb = new StringBuilder();
		
		boolean f = true;
		
		while (itrs.hasNext()) {
			Risco risco = (Risco) itrs.next();
			sb.append(f ? "": itrs.hasNext()?", ":" e ");
			sb.append(risco.getDescricao());
			sb.append(itrs.hasNext()?"":".");
			f=false;
		}
		
		return sb.toString();
	}
	
	public List<NivelRisco> niveisRiscos(){
		List<NivelRisco> ret = new ArrayList<>();
		
		NivelRisco nr = new NivelRisco();
		nr.setDescricao("Grande Risco");
		nr.setEscala(0.9f);
		ret.add(nr);
		
		nr = new NivelRisco();
		nr.setDescricao("Médio Risco");
		nr.setEscala(0.7f);
		ret.add(nr);
		
		
		nr = new NivelRisco();
		nr.setDescricao("Pequeno Risco");
		nr.setEscala(0.6f);
		ret.add(nr);
		
		
		
		return ret;
	}
	
	public List<Local> getLocais(List<GrupoLocais> grupos){
		List<Local> ret = new ArrayList<>();
		
		for (GrupoLocais g : grupos) {
			ret.addAll(g.getLocais());
		}
		
		return ret;
	}
	
	public List<TipoRisco> tiposRiscos(){
		String query = "{tiposRisco{numero cor descricao grupoRisco id}}";
		try {
			Data data = get().graphQL.query(query, Data.class);
			return data.getTiposRisco();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	private List<GrupoLocais> local2_(int numero){
		String query = "{grupoLocalFromNumero(numero:%d){id descricao numero locais{descricao riscos{descricao grupoRisco tamanho}}}}";
		query = String.format(query, numero);
		
		try {
			Data data = get().graphQL.query(query, Data.class);
			return Arrays.asList(data.getGrupoLocalFromNUmero());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<GrupoLocais> local_(Long id){
		
		String query = "{grupoLocalFromId(id:%d){id descricao numero locais{descricao riscos{descricao grupoRisco tamanho}}}}";
		query = String.format(query, id);
		
		try {
			Data data = get().graphQL.query(query, Data.class);
			return Arrays.asList(data.getGrupoLocal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		if(locais.isEmpty()) {
//			String query = "{gruposLocais{id locais{id descricao riscos{grupoRisco tamanho}}}}";
//			try {
//				Data data = get().graphQL.query(query, Data.class);
//				locais.addAll(data.getGruposLocais());
//			} catch (Exception e) {
//			}
//		}
		
		
//		if(locais.isEmpty()) {
//			String query = "{locais{id descricao riscos{id descricao grupoRisco tamanho}}}";
//			try {
//				Data data = get().graphQL.query(query, Data.class);
//				locais.addAll(data.getLocais());
//			} catch (Exception e) {
//			}
//		}
//
//		for (GrupoLocais g : locais) {
//			if(g.getId().equals(id)) {
//				return Arrays.asList(g);
//			}
//		}
		
		return null;
	}
	
	private MP2 get() {
		IEclipseContext context = EclipseContextFactory.create();
		return ContextInjectionFactory.make(MP2.class, context);
	}
	
	
}
