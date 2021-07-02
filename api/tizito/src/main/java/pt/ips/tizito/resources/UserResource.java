package pt.ips.tizito.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.rs.managers.RequestManager;

@Path("users")
public class UserResource {

	@Inject
	private UserDAO userDAO;

	@Inject
	private RequestManager requestManager;

	@GET
	public List<User> list(@QueryParam("name") String name) {
		User user = this.requestManager.getUser();

		if (name == null || name.isEmpty()) {
			return this.userDAO.listAll(user);
		}

		return this.userDAO.listByName(name, user);
	}

}
