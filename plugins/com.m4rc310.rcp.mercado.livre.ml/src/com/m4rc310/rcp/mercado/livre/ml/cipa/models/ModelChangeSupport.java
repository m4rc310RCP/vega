package com.m4rc310.rcp.mercado.livre.ml.cipa.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelChangeSupport {
	protected PropertyChangeSupport changes;
	
	public ModelChangeSupport() {
		this.changes = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changes.addPropertyChangeListener(propertyName, listener);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		changes.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		changes.removePropertyChangeListener(pcl);
	}
}
