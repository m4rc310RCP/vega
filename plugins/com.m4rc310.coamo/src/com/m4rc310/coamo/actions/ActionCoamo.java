package com.m4rc310.coamo.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.brazilutils.br.cpfcnpj.CpfCnpj;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.workbench.UIEvents;

import com.m4rc310.coamo.MConstants;
import com.m4rc310.coamo.models.Funcionario;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.coamo.models.ServerInfo;
import com.m4rc310.coamo.models.Sexo;
import com.m4rc310.coamo.models.Unidade;
import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.ui.statusbar.actions.ActionMessage;
import com.m4rc310.rcp.ui.utils.DateUtils;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.MD5Utils;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import reports.utils.R;

@Creatable
//@Singleton
public class ActionCoamo extends MAction implements IActionCoamoConsts, MConstants {

	@Inject
	MGraphQL graphQL;

	@Inject
	IEventBroker eventBroker;

	@Inject
	@Preference(nodePath = PLUGIN_ID)
	IEclipsePreferences prefs;

	@Inject
	UISynchronize sync;

	@Inject
	ActionNavigation navigation;

	@Inject
	ActionMessage actionMessage;

	private PessoaFisica pf;

	private String baseChecksun;

	@Inject
	IEclipseContext context;

//	@Inject
//	public void monitoreServerChanges() {
//		
//		
//	}

	public void informeCtpsDigital(boolean digital) {
		fire(FIRE$enable_ctps_digital, digital);
	}

	public void serverInfo() {
		String query = "{serverInfo{date version}}";
		Optional<ServerInfo> data = graphQL.query(query).getData("serverInfo", ServerInfo.class);
		if (data.isPresent()) {
			System.out.println(data.get().getDate().getTime());
		}
	}

	public void open(Object element) {
		if (element instanceof Funcionario) {
			Funcionario f = (Funcionario) element;
			String title = String.format("%s", f.getDados().getNome());
			navigation.showPart("com.m4rc310.coamo.partdescriptor.funcionario", title, title, f);
		}
	}

	public void escrevendoCPF(String scpf, boolean semCpf) {
		if (!semCpf) {
			CpfCnpj cc = new CpfCnpj(scpf);
			boolean valid = cc.isCpf() && cc.isValid();
			sync.syncExec(() -> fire(FIRE$report_cpf_status, valid));
		}
	}

	public void avancarCadastroPF(String scpf, boolean semCpf) {
		Job.create("Buscando...", monitor -> {
			sync.syncExec(() -> fire(FIRE$report_aguarde_por_pf));

			String query = "{pessoaFisica(cpf:\"%s\" semCPF:%s){id nome nascimento cpf ativo bloqueado funcionario rg{id numero emissor uf dataEmissao} "
					+ "cnh{id categoria numero validade}  ctps{id numero digital serie dataCadastro dataExpedicao uf}}}";

			Optional<PessoaFisica> data = graphQL.query(query, scpf, semCpf).getData("pessoaFisica",
					PessoaFisica.class);
			if (data.isPresent()) {
				try {
					PessoaFisica fisica = data.get();
					setPessoaFisica(fisica);
					informeCtpsDigital(fisica.getCtps().getDigital());
				} catch (Exception e) {
					informeCtpsDigital(false);
				}
			}

		}).schedule();
	}

	public void setPessoaFisica(PessoaFisica pf) {
		this.pf = pf;
		this.baseChecksun = MD5Utils.getChecksum(pf);
		sync.syncExec(() -> fire(FIRE$load_pf, pf));

	}

	public void cancelarCadastroPF() {
		setPessoaFisica(null);
		sync.syncExec(() -> fire(FIRE$cancelar_cadastro_pf));
		sync.syncExec(() -> fire(FIRE$enable_save_pf, false));
	}

	public void avancarSemCPF(boolean semCpf) {
		sync.syncExec(() -> fire(FIRE$avancar_sem_cpf, semCpf));
	}

	public void save() {
		if (pf != null) {
			String query = "mutation{cadastrarPessoaFisica(pessoaFisica:%s){id nome  ativo nascimento bloqueado funcionario}}";
			Optional<PessoaFisica> data = graphQL.query(query, graphQL.toGraph(pf)).getData("cadastrarPessoaFisica",
					PessoaFisica.class);
			if (data.isPresent()) {
				cancelarCadastroPF();
			}
		}
	}

