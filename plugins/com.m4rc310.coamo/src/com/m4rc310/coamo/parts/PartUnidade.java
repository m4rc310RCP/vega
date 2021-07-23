
package com.m4rc310.coamo.parts;

import java.io.File;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.m4rc310.coamo.actions.ActionCoamo;
import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.models.Funcionario;
import com.m4rc310.coamo.models.Lotacao;
import com.m4rc310.coamo.models.Unidade;
import com.m4rc310.rcp.ui.utils.PartControl;

public class PartUnidade implements IActionCoamoConsts {

	@Inject
	UISynchronize sync;

	@Inject
	ESelectionService selectionService;
	
	@Inject MPart part;
	@Inject EPartService partService;
	
	@Inject IEventBroker eventBroker;

	@Inject
	public PartUnidade() {

	}

	@PostConstruct
	public void post(Composite parent, ActionCoamo action, EMenuService menuService, PartControl pc) {
		TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new UnidadeContentProvider());

		
		ISelectionChangedListener selectionListener = (event -> {
			selectionService.setSelection(event.getSelection());
		});

		viewer.addOpenListener(event -> {
			Object selected = ((IStructuredSelection) event.getSelection()).getFirstElement();
			action.open(selected);
		});
//		

		menuService.registerContextMenu(viewer.getTree(), "com.m4rc310.coamo.popupmenu.unidade");

		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				if (element instanceof Funcionario) {
					return pc.getImage("com.m4rc310.coamo", "icons/user.png");
				}
				return createImageDescriptor().createImage();
			}

			@Override
			public String getText(Object element) {
				if (element instanceof Unidade) {
					Unidade u = (Unidade) element;
					return String.format("(%03d) %s [ %d funcionário(s) ]", u.getNumero(), u.getNome(),
							u.getQuantidadeFuncionarios());
				}

				if (element instanceof Lotacao) {
					Lotacao l = (Lotacao) element;
					return String.format("%04d - %s", l.getNumero(), l.getNome());
				}

				if (element instanceof Funcionario) {
					Funcionario fun = (Funcionario) element;
					return String.format("%s - %s", fun.getMatricula(), fun.getDados().getNome());
				}

				return super.getText(element);
			}
		});

		viewer.addSelectionChangedListener(selectionListener);
		

//		action.addListener(this, FIRE$open_funcionario_part, event -> {
//			Funcionario f = event.getValue(Funcionario.class);
//			String title = String.format("%s", f.getDados().getNome());
//			sync.syncExec(() -> pc.show("com.m4rc310.coamo.partdescriptor.funcionario", title, title, f));
//		});

		
		eventBroker.subscribe(EVENT_TOPIC$update, e->{
			action.startedUnidadePart();
		});
		
		action.addListener(this, FIRE$load_unidade, e -> {
			sync.syncExec(() -> {
				Tree tree = viewer.getTree();
				tree.setRedraw(false);

				TreePath[] expandedTreePaths = viewer.getExpandedTreePaths();

				final ISelection selection = viewer.getSelection();

				viewer.setInput(e.getValue());

				viewer.expandAll();

				viewer.setSelection(selection);
				
//				viewer.setExpandedElements(expandedElements);
				viewer.setExpandedTreePaths(expandedTreePaths);

				tree.setRedraw(true);
				partService.showPart(part, PartState.ACTIVATE);
			});

		});

		action.startedUnidadePart();

	}

	public void postConstruct(Composite parent, PartControl pc, ActionCoamo action) {

		TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider(createImageDescriptor())));
		viewer.setInput(File.listRoots());

		action.addListener(this, FIRE$load_unidade, e -> {
//			sync.syncExec(()->treeViewer.setInput(e.getValue()));
//			sync.syncExec(()->treeViewer.setInput(new String[] { "Simon Scholz", "Lars Vogel", "Dirk Fauth", "Wim Jongman", "Tom Schindl" }));
		});

