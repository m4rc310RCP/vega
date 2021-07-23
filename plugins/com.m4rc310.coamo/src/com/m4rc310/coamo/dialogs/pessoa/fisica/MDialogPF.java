package com.m4rc310.coamo.dialogs.pessoa.fisica;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.dialogs.MDialog;
import com.m4rc310.coamo.dialogs.pf.CompositeCNH;
import com.m4rc310.coamo.dialogs.pf.CompositeCTPS;
import com.m4rc310.coamo.dialogs.pf.CompositeRG;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.rcp.ui.utils.custom.databinds.MChangeListener;

public class MDialogPF extends MDialog {

	@Inject
	FragmentPFIdentify fragmentPFIdentify;
	@Inject
	FragmentPFStatus fragmentPFStatus;
	@Inject
	FragmentPFBasicsData fragmentPFBasicsData;

	@Inject
	CompositeRG compositeRG;
	
	@Inject
	CompositeCNH compositeCNH;

	@Inject
	CompositeCTPS compositeCTPS;
	
	@Inject
	ActionPF actionPF;
	
//	private final Color LABEL_BACKGROUND = pc.getColor(SWT.COLOR_DARK_GRAY) ;
//	private final Color LABEL_FOREGROUND = pc.getColor(SWT.COLOR_WHITE) ;

	@Inject
	public MDialogPF(Shell parentShell) {
		super(parentShell);
	}

	MChangeListener listener = e -> {
		
		if (e.getSource() instanceof PessoaFisica) {
			PessoaFisica pf = (PessoaFisica) e.getSource();
			actionPF.prepareToPersiste(pf);
		}
		
		
	};

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Group groupA = getGroup(parent, 1);
		fragmentPFIdentify.make(actionPF, groupA);

		Group groupB = getGroup(parent, 1);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupB.setLayoutData(gd);
		fragmentPFStatus.make(actionPF, groupB);

		Group groupC = getGroup(parent, 1);
		gd = pc.fillHorizontalComponent(groupC);
		gd.horizontalSpan = 2;

		fragmentPFBasicsData.make(actionPF, groupC);

		CTabFolder folder = pc.createCTabFolder(groupC);
		folder.setBorderVisible(true);
		folder.setLayout(new GridLayout());
		pc.fillHorizontalComponent(folder);

		Composite docs = pc.createCTabItemContainer(folder, "Documentos");
		
		Composite docComposite = pc.getComposite(docs);
		docComposite.setLayout(new GridLayout(2, false));
		pc.fillHorizontalComponent(docComposite);

		Composite c = getComposite(docComposite, 1);
		Label label = getLabel(c, "RG - Carteira de Identidade");
		compositeRG.make(actionPF, c);
		pc.setContentToScrolled(docs);
		pc.groupControl(c,label);
		

		c = getComposite(docComposite, 1);
		pc.fillHorizontalComponent(c);
		label = getLabel(c, "CNH - Carteira Nacional de Habilitação");
		compositeCNH.make(actionPF, c);
		pc.setContentToScrolled(c);
		
		c = getComposite(docComposite, 1);
		gd = pc.fillHorizontalComponent(c);
		gd.horizontalSpan = 2;
		label = getLabel(c, "CTPS - Carteira de Trabalho e Previdência Social");
		compositeCTPS.make(actionPF, c);
		pc.setContentToScrolled(c);
		
//		pc.removeObserverListener(listener);
//		pc.addObserverListener(listener);

		actionPF.addListener(this, FIRE$close_dialog_pf, e -> {
			close(true);
		});

		actionPF.resetDialog();

		return parent;
	}

	
	public void close(boolean confirm) {
		if (confirm) {
			super.close();
		}
	}

	@Override
	protected void cancelPressed() {
		actionPF.cancelPf();
	}

	@Override
	protected void okPressed() {
		actionPF.save();
	}

}
