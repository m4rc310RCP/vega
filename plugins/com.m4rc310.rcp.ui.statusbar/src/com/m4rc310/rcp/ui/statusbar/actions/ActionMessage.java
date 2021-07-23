package com.m4rc310.rcp.ui.statusbar.actions;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.di.UISynchronize;

import com.m4rc310.rcp.ui.statusbar.IConst;
import com.m4rc310.rcp.ui.utils.CronUtils;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable @Singleton
public class ActionMessage extends MAction implements IConst{
	

	@Inject
	UISynchronize sync;
	
	@Inject CronUtils cron;
	
	int count;

	private Job job;

	@Inject
	public void init() {
		this.job = Job.create("", monitor->{
			sync.syncExec(()->fire(TEXT_MESSAGE_STATUS, ""));
		});
	}
	
	public void message(String string) {
		sync.syncExec(()->fire(TEXT_MESSAGE_STATUS, string));
		job.cancel();
		job.schedule(2000);
	}

}
