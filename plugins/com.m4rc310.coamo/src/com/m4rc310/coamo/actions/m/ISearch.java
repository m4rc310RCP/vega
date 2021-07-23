package com.m4rc310.coamo.actions.m;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

public interface ISearch {
	void search(Object ...args);
	void searchResults(List<?> results);
	
	void initListeners();
	
	
	void buildSearchTopSide(Composite parent);
	void buildResultSide(Composite parent);
	void makeCollums(TableViewer tableView);
	
	void selectValue(Object selected);
	void returnValue(Object value);
}
