package com.m4rc310.coamo.dialogs.pessoa.fisica;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.m4rc310.coamo.dialogs.FragmentComposite;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
public class FragmentPFStatus extends FragmentComposite {

	@Override
	public Composite make(MAction action, Composite parent_) {

		ActionPF actionPF = (ActionPF) action;

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(3, false));
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		pc.clearMargins(parent);

		Button checkPfIsActive = pc.getButton(parent, "Ativo", SWT.CHECK);
		checkPfIsActive.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, true, true));
		
		Button checkPfIsEmploye = pc.getButton(parent, "Funcionário", SWT.CHECK);
		checkPfIsEmploye.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, true, true));
		
		Button checkPfIsLocked = pc.getButton(parent, "Bloqueado", SWT.CHECK);
		checkPfIsLocked.setLayoutData(new GridData(SWT.NONE, SWT.CENTER, true, true));
		
		actionPF.addListener(this, FIRE$load_pf, e->{
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.enabled(pf!=null, checkPfIsActive);
			pc.enabled(false, checkPfIsEmploye, checkPfIsLocked);
			
			pc.observeWidget(checkPfIsActive, "ativo", pf);
			pc.observeWidget(checkPfIsEmploye, "funcionario", pf);
			pc.observeWidget(checkPfIsLocked, "bloqueado", pf);
		});

		
		
		return parent;
	}

}