//		GridLayoutFactory.fillDefaults().generateLayout(parent);

	}

	private ImageDescriptor createImageDescriptor() {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/folder.png"), null);
		return ImageDescriptor.createFromURL(url);
	}

	class UnidadeContentProvider implements ITreeContentProvider {

		final ArrayContentProvider provider = ArrayContentProvider.getInstance();

		@Override
		public Object[] getElements(Object element) {
			return provider.getElements(element);
		}

		@Override
		public Object[] getChildren(Object element) {
			if (element instanceof Unidade) {
				Unidade unidade = (Unidade) element;
				return provider.getElements(unidade.getLotacoes());
			}

			if (element instanceof Lotacao) {
				Lotacao lotacao = (Lotacao) element;
				return provider.getElements(lotacao.getFuncionarios());
			}

			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof Unidade) {
				Unidade u = (Unidade) element;
				return u.getLotacoes() != null;
			}

			if (element instanceof Lotacao) {
				Lotacao l = (Lotacao) element;
				return l.getFuncionarios() != null;
			}

			return false;
		}

	}

	class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {

		private ImageDescriptor directoryImage;
		private ResourceManager resourceManager;

		public ViewLabelProvider(ImageDescriptor directoryImage) {
			this.directoryImage = directoryImage;
		}

		@Override
		public StyledString getStyledText(Object element) {
			if (element instanceof File) {
				File file = (File) element;
				StyledString styledString = new StyledString(getFileName(file));
				String[] files = file.list();
				if (files != null) {
					styledString.append(" ( " + files.length + " ) ", StyledString.COUNTER_STYLER);
				}
				return styledString;
			}
			return null;
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof File) {
				if (((File) element).isDirectory()) {
					return getResourceManager().createImage(directoryImage);
				}
			}

			return super.getImage(element);
		}

		@Override
		public void dispose() {
			// garbage collect system resources
			if (resourceManager != null) {
				resourceManager.dispose();
				resourceManager = null;
			}
		}

		protected ResourceManager getResourceManager() {
			if (resourceManager == null) {
				resourceManager = new LocalResourceManager(JFaceResources.getResources());
			}
			return resourceManager;
		}

		private String getFileName(File file) {
			String name = file.getName();
			return name.isEmpty() ? file.getPath() : name;
		}
	}

//	private TreeViewer makeTreeViewer(Composite parent) {
//		TreeViewer tv = new TreeViewer(parent);
//		Tree tree = tv.getTree();
//		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
//		tv.setLabelProvider(new StyledCellLabelProvider() {
//			@Override
//			public void update(ViewerCell cell) {
//				Object element = cell.getElement();
//				if (element instanceof Unidade) {
//					Unidade u = (Unidade) element;
//					String text = String.format("(%03d) %s", u.getNumero(), u.getNome());
//					cell.setText(text);
//					
//					System.out.println(text);
//				}
//
//				super.update(cell);
//			}
//		});
////		
//		tv.setContentProvider(new ITreeContentProvider() {
//
//			@Override
//			public boolean hasChildren(Object element) {
//				if (element instanceof Unidade) {
//					return true;
//				}
//				return false;
//			}
//
//			@Override
//			public Object getParent(Object element) {
//				return null;
//			}
//
//			@Override
//			public Object[] getElements(Object inputElement) {
//				if (inputElement instanceof Map) {
//					return ArrayContentProvider.getInstance().getElements(((Map<?, ?>) inputElement).values());
//				}
//				return ArrayContentProvider.getInstance().getElements(inputElement);
//			}
//
//			@Override
//			public Object[] getChildren(Object element) {
//				if (element instanceof Unidade) {
//					Unidade unidade = (Unidade) element;
//					return ArrayContentProvider.getInstance().getElements(unidade.getLotacoes());
//				}
//				return null;
//			}
//		});
//
//		return tv;
//	}

}