	public void searchForUnidade() {
		Job.create("initing", monitor -> {

			message("Iniciando...");

			String query = "{unidadeIsEmpty}";
			Optional<Boolean> data = graphQL.query(query).getData("unidadeIsEmpty", Boolean.class);

			Boolean unidadeIsEmpty = data.get();

			if (unidadeIsEmpty) {
				fire(FIRE$show_perspective);
			}

			prefs.putBoolean(PREFERENCE$unidade_is_empty, !unidadeIsEmpty);

			reportAllHandler();
			message("");
		}).schedule();
	}

	public void update(@UIEventTopic(EVENT_TOPIC$update) String ref) {
		startedUnidadePart();
	}

	public void startedUnidadePart() {
		Job.create("initing", monitor -> {
			message("Buscando pela unidade padrao...");

//			String query = "{unidades{id nome numero quantidadeFuncionarios lotacoes{id nome numero funcionarios{id matricula dados{nome}  vinculo{id codigo contrato{paragrafoEmpregador}}}}}";
			List<Unidade> unidades = graphQL.queryInFile(PLUGIN_ID, "queries/QueryUnidade").getDataList("unidades",
					Unidade.class);
			fire(FIRE$load_unidade, unidades);

			List<Sexo> listSexo = new ArrayList<>();
			for (Unidade unidade : unidades) {
				listSexo = unidade.getListSexo();
			}

			context.set("list_sexo", listSexo);
			context.set("list_unidades", unidades);
			if (!unidades.isEmpty()) {
				context.set("unidade", unidades.get(0));
			}

			message("Unidade padrão: %s", unidades.size());
		}).schedule();
	}

	private void message(String text, Object... args) {
		text = String.format(text, args);
		actionMessage.message(text);
	}

	private void reportAllHandler() {
		eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
	}

	public void loadContratoTrabalho(Funcionario funcionario) {
		Job.create("Loading...", monitor -> {
			try {
				sync.syncExec(() -> actionMessage.message("Carregando o Contrato de Trabalho..."));
				sync.syncExec(() -> fire(FIRE$report_loading, true));
				Map<String, Object> params = new HashMap<>();
				JasperReport report = R.getReport("com.m4rc310.coamo.contrato_trabalho");
				JasperPrint print = R.getJasperPrint(report, params, Arrays.asList(funcionario));
				sync.syncExec(() -> fire(FIRE$load_contrato_trabalho, print));
				sync.syncExec(() -> actionMessage.message(""));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sync.syncExec(() -> fire(FIRE$report_loading, false));
				sync.syncExec(() -> actionMessage.message(""));
			}
		}).schedule();
	}

	public void loadDadosFuncionario(Funcionario funcionario) {
		Job.create("loading dados funcionario", monitor -> {
			sync.syncExec(() -> fire(FIRE$report_loading, true));
			sync.asyncExec(() -> {
				fire(FIRE$report_loading, true);
				fire(FIRE$load_funcionario, funcionario);
				fire(FIRE$report_loading, false);
			});
		}).schedule();
	}

	public void verifyConnectionAvailable() {
		try {
			Optional<ServerInfo> ret = graphQL.query("{serverInfo{date}}").getData("serverInfo", ServerInfo.class);
			if (ret.isPresent()) {
				navigation.switchPerspective(PERSPECTIVE$coamo);
				navigation.showPart("com.m4rc310.coamo.partdescriptor.unidade");
				navigation.showPart("com.m4rc310.coamo.partdescriptor.grupos.certificacoes");

				navigation.requestAtivation();

			}

		} catch (Exception e) {
			navigation.switchPerspective(PERSPECTIVE$unconnected);
		}

//		Job.create("loading dados funcionario", monitor -> {
//			boolean available = pingURL(graphQL.getServerUrl(), 3000);
//			if (available) {
//				navigation.switchPerspective(PERSPECTIVE$coamo);
//			} else {
//				navigation.switchPerspective(PERSPECTIVE$unconnected);
//			}
//		}).schedule();
	}

	public void changePF(PessoaFisica pf) {

		if (pf == null) {
			return;
		}

		fireInCache(FIRE$enable_save_pf, !baseChecksun.equals(MD5Utils.getChecksum(pf)));

		if (pf.getNascimento() != null) {

			String sidade = "";
			try {
				Long idade = DateUtils.getIdade(pf.getNascimento().getTime());
				sidade = String.format("%d anos de idade.", idade);
			} catch (Exception e) {
				sidade = "-";
			}

			fireInCache(FIRE$report_idade, sidade);

		}
	}

//	private boolean netIsAvailable() {
//		try {
//			final URL url = new URL(graphQL.getServerUrl());
//			final URLConnection conn = url.openConnection();
//			conn.connect();
//			conn.getInputStream().close();
//			return true;
//		} catch (MalformedURLException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

}
