
package com.m4rc310.vega.rcp.addons;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.m4rc310.vega.rcp.Consts;
import com.m4rc310.vega.rcp.preferences.PreferenceConsts;

public class AddonInitPreferences implements PreferenceConsts, Consts {

	@Inject
	EModelService model;

	@Inject
	MApplication app;

//	@Inject
//	EModelService model;
//
//	@Inject
//	MApplication app;
//
//	@Inject
//	@Optional
//	public void init() {
//
//		IEclipsePreferences node = DefaultScope.INSTANCE.getNode("com.m4rc310.vega.rcp");
//
//		if (node != null) {
//
//			node.put(PREF_NAME_OF_THE_ESTABLISHMENT, "MLS");
//
//			try {
//				node.flush();
//			} catch (BackingStoreException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	@Inject
//	@Optional
//	public void updateUI(@EventTopic(UPDATE_UI) String log, @Preference(value = PREF_NAME_OF_THE_ESTABLISHMENT) String appCodeName) {
//		changeAppCodeName(appCodeName);
//	}

//	@Inject
//	@Optional
//	public void showMenu(@EventTopic("show_menu") boolean visible) {
//
//		List<MTrimmedWindow> elements = model.findElements(app, "com.m4rc310.vega.rcp.window.main",
//				MTrimmedWindow.class);
//		elements.forEach(tw -> {
//			tw.getMainMenu().setToBeRendered(visible);
//		});
//		
//		showElement("com.m4rc310.vega.rcp.trimbar.top", visible);
//	}
//
//	private void showElement(String id, boolean show) {
//		MUIElement element = model.find(id, app);
//		if (element != null) {
//			element.setVisible(show);
//			element.setToBeRendered(show);
//		}
//	}

//	@Inject
//	@Optional
//	public void changeAppCodeName(@EventTopic(PREF_APP_CODE_NAME) String appCodeName) {
//		String title = "%s - %s %s";
//		title = String.format(title, appCodeName, "Vega", "1.0.1");
//		
//		System.out.println(title);
//		
//		((MWindow) model.find("com.m4rc310.vega.rcp.window.main", app)).setLabel(title);
//	}
//

//
}
