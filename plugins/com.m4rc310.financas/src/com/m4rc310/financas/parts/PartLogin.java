
package com.m4rc310.financas.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.financas.actions.login.ActionLogin;
import com.m4rc310.financas.constants.ConstLogin;

public class PartLogin extends PartDefault implements ConstLogin {

	@Inject
	IStylingEngine engine;

	@Inject
	ActionLogin action;

	@Inject
	public PartLogin() {

	}

	@PostConstruct
	public void postConstruct(Composite content) {
		content.setLayout(new GridLayout());

		Group parent = getGroup(content, 1);
		parent.setLayout(new GridLayout(1, false));
		parent.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		getLabel(parent, m.titleLogin, 15);

		Composite sp = getComposite(parent, 1);
		GridData gd = new GridData();
		gd.heightHint = 1;
		sp.setLayoutData(gd);

//		pc.getLabel(parent, m.empty);

		pc.fillHorizontalComponent(pc.getLabel(parent, m.empty, SWT.HORIZONTAL | SWT.SEPARATOR));

		Composite stack = pc.getStackComposite(parent);

		Composite sideWait = getComposite(stack, 1);
		buildCompositeLoading(sideWait);

		Composite sideSuccess = getComposite(stack, 1);
		buildCompositeInforme(sideSuccess);

		Composite sideFail = getComposite(stack, 1);
		buildCompositeConnectionFail(sideFail);

		pc.fillHorizontalComponent(pc.getLabel(parent, m.empty, SWT.HORIZONTAL | SWT.SEPARATOR));

		buildComposieToken(parent);

		action.addListener(this, LOGIN$wait_for_server, e -> {
			pc.toTopControl(sideWait);
		});

		action.addListener(this, LOGIN$server_connection_unsuccess, e -> {
			pc.toTopControl(sideFail);
		});

		action.addListener(this, LOGIN$server_connection_success, e -> {
			pc.toTopControl(sideSuccess);
		});

		action.init();

	}

	private void buildCompositeLoading(Composite content) {
		Composite parent = getComposite(content, 2);
		parent.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, true, true));
		pc.margins(parent, 4);
		pc.fillHorizontalComponent(parent);

		engine.setClassname(parent, "MContainnerLoading");

		pc.getIcon(parent, "com.m4rc310.icons", "icons/16x16/others/hourglass.png");
//		GridData gd = new GridData();
//		gd.verticalSpan = 2;
//		icon.setLayoutData(gd);

		pc.getLabel(parent, m.textWaitForServer);
	}

	private void buildCompositeInforme(Composite content) {
		Composite parent = getComposite(content, 2);
		parent.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, true, true));
//		pc.fillHorizontalComponent(parent);
		pc.getIcon(parent, GLOBAL$plugin_id, "icons/ok.png");
		Label labelSuccess = pc.getLabel(parent, m.textConnectionSuccess);
		pc.fillHorizontalComponent(labelSuccess);
	}

	private void buildCompositeConnectionFail(Composite content) {
		Composite parent = getComposite(content, 2);
		engine.setClassname(parent, "MMessageInfo");
		pc.margins(parent, 2);
		pc.fillHorizontalComponent(parent);

		Label icon = pc.getIcon(parent, "com.m4rc310.icons", "icons/16x16/others/bullet_error.png");
		GridData gd = new GridData();
		gd.verticalSpan = 2;
		icon.setLayoutData(gd);

		Label labelError = pc.getLabel(parent, m.textConnectionUnsuccess);
		Link info = new Link(parent, SWT.NONE);
		info.setText(m.textConnectionUnsuccessInfo);
		info.addListener(SWT.Selection, e -> {
			action.init();
		});

		action.addListener(this, LOGIN$load_error, e -> {
			String error = e.getValue(0, String.class);
			String errorInfo = e.getValue(1, String.class);

			labelError.setText(error);
			info.setText(errorInfo);
		});

	}

	private void buildComposieToken(Composite content) {
		Composite parent = getComposite(content, 1);
		parent.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, true, true));

		Label label = pc.getLabel(parent, m.labelToken);

		Text textToken = pc.getText(parent, m.empty, SWT.BORDER | SWT.CENTER | SWT.PASSWORD);
		textToken.setMessage(m.textToken);
		pc.addTextListener(textToken, stoken -> action.writingToken(stoken));

		pc.groupControl(textToken, label);

		pc.fillHorizontalComponent(label);
		pc.fillHorizontalComponent(textToken);

		Button buttonOK = pc.getButton(parent, m.textAuthenticate, e -> action.authenticate());
//		pc.setWidth(textToken, 16);
		pc.setWidth(buttonOK, 10);
		buttonOK.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, true, true));

		action.addListener(this, LOGIN$part_reset, e -> {
			pc.enabled(false, textToken, buttonOK);
		});

		action.addListener(this, LOGIN$server_connection_success, e -> {
			pc.grabFocus(textToken);
		});

		action.addListener(this, LOGIN$report_prepare_to_auth, e -> {
			boolean valid = e.getValue(boolean.class);
			pc.enabled(valid, buttonOK);
			pc.setDefaultButton(buttonOK);
		});

	}
}