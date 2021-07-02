package pt.ips.tizito.rs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class MyApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(RolesAllowedDynamicFeature.class);

		return classes;
	}

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("jersey.config.server.provider.packages", "pt.ips.tizito");

		return properties;
	}

}
