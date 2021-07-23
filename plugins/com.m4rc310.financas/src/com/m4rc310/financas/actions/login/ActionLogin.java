package com.m4rc310.financas.actions.login;

import java.text.MessageFormat;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.m4rc310.financas.actions.ActionDefault;
import com.m4rc310.financas.constants.ConstLogin;
import com.m4rc310.financas.models.Acesso;
import com.m4rc310.financas.models.Funcionario;
import com.m4rc310.financas.models.ServerInfo;
import com.m4rc310.rcp.ui.statusbar.actions.ActionMessage;

@Creatable
public class ActionLogin extends ActionDefault implements ConstLogin {

	@Inject
	EModelService model;

	@Inject
	MApplication app;
	
	@Inject ActionMessage statusBar;

	private Acesso acesso;

//	@Inject Shell shell;

	public void init() {
		Job.create("Try connection", monitor -> {
			sfire(LOGIN$part_reset);
			sfire(LOGIN$wait_for_server);

			try {
//				String sn = HardwareInfo.getSerialNumber();
				String query = "{server_time{dataServidor}}";
				Optional<ServerInfo> data = graphQL.query(query).getData("server_time", ServerInfo.class);
				ServerInfo cal = data.get();
				sfire(LOGIN$server_connection_success);
				Acesso acesso = new Acesso();
				loadAcesso(acesso);
				
				String message = MessageFormat.format(m.textWaitForLogin, cal.getDataServidor().getTime());
				statusBar.message(message);
			} catch (Exception e) {
				sfire(LOGIN$server_connection_unsuccess);
				sfire(LOGIN$load_error, m.textConnectionUnsuccess, m.textConnectionUnsuccessInfo);
				loadAcesso(null);
			}

		}).schedule();

	}

	private void loadAcesso(Acesso acesso) {
		this.acesso = acesso;
		sfire(LOGIN$load_acesso, acesso);
	}
	
	
	public void authenticate() {
		Job.create("Login", monitor -> {
			String query = "{login(token:\"%s\"){id nomeCompleto nome sobrenome apelido phonetic}}";

			Optional<Funcionario> data = graphQL.query(query, acesso.getToken()).getData("login", Funcionario.class);
			if (data.isPresent()) {
				sync.asyncExec(() -> {
					eventBroker.send("login", false);
					eventBroker.send("load_funcionario", data.get());
					sfire(LOGIN$success);
				});
			}else {
				sfire(LOGIN$unsuccess);
				statusBar.message(m.textErrorAutentication);
			}
		}).schedule();

	}

	public void writingToken(String stoken) {
		sfire(LOGIN$report_prepare_to_auth, !stoken.isEmpty());
	}

	public void cancel(boolean force) {
		if (force) {
			sfire(LOGIN$close);
		} else {
			sfire(LOGIN$show_question_dialog, m.titleCancelAuthentication, m.textCancelAuthentication);
		}
	}
}
