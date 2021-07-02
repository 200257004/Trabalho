package pt.ips.ubot.constants;

import org.glassfish.jersey.internal.util.Base64;

public final class Web {

	public static final String BASE_URI = "http://localhost:8080/tizito/api";
	
	public static final String ACCOUNTS_URI = BASE_URI + "/accounts";
	
	public static final String PROFILE_URI = BASE_URI + "/profile";
	
	public static final String CATEGORIES_URI = BASE_URI + "/categories";
	
	public static final String HEADER = "Authorization";
	
	public static final String BASIC_PREFIX = "Basic ";
	
	public static final String ADMIN_VALUE = BASIC_PREFIX + Base64.encodeAsString("admin@ips.pt:!10Admin");
	
	public static final String ACTIVE_VALUE = BASIC_PREFIX + Base64.encodeAsString("active@ips.pt:!10Active");
	
}
