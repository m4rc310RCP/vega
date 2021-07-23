package com.m4rc310.coamo.dialogs.formacao;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.IDialogConstants;
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
import com.m4rc310.coamo.models.Formacao;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

public class MDialogFormacao extends MDialog implements ConstFormacao {

	private final int REMOVE_ID = "remove_id".hashCode();
	private final int CANCEL_ID = IDialogConstants.CANCEL_ID;
	private final int OK_ID = IDialogConstants.OK_ID;

	@Inject
	ActionFormacao action;

	final MChangeListener listener = e -> {
		action.changeFormacao((Formacao) e.getSource());
	};

	@Inject
	public MDialogFormacao(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent_) {

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));
		pc.fillHorizontalComponent(parent);

		Composite line = pc.getComposite(parent);
		pc.clearMargins(line, parent, parent_);
		line.setLayout(new GridLayout(6, false));
		Label label = getLabel(line, "Certificação Treinamento ou Formação:");
		pc.fillHorizontalComponent(label).horizontalSpan = 6;

		label = pc.getLabel(line, "Código:");
		Text textId = pc.getText(line, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textId, 4);
		pc.groupControl(textId, label);
		pc.addTextModifyListener(textId, e -> action.writingFormacaoId(textId.getText()));

		ToolBar toolBar = new ToolBar(line, SWT.FLAT);
		ToolItem itemSearch = getToolItem(toolBar, ICONS$search);
		itemSearch.addListener(SWT.Selection, e->{
			action.search();
		});

		Composite stack = pc.getStackComposite(line);
		Button buttonClear = pc.getButton(stack, "Limpar", e -> action.reset());
		Button buttonNew = pc.getButton(stack, "Nova(o)", e -> action.newFormacao());
		Button buttonAdvance = pc.getButton(stack, "Avançar", e -> action.advance(textId.getText()));
		Button buttonLoading = pc.getButton(stack, "Carregando...");

		Text textSigla = pc.getText(line, "", SWT.BORDER | SWT.CENTER);
		textSigla.setMessage("Sigla");
		pc.setWidth(textSigla, 6);

		Text textDescricao = pc.getText(line, "");
		textDescricao.setMessage("Descrição");
		pc.setWidth(textDescricao, 28);
		pc.configureUpperCase(textSigla, textDescricao);
		pc.groupControl(textSigla, textDescricao);

		label = pc.getLabel(parent, "", SWT.SEPARATOR | SWT.HORIZONTAL);
		pc.fillHorizontalComponent(label);

		action.addListener(this, FORMACAO$report_changed, e -> {
			boolean changed = e.getValue(boolean.class);
			Button button = getButton(OK_ID);
			pc.enabled(changed, button);
			getShell().setDefaultButton(button);
		});
		
		action.addListener(this, FORMACAO$enable_delete_option, e -> {
			boolean enabled = e.getValue(boolean.class);
			pc.enabled(enabled, getButton(REMOVE_ID));
		});

		action.addListener(this, FORMACAO$load_formacao, e -> {

			Formacao formacao = e.getValue(Formacao.class);
			pc.observeWidget(textId, "id", formacao, "%03d");
			pc.observeWidget(textSigla, "sigla", formacao);
			pc.observeWidget(textDescricao, "descricao", formacao);
			
			if(formacao!=null) {
				pc.addObserverListener(formacao, listener);
			}
		});

		action.addListener(this, FORMACAO$in_edition, e -> {
			pc.toTopControl(buttonClear);
			pc.enabled(false, textId, itemSearch, getButton(OK_ID));
			pc.enabled(true, textSigla);
			pc.grabFocus(textSigla);
		});

		action.addListener(this, FORMACAO$wait, e -> {
			pc.toTopControl(buttonLoading);
			pc.enabled(false, textId, itemSearch);
		});

		action.addListener(this, FORMACAO$empty_id, e -> {
			boolean empty = e.getValue(boolean.class);
			pc.toTopControl(empty ? buttonNew : buttonAdvance);
			pc.setDefaultButton(empty ? buttonNew : buttonAdvance);
			pc.enabled(empty, itemSearch);
		});

		action.addListener(this, FORMACAO$reset_dialog, e -> {
			pc.toTopControl(buttonNew);
			pc.setDefaultButton(buttonNew);
			pc.enabled(false, getButton(OK_ID), getButton(REMOVE_ID), textSigla, buttonLoading);
			pc.enabled(true, itemSearch);
			pc.grabFocus(textId);

		});

		getShell().addListener(SWT.Show, e -> {
			action.init();
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
		createButton(parent, OK_ID, "Efetivar", true);

		pc.clearMargins(parent);

		return parent;
	}
	
	@Override
	protected void okPressed() {
		action.save();
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
		if(buttonId == REMOVE_ID) {
			action.remover();
		}
	}


}
