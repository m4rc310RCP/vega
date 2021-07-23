package com.m4rc310.coamo.dialogs.funcionarios;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.m4rc310.coamo.dialogs.FragmentComposite;
import com.m4rc310.coamo.dialogs.pessoa.fisica.ActionPF;
import com.m4rc310.coamo.models.Lotacao;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.coamo.models.VinculoEmprego;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
public class FragmentFuncionarioPFIdentify extends FragmentComposite implements ConstFuncionario {
	
	@Inject Shell shell;
	@Inject ActionPF actionPF;

	@Override
	public Composite make(MAction action, Composite parent) {

		ActionFuncionario actionFuncionario = (ActionFuncionario) action;

		Composite content = pc.getComposite(parent);
		content.setLayout(new GridLayout(4, false));
		pc.clearMargins(content);
		pc.fillHorizontalComponent(content);

		Label label = pc.getLabel(content, "Pessoa Física:");
		pc.fillHorizontalComponent(label).horizontalSpan = 4;
		
		Text textPFId = pc.getText(content, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textPFId, 4);
		pc.addTextModifyListener(textPFId, e -> {
			actionFuncionario.writingPfId(textPFId.getText());
		});
		pc.groupControl(textPFId, label);

		ToolBar toolbar = new ToolBar(content, SWT.FLAT);
		ToolItem itemSearch = getToolItem(toolbar, ICONS$search);
		itemSearch.addListener(SWT.Selection, e -> {
			actionPF.searchPessoaFisica();
		});
		Composite stack = pc.getStackComposite(content);
		Button buttonAdvance = pc.getButton(stack, "Avançar", e -> actionFuncionario.findPf(textPFId.getText()));
		Button buttonCancel = pc.getButton(stack, "Cancelar", e-> actionFuncionario.cancelPF());
		Button buttonLoading = pc.getButton(stack, "Aguarde...");

		Text textNomePF = pc.getText(content, "");
		pc.fillHorizontalComponent(textNomePF);
		pc.configureUpperCase(textNomePF);
		pc.editable(false, textNomePF);
		
		content = pc.getComposite(parent);
		content.setLayout(new GridLayout(2, false));
		
		Composite c = pc.getComposite(content);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(content, c);
		
		label = pc.getLabel(c, "Lotação:");
		ComboViewer comboViewerLotacao = pc.getComboViewer(c, SWT.READ_ONLY);
		comboViewerLotacao.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				Lotacao l = (Lotacao) element;
				return String.format("%04d - %s", l.getNumero(), l.getNome());
			};
		});
		
		comboViewerLotacao.addSelectionChangedListener(e->{
			Lotacao lotacao = (Lotacao) e.getStructuredSelection().getFirstElement();
			actionFuncionario.setLotacao(lotacao);
		});
		
		
		pc.fillHorizontalComponent(c);
		pc.fillHorizontalComponent(comboViewerLotacao.getControl());
		
		c = pc.getComposite(content);
		c.setLayout(new GridLayout(3, false));
		pc.clearMargins(c);
		
		label = pc.getLabel(c, "Adicionar Vinculo de Emprego:");
		pc.fillHorizontalComponent(label).horizontalSpan=3;
		
		label = pc.getLabel(c, "Matrícula:");
		Text textMatricula = pc.getText(c, "",SWT.BORDER|SWT.CENTER);
		pc.setWidth(textMatricula, 7);
		pc.addTextModifyListener(textMatricula, e->{
			Text txt = (Text) e.widget;
			actionFuncionario.writingMatriculaFuncionario(txt.getText());
		});
		
		stack = pc.getStackComposite(c);
		Button buttonVincular = pc.getButton(stack, "Vincular", e->actionFuncionario.vinculoEmprego(textMatricula.getText()));
		Button buttonDesvincular = pc.getButton(stack, "Desvincular", e->actionFuncionario.cancelVinculo());
		
		actionPF.addListener(this, FIRE$load_pf, e->{
			actionFuncionario.loadPF(e.getValue(PessoaFisica.class));
		});
		
		actionFuncionario.addListener(this, FUNCIONARIO$load_vinculo, e->{
			VinculoEmprego vinculo = e.getValue(VinculoEmprego.class);
			
			pc.observeComboList(comboViewerLotacao, "lotacao", vinculo);
			pc.observeWidget(textMatricula, "matricula", vinculo);
			pc.enabled(false, buttonVincular);
			pc.toTopControl(vinculo==null?buttonVincular:buttonDesvincular);
		});
		
		actionFuncionario.addListener(this, FUNCIONARIO$valid_matricula, e->{
			boolean valid = e.getValue(boolean.class);
			pc.enabled(valid, buttonVincular);
			pc.setDefaultButton(buttonVincular);
		});
		actionFuncionario.addListener(this, FUNCIONARIO$edit_vinculo, e->{
			boolean edit = e.getValue(boolean.class);
			pc.enabled(edit, textMatricula, comboViewerLotacao.getCombo());
			
		});
		
		actionFuncionario.addListener(this, FUNCIONARIO$load_lotacoes, e->{
			List<Lotacao> list = e.getListValue(0, Lotacao.class);
			comboViewerLotacao.setInput(list);
		});

		actionFuncionario.addListener(this, FUNCIONARIO$pf_loaded, e -> {
			pc.enabled(false, textPFId);
			pc.grabFocus(textNomePF);
			pc.toTopControl(buttonCancel);
		});
		
		actionFuncionario.addListener(this, FUNCIONARIO$informe_pf_not_found, e -> {
			MessageDialog.openInformation(shell, "", "Pessoa Física não cadastrada!");
			pc.grabFocus(textPFId);
			pc.toTopControl(buttonAdvance);
		});
		
		actionFuncionario.addListener(this, FUNCIONARIO$load_pf, e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.observeWidget(textPFId, "id", pf, "%03d");
			pc.observeWidget(textNomePF, "nome", pf);
		});

		actionFuncionario.addListener(this, FUNCIONARIO$writing_id, e -> {
			boolean empty = e.getValue(boolean.class);
			pc.enabled(empty, itemSearch);
			pc.enabled(!empty, buttonAdvance);
			pc.setDefaultButton(buttonAdvance);
		});

		actionFuncionario.addListener(this, FUNCIONARIO$searching_pf, e -> {
			pc.enabled(false, textPFId, buttonLoading);
			pc.toTopControl(buttonLoading);
//			pc.toTopControl(searching?buttonLoading:buttonAdvance);
		});

		actionFuncionario.addListener(this, FUNCIONARIO$reset_dialog, e -> {
			pc.enabled(false, textNomePF, buttonAdvance, textMatricula, buttonVincular);
			pc.enabled(true, itemSearch);
			pc.toTopControl(buttonAdvance);
			pc.toTopControl(buttonVincular);
			pc.grabFocus(textPFId);
		});

		return parent;
	}

	private ToolItem getToolItem(ToolBar parent, String pathIcon) {
		ToolItem item = new ToolItem(parent, SWT.PUSH);
		item.setImage(pc.getImage(PLUGIN$coamo, pathIcon));
		return item;
	}

}
