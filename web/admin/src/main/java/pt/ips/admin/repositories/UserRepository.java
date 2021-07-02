package pt.ips.admin.repositories;

import static pt.ips.admin.constants.API.BASE_URI;
import static pt.ips.admin.constants.API.BASIC_PREFIX;
import static pt.ips.admin.constants.API.HEADER;
import static pt.ips.admin.constants.API.STATUS_OK;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import pt.ips.admin.dtos.User;

public class UserRepository {

	private static final String PROFILE_URI = BASE_URI + "/profile";

	private WebTarget target;

	@Inject
	public UserRepository(Client client) {
		this.target = client.target(PROFILE_URI);
	}

	UserRepository() {
		this(null);
	}

	public User findByToken(String token) {
		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();

		if (response.getStatus() == STATUS_OK) {
			return response.readEntity(User.class);
		} else {
			return null;
		}
	}

}
