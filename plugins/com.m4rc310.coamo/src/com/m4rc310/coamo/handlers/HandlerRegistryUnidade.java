 
package com.m4rc310.coamo.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;

import com.m4rc310.coamo.MConstants;

import org.eclipse.e4.core.di.annotations.CanExecute;

public class HandlerRegistryUnidade implements MConstants {
	@Execute
	public void execute() {
		
	}
	
	
	@CanExecute
	public boolean canExecute(@Preference(nodePath = PLUGIN_ID, value = PREFERENCE$unidade_is_empty)boolean isEmpty ) {
		return isEmpty;
	}
		
}