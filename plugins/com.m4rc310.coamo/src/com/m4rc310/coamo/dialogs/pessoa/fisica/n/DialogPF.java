package com.m4rc310.coamo.dialogs.pessoa.fisica.n;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
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
import com.m4rc310.coamo.dialogs.pf.MCompositeBloqueios;
import com.m4rc310.coamo.dialogs.pf.MCompositeDocumentos;
import com.m4rc310.coamo.models.CNH;
import com.m4rc310.coamo.models.CTPS;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.coamo.models.RG;
import com.m4rc310.coamo.models.Sexo;
import com.m4rc310.rcp.ui.utils.MLabelProvider;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

public class DialogPF extends MDialog implements ConstPF {

	@Inject
	ActionPessoaFisica action;

	@Inject
	@Named("list_sexo")
	List<Sexo> listSexo;
	

	@Inject
	MCompositeBloqueios compositeBloqueios;
	@Inject
	MCompositeDocumentos compositeDocumentos;
	

	final MChangeListener listener = e->{
		action.changePF();
	};

	@Inject
	public DialogPF(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent_) {
		
		Composite container = pc.getComposite(parent_);
		container.setLayout(new GridLayout(1, false));

		Composite line = pc.getComposite(container);
		line.setLayout(new GridLayout(1, false));
		pc.fillHorizontalComponent(line);
		pc.clearMargins(line);
		
		Label label = getLabel(line, "Cadastro de Pessoa Física");
		pc.fillHorizontalComponent(label);
		
		line = pc.getComposite(container);
		line.setLayout(new GridLayout(3, false));
		pc.fillHorizontalComponent(line);
		pc.clearMargins(line);
		
		Composite d = pc.getComposite(line);
		makeIdentify(d);
		
		d = pc.getComposite(line);
		pc.fillHorizontalComponent(d);
		
		d = pc.getComposite(line);
		makePfInfo(d);
	
		Label separator = pc.getLabel(container, "", SWT.HORIZONTAL | SWT.SEPARATOR);
		pc.fillHorizontalComponent(separator);
		
		line = pc.getComposite(container);
		line.setLayout(new GridLayout(1, false));
		pc.fillHorizontalComponent(line);
		pc.clearMargins(line);
		d = pc.getComposite(line);
		makeBasicData(d);
		
		line = pc.getComposite(container);
		line.setLayout(new GridLayout(1, false));
		pc.clearMargins(line);
		pc.fillHorizontalComponent(line);
		
		d = pc.getComposite(line);
		makeOthersSide(d);
		
		separator = pc.getLabel(container, "", SWT.HORIZONTAL | SWT.SEPARATOR);
		pc.fillHorizontalComponent(separator);
		
		action.addListener(this, PF$dialog_dispose, e -> {
			super.close();
		});
		

		action.addListener(this, PF$reset_dialog, e -> {
			pc.enabled(false, getButton(OK_ID));
		});
		
		action.addListener(this, PF$init_change_listener, e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.addObserverListener(pf, listener);
			
			RG rg = pf == null? null: pf.getRg();
			pc.addObserverListener(rg, listener);
			
			CNH cnh = pf == null? null: pf.getCnh();
			pc.addObserverListener(cnh, listener);
			
			CTPS ctps = pf == null? null: pf.getCtps();
			pc.addObserverListener(ctps, listener);
		});
		
		action.addListener(this, PF$changeded, e->{
			boolean changeded = e.getValue(boolean.class);
			Button button = getButton(OK_ID);
			pc.enabled(changeded, button);
			getShell().setDefaultButton(button);
		});

		getShell().addListener(SWT.Show, e -> {
			action.initDialog();
		});
		
