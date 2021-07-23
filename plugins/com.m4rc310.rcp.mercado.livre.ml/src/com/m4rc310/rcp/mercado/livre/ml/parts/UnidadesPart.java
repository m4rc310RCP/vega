
package com.m4rc310.rcp.mercado.livre.ml.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import com.m4rc310.rcp.mercado.livre.ml.addons.actions.CipaAction;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.GrupoLocais;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Risco;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Setor;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Unidade;
import com.m4rc310.rcp.mercado.livre.ml.i18n.Messages;
import com.m4rc310.rcp.mercado.livre.ml.utils.AWTImageConverter;
//import com.m4rc310.rcp.mercado.livre.utils.AWTImageConverter;
import com.m4rc310.rcp.ui.utils.PartControl;

import cipa.Img;

public class UnidadesPart {

	@Inject
	UISynchronize sync;

	@Inject
	@Translation
	Messages m;

	@Inject
	ESelectionService selectionService;

	ISelectionChangedListener selectionListener;

//	@Inject IMemento memeto;

	@Inject
	PartControl pc;

	private Text textSearch;

	private TreeViewer viewer;

	@Inject
	public UnidadesPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent_, EMenuService menuService, Shell shell, CipaAction action) {

		selectionListener = (event -> {
			selectionService.setSelection(event.getSelection());
		});

//		listener = event -> {
//			selectionService.setSelection(event.getSelection());
//		}

		Composite parent = new Composite(parent_, SWT.NONE);
		parent.setLayout(new GridLayout(1, false));
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		this.textSearch = pc.getText(parent, "", SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		textSearch.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textSearch.setMessage(m.textSearch);

//		this.viewer = tree.getViewer();

		this.viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		menuService.registerContextMenu(viewer.getControl(),
				"com.m4rc310.rcp.mercado.livre.ml.popupmenu.unidade.itens");

//		viewer.addFilter(filter);
//		this.viewer = new SearchableTreeViewer(parent, (treeItems, searchText) -> {
//			for (TreeItem item : treeItems) {
//				if (item.getText().toUpperCase().contains(searchText.toUpperCase())) {
//					return item;
//				}
//			}
//			return null;
//		});

		Tree tree = viewer.getTree();
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));

		viewer.setContentProvider(new TreeContentProvider());
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);

		viewer.addSelectionChangedListener(selectionListener);

		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(300);
		viewerColumn.getColumn().setResizable(true);
		viewerColumn.getColumn().setText(m.textColumnUnidades);

		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object e) {

				if (e instanceof String) {
					return String.format("%s", e);
				}

				if (e instanceof Unidade) {
					Unidade u = (Unidade) e;

					String nome = "%03d - %s";
					nome = String.format(nome, u.getNumero(), u.getNome());

					return nome;
				}

				if (e instanceof Setor) {
					Setor s = (Setor) e;
					String nome = "%03d - %s";
					nome = String.format(nome, s.getLotacao(), s.getDescricao());
					return nome;
				}

				if (e instanceof GrupoLocais) {
					GrupoLocais g = (GrupoLocais) e;
					String nome = "%03d - %s (Total de %d pessoas)";
					nome = String.format(nome, g.getNumero(), g.getDescricao(), g.getNumeroPessoas());
					return nome;
				}

				if (e instanceof Local) {
					Local l = (Local) e;
					String nome = " %03d - %s (%d pessoas)";
					nome = String.format(nome, l.getId(), l.getDescricao(), l.getNumeroPessoas());
					return nome;
				}

				if (e instanceof Risco) {
					Risco r = (Risco) e;
					String nome = "%s (%s)";

					String sr = "";
					

					switch (r.getTamanho()) {
					case 1:
						sr = "P";
						break;
					case 2:
						sr = "M";
						break;
					case 3:
						sr = "G";
						break;
					}

					nome = String.format(nome, r.getDescricao(), sr);
					return nome;
				}

				return super.getText(e);
			}

			@Override
			public Image getImage(Object element) {

				if (element instanceof Unidade) {
					return pc.createImageDescriptor("com.m4rc310.rcp.mercado.livre.ml", "img/chart_org_inverted.png")
							.createImage();
				}

				if (element instanceof Setor) {
					return pc.createImageDescriptor("com.m4rc310.rcp.mercado.livre.ml", "img/flag_white.png")
							.createImage();
				}

				if (element instanceof GrupoLocais) {
					return pc.createImageDescriptor("com.m4rc310.rcp.mercado.livre.ml", "img/cmy.png").createImage();
				}

				if (element instanceof Risco) {
					Risco r = (Risco) element;
					Display display = Display.getDefault();
					return AWTImageConverter.convert(display, Img.getIcon(r.getGrupoRisco(), 32));
				}

				if (element instanceof Local) {
					Local local = (Local) element;
					Display display = Display.getDefault();
					return AWTImageConverter.convert(display, Img.getSingleIcon(local, 64));
				}

				return super.getImage(element);
			}

		});

		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		Transfer[] transferTypes = { LocalSelectionTransfer.getTransfer() };

		viewer.addDragSupport(operations, transferTypes, new DragSourceListener() {

			@Override
			public void dragStart(DragSourceEvent event) {
				final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
				final ISelection selection = viewer.getSelection();
				transfer.setSelection(selection);
				transfer.setSelectionSetTime(event.time & 0xFFFF);
				event.doit = !selection.isEmpty();
			}

			@Override
			public void dragSetData(DragSourceEvent event) {

			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				final LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
				if (event.doit == false) {
					return;
				}
				transfer.setSelection(null);
				transfer.setSelectionSetTime(0);
			}
		});

		viewer.setUseHashlookup(true);
		viewer.setAutoExpandLevel(1);

		viewer.setInput(m.textLoading);

		action.getStream().addListener(CipaAction.LOAD_UNIDADES, event -> {
			List<Unidade> unidades = event.getListValue(0, Unidade.class);
			load(unidades);
		});

		action.getStream().addListener(CipaAction.INFORME_ERRO, event -> {
			MessageDialog.openError(shell, "", event.getValue(0, Exception.class).getMessage());
		});

	}

	@PreDestroy
	public void preDestroy() {
		if (selectionListener != null)
			viewer.removeSelectionChangedListener(selectionListener);
		selectionListener = null;
	}

	@Inject
	@Optional
	public void load(@EventTopic("load_unidades") List<Unidade> unidades) {
		sync.asyncExec(() -> {

			Tree tree = viewer.getTree();
			tree.setRedraw(false);

//			Object[] expandedElements = viewer.getExpandedElements();
			TreePath[] expandedTreePaths = viewer.getExpandedTreePaths();

			final ISelection selection = viewer.getSelection();

			viewer.setInput(unidades);
			viewer.expandAll();

			viewer.setSelection(selection);
//			viewer.setExpandedElements(expandedElements);
			viewer.setExpandedTreePaths(expandedTreePaths);

			tree.setRedraw(true);

		});

	}

	private class TreeContentProvider implements ITreeContentProvider {

		private final Object[] EMPTY_ARRAY = new Object[0];

		@Override
		public Object[] getElements(Object inputElement) {
			try {

				if (inputElement instanceof String) {
					String text = (String) inputElement;
					return new Object[] { text };
				}

				return ((List<?>) inputElement).toArray();
			} catch (Exception e) {
				return EMPTY_ARRAY;
			}
		}

		@Override
		public Object[] getChildren(Object parentElement) {

			if (parentElement instanceof Unidade) {
				return ((Unidade) parentElement).getSetores().toArray();
			}
			if (parentElement instanceof Setor) {
				return ((Setor) parentElement).getGrupos().toArray();
			}
			if (parentElement instanceof GrupoLocais) {
				return ((GrupoLocais) parentElement).getLocais().toArray();
			}
			if (parentElement instanceof Local) {
				return ((Local) parentElement).getRiscos().toArray();
			}

			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {

			if (element instanceof String) {
				return false;
			}

			if (element instanceof Unidade) {
				Unidade unidade = (Unidade) element;
				return unidade.getSetores() != null;
			}

			if (element instanceof Setor) {
				Setor setor = (Setor) element;
				return setor.getGrupos() != null;
			}
			if (element instanceof GrupoLocais) {
				GrupoLocais grupo = (GrupoLocais) element;
				return grupo.getLocais() != null;
			}
			if (element instanceof Local) {
				Local l = (Local) element;
				return l.getRiscos() != null;
			}

			return false;
		}

	}

}