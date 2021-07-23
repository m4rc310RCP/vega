package com.m4rc310.coamo.dialogs.atividades;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
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
import com.m4rc310.coamo.models.Atividade;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

public class DialogAtividade extends MDialog implements ConstAtividade {

	@Inject
	ActionAtividade action;

	@Inject
	public DialogAtividade(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));
		pc.fillHorizontalComponent(parent);
		pc.clearMargins(parent);

		Composite line = pc.getComposite(parent);
		line.setLayout(new GridLayout(5, false));
		pc.fillHorizontalComponent(line);

		Label label = getLabel(line, "Cadastro de Atividade");
		pc.fillHorizontalComponent(label).horizontalSpan = 5;

		label = pc.getLabel(line, "Código:");
		Text textId = pc.getText(line, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textId, 5);
		pc.configureNumericValues(textId);
		pc.groupControl(textId, label);
		pc.addTextModifyListener(textId, e->{
			String sid = ((Text)e.widget).getText();
			action.writingId(sid);
		});

		ToolBar toolBar = new ToolBar(line, SWT.FLAT);
		ToolItem itemSearch = getToolItem(toolBar, ICONS$search);
		itemSearch.addListener(SWT.Selection, e->{
			action.searchAtividade();
		});

		Composite stack = pc.getStackComposite(line);

		Button buttonAdvance = pc.getButton(stack, "Avançar", e->action.advance(textId.getText()));
		Button buttonWait = pc.getButton(stack, "Buscando...");
		Button buttonCancel = pc.getButton(stack, "Cancelar", e->action.cancel());

		pc.toTopControl(buttonAdvance);

		Text textDescription = pc.getText(line, "");
		pc.setWidth(textDescription, 38);
		pc.configureUpperCase(textDescription);
		textDescription.setMessage("Descrição da Atividade de Risco");
		

		getShell().addListener(SWT.Show, e -> {
			action.init();
		});
		
		final MChangeListener listener = e->{
			Atividade a = (Atividade) e.getSource();
			action.change(a);
		};

		action.addListener(this, ATIVIDADE$in_edition, e -> {
			pc.enabled(false, textId, itemSearch);
			pc.enabled(true, buttonCancel, textDescription);
			pc.grabFocus(textDescription);
			pc.toTopControl(buttonCancel);
		});
		
		action.addListener(this, ATIVIDADE$changed_atividade, e -> {
			boolean changed = e.getValue(boolean.class);
			Button button = getButton(OK_ID);
			pc.enabled(changed, button);
			getShell().setDefaultButton(button);
		});
		
		action.addListener(this, ATIVIDADE$load_atividade, e -> {
			Atividade atividade = e.getValue(Atividade.class);
			pc.observeWidget(textId, "id", atividade, "%03d");
			pc.observeWidget(textDescription, "descricao", atividade);
			pc.addObserverListener(atividade, listener);
		});
		
		action.addListener(this, ATIVIDADE$wait_for_action, e -> {
			pc.enabled(false, textId, textDescription, buttonAdvance, buttonCancel, buttonWait, getButton(OK_ID),
					getButton(REMOVE_ID));
			pc.toTopControl(buttonWait);
		});
		
		action.addListener(this, ATIVIDADE$prepare_to_advance, e -> {
			boolean prepare = e.getValue(boolean.class);
			pc.enabled(prepare, buttonAdvance);
			pc.enabled(!prepare, itemSearch);
			pc.setDefaultButton(buttonAdvance);
		});

		action.addListener(this, ATIVIDADE$reset_dialog, e -> {
			pc.enabled(false, textDescription, buttonAdvance, buttonCancel, buttonWait, getButton(OK_ID),
					getButton(REMOVE_ID));
			pc.enabled(true, textId, itemSearch);
			pc.toTopControl(buttonAdvance);
			pc.grabFocus(textId);
		});
		
		action.addListener(this, ATIVIDADE$close_dialog, e -> {
			super.close();
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
		close();
	}

	@Override
	public boolean close() {
		action.cancel();
		return false;
	}


}