		getShell().setText("Cadastro de Pessoa Física");
		
		
		return container;
	}

	private void makeOthersSide(Composite parent) {
		
		parent.setLayout(new GridLayout(1, false));
		pc.clearMargins(parent);
		pc.fillHorizontalComponent(parent);
		
		
		CTabFolder folder = pc.createCTabFolder(parent);
		folder.setBorderVisible(true);
		pc.fillHorizontalComponent(folder);

		Composite itemContainerDocumentos = pc.createCTabItemContainer(folder, "Documentos");
		compositeDocumentos.make(action, itemContainerDocumentos);
		pc.setContentToScrolled(itemContainerDocumentos);

//		Composite itemContainerBloqueios = pc.createCTabItemContainer(folder, "Bloqueios");
//		compositeBloqueios.make(action, itemContainerBloqueios);
//		pc.setContentToScrolled(itemContainerBloqueios);

		
		action.addListener(this, PF$reset_dialog, e -> {
			pc.ctabFolderSetSelection(itemContainerDocumentos);
		});
		
	}



	private void makeIdentify(Composite parent) {
		parent.setLayout(new GridLayout(6, false));
		pc.clearMargins(parent);

		Label label = pc.getLabel(parent, "Código:");
		Text textId = pc.getText(parent, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textId, 5);
		pc.configureNumericValues(textId);
		pc.groupControl(textId, label);
		pc.addTextListener(textId, sid -> action.writeId(sid));

		label = pc.getLabel(parent, "CPF:");
		Text textCpf = pc.getText(parent, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textCpf, 14);
		pc.groupControl(textCpf, label);
		pc.addTextListener(textCpf, scpf -> action.writeCPF(scpf));

		ToolBar toolbar = new ToolBar(parent, SWT.FLAT);
		ToolItem itemSearch = getToolItem(toolbar, ICONS$search);
		itemSearch.addListener(SWT.Selection, e->action.searchPF());

		Composite stack = pc.getStackComposite(parent);
		Button buttonAdvance = pc.getButton(stack, "Avançar", e -> action.advance(textId.getText(), textCpf.getText()));
		Button buttonWait = pc.getButton(stack, "Aguarde...");
		Button buttonNew = pc.getButton(stack, "Novo", e-> action.newPF());
		Button buttonCancel = pc.getButton(stack, "Cancelar", e -> cancelPressed());

		pc.toTopControl(buttonAdvance);

		final ControlDecoration deco = new ControlDecoration(textId, SWT.TOP | SWT.LEFT);
		Image img = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
				.getImage();
//		deco.setDescriptionText("Pessoa física não encontrada na base de dados!");
		deco.setImage(img);
		deco.setShowOnlyOnFocus(false);

		action.addListener(this, PF$load_pessoa_fisica, e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.observeWidget(textId, "id", pf, "%03d");
			pc.observeWidget(textCpf, "cpf", pf);
		});

		action.addListener(this, PF$mode_loading, e -> {
			pc.enabled(false, textId, textCpf, itemSearch);
			pc.toTopControl(buttonWait);
		});

		action.addListener(this, PF$prepare_to_advance, e -> {
			Boolean valid = e.getValue(boolean.class);
			pc.enabled(valid, buttonAdvance);
			pc.enabled(!valid, buttonNew, itemSearch);
			pc.toTopControlAndDefault(valid ? buttonAdvance : buttonNew);
			deco.hide();
		});

		action.addListener(this, PF$dialog_in_edition, e -> {
			pc.enabled(false, textId, textCpf, itemSearch);
			pc.enabled(true, buttonCancel);
			pc.toTopControl(buttonCancel);
		});

		action.addListener(this, PF$reset_dialog, e -> {
			pc.enabled(true, textId, textCpf, itemSearch, buttonNew);
			pc.enabled(false, buttonWait, buttonAdvance, buttonCancel);
			pc.toTopControlAndDefault(buttonNew);
			pc.grabFocus(textId);
			deco.hide();
		});

		action.addListener(this, PF$report_search_unsuccess, e -> {
			pc.enabled(true, textId, textCpf, itemSearch, buttonNew);
			pc.enabled(false, buttonWait, buttonAdvance, buttonCancel);
			pc.toTopControlAndDefault(buttonAdvance);
			pc.grabFocus(textId);
			deco.show();
			deco.showHoverText("Pessoa física não encontrada na base de dados!");
		});

	}

	private void makePfInfo(Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		pc.clearMargins(parent);

		Button checkActive = pc.getButton(parent, "Ativo", SWT.CHECK);
		Button checkEmploye = pc.getButton(parent, "Funcionário", SWT.CHECK);
		Button checkLocked = pc.getButton(parent, "Bloqueado", SWT.CHECK);

		action.addListener(this, PF$load_pessoa_fisica, e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.observeWidget(checkActive, "ativo", pf);
			pc.observeWidget(checkEmploye, "funcionario", pf);
			pc.observeWidget(checkLocked, "bloqueado", pf);
		});

		action.addListener(this, PF$dialog_in_edition, e -> {
			pc.enabled(true, checkActive);
		});

		action.addListener(this, PF$reset_dialog, e -> {
			pc.enabled(false, checkActive, checkEmploye, checkLocked);
		});
	}

	private void makeBasicData(Composite parent) {
//		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(3, false));
		pc.clearMargins(parent);
		pc.fillHorizontalComponent(parent).horizontalSpan = 3;

		Composite line = pc.getComposite(parent);
		line.setLayout(new GridLayout(1, false));
		pc.clearMargins(line);
		pc.fillHorizontalComponent(line);

		Label label = pc.getLabel(line, "Nome Completo:");
		Text textNome = pc.getText(line, "");
		textNome.setMessage("Informe o nome completo da Pessoa Física");
		pc.configureUpperCase(textNome);
		pc.groupControl(textNome, label);
		pc.fillHorizontalComponent(textNome);

		line = pc.getComposite(parent);
		line.setLayout(new GridLayout(1, false));
		pc.clearMargins(line);
		label = pc.getLabel(line, "Sexo:");
		ComboViewer comboViewerSexo = pc.getComboViewer(line, SWT.READ_ONLY);
		comboViewerSexo.setLabelProvider(new MLabelProvider<Sexo>() {
			@Override
			public String getMText(Sexo sexo) {
				return sexo.getDescricao();
			}
		});
		pc.setWidth(comboViewerSexo.getControl(), 13);
		pc.groupControl(comboViewerSexo.getCombo(), label);

		line = pc.getComposite(parent);
		line.setLayout(new GridLayout(2, false));
		pc.clearMargins(line);
		label = pc.getLabel(line, "Data de Nascimento:");
		pc.fillHorizontalComponent(label).horizontalSpan = 2;
		Text textDataNascimento = pc.getText(line, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textDataNascimento, 10);
		Label labelIdade = pc.getLabel(line, "Não informada");
		pc.groupControl(textDataNascimento, label);

		pc.setForeground(labelIdade, SWT.COLOR_BLUE);

		action.addListener(this, PF$load_idade, e -> {
			String sidade = e.getValue(String.class);
			labelIdade.setText(sidade);
		});
		
		action.addListener(this, PF$load_pessoa_fisica, e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.observeWidget(textNome, "nome", pf);
			pc.observeComboList(comboViewerSexo, "sexo", pf);
			pc.observeWidget(textDataNascimento, "nascimento", pf, "dd/MM/yyyy");
		});

		action.addListener(this, PF$dialog_in_edition, e -> {
			pc.enabled(true, textNome, comboViewerSexo.getCombo(), textDataNascimento, labelIdade);
			pc.grabFocus(textNome);
			pc.setForeground(labelIdade, SWT.COLOR_BLUE);
		});

		action.addListener(this, PF$reset_dialog, e -> {
			comboViewerSexo.setInput(listSexo);
			pc.enabled(false, textNome, comboViewerSexo.getCombo(), textDataNascimento, labelIdade);
		});
	}
	
	@Override
	protected void okPressed() {
		action.salvar();
	}

	@Override
	protected void cancelPressed() {
		action.cancel();
	}

	@Override
	public boolean close() {
		action.cancel();
		return false;
	}

}
