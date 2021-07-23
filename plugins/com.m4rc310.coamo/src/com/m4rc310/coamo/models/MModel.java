package com.m4rc310.coamo.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class MModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	protected PropertyChangeSupport changes;

	public MModel() {
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
