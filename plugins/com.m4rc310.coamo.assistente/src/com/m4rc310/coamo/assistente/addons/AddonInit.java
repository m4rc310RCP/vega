
package com.m4rc310.coamo.assistente.addons;

import java.text.MessageFormat;
import java.util.Date;

import javax.inject.Inject;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.framework.Version;

import com.m4rc310.coamo.assistente.actions.ActionFuncionario;
import com.m4rc310.coamo.assistente.constants.ConstFuncionario;
import com.m4rc310.coamo.assistente.constants.ConstSetor;
import com.m4rc310.coamo.assistente.i18n.M;
import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.ui.utils.CronUtils;
import com.m4rc310.rcp.ui.utils.PartControl;

public class AddonInit implements ConstFuncionario, ConstSetor {

	@Inject
	EModelService model;

	@Inject
	MApplication app;

	@Inject
	IEventBroker eventBroker;

	@Inject
	@Preference(nodePath = GLOBAL$plugin_id)
	IEclipsePreferences prefs;

	@Inject
	@Translation
	M m;

	@Inject
	CronUtils cronUtils;

	@Inject
	ActionFuncionario action;

	@Inject
	EModelService service;

	@Inject
	MGraphQL graphql;

	@Inject
	PartControl pc;

//	private final String TOOLBAR_ITEM_SHOW_FUNCIONARIOS = "com.m4rc310.coamo.assistente.handledtoolitem.showListFuncionarios";
//	private final String MENU_ITEM_SHOW_FUNCIONARIOS = "com.m4rc310.coamo.assistente.handledmenuitem.text";

	@Inject
	@Optional
	public void applicationStarted() {

		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, e -> {

			eventBroker.send(GLOBAL$ref_register_ws_subscribes, "Initial subscribes");

			updateTitle();
			initActionFuncionario(action);

			java.util.Optional<Boolean> data = graphql.query("{test}").getData("test", Boolean.class);
			if (data.isPresent()) {
//				pc.show(SETOR$part_url);
			}

			cronUtils.cron("0 0/1 * * * ?", () -> {
				updateTitle();
			});

			graphql.connectWebSocket();

		});

	}

	public void initActionFuncionario(ActionFuncionario action) {
		updateHandlers();
	}

	private void updateTitle() {

		try {
			Version v = Platform.getBundle(GLOBAL$plugin_id).getVersion();

			String version = String.format("%d.%d.%d", v.getMajor(), v.getMinor(), v.getMicro());
			String title = MessageFormat.format(m.titleAppTitle, new Date(), version);
//				title = String.format("%s - %s", m.titleAppTitle, title);

			((MWindow) model.find("com.m4rc310.vega.rcp.window.main", app)).setLabel(title);
//				prefs.putBoolean(GLOBAL$key_test_mode, MODE_TESTE);
//				prefs.flush();
//				
//				actionNavigation.switchPerspective(GLOBAL$perspective_id_main);
//				actionMessage.message(m.textWelcome);

		} catch (Exception e2) {
		}

	}

	private void updateHandlers() {
		eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
	}

}
