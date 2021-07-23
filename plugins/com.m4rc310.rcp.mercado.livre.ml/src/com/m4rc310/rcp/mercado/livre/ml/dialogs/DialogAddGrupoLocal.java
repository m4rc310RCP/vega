package com.m4rc310.rcp.mercado.livre.ml.dialogs;

import java.util.Arrays;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Data;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.GrupoLocais;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Setor;

public class DialogAddGrupoLocal extends DialogAddItens {
	

	private Text textDescricao;

	private Setor setor;

	private GrupoLocais grupoLocais;


	@Inject
	public DialogAddGrupoLocal(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout());

		Group group = pc.getGroup(container);
		group.setLayout(new GridLayout(2, false));
		Label label = pc.getLabel(group, m.labelSetores);
		ComboViewer comboSetores = pc.getComboViewer(group);

		GridData gd = new GridData();
		gd.widthHint = 300;

		comboSetores.getCombo().setLayoutData(gd);

		comboSetores.setContentProvider(ArrayContentProvider.getInstance());
		comboSetores.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {

				if (element instanceof Setor) {
					Setor setor = (Setor) element;
					return setor.getDescricao();
				}

				return super.getText(element);
			}
		});

		comboSetores.addSelectionChangedListener(this::selectSetor);

		pc.getLabel(group, m.labelNumero);
		Text textNumero = pc.getText(group, "");
		pc.setGriData(textNumero, 70);
		pc.configureNumericValues(textNumero);
		
		pc.getLabel(group, m.labelDescricao);
		this.textDescricao = pc.getText(group, "");
		textDescricao.setMessage(m.textInfoGroupLocal);
		gd = new GridData();
		gd.widthHint = 300;
		textDescricao.setLayoutData(gd);
		
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

		grupoLocais = new GrupoLocais();
		pc.observeTextString(textNumero, "numero", grupoLocais);
		pc.observeTextString(textDescricao, "descricao", grupoLocais);

		if (sel == null) {
			return container;
		}

		Object selected = ((IStructuredSelection) sel).getFirstElement();

		if (selected instanceof Setor) {
			Setor setor = (Setor) selected;
			comboSetores.setInput(Arrays.asList(setor));
			comboSetores.setSelection(new StructuredSelection(setor));
			pc.enabled(false, label, comboSetores);
		} else {
			try {
				String query = "{setores{id lotacao descricao}}";
				Data data = graphql.query(query, Data.class);
				comboSetores.setInput(data.getSetores());
				pc.enabled(true, label, comboSetores);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

//		comboSetores.setInput(input);

		return container;
	}

//	private void changeText() {}
	
	
	
	private void selectSetor(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		if (selection.size() > 0) {
			this.setor = (Setor) selection.getFirstElement();
		}
	}

	@Override
	public void save(SelectionEvent event) {
		sync.syncExec(() -> {
			cipaAction.addGrupoLocal(grupoLocais.getNumero(), setor, grupoLocais.getDescricao());
		});
	}

	@Override
	public void cancel(SelectionEvent event) {

	}

}
