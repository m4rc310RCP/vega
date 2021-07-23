package com.m4rc310.financas.actions;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;

import com.m4rc310.financas.i18n.M;
import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;

@Creatable
public class ActionDefault extends MAction {
	
	@Inject protected UISynchronize sync;
	@Inject protected MGraphQL graphQL;
	@Inject protected PartControl pc;
	@Inject protected IEventBroker eventBroker;
	@Inject @Translation protected M m;
	protected final Logger logger = Logger.getLogger(getClass());
	
	private boolean lock;
	
	protected void afire(String ref, Object... args) {
		sync.asyncExec(()->{
			fire(ref, args);
		});
	}
	protected void sfire(String ref, Object... args) {
		sync.syncExec(()->{
			fire(ref, args);
		});
	}
	
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	public boolean isLock() {
		return lock;
	}

}
