package com.m4rc310.coamo.dialogs.pessoa.fisica.n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.coamo.actions.m.MDialogSearch;
import com.m4rc310.coamo.models.PessoaFisica;

public class DialogSearchPF extends MDialogSearch implements ConstPF {

	@Inject
	ActionPessoaFisica action;
	
	public static final String ICON$plugin_url = "com.m4rc310.coamo";
	public static final String ICON$path_lock = "icons/lock.png";
	public static final String ICON$path_user = "icons/user.png";

	@Inject
	public DialogSearchPF(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void initListeners() {
		action.addListener(this, PF$load_list_search_results, e -> {
			List<PessoaFisica> results = e.getListValue(0, PessoaFisica.class);
			searchResults(results);
		});
	}

	@Override
	public void makeCollums(TableViewer tableViewer) {
		tableViewer.getTable().setLinesVisible(true);

		pc.createCollumn(tableViewer, SWT.NONE, "", 0, new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				final Object element = cell.getElement();
				final List<StyleRange> ranges = new ArrayList<>();

				if (element instanceof PessoaFisica) {
					PessoaFisica pf = (PessoaFisica) element;
					String text = pf.getCpf() == null ? "{0,number,000} - {2}" : "{0,number,000} - ({1}) {2}";
					text = MessageFormat.format(text, pf.getId(), pf.getCpf(), pf.getNome());
					cell.setText(text);
					
					cell.setImage(pc.getImage(ICON$plugin_url, ICON$path_user));
					
					String cpf = pf.getCpf();
					if(cpf != null) {
						int start = text.indexOf(cpf);
						int length = cpf.length();
						
						Color foreground = pc.getColor(SWT.COLOR_DARK_BLUE);
						Color background = pc.getColor(SWT.COLOR_WHITE);
						
						StyleRange range = new StyleRange(start, length, foreground, background, SWT.ITALIC);
						ranges.add(range);
					}

					if (!pf.getAtivo()) {
						StyleRange range = new StyleRange();
						range.start = 0;
						range.length = text.length();

						range.strikeout = true;
						range.strikeoutColor = pc.getColor(SWT.COLOR_RED);
						range.foreground = pc.getColor(SWT.COLOR_GRAY);

						ranges.add(range);
						cell.setImage(pc.getImage(ICON$plugin_url, ICON$path_lock));
					}
					
					

					cell.setStyleRanges(ranges.stream().toArray(StyleRange[]::new));

					super.update(cell);
				}
			}
		});

		pc.widtCollumn(tableViewer, 0, 100f);

	}

	@Override
	public void search(Object... args) {
		String text = (String) args[0];
		action.searchFor(text);
	}

	@Override
	public void returnValue(Object value) {
		action.loadFromSearch(value);
	}

}
