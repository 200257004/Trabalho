package pt.ips.tizito.rs.strategies;

import pt.ips.tizito.rs.annotations.Ignore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class IgnoreStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes field) {
		return field.getAnnotation(Ignore.class) != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
