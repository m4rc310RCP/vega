package com.m4rc310.coamo.dialogs.pf;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
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

import com.m4rc310.coamo.actions.ActionSearch;
import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.PartControl;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

public class DialogPessoaFisica extends Dialog implements IActionCoamoConsts {

	@Inject
	PartControl pc;
	@Inject
	ActionSearch actionCoamo;
	

	@Inject
	MCompositeBloqueios compositeBloqueios;
	@Inject
	MCompositeDocumentos compositeDocumentos;

	@Inject
	public DialogPessoaFisica(Shell shell) {
		super(shell);
	}

	@Override
	protected Control createDialogArea(Composite parent_) {

		getShell().setText("Cadastro de Pessoa Física");

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(2, false));

		// ************************************************************

		Group group = pc.getGroup(parent);
		group.setLayout(new GridLayout(7, false));
		pc.clearMargins(group);

		Label label = pc.getLabel(group, "Código:");

		Text textId = pc.getText(group, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textId, 4);
		pc.groupControl(textId, label);

		label = pc.getLabel(group, "CPF:");
		pc.enabled(false, textId);

		Text textCpf = pc.getText(group, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textCpf, 12);

		pc.groupControl(textCpf, label);
		
		ToolBar toolbar = new ToolBar(group,SWT.FLAT);
		ToolItem toolItemSearch = new ToolItem(toolbar, SWT.PUSH);
		toolItemSearch.setImage(pc.getImage("com.m4rc310.coamo", "icons/magnifier.png"));
		toolItemSearch.addListener(SWT.Selection, e->{
			actionCoamo.openSearch();
		});
		

		Button checkSemCPF = pc.getButton(group, "Sem CPF", SWT.CHECK, e -> {
			Button button = (Button) e.widget;
			actionCoamo.avancarSemCPF(button.getSelection());
		});

		pc.addTextModifyListener(textCpf, e -> {
			actionCoamo.escrevendoCPF(((Text) e.widget).getText(), checkSemCPF.getSelection());
		});

		Composite stack = pc.getStackComposite(group);

		Button buttonAvancar = pc.getButton(stack, "Avançar",
				e -> actionCoamo.avancarCadastroPF(textCpf.getText(), checkSemCPF.getSelection()));
		Button buttonAguarde = pc.getButton(stack, "Aguarde...");
		Button buttonCancelar = pc.getButton(stack, "Cancelar", e -> actionCoamo.cancelarCadastroPF());

		pc.enabled(false, buttonAvancar);

		pc.toTopControl(buttonAvancar);

		// ************************************************************

		group = pc.getGroup(parent);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		pc.clearMargins(group);

		Button checkCadastroAtivoInativo = pc.getButton(group, "Cadastro Ativo", SWT.CHECK);
		checkCadastroAtivoInativo.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, true, true));

		Button checkFuncionario = pc.getButton(group, "Funcionário", SWT.CHECK);
		checkFuncionario.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, true, true));
		

		Button checkPFBloqueado = pc.getButton(group, "Bloqueado", SWT.CHECK);
		checkPFBloqueado.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, true, true));

		pc.enabled(false, checkFuncionario, checkPFBloqueado);

		// ************************************************************

		group = pc.getGroup(parent);
		group.setLayout(new GridLayout(4, false));

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);

		Text textNome = pc.getText(group, "");
		textNome.setMessage("Nome Completo");
		pc.setWidth(textNome, 30);

		label = pc.getLabel(group, "Data de Nascimento:");
		pc.groupControl(textNome, label);

		Text textDataNascimento = pc.getText(group, "", SWT.BORDER | SWT.CENTER);
