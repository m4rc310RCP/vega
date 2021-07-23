package com.m4rc310.coamo.assistente.dialogs;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.m4rc310.coamo.assistente.actions.ActionFuncionario;
import com.m4rc310.coamo.assistente.constants.ConstFuncionario;
import com.m4rc310.coamo.assistente.models.Funcionario;
import com.m4rc310.coamo.assistente.models.Matricula;
import com.m4rc310.coamo.assistente.models.Setor;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

public class DialogFuncionario extends DialogDefault implements ConstFuncionario {

	@Inject
	ActionFuncionario action;

	final MChangeListener listener = e -> {
		action.changeFuncionario();
	};

	@Inject
	public DialogFuncionario(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Composite line = getComposite(parent, 2);
		makeTopComposite(line);
		makeCompositeSetor(line);

		line = getComposite(parent, 2);
		pc.fillHorizontalComponent(line);
		makeCompositeName(line);
		makeCompositeApelido(line);

		getShell().addListener(SWT.Show, e -> {
			action.init();
		});

		action.addListener(this, FUNCIONARIO$dialog_prepare_to_save, e -> {
			boolean ischange = e.getValue(boolean.class);
			pc.enabled(ischange, getButton(OK_ID));
			getShell().setDefaultButton(getButton(OK_ID));
		});

		action.addListener(this, FUNCIONARIO$dialog_dispose, e -> {
			super.close();
		});

		return parent;
	}

	private void makeTopComposite(Composite parent_) {
		Composite parent = getComposite(parent_, 1);
		Group group = pc.getGroup(parent);
		group.setLayout(new GridLayout(4, false));
		pc.clearMargins(group, parent);
		Label label = pc.getLabel(group, m.labelMatricula);
		Text textMatricula = pc.getText(group, m.empty, SWT.CENTER | SWT.BORDER);
		pc.setWidth(textMatricula, 9);
		pc.groupControl(textMatricula, label);

		ToolBar toolbar = new ToolBar(group, SWT.FLAT);
		ToolItem itemSearch = getToolItem(toolbar, ICONS$search);

		Composite stack = pc.getStackComposite(group);
		Button buttonLoading = pc.getButton(stack, m.textLoading);
		Button buttonCancel = pc.getButton(stack, m.textCancel, e -> action.reset());
		Button buttonAdvance = pc.getButton(stack, m.textAdvance, e -> action.advance(textMatricula.getText()));

		pc.addTextListener(textMatricula, smatricula -> action.writingMatricula(smatricula));

		action.addListener(this, FUNCIONARIO$dialog_loading, e -> {
			pc.toTopControl(buttonLoading);
			pc.enabled(false, textMatricula, itemSearch, buttonAdvance);
		});

		action.addListener(this, FUNCIONARIO$dialog_reset, e -> {
			pc.toTopControl(buttonAdvance);
			pc.enabled(false, buttonAdvance, buttonLoading, getButton(OK_ID));
			pc.enabled(true, textMatricula, itemSearch);
			pc.grabFocus(textMatricula);
		});

		action.addListener(this, FUNCIONARIO$dialog_prepare_to_search, e -> {
			pc.toTopControl(buttonAdvance);
			pc.enabled(false, buttonAdvance, buttonCancel);
			pc.enabled(true, itemSearch);
		});

		action.addListener(this, FUNCIONARIO$dialog_prepare_to_advance, e -> {
			boolean advance = e.getValue(boolean.class);
			pc.enabled(advance, buttonAdvance);
			pc.enabled(!advance, itemSearch);
			pc.toTopControlAndDefault(buttonAdvance);
		});

		action.addListener(this, FUNCIONARIO$dialog_in_edition, e -> {
			pc.toTopControl(buttonCancel);
			pc.enabled(true, buttonCancel);
		});

		action.addListener(this, FUNCIONARIO$dialog_load_funcionario, e -> {
			Funcionario funcionario = e.getValue(Funcionario.class);
			Matricula matricula = funcionario == null ? null : funcionario.getMatricula();
			pc.observeWidget(textMatricula, "matricula", matricula);
		});
	}

