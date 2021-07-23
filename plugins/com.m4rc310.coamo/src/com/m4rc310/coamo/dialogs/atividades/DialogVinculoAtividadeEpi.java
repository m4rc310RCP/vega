package com.m4rc310.coamo.dialogs.atividades;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.m4rc310.coamo.dialogs.MDialog;
import com.m4rc310.coamo.dialogs.epis.ActionEpi;
import com.m4rc310.coamo.dialogs.epis.ConstEpi;
import com.m4rc310.coamo.models.Atividade;
import com.m4rc310.coamo.models.EPI;

public class DialogVinculoAtividadeEpi extends MDialog implements ConstAtividade, ConstEpi {

	@Inject
	ActionAtividade actionAtividade;
	@Inject
	ActionEpi actionEpi;

	@Inject
	public DialogVinculoAtividadeEpi(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(1, false));
		pc.fillHorizontalComponent(parent);

		Composite line = pc.getComposite(parent);
		makeAtividadeArea(line);

		line = pc.getComposite(parent);
		makeListEpisArea(line);

		return parent;
	}

	private void makeAtividadeArea(Composite line) {
		line.setLayout(new GridLayout(5, false));
		pc.fillHorizontalComponent(line);
		pc.clearMargins(line);

		Label label = getLabel(line, "Atividade e/ou Risco");
		pc.fillHorizontalComponent(label).horizontalSpan = 5;

		label = pc.getLabel(line, "Código:");
		Text textId = pc.getText(line, "", SWT.CENTER | SWT.BORDER);
		pc.setWidth(textId, 5);
//		pc.addKeyListener(listener, controls);
		pc.addTextModifyListener(textId, e -> {
			Text text = ((Text) e.widget);
			if (text.isEnabled()) {
				String sid = text.getText();
				actionAtividade.writingId(sid);
			}
		});

		ToolBar toolbar = new ToolBar(line, SWT.FLAT);
		ToolItem itemSearch = getToolItem(toolbar, ICONS$search);
		itemSearch.addListener(SWT.Selection, e -> actionAtividade.searchAtividade());

		Composite stack = pc.getStackComposite(line);
		Button buttonAdvance = pc.getButton(stack, "Avançar", e -> actionAtividade.advance(textId.getText()));
		Button buttonWait = pc.getButton(stack, "Aguarde...");
		Button buttonCancel = pc.getButton(stack, "Cancelar", e -> actionAtividade.cancel());

		Text textAtividadeDescricao = pc.getText(line, "");
		pc.setWidth(textAtividadeDescricao, 40);

		getShell().addListener(SWT.Show, e -> {
			actionAtividade.init();
		});

		actionAtividade.addListener(this, ATIVIDADE$load_atividade, e -> {
			Atividade atividade = e.getValue(Atividade.class);
			pc.observeWidget(textId, "id", atividade, "%03d");
			pc.observeWidget(textAtividadeDescricao, "descricao", atividade);
		});

		actionAtividade.addListener(this, ATIVIDADE$wait_for_action, e -> {
			pc.enabled(false, textId, textAtividadeDescricao, itemSearch);
			pc.toTopControl(buttonWait);
		});

		actionAtividade.addListener(this, ATIVIDADE$reset_dialog, e -> {
			pc.enabled(false, buttonAdvance, textAtividadeDescricao, buttonWait);
			pc.enabled(true, itemSearch, textId);
			pc.grabFocus(textId);
			pc.toTopControl(buttonAdvance);
			
			actionEpi.cancel();
		});

		actionAtividade.addListener(this, ATIVIDADE$in_edition, e -> {
			pc.enabled(false, textId, itemSearch);
			pc.enabled(true, textAtividadeDescricao);
			pc.toTopControl(buttonCancel);
			
			actionEpi.initEpiAgregation();
		});

		actionAtividade.addListener(this, ATIVIDADE$prepare_to_advance, e -> {
			boolean prepare = e.getValue(boolean.class);
			pc.enabled(prepare, buttonAdvance);
			pc.enabled(!prepare, itemSearch);
			pc.toTopControl(buttonAdvance);
			pc.setDefaultButton(buttonAdvance);
		});

	}

	private void makeListEpisArea(Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		pc.fillHorizontalComponent(parent);
		pc.clearMargins(parent);

		Composite line = pc.getComposite(parent);
		line.setLayout(new GridLayout(2, false));
		pc.clearMargins(line);
		pc.fillHorizontalComponent(line).horizontalSpan = 3;

		Label labelTitle = pc.getLabel(line, "Lista de EPIs (Separados por virgula):");
		Label separator = pc.getLabel(line, "", SWT.HORIZONTAL | SWT.SEPARATOR);
		pc.fillHorizontalComponent(separator);

		Label label = pc.getLabel(parent, "Códigos de EPIs:");
		Text textEpis = pc.getText(parent, "");
		pc.addTextModifyListener(textEpis, e->{
			String text = ((Text)e.widget).getText();
			actionAtividade.writingEpis(text);
		});
		pc.fillHorizontalComponent(textEpis);
		pc.groupControl(textEpis, labelTitle, separator, label);

		Button buttonAdd = pc.getButton(parent, "Vincular", e->actionAtividade.saveAndLoad());

		TableViewer tableViewerEpis = pc.getTableViewer(parent,SWT.BORDER);
		tableViewerEpis.getTable().setLinesVisible(true);
		GridData gd = pc.fillHorizontalComponent(tableViewerEpis.getTable());
		gd.heightHint = 200;
		gd.horizontalSpan = 3;
		pc.widtCollumn(tableViewerEpis, 0, 100f);
		
		pc.createCollumn(tableViewerEpis, SWT.NONE, "", 0, new StyledCellLabelProvider() {
			public void update(ViewerCell cell) {
				Object element = cell.getElement();
				if (element instanceof EPI) {
					EPI epi = (EPI) element;
					String txt = String.format("%03d - %s", epi.getId(), epi.getDescricao());
					cell.setText(txt);
					cell.setImage(pc.getImage(PLUGIN$coamo, "icons/applications-development.png"));
				}
				
				super.update(cell);
			}
		});
//			
//			@Override
//			public void update(ViewerCell cell) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			public org.eclipse.swt.graphics.Image getImage(Object element) {
//				if (element instanceof EPI) {
//					return pc.getImage(PLUGIN$coamo, "icons/applications-development.png");
//				}
//				return super.getImage(element);
//			}
//			
//			public String getText(Object element) {
//				if (element instanceof EPI) {
//					EPI epi = (EPI) element;
//					String txt = String.format("%03d - %s", epi.getId(), epi.getDescricao());
//					return txt;
//				}
//				
//				return super.getText(element);
//			}
//		});

		separator = pc.getLabel(parent, "", SWT.HORIZONTAL | SWT.SEPARATOR);
		pc.fillHorizontalComponent(separator).horizontalSpan = 3;

		getShell().addListener(SWT.Show, e -> {
			actionEpi.init();
		});

		actionAtividade.addListener(this, ATIVIDADE$load_epis, e->{
			List<EPI> epis = e.getListValue(0, EPI.class);
			tableViewerEpis.setInput(epis);
			getShell().setDefaultButton(getButton(OK_ID));
		});
		
		actionAtividade.addListener(this, ATIVIDADE$load_atividade, e->{
			Atividade atividade = e.getValue(Atividade.class);
			pc.observeWidget(textEpis, "epis", atividade);
		});
		
		actionAtividade.addListener(this, ATIVIDADE$valid_epis, e->{
			boolean valid = e.getValue(boolean.class);
			pc.enabled(valid, buttonAdd);
			pc.setDefaultButton(buttonAdd);
		});
		
		actionEpi.addListener(this, EPI$reset_dialog, e -> {
			pc.enabled(false, textEpis, buttonAdd);
		});
		
		actionEpi.addListener(this, EPI$init_epi_agregation, e -> {
			pc.enabled(true, textEpis);
			pc.grabFocus(textEpis);
		});
	}
	
	@Override
	protected void okPressed() {
		actionAtividade.cancel();
	}

	@Override
	protected Control createButtonBar(Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(3, false));

		Label label = pc.getLabel(parent, "");
		pc.fillHorizontalComponent(parent);
		pc.fillHorizontalComponent(label);

		createButton(parent, CANCEL_ID, "Cancelar", false);
		createButton(parent, OK_ID, "Efetivar", true);

		pc.clearMargins(parent);

		return parent;
	}

}