//		pc.configureDateField("dd/MM/yyyy", textDataNascimento);
		pc.setWidth(textDataNascimento, 10);

		Label labelIdade = pc.getLabel(group, "---");
		pc.setWidth(labelIdade, 18);

		pc.groupControl(textNome, label, textDataNascimento, labelIdade, checkCadastroAtivoInativo);

		// ************************************************************

		CTabFolder folder = pc.createCTabFolder(group);
		folder.setBorderVisible(true);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		folder.setLayoutData(gd);

		Composite itemContainerDocumentos = pc.createCTabItemContainer(folder, "Documentos");
		compositeDocumentos.make(actionCoamo, itemContainerDocumentos);
		pc.setContentToScrolled(itemContainerDocumentos);

		Composite itemContainerBloqueios = pc.createCTabItemContainer(folder, "Bloqueios");
		compositeBloqueios.make(actionCoamo, itemContainerBloqueios);
		pc.setContentToScrolled(itemContainerBloqueios);

		pc.ctabFolderSetSelection(itemContainerDocumentos);
		
		actionCoamo.addListener(this, FIRE$report_idade, e -> {
			String sidade = e.getValue(String.class);
			labelIdade.setText(sidade);
		});

		actionCoamo.addListener(this, FIRE$report_cpf_status, e -> {
			boolean valid = e.getValue(boolean.class);
			pc.enabled(valid, buttonAvancar);
			pc.enabled(!valid, checkSemCPF);
			pc.setDefaultButton(buttonAvancar);

			toolItemSearch.setEnabled(!valid);
		});

		actionCoamo.addListener(this, FIRE$report_aguarde_por_pf, e -> {
			pc.enabled(false, textCpf, checkSemCPF, buttonAguarde);
			pc.toTopControl(buttonAguarde);
		});

		MChangeListener listener = ev -> {
			Object source = ev.getSource();
			if (source instanceof PessoaFisica) {
				PessoaFisica pessoa = (PessoaFisica) source;
				actionCoamo.changePF(pessoa);
			}
		};

		actionCoamo.addListener(this, FIRE$enable_save_pf, e -> {
			Boolean enabled = e.getValue(boolean.class);
			Button buttonOK = getButton(IDialogConstants.OK_ID);
			if (buttonOK != null) {
				buttonOK.setEnabled(enabled);
				if (enabled) {
					pc.setDefaultButton(buttonOK);
				}
			}
		});

		actionCoamo.addListener(this, FIRE$load_pf, e -> {
			pc.toTopControl(buttonCancelar);

			PessoaFisica pf = e.getValue(PessoaFisica.class);

			pc.removeObserverListener(listener);
			pc.addObserverListener(listener);

			pc.observeWidget(textId, "id", pf, "%03d");
			pc.observeTextCpfCnpj(textCpf, "cpf", pf);
			pc.observeWidget(textNome, "nome", pf);
			pc.observeWidget(textDataNascimento, "nascimento", pf, "dd/MM/yyyy");
			pc.observeWidget(checkCadastroAtivoInativo, "ativo", pf);
			pc.observeWidget(checkFuncionario, "funcionario", pf);
			pc.observeWidget(checkPFBloqueado, "bloqueado", pf);

			if (pf != null) {
				pc.grabFocus(textNome);
			}
		});

		actionCoamo.addListener(this, FIRE$avancar_sem_cpf, e -> {
			boolean avancar = e.getValue(boolean.class);
			pc.enabled(avancar, buttonAvancar);
			pc.enabled(!avancar, textCpf);
			toolItemSearch.setEnabled(!avancar);
			if (avancar) {
				pc.setDefaultButton(buttonAvancar);
			} else {
				pc.grabFocus(textCpf);
			}
		});

		actionCoamo.addListener(this, FIRE$cancelar_cadastro_pf, e -> {
			pc.toTopControl(buttonAvancar);
			pc.enabled(false, buttonAvancar, checkCadastroAtivoInativo, textNome);
			pc.enabled(true, textCpf, checkSemCPF);
			pc.grabFocus(textCpf);
			checkSemCPF.setSelection(false);
			actionCoamo.escrevendoCPF(textCpf.getText(), checkSemCPF.getSelection());
		});

		actionCoamo.cancelarCadastroPF();

		return parent;
	}

//	485.769.649-53
	@Override
	protected Control createButtonBar(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite sp = pc.getComposite(parent);
		sp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);

		return parent;
	}

	@Override
	protected void okPressed() {
		actionCoamo.save();
	}

	@Override
	public boolean close() {
		actionCoamo.removeListeners(this);
		return super.close();
	}
}