	private void makeCompositeSetor(Composite parent_) {
		Group parent = pc.getGroup(parent_);
		parent.setLayout(new GridLayout(3, false));

		Label label = pc.getLabel(parent, m.labelSetor);
		Text textSetor = pc.getText(parent, m.empty, SWT.BORDER | SWT.CENTER);
		pc.setWidth(textSetor, 7);
		pc.addLostFocus(textSetor, e -> {
			action.searchForSetor(textSetor.getText());
		});

		Text textSetorNome = pc.getText(parent, m.empty);
		pc.setWidth(textSetorNome, 28);
		pc.editable(false, textSetorNome);

		pc.groupControl(textSetor, textSetorNome, label);

		action.addListener(this, FUNCIONARIO$dialog_report_invalid_setor, e -> {
			pc.grabFocus(textSetor);
		});

		action.addListener(this, FUNCIONARIO$dialog_load_funcionario, e -> {
			Funcionario fun = e.getValue(Funcionario.class);
			Setor setor = fun == null ? null : fun.getSetor();
			pc.observeWidget(textSetor, "lotacao", setor, "%04d");
			pc.observeWidget(textSetorNome, "nome", setor);

			pc.addObserverListener(setor, listener);
		});

		action.addListener(this, FUNCIONARIO$dialog_reset, e -> {
			pc.enabled(false, textSetor);
		});

		action.addListener(this, FUNCIONARIO$dialog_in_edition, e -> {
			pc.enabled(true, textSetor);
			pc.grabFocus(textSetor);
		});
	}

	private void makeCompositeApelido(Composite parent) {
		Group group = pc.getGroup(parent);
		group.setLayout(new GridLayout(1, false));
		Label label = pc.getLabel(group, m.labelApelido);
		pc.fillHorizontalComponent(group);
		
		Text textApelido = pc.getText(group, m.empty, SWT.CENTER|SWT.BORDER);
		pc.configureUpperCase(textApelido);
		pc.fillHorizontalComponent(textApelido);
		pc.groupControl(textApelido, label);
		
		action.addListener(this, FUNCIONARIO$dialog_load_funcionario, e -> {
			Funcionario funcionario = e.getValue(Funcionario.class);
			pc.observeWidget(textApelido, "apelido", funcionario);
			pc.addObserverListener(funcionario, listener);
		});
		
	}

	private void makeCompositeName(Composite parent_) {
		Group parent = pc.getGroup(parent_);
		parent.setLayout(new GridLayout(2, false));
		Label label = pc.getLabel(parent, m.labelInformNomeCompleto);
		pc.fillHorizontalComponent(label).horizontalSpan = 2;
		Text textFirsName = pc.getText(parent, m.empty);
		textFirsName.setMessage(m.textNome);
		Text textLastName = pc.getText(parent, m.empty);
		textLastName.setMessage(m.textSobrenome);

		pc.groupControl(textFirsName, label, textLastName);

		pc.configureUpperCase(textFirsName, textLastName);

		pc.setWidth(textFirsName, 18);
		pc.setWidth(textLastName, 33);

		action.addListener(this, FUNCIONARIO$dialog_reset, e -> {
			pc.enabled(false, textFirsName);
		});

		action.addListener(this, FUNCIONARIO$dialog_in_edition, e -> {
			pc.enabled(true, textFirsName);
//			pc.grabFocus(textFirsName);
		});

		action.addListener(this, FUNCIONARIO$dialog_load_funcionario, e -> {
			Funcionario funcionario = e.getValue(Funcionario.class);

			pc.observeWidget(textFirsName, "nome", funcionario);
			pc.observeWidget(textLastName, "sobrenome", funcionario);

			pc.addObserverListener(funcionario, listener);
		});

	}

	@Override
	public boolean close() {
		cancelPressed();
		return false;
	}

	@Override
	protected void okPressed() {
		action.save();
	}

	@Override
	protected void cancelPressed() {
		action.reset();
	}

}
