package com.m4rc310.coamo.dialogs.pf;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.controllers.LocalidadeController;
import com.m4rc310.coamo.dialogs.pessoa.fisica.ActionPF;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.ActionPessoaFisica;
import com.m4rc310.coamo.dialogs.pessoa.fisica.n.ConstPF;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.coamo.models.RG;
import com.m4rc310.coamo.parts.IComposite;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

@Creatable
public class CompositeRG implements IComposite, ConstPF {

	@Inject
	PartControl pc;
	
	@Inject LocalidadeController localidadeController;

	@Override
	public Composite make(Composite parent) {
		return null;
	}

	@Override
	public Composite make(MAction action, Composite parent_) {
//		Composite parent = pc.getComposite(parent_);

		ActionPessoaFisica actionCoamo = (ActionPessoaFisica) action;

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(4, false));
		pc.margins(parent, 2, 2, 2, 2);
//		parent.setText("Carteira de Identidade (RG)");
		
		Composite c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);
		

		Label label = pc.getLabel(c, "Número:");
		Text textNumeroRg = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textNumeroRg, 11);
		pc.groupControl(textNumeroRg, label);

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);

		label = pc.getLabel(c, "Orgão Emissor:");
		Text textOrgaoEmissor = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		textOrgaoEmissor.setLayoutData(gd);
		pc.configureUpperCase(textOrgaoEmissor);
		pc.groupControl(textOrgaoEmissor, label);

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);

		label = pc.getLabel(c, "UF:");
		ComboViewer comboViewerUF = pc.getComboViewer(c, SWT.CENTER|SWT.READ_ONLY);
		pc.setWidth(comboViewerUF.getControl(), 8);
		pc.groupControl(comboViewerUF.getCombo(), label);
		
		comboViewerUF.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerUF.setInput(localidadeController.listUfs());
		

		c = pc.getComposite(parent);
		c.setLayout(new GridLayout(1, false));
		pc.clearMargins(c);

		label = pc.getLabel(c, "Data Emissão:");
		Text textDataEmissao = pc.getText(c, "", SWT.BORDER | SWT.CENTER);
		pc.configureDateField("dd/MM/yyyy", textDataEmissao);
		pc.setWidth(textDataEmissao, 9);
		pc.groupControl(textDataEmissao, label, parent);
		
		
		actionCoamo.addListener(this, PF$reset_dialog, e -> {
			pc.enabled(false, textNumeroRg, textOrgaoEmissor, textDataEmissao, parent, comboViewerUF.getCombo());
		});
		
		actionCoamo.addListener(this, PF$dialog_in_edition, e -> {
			pc.enabled(true, textNumeroRg, textOrgaoEmissor, textDataEmissao, parent, comboViewerUF.getCombo());
		});
		

		actionCoamo.addListener(this, PF$load_pessoa_fisica , e -> {
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			RG rg = pf == null ? null : pf.getRg();

			pc.observeWidget(textNumeroRg, "numero", rg);
			pc.observeWidget(textOrgaoEmissor, "emissor", rg);
			pc.observeComboList(comboViewerUF, "uf", rg);
			pc.observeWidget(textDataEmissao, "dataEmissao", rg, "dd/MM/yyyy");

		});

		return parent;
	}

}
