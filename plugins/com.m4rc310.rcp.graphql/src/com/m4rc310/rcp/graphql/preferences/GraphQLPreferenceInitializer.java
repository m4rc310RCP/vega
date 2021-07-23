package com.m4rc310.rcp.graphql.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.framework.FrameworkUtil;

public class GraphQLPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(FrameworkUtil.getBundle(getClass()).getSymbolicName());
		if(node!=null) {
			node.putBoolean("is.local.server", true);
			node.put("url.local.server", "http://localhost:8080");
			node.put("url.web.server", "https://corumbatai.herokuapp.com");
			
			node.put("url.web.socket", "ws://corumbatai.herokuapp.com/ws/websocket");
			node.put("url.local.socket", "ws://localhost:8080/ws/websocket");
			try {
				node.flush();
			} catch (Exception e) {
			}
		}
	}

}
