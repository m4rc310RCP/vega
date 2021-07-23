
package com.m4rc310.financas.addons;

import java.text.MessageFormat;
import java.util.Date;

import javax.inject.Inject;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;

import com.m4rc310.financas.constants.ConstGlobal;
import com.m4rc310.financas.dialogs.DialogLogin;
import com.m4rc310.rcp.ui.utils.CronUtils;
import com.m4rc310.rcp.ui.utils.PartControl;
import com.m4rc310.rcp.ui.utils.hardware.info.HardwareInfo;

public class AddonStart implements ConstGlobal{

	@Inject
	PartControl pc;

	
	
	@Inject IEclipseContext context;

	@Inject
	protected UISynchronize sync;
	
	@Inject Display display;
	
	@Inject
	CronUtils cron;
	
//	@Inject ActionLogin action;

//	@Inject @Optional Shell shell;

//	@Inject
//	@Optional
//	public void applicationStarted(
//			@EventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {
//		
//		System.out.println(shell);
//		
//		Job.create("starting", monitor->{
//			pc.showDialog(DialogLogin.class);
//		}).schedule(2000);
//	}

	@Inject
	@Optional
	public void start(@EventTopic("m_start") Event event) {
		Job.create("starting", monitor -> {
			sync.asyncExec(() -> {
				
				cron.cron("0 * * * * *", ()->{
					changeTitle();
				});
				
				changeTitle();
				showLogin(true);
				
//				pc.showDialog(DialogLogin.class,Display.getCurrent().getActiveShell());
			});
		}).schedule(2000);
	}
	
	private String defaultTitle;
	private void changeTitle() {
		MWindow window =  pc.find("com.m4rc310.vega.rcp.window.main", MWindow.class);
		defaultTitle = defaultTitle == null? window.getLabel(): defaultTitle;
		String title = "{0} - {1} - {2,date,dd/MM/yyyy HH:mm (EEEE)}";
		title = MessageFormat.format(title, defaultTitle, HardwareInfo.getSerialNumber(), new Date());
		
		window.setLabel(title);
	}
	
	@Inject @Optional
	public void switchPerspective(@EventTopic("switch_perspective")String perspective) {
		
		pc.switchPerspective(perspective);
		
//		MPerspective element = (MPerspective) model.find(perspective, app);
//		
//		EPartService service = app.getContext().getActiveLeaf().get(EPartService.class); 
//		service.switchPerspective(element);
	}
	
	@Inject
	@Optional
	public void showLogin(@EventTopic("login") boolean login) {
		if(login) {
			showMenu(false);
			switchPerspective(GLOBAL$perspective_id_login);
			pc.showDialog(DialogLogin.class,Display.getCurrent().getActiveShell());
		}else {
			showMenu(true);
			switchPerspective(GLOBAL$perspective_id_default);
		}
	}
	
	
	
	@Inject
	@Optional
	public void showMenu(@EventTopic("show_menu") boolean visible) {
		
		MTrimmedWindow window = pc.find("com.m4rc310.vega.rcp.window.main", MTrimmedWindow.class);
		window.getMainMenu().setToBeRendered(true);
		showElement("com.m4rc310.vega.rcp.trimbar.top", visible);
	}

	private void showElement(String id, boolean show) {
		
		MUIElement element = pc.find(id, MUIElement.class);
		if (element != null) {
			element.setVisible(show);
			element.setToBeRendered(show);
		}
	}
	
	
	

//	@Inject
//	@Optional
//	public void applicationStarted(@EventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {
////		sync.asyncExec(() -> {
////			final Shell shell = new Shell(SWT.INHERIT_NONE);
////			pc.showDialog(DialogLogin.class, shell);
////		});
//	}

}
