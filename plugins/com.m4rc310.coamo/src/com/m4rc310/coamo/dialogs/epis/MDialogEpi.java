package com.m4rc310.coamo.dialogs.epis;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.m4rc310.coamo.dialogs.MDialog;
import com.m4rc310.coamo.models.EPI;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

public class MDialogEpi extends MDialog implements ConstEpi {


	@Inject
	ActionEpi action;

	@Inject
	public MDialogEpi(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));
		pc.fillHorizontalComponent(parent);

		Composite line = pc.getComposite(parent);
		line.setLayout(new GridLayout(5, false));

		Label label = getLabel(line, "Cadastro de EPI");
		pc.fillHorizontalComponent(label).horizontalSpan = 5;

		label = pc.getLabel(line, "Código:");
		Text textId = pc.getText(line, "", SWT.BORDER | SWT.CENTER);
		pc.groupControl(textId, label);
		pc.setWidth(textId, 5);
		pc.addTextModifyListener(textId, e -> {
			Text t = (Text) e.widget;
			action.writingId(t.getText());
		});

		ToolBar toolbar = new ToolBar(line, SWT.NONE);
		ToolItem itemSearch = getToolItem(toolbar, ICONS$search);
		itemSearch.addListener(SWT.Selection, e->action.searchEpi());

		Composite stack = pc.getStackComposite(line);
		Button buttonAdvance = pc.getButton(stack, "Avançar", e -> action.advance(textId.getText()));
		Button buttonLoading = pc.getButton(stack, "Aguarde...");
		Button buttonCancel = pc.getButton(stack, "Cancelar", e -> action.cancel());
		
		final KeyAdapter keyListenerF3 = new KeyAdapter() {
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				if(e.keyCode==SWT.F3) {
					action.enableDesableClone();
				}
			}
		};
		
		Text textResumo = pc.getText(line, "");
		textResumo.setMessage("Descrição resumida");
		textResumo.setTextLimit(35);
		pc.setWidth(textResumo, 35);
		pc.addTextModifyListener(textResumo, e->{
			String sresumo = ((Text)e.widget).getText();
			action.writingResumo(sresumo);
		});
		
		textResumo.addKeyListener(keyListenerF3);

		Text textDescricao = pc.getText(line, "");
		textDescricao.setMessage("Descrição completa");
		pc.addTextModifyListener(textDescricao, e->{
			String descricao = ((Text)e.widget).getText();
			action.writingDescricao(descricao);
		});
		
		textDescricao.addKeyListener(keyListenerF3);

		pc.fillHorizontalComponent(textDescricao).horizontalSpan = 5;
		pc.configureUpperCase(textDescricao, textResumo);
		
//		getShell().getDisplay().addFilter(SWT.F3, e->{
//			action.enableDesableClone();
//			System.out.println("f3");
//		});

		final MChangeListener listener = e -> {
			action.changingEpi((EPI) e.getSource());
		};

		label = pc.getLabel(parent, "", SWT.SEPARATOR | SWT.HORIZONTAL);
		pc.fillHorizontalComponent(label);

		getShell().addListener(SWT.Show, e -> {
			action.init();
		});
		
		action.addListener(this, EPI$informe_descricao, e->{
			String s = e.getValue(String.class);
			textDescricao.setText(s);
		});
		
		action.addListener(this, EPI$informe_resumo, e->{
			String s = e.getValue(String.class);
			textResumo.setText(s);
		});

		action.addListener(this, EPI$prepare_to_advance, e -> {
			boolean prepare = e.getValue(boolean.class);
			pc.enabled(!prepare, itemSearch);
			pc.enabled(prepare, buttonAdvance);
			pc.toTopControl(buttonAdvance);
			pc.setDefaultButton(buttonAdvance);
		});

		action.addListener(this, EPI$wait_for_data, e -> {
			pc.enabled(false, textId, textDescricao, itemSearch);
			pc.toTopControl(buttonLoading);
		});

		action.addListener(this, EPI$load_epi, e -> {
			EPI epi = e.getValue(EPI.class);
			pc.observeWidget(textId, "id", epi, "%03d");
			pc.observeWidget(textResumo, "resumo", epi);
			pc.observeWidget(textDescricao, "descricao", epi);
			pc.addObserverListener(epi, listener);
		});

		action.addListener(this, EPI$changed_epi, e -> {
			boolean changed = e.getValue(boolean.class);
			Button button = getButton(OK_ID);
			pc.enabled(changed, button);
			getShell().setDefaultButton(button);
		});

		action.addListener(this, EPI$in_edition, e -> {
			pc.enabled(false, textId, itemSearch);
			pc.enabled(true, textDescricao, textResumo);
			pc.toTopControl(buttonCancel);
			pc.grabFocus(textResumo);
		});
		
		action.addListener(this, EPI$close_dialog, e -> {
			super.close();
		});

		action.addListener(this, EPI$reset_dialog, e -> {
			pc.toTopControl(buttonAdvance);
			pc.enabled(true, itemSearch);
			pc.enabled(false, buttonAdvance, buttonLoading,textResumo, textDescricao, getButton(OK_ID), getButton(REMOVE_ID));
			pc.grabFocus(textId);
		});

		return parent;
	}

	@Override
	protected Control createButtonBar(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(4, false));

		createButton(parent, REMOVE_ID, "Remover", false);

		Label label = pc.getLabel(parent, "");
		pc.fillHorizontalComponent(parent);
		pc.fillHorizontalComponent(label);

		createButton(parent, CANCEL_ID, "Cancelar", false);
		createButton(parent, OK_ID, "Adicionar", true);

		pc.clearMargins(parent);

		return parent;
	}

	@Override
	protected void okPressed() {
		action.save();
	}

	@Override
	protected void cancelPressed() {
		action.close();
	}

	@Override
	public boolean close() {
		action.close();
		return false;
	}

	
}
