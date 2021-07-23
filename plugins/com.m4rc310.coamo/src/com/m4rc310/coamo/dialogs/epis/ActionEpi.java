package com.m4rc310.coamo.dialogs.epis;

import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.m4rc310.coamo.actions.m.MActionDefault;
import com.m4rc310.coamo.models.EPI;
import com.m4rc310.rcp.ui.utils.MD5Utils;

@Creatable
public class ActionEpi extends MActionDefault implements ConstEpi {

	private Object hashMD5;
	private EPI selected;
	private boolean inEdition = false;
	private boolean enableClone = false;

	public void writingId(String sid) {
		if (!isLock()) {
			sfire(EPI$prepare_to_advance, !sid.isEmpty());
		}
	}

	public void writingResumo(String sresumo) {
		if (!isLock()) {
			if (selected != null) {
				if (enableClone) {
					sfire(EPI$informe_descricao, sresumo);
				}
			}
		}
	}

	public void writingDescricao(String descricao) {
		if (!isLock()) {
			if (selected != null) {
				if (enableClone) {
					sfire(EPI$informe_resumo, descricao);
				}
			}
		}
	}

	public void enableDesableClone() {
//		enableClone = !enableClone;
	}

	public void searchEpi() {
		pc.showDialog(DialogSearchEPI.class, this);
	}

	public void advance(String sid) {
		Job.create("", monitor -> {
			sfire(EPI$wait_for_data);

			String query = "{epi(id:%d){id descricao resumo}}";

			Optional<EPI> data = graphQL.query(query, Long.parseLong(sid)).getData("epi", EPI.class);
			if (data.isPresent()) {
				loadEpi(data.get());
				sfire(EPI$in_edition);
			}else {
				cancel();
			}

			

		}).schedule();
	}

	public void changingEpi(EPI epi) {
		if (!isLock()) {
			String md5 = MD5Utils.getChecksum(epi);
			boolean changed = !hashMD5.equals(md5);
			sfire(EPI$changed_epi, changed);
		}
	}

	public void save() {
		Job.create("", monitor -> {
			String query = "mutation{epi(epi:%s){id descricao resumo}}";
			Optional<EPI> data = graphQL.query(query, graphQL.toGraph(selected)).getData("epi", EPI.class);
			if (data.isPresent()) {
				cancel();
			}
		}).schedule();
	}

	public void loadEpi(EPI epi) {
		setLock(true);
		sfire(EPI$load_epi, epi);
		this.inEdition = epi != null;
		this.hashMD5 = epi == null ? "" : MD5Utils.getChecksum(epi);
		this.selected = epi;
		setLock(false);
	}
	
	public void resultSearch(EPI epi) {
		setLock(true);
		sfire(EPI$load_epi, epi);
		sfire(EPI$in_edition);
		setLock(false);
	}

	public void init() {
		sfire(EPI$reset_dialog);
	}
	
	public void initEpiAgregation() {
		sfire(EPI$init_epi_agregation);
	}
	
	

	public void close() {
		if (inEdition) {
			cancel();
		} else {
			sfire(EPI$close_dialog);
		}
	}

	public void cancel() {
		loadEpi(null);
		init();
	}

	public void searchForEpi(String text) {
		Job.create("", monitor -> {
			String query = "{searchEpi(descricao:\"%s\"){id  resumo descricao}}";
			List<EPI> list = graphQL.query(query, text).getDataList("searchEpi", EPI.class);
			sfire(EPI$search_list_result, list);
		}).schedule();
	}
}
