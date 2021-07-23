package com.m4rc310.rcp.mercado.livre.ml.dialogs;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Risco;

public class DialogAddRisco extends DialogAddItens {

	private Risco risco;
	private String srisco;
	private Local local;

	@Inject
	public DialogAddRisco(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent_) {

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));

		Group group = pc.getGroup(parent);
		group.setLayout(new GridLayout(2, false));

		pc.getLabel(group, m.labelLocal);
		Text textLocal = pc.getText(group, "");
		pc.enabled(false, textLocal);

		pc.setGriData(textLocal, 300);

		pc.getLabel(group, m.labelDescricao);
		Text textDescricao = pc.getText(group, "");
		pc.setGriData(textDescricao, 300);

		Composite rcontainer = pc.getComposite(group);
		rcontainer.setLayout(new GridLayout(4, false));

		pc.clearMargins(rcontainer);
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;

		rcontainer.setLayoutData(gridData);

		pc.getLabel(rcontainer, m.labelGrupo);
		Text textGrupo = pc.getText(rcontainer, "", SWT.BORDER | SWT.CENTER);
		textGrupo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		pc.configureVerifyValues("[F|f|Q|q|b|B|a|A|e|E]", textGrupo);

		pc.getLabel(rcontainer, m.labelTamanho);
		Text textTamanho = pc.getText(rcontainer, "", SWT.BORDER | SWT.CENTER);
		textTamanho.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		pc.configureVerifyValues("[P|p|m|M|g|G]", textTamanho);

		pc.configureUpperCase(textGrupo, textTamanho);

		this.risco = new Risco();
		
		pc.observeTextString(textDescricao, "descricao", risco);
		
		this.srisco = "";
		
		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				boolean enabled = !textDescricao.getText().isEmpty();
				enabled = enabled && !textGrupo.getText().isEmpty();
				enabled = enabled && !textTamanho.getText().isEmpty();
		
				srisco = String.format("%s%s", textGrupo.getText(), textTamanho.getText());
				
				Button button = getButton(IDialogConstants.OK_ID);
				button.setEnabled(enabled);
				getShell().setDefaultButton(button);
			}
		};
		
		
		textDescricao.addKeyListener(keyListener);
		textGrupo.addKeyListener(keyListener);
		textTamanho.addKeyListener(keyListener);
		
		
		Object selection = ((StructuredSelection) sel).getFirstElement();

		if (selection instanceof Local) {
			this.local = (Local) selection;
			pc.observeTextString(textLocal, "descricao", local);
		}

		return parent;
	}

	@Override
	public void save(SelectionEvent event) {
		cipaAction.addRisco(local, risco.getDescricao(), srisco);
	}

	@Override
	public void cancel(SelectionEvent event) {
	}

}
