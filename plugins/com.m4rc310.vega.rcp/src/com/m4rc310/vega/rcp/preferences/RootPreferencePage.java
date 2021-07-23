package com.m4rc310.vega.rcp.preferences;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.preference.FieldEditorPreferencePage;

import com.m4rc310.vega.rcp.Consts;
import com.m4rc310.vega.rcp.i18n.M;

public class RootPreferencePage extends FieldEditorPreferencePage  implements PreferenceConsts, Consts{
	
	@Inject @Translation
	private M m;
	
	@Inject @Optional
	private ESelectionService selectionService;
	
	@Inject IEventBroker eventBroker;
	

	public RootPreferencePage() {
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void createFieldEditors() {
		
//		Composite parent = getFieldEditorParent();
//		StringFieldEditor appCodeNameField = new StringFieldEditor(PREF_APP_CODE_NAME, m.labelAppCodeName,50, parent);
//		appCodeNameField.getTextControl(parent).addListener(SWT.Modify, e->{
//			String appCodeName = ((Text)e.widget).getText();
//			eventBroker.send(PREF_APP_CODE_NAME, appCodeName);
//		});
//		
//		parent = getFieldEditorParent();
//		
//		
//		addField(appCodeNameField);
	}
	
	@Override
	public boolean performOk() {
		boolean ret = super.performOk();
		eventBroker.send(UPDATE_UI, "Change preferences");
		return ret;
	}

}
