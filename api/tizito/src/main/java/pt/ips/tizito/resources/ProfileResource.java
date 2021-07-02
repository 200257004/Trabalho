package pt.ips.tizito.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import pt.ips.tizito.entities.Profile;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.rs.managers.RequestManager;

@Path("profile")
public class ProfileResource {

	@Inject
	private RequestManager requestManager;

	@GET
	public Profile get() {
		User user = this.requestManager.getUser();

		Profile profile = new Profile();
		profile.setName(user.getName());
		profile.setEmail(user.getEmail());

		return profile;
	}

}
