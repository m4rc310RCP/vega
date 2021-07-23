package com.m4rc310.coamo.dialogs.pf;

import static org.hamcrest.Matchers.endsWithIgnoringCase;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.coamo.controllers.LocalidadeController;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.ActionPessoaFisica;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.ConstPF;
import com.m4rc310.coamo.models.CTPS;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.coamo.parts.IComposite;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;

@Creatable
public class CompositeCTPS implements IComposite, ConstPF {

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
		parent.setLayout(new GridLayout(5, false));
		pc.margins(parent, 2, 2, 2, 2);
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		parent.setText("CNH");

		Composite c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);

		pc.getLabel(c, "");
		Button buttonIsDigital = pc.getButton(c, "Informar CTPS Digital", SWT.CHECK, e -> {
//			Button button = (Button)e.widget;
//			actionCoamo.informeCtpsDigital(button.getSelection());
		});

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);

		Label label = pc.getLabel(c, "Número:");
		Text textNumero = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textNumero, 13);
		pc.groupControl(textNumero, label);

		pc.fillHorizontalComponent(c);
		pc.fillHorizontalComponent(textNumero);

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);

		label = pc.getLabel(c, "Série:");
		Text textSerie = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textSerie, 8);
		pc.groupControl(textSerie, label);

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);

		label = pc.getLabel(c, "Data:");
		Text textData = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		pc.configureDateField("dd/MM/yyyy", textData);
		pc.setWidth(textData, 10);
		pc.groupControl(textData, label);

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(2, false));
		pc.clearMargins(c);

		label = pc.getLabel(c, "Expedição (UF e data):");
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		ComboViewer comboViewerUF = pc.getComboViewer(c, SWT.READ_ONLY);
		pc.setWidth(comboViewerUF.getControl(), 8);
		comboViewerUF.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerUF.setInput(localidadeController.listUfs());

		Text textDataExpedicao = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textDataExpedicao, 10);
		pc.configureDateField("dd/MM/yyyy", textDataExpedicao);

		pc.groupControl(comboViewerUF.getCombo(), label, textDataExpedicao);

		pc.groupControl(textNumero, textSerie, textData, textDataExpedicao, comboViewerUF.getCombo());

//		actionCoamo.addListener(this, FIRE$enable_ctps_digital, e->{
//			boolean isdigital = e.getValue(boolean.class);
//			pc.enabled(!isdigital, textNumero);
//		});
		
		actionCoamo.addListener(this, PF$dialog_in_edition, e -> {
			pc.enabled(true, textNumero);
		});

		actionCoamo.addListener(this, PF$reset_dialog, e -> {
			pc.enabled(false, textNumero);
		});
		
		actionCoamo.addListener(this, PF$load_pessoa_fisica, e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			CTPS ctps = pf == null ? null : pf.getCtps();

			pc.observeWidget(buttonIsDigital, "digital", ctps);
			pc.observeWidget(textNumero, "numero", ctps);
			pc.observeWidget(textSerie, "serie", ctps);
			pc.observeWidget(textData, "dataCadastro", ctps, "dd/MM/yyyy");
			pc.observeWidget(textDataExpedicao, "dataExpedicao", ctps, "dd/MM/yyyy");
			pc.observeComboList(comboViewerUF, "uf", ctps);

		});

		return parent;
	}

}
