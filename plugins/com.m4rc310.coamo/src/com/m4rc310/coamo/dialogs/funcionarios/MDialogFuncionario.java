package com.m4rc310.coamo.dialogs.funcionarios;

import javax.inject.Inject;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
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

import com.fasterxml.jackson.databind.node.TextNode;
import com.m4rc310.coamo.dialogs.MDialog;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.ActionPessoaFisica;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.ConstPF;
import com.m4rc310.coamo.dialogs.setores.ActionSetor;
import com.m4rc310.coamo.dialogs.setores.ConstSetor;
import com.m4rc310.coamo.models.Lotacao;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

public class MDialogFuncionario extends MDialog implements ConstPF, ConstSetor{

	
//	@Inject FragmentFuncionarioPFIdentify fragmentFuncionarioPFIdentify;
	@Inject ActionPessoaFisica actionPessoaFisica;
	@Inject ActionSetor actionSetor;

	@Inject
	public MDialogFuncionario(Shell parentShell) {
		super(parentShell);
	}

	MChangeListener listener = e -> {
		
		if (e.getSource() instanceof PessoaFisica) {
			PessoaFisica pf = (PessoaFisica) e.getSource();
		}
		
		
	};

	@Override
	protected Control createDialogArea(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));
		pc.fillHorizontalComponent(parent);
		
		getLabel(parent, "Vincular Pessoa Física a Quadro de Funcionários");
		
		Composite line = pc.getComposite(parent);
		line.setLayout(new GridLayout(3, false));
		pc.clearMargins(line);
		Composite d = pc.getComposite(line);
		buildTopSide(d);
		
		pc.fillHorizontalComponent(pc.getLabel(parent, "", SWT.HORIZONTAL|SWT.SEPARATOR));
		
		line = pc.getComposite(parent);
		line.setLayout(new GridLayout(3, false));
		pc.clearMargins(line);
		d = pc.getComposite(line);
		buildLotacaoSide(d);
		
		pc.fillHorizontalComponent(pc.getLabel(parent, "", SWT.HORIZONTAL|SWT.SEPARATOR));
		
		getShell().addListener(SWT.Show, e->{
			actionPessoaFisica.initDialog();
		});
		
		return parent;
	}

	
	private void buildLotacaoSide(Composite parent) {
		
		Composite stackPrincipal = pc.getStackComposite(parent);
		
		Composite sideA = pc.getComposite(stackPrincipal);
		sideA.setLayout(new GridLayout(5, false));
		pc.clearMargins(sideA);
		
		Label label = pc.getLabel(sideA, "Setor/Lotação:");
		Text textLotacao = pc.getText(sideA, "", SWT.BORDER | SWT.CENTER);
		pc.configureNumericValues(textLotacao);
		pc.addTextListener(textLotacao, e->actionSetor.writeNumero(e));
		pc.setWidth(textLotacao, 6);
		
		ToolBar toolBar = new ToolBar(sideA, SWT.FLAT);
		ToolItem itemSearch = getToolItem(toolBar, ICONS$search);
		itemSearch.addListener(SWT.Selection, e->actionSetor.searchSetor());
		pc.groupControl(textLotacao, label);
		
		Composite stack = pc.getStackComposite(sideA);
		Button buttonAdvance = pc.getButton(stack, "Avançar", e->actionSetor.advance(textLotacao.getText()));
		Button buttonLoading = pc.getButton(stack, "Aguarde...");
		
		Composite sideB = pc.getComposite(stackPrincipal);
		sideB.setLayout(new GridLayout(5, false));
		pc.clearMargins(sideB);
		
		Text textSetorNome = pc.getText(sideB, "");
		pc.setWidth(textSetorNome, 30);
		pc.editable(false, textSetorNome);
		
		toolBar = new ToolBar(sideB, SWT.FLAT);
		ToolItem itemReset = getToolItem(toolBar, ICONS$cancel);
		itemReset.addListener(SWT.Selection, e->actionSetor.cancel());
		
		label = pc.getLabel(sideB, "Matrícula:");
		Text textMatricula = pc.getText(sideB, "",SWT.BORDER|SWT.CENTER);
		pc.setWidth(textMatricula, 8);
		
		Button buttonVincular = pc.getButton(sideB, "Vincular");
		
		actionSetor.addListener(this, SETOR$report_prepare_to_advance, e->{
			boolean valid = e.getValue(boolean.class);
			pc.enabled(valid, buttonAdvance);
			pc.enabled(!valid, itemSearch);
			pc.toTopControlAndDefault(buttonAdvance);
		});
		
		actionSetor.addListener(this, SETOR$setor_report_edit, e->{
			pc.toTopControl(sideB);
			pc.grabFocus(textMatricula);
		});
		actionSetor.addListener(this, SETOR$setor_load, e->{
			Lotacao lotacao = e.getValue(Lotacao.class);
			pc.observeWidget(textSetorNome, "nome", lotacao);
		});
		
		actionSetor.addListener(this, SETOR$loading, e->{
			pc.enabled(false, textLotacao, itemSearch, buttonAdvance, buttonLoading);
			pc.toTopControl(buttonLoading);
			
		});
		
		actionSetor.addListener(this, SETOR$setor_dialog_reset, e->{
			pc.enabled(true, textLotacao, itemSearch);
			pc.toTopControl(buttonAdvance);
			pc.toTopControl(sideA);
			pc.enabled(false, buttonAdvance);
			pc.grabFocus(textLotacao);
		});
		
		
		
		// ----------------
		
		actionPessoaFisica.addListener(this, PF$dialog_in_edition, e->{
			
			pc.enabled(true, textLotacao, itemSearch);
			pc.enabled(false,  buttonAdvance, buttonLoading);
			pc.grabFocus(textLotacao);
		});
		
		actionPessoaFisica.addListener(this, PF$reset_dialog, e->{
			pc.toTopControl(buttonAdvance);
			pc.enabled(false, textLotacao, buttonAdvance, buttonLoading, itemSearch);
			pc.toTopControl(sideA);
		});
		
		
	}


	private void buildTopSide(Composite d) {
		d.setLayout(new GridLayout(5, false));
		pc.clearMargins(d);
		Label label = pc.getLabel(d, "Pessoa Física:");
		Text textId = pc.getText(d, "", SWT.BORDER | SWT.CENTER);
		pc.addTextListener(textId, e->actionPessoaFisica.writeId(e));
		pc.setWidth(textId, 4);
		pc.groupControl(textId, label);
		
		final ControlDecoration deco = new ControlDecoration(textId, SWT.TOP | SWT.LEFT);
		Image img = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
				.getImage();
		deco.setImage(img);
		deco.setShowOnlyOnFocus(false);

		
		ToolBar toolBar = new ToolBar(d, SWT.FLAT);
		ToolItem itemSearch = getToolItem(toolBar, ICONS$search);
		itemSearch.addListener(SWT.Selection, e->actionPessoaFisica.searchPF());
		
		Composite stack = pc.getStackComposite(d);
		Button buttonAdvance = pc.getButton(stack, "Avançar", e->actionPessoaFisica.advance(textId.getText(),""));
		Button buttonLoading = pc.getButton(stack, "Aguarde...");
		
		Text textNome = pc.getText(d, "");
		pc.setWidth(textNome, 30);
		pc.editable(false, textNome);
		
		actionPessoaFisica.addListener(this, PF$prepare_to_advance, e->{
			boolean advance = e.getValue(boolean.class);
			pc.enabled(advance, buttonAdvance);
			pc.enabled(!advance, itemSearch);
			pc.toTopControlAndDefault(buttonAdvance);
			deco.hide();
		});
		
		actionPessoaFisica.addListener(this, PF$load_pessoa_fisica, e->{
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.observeWidget(textId, "id", pf,"%03d");
			pc.observeWidget(textNome, "nome", pf);
		});
		
		actionPessoaFisica.addListener(this, PF$mode_loading, e->{
			pc.toTopControl(buttonLoading);
			pc.enabled(false, textId, itemSearch);
		});
		
		actionPessoaFisica.addListener(this, PF$dialog_in_edition, e->{
			pc.toTopControl(buttonAdvance);
			pc.enabled(false, buttonAdvance, textId, itemSearch);
			pc.enabled(true, textNome);
		});
		
		actionPessoaFisica.addListener(this, PF$report_search_unsuccess, e->{
			deco.show();
			deco.showHoverText("Pessoa Física não encontrada!");
			pc.grabFocus(textId);
			pc.toTopControl(buttonAdvance);
		});
		
		actionPessoaFisica.addListener(this, PF$reset_dialog, e->{
			pc.enabled(true, textId, itemSearch);
			pc.enabled(false, buttonAdvance, buttonLoading,textNome);
			pc.grabFocus(textId);
			pc.toTopControl(buttonAdvance);
			deco.hide();
		});
		
		
	}


	public void close(boolean confirm) {
		if (confirm) {
			super.close();
		}
	}

	@Override
	protected void cancelPressed() {
		close(true);
	}

	@Override
	protected void okPressed() {
	}

}
