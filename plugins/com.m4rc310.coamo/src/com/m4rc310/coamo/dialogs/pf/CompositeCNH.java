package com.m4rc310.coamo.dialogs.pf;

import java.util.Arrays;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.coamo.controllers.LocalidadeController;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.ActionPessoaFisica;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.ConstPF;
import com.m4rc310.coamo.models.CNH;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.coamo.parts.IComposite;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;

@Creatable
public class CompositeCNH implements IComposite, ConstPF {

	@Inject
	PartControl pc;

	@Inject
	LocalidadeController localidadeController;

	@Override
	public Composite make(Composite parent) {
		return null;
	}

	@Override
	public Composite make(MAction action, Composite parent_) {
//		Composite parent = pc.getComposite(parent_);

		ActionPessoaFisica actionCoamo = (ActionPessoaFisica) action;

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(3, false));
		pc.margins(parent, 2, 2, 2, 2);
		pc.fillHorizontalComponent(parent);
//		parent.setText("CNH");

		Composite c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);

		Label label = pc.getLabel(c, "Categoria:");
		ComboViewer comboViewerCategoria = pc.getComboViewer(c, SWT.READ_ONLY);
		pc.fillHorizontalComponent(comboViewerCategoria.getControl());
		pc.groupControl(comboViewerCategoria.getCombo(), label);

		comboViewerCategoria.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerCategoria.setInput(Arrays.asList("A", "B", "AB", "C", "AC", "D", "AD", "E", "AE"));

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.fillHorizontalComponent(c);
		pc.clearMargins(c);
		label = pc.getLabel(c, "Número:");
		Text textNumero = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		pc.fillHorizontalComponent(textNumero);
		pc.groupControl(textNumero, label);

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);
		label = pc.getLabel(c, "Validade:");
		Text textValidade = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textValidade, 10);
		pc.configureDateField("dd/MM/yyyy", textValidade);
		pc.groupControl(textValidade, label);
		
		
		
		actionCoamo.addListener(this, PF$reset_dialog, e -> {
			pc.enabled(false, textNumero, textValidade, comboViewerCategoria.getCombo());
		});
		
		actionCoamo.addListener(this, PF$dialog_in_edition, e -> {
			pc.enabled(true, textNumero, textValidade, comboViewerCategoria.getCombo());
		});

		actionCoamo.addListener(this,PF$load_pessoa_fisica , e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			CNH cnh = pf == null ? null : pf.getCnh();
			pc.observeComboList(comboViewerCategoria, "categoria", cnh);
			pc.observeWidget(textNumero, "numero", cnh);
			pc.observeWidget(textValidade, "validade", cnh, "dd/MM/yyyy");
		});

		return parent;
	}

}
