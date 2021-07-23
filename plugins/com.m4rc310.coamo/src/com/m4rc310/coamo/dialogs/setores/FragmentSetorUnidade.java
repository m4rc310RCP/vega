package com.m4rc310.coamo.dialogs.setores;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.m4rc310.coamo.dialogs.FragmentComposite;
import com.m4rc310.coamo.models.Unidade;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
public class FragmentSetorUnidade extends FragmentComposite implements ConstSetor {

	@Override
	public Composite make(MAction action, Composite parent_) {

		ActionSetor actionSetor = (ActionSetor) action;

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(3, false));
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		pc.clearMargins(parent);

		Label label = pc.getLabel(parent, "Unidade:");
		ComboViewer comboViewerUndiade = pc.getComboViewer(parent, SWT.READ_ONLY);
		comboViewerUndiade.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerUndiade.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Unidade u = (Unidade) element;

				String nome = String.format("%03d - %s", u.getNumero(), u.getNome());

				return nome;
			}
		});

		pc.setWidth(comboViewerUndiade.getControl(), 35);
		pc.groupControl(comboViewerUndiade.getCombo(), label);

		actionSetor.addListener(this, SETOR$select_unidade, e -> {
			Unidade unidade = e.getValue(Unidade.class);
			comboViewerUndiade.setSelection(new StructuredSelection(unidade));
		});
		
		actionSetor.addListener(this, SETOR$load_unidades, e -> {
			List<Unidade> unidades = e.getListValue(0, Unidade.class);
			comboViewerUndiade.setInput(unidades);
		});

		return parent;
	}

}
