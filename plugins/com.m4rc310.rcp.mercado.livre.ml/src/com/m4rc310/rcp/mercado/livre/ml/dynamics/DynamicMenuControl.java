
package com.m4rc310.rcp.mercado.livre.ml.dynamics;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.AboutToHide;
import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.osgi.service.log.LogService;

import com.m4rc310.rcp.mercado.livre.ml.cipa.models.GrupoLocais;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Local;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Risco;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Setor;
import com.m4rc310.rcp.mercado.livre.ml.cipa.models.Unidade;
import com.m4rc310.rcp.mercado.livre.ml.i18n.Messages;

public class DynamicMenuControl {
	
	@Inject
	EModelService modelService;

	@Inject
	private LogService log;
	
	@Inject @Translation Messages m;
	
	@Inject
	MApplication application;
	
	
	@AboutToShow
	public void aboutToShow(List<MMenuElement> items, @Named(IServiceConstants.ACTIVE_SELECTION) ISelection sel) {
		
		log.getLogger(getClass()).debug("aboutToShow");
		
		Object selected = ((IStructuredSelection) sel).getFirstElement();
		
		String handleAddRisco = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.DynamicAddRiscoHandler";
		String handleRemoverRisco = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.RemoverRiscoHandler";
//		String bundleRemoverSetor = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.DynamicRemoverSetorHandler";

		
		String handleAddGrupoLocal = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.AddGrupoLocalHandler";
		String handleRemoverItem = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.RemoverItemHandler";
		
		String handleAddSetorItem = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.AddSetorHandler";
		String handleRemoverSetorItem = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.RemoverSetorHandler";

		String handleAddLocal = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.AddLocalHandler";
		String handleRemoverLocal = "bundleclass://com.m4rc310.rcp.mercado.livre.ml/com.m4rc310.rcp.mercado.livre.ml.handlers.RemoverLocalHandler";
		
		if (selected instanceof Unidade) {
			
//			
//			MCommand command = modelService.createModelElement(MCommand.class);
//			command.setElementId("command.delete.setor");
//			command.setCommandName("bundleAddSetorItem");
//			
//			application.getCommands().add(command);
//			
//			MKeyBinding keyBinding = modelService.createModelElement(MKeyBinding.class);
//			keyBinding.setKeySequence("F4");
//			keyBinding.setCommand(command);
			
			
			MDirectMenuItem item = modelService.createModelElement(MDirectMenuItem.class);
			item.setContributionURI(handleAddSetorItem);
			item.setLabel(m.textAddSetor);
			
			
			items.add(item);
		}
		
		
		if (selected instanceof Setor) {
			MDirectMenuItem item = modelService.createModelElement(MDirectMenuItem.class);
			item.setContributionURI(handleAddGrupoLocal);
			item.setLabel(m.textAddGrupoLocal);
			items.add(item);
			
			item = modelService.createModelElement(MDirectMenuItem.class);
			item.setContributionURI(handleRemoverSetorItem);
			item.setLabel(m.textRemoverSetor);
			items.add(item);
		}
		
		if (selected instanceof Local) {
			Local local = (Local) selected;

			MDirectMenuItem item = modelService.createModelElement(MDirectMenuItem.class);
			item.setContributionURI(handleAddRisco);
			item.setLabel(m.textAddRisco);
			items.add(item);
			
			item = modelService.createModelElement(MDirectMenuItem.class);
			item.setContributionURI(handleRemoverLocal);
			item.setLabel(NLS.bind(m.textRemoverLocal, local.getDescricao()));
			items.add(item);
		}
		
		if (selected instanceof GrupoLocais) {
			MDirectMenuItem item = modelService.createModelElement(MDirectMenuItem.class);
			item.setContributionURI(handleAddLocal);
//			item.setIconURI("platform:/plugin/com.m4rc310.rcp.mercado.livre.icons/icons/16x16/places/user-trash.png");
			item.setLabel(m.textAddLocal);
			items.add(item);
			
			item = modelService.createModelElement(MDirectMenuItem.class);
			item.setContributionURI(handleRemoverItem);
			item.setIconURI("platform:/plugin/com.m4rc310.rcp.mercado.livre.icons/icons/16x16/places/user-trash.png");
			item.setLabel(m.textRemover);
			items.add(item);
		}

		if (selected instanceof Risco) {
			MDirectMenuItem item = modelService.createModelElement(MDirectMenuItem.class);
			item.setContributionURI(handleRemoverRisco);
			item.setIconURI("platform:/plugin/com.m4rc310.rcp.mercado.livre.icons/icons/16x16/places/user-trash.png");
			item.setLabel(m.textRemover);
			items.add(item);
			
//			item = modelService.createModelElement(MDirectMenuItem.class);
//			item.setContributionURI(handleAddRisco);
//			item.setIconURI("platform:/plugin/com.m4rc310.rcp.mercado.livre.icons/icons/16x16/places/user-trash.png");
//			item.setLabel(m.textAddRisco);
//			items.add(item);
			
		}
		
	}

	@AboutToHide
	public void aboutToHide(List<MMenuElement> items) {
//		System.out.println(items);

	}

}