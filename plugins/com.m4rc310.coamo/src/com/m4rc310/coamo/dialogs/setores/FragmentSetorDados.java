package com.m4rc310.coamo.dialogs.setores;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.coamo.dialogs.FragmentComposite;
import com.m4rc310.coamo.models.Lotacao;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
public class FragmentSetorDados extends FragmentComposite implements ConstSetor {

	@Override
	public Composite make(MAction action, Composite parent_) {

		ActionSetor actionSetor = (ActionSetor) action;

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(3, false));
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		pc.clearMargins(parent);

		Label label = pc.getLabel(parent, "Número:");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		label.setLayoutData(gd);

		Text textNumero = pc.getText(parent, "", SWT.BORDER | SWT.CENTER);
		pc.configureNumericValues(textNumero);
		pc.addTextModifyListener(textNumero, e -> {
			Text txt = (Text) e.widget;
			actionSetor.writingNumeroSetor(txt.getText());
		});

		pc.setWidth(textNumero, 8);

		Composite stack = pc.getStackComposite(parent);
		Button buttonAvancar = pc.getButton(stack, "Avancar", e -> actionSetor.advance(textNumero.getText()));
		Button buttonCancelar = pc.getButton(stack, "Cancelar", e -> actionSetor.cancel());
		pc.toTopControl(buttonAvancar);

		Text textSetorDescricao = pc.getText(parent, "");
		textSetorDescricao.setMessage("Descrição");
		pc.setWidth(textSetorDescricao, 35);
		pc.configureUpperCase(textSetorDescricao);

		pc.enabled(false, buttonAvancar);

		actionSetor.addListener(this, SETOR$setor_load, e -> {
			Lotacao setor = e.getValue(Lotacao.class);
			pc.observeWidget(textNumero, "numero", setor,"%04d");
			pc.observeWidget(textSetorDescricao, "nome", setor);
			
			System.out.println(setor);
		});
		
			actionSetor.addListener(this, SETOR$setor_report_edit, e -> {
			pc.grabFocus(textSetorDescricao);
			pc.enabled(false, textNumero);
			pc.enabled(true, buttonCancelar);
			pc.toTopControl(buttonCancelar);

		});

		actionSetor.addListener(this, SETOR$setor_dialog_reset, e -> {
			pc.enabled(false, textSetorDescricao, buttonAvancar, buttonCancelar);
			pc.toTopControl(buttonAvancar);
			pc.grabFocus(textNumero);
		});

		actionSetor.addListener(this, SETOR$report_prepare_to_advance, e -> {
			boolean advance = e.getValue(boolean.class);
			pc.enabled(advance, buttonAvancar);
			pc.setDefaultButton(buttonAvancar);
		});

		return parent;
	}

}
