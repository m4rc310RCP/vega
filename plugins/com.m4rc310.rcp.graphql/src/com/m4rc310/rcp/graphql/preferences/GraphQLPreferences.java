
package com.m4rc310.rcp.graphql.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class GraphQLPreferences extends FieldEditorPreferencePage {

	
	
	public GraphQLPreferences() {
		super(GRID);
	}
	
	
	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor("is.local.server", "Usar servidor local", getFieldEditorParent()));
		addField(new StringFieldEditor("url.local.server", "URL do servidor local:", getFieldEditorParent()));
		addField(new StringFieldEditor("url.web.server", "URL do servidor web:", getFieldEditorParent()));
		addField(new StringFieldEditor("url.local.socket", "URL do websocket local:", getFieldEditorParent()));
		addField(new StringFieldEditor("url.web.socket", "URL do websocket web:", getFieldEditorParent()));
	}
	
	@Override
	protected Composite getFieldEditorParent() {
		return null;
	}

}
