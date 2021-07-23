package com.m4rc310.coamo.dialogs.pessoa.fisica;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.m4rc310.coamo.dialogs.FragmentComposite;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
public class FragmentPFIdentify extends FragmentComposite {

	@Override
	public Composite make(MAction action, Composite parent_) {

		ActionPF actionPF = (ActionPF) action;

		Composite parent = pc.getComposite(parent_);

		parent.setLayout(new GridLayout(7, false));
		pc.clearMargins(parent);

		Label label = pc.getLabel(parent, "Código:");
		Text textId = pc.getText(parent, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textId, 5);
		pc.groupControl(textId, label);
		pc.configureNumericValues(textId);
		pc.addTextModifyListener(textId, e -> {
			Text txt = (Text) e.widget;
			actionPF.writingId(txt.getText());
		});

		label = pc.getLabel(parent, "CPF:");
		Text textCPF = pc.getText(parent, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textCPF, 13);
		pc.groupControl(textCPF, label);
		pc.addTextModifyListener(textCPF, e -> {
			Text txt = (Text) e.widget;
			actionPF.writingCpf(txt.getText());
		});

		ToolBar toolbar = new ToolBar(parent, SWT.FLAT);
		ToolItem toolItemSearch = new ToolItem(toolbar, SWT.PUSH);
		toolItemSearch.setImage(pc.getImage("com.m4rc310.coamo", "icons/magnifier.png"));
		toolItemSearch.addListener(SWT.Selection, e -> {
			actionPF.searchPessoaFisica();
		});

		Button checkSemCPF = pc.getButton(parent, "Sem CPF", SWT.CHECK, e -> {
			Button button = (Button) e.widget;
			actionPF.noCPF(button.getSelection());
		});

		Composite stack = pc.getStackComposite(parent);

		Button buttonAvancar = pc.getButton(stack, "Avançar",
				e -> actionPF.advance(textId.getText(), textCPF.getText(), checkSemCPF.getSelection()));
		Button buttonAguarde = pc.getButton(stack, "Aguarde...");
		Button buttonCancelar = pc.getButton(stack, "Cancelar", e -> actionPF.cancelPf());

		actionPF.addListener(this, FIRE$report_no_cpf, e -> {
			boolean noCPF = e.getValue(boolean.class);
			pc.enabled(!noCPF, textCPF, textId, toolItemSearch);
			pc.enabled(noCPF, buttonAvancar);
//			pc.enabled(!noCPF, textId, textCPF, toolItemSearch);
//			pc.enabled(noCPF, buttonAvancar);
//			if (!noCPF) {
//				pc.grabFocus(textCPF);
//			} else {
//				pc.setDefaultButton(buttonAvancar);
				pc.toTopControl(buttonAvancar);
//			}
		});

		actionPF.addListener(this, FIRE$not_found_pf, e -> {
			pc.grabFocus(textId);
			pc.toTopControl(buttonAvancar);
		});

		actionPF.addListener(this, FIRE$load_pf, e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.observeWidget(textId, "id", pf, "%03d");
			pc.observeWidget(textCPF, "cpf", pf);
			if (pc != null) {
				pc.toTopControl(buttonCancelar);
			}
		});

		actionPF.addListener(this, FIRE$prepare_to_advance_id, e -> {
			boolean advance = e.getValue(boolean.class);
			pc.enabled(!advance, textCPF, toolItemSearch, checkSemCPF);
			pc.enabled(advance, buttonAvancar);
			if (advance) {
				pc.toTopControl(buttonAvancar);
				pc.setDefaultButton(buttonAvancar);
			}
		});

		actionPF.addListener(this, FIRE$prepare_to_advance_cpf, e -> {
			boolean advance = e.getValue(boolean.class);
			pc.enabled(!advance, textId, toolItemSearch, checkSemCPF);
			pc.enabled(advance, buttonAvancar);
			if (advance) {
				pc.toTopControl(buttonAvancar);
				pc.setDefaultButton(buttonAvancar);
			}
		});

		actionPF.addListener(this, FIRE$lock_form_pf, e -> {
			boolean lock = e.getValue(boolean.class);
			pc.enabled(lock, textId, textCPF, checkSemCPF, toolItemSearch);
			pc.enabled(!lock, buttonAvancar);
			if(lock) {
				pc.grabFocus(textId);
				checkSemCPF.setSelection(false);
			}else {
				pc.toTopControl(buttonCancelar);
//				pc.toTopControl(buttonAvancar);

			}
		});
		
		actionPF.addListener(this, FIRE$report_wait, e -> {
			pc.toTopControl(buttonAguarde);
			pc.enabled(false, textId, textCPF, checkSemCPF);
		});

		actionPF.addListener(this, FIRE$start_dialog_pf, e -> {
			pc.toTopControl(buttonAvancar);
			pc.enabled(false, buttonAvancar);
			pc.enabled(true, textId, textCPF, checkSemCPF, toolItemSearch);
			pc.grabFocus(textId);
			checkSemCPF.setSelection(false);
		});

//		actionPF.resetDialog();

		return parent;
	}

}
