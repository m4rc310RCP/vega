package com.m4rc310.rcp.mercado.livre.ml.dialogs;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.rcp.mercado.livre.ml.cipa.models.GrupoLocais;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;

public class DialogAddLocal extends DialogAddItens{

	private Local local;

	@Inject
	public DialogAddLocal(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent_) {

		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));

		Group group = pc.getGroup(parent);
		group.setLayout(new GridLayout(2, false));
		
		pc.getLabel(group, m.labelGrupoLocal);

		Text textGrupoLocal = pc.getText(group, "");
		pc.enabled(false, textGrupoLocal);
		pc.setGriData(textGrupoLocal, 300);
		
		pc.getLabel(group, m.labelDescricao);
		Text textDescricao = pc.getText(group, "");
		pc.setGriData(textDescricao, 200);
		
		pc.getLabel(group, m.labelNumeroPessoas);
		Text textNumeroPessoas = pc.getText(group, "");
		pc.setGriData(textNumeroPessoas, 80);
		pc.configureNumericValues(textNumeroPessoas);

		Object selection = ((StructuredSelection) sel).getFirstElement();
		if (selection instanceof GrupoLocais) {
			GrupoLocais g = (GrupoLocais) selection;
			textGrupoLocal.setText(g.getDescricao());
		}
		
		this.local = new Local();
		
		pc.observeTextString(textDescricao, "descricao", local);
		pc.observeTextString(textNumeroPessoas, "numeroPessoas", local);
		
		textDescricao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Text text = (Text)e.widget;
				boolean en = !text.getText().isEmpty();
				Button button = getButton(IDialogConstants.OK_ID);
				button.setEnabled(en);
				getShell().setDefaultButton(button);
			}
		});

//		Button button = getButton(IDialogConstants.OK_ID);
//		System.out.println(button);
		
		return parent;
	}
	
	
	
	
	
	@Override
	public void save(SelectionEvent event) {
		Object selection = ((StructuredSelection) sel).getFirstElement();
		if (selection instanceof GrupoLocais) {
			GrupoLocais g = (GrupoLocais) selection;
			cipaAction.addLocal(g, local);
		}
		
		
	}

	@Override
	public void cancel(SelectionEvent event) {
	}

}
