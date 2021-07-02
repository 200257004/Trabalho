package pt.ips.tizito.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import pt.ips.tizito.cdi.annotations.Transactional;
import pt.ips.tizito.daos.RequestDAO;
import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.entities.Request;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.rs.managers.RequestManager;

@Path("friends")
public class FriendResource {

	@Inject
	private RequestManager requestManager;

	@Inject
	private UserDAO userDAO;

	@Inject
	private RequestDAO requestDAO;

	@GET
	public List<User> list() {
		List<User> friends = this.requestManager.getUser().getFriends();

		return friends.stream()
				.filter(c -> c.isActive())
				.sorted((c1, c2) -> c1.getName().compareTo(c2.getName()))
				.collect(Collectors.toList());
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response remove(@PathParam("id") Long id) {
		User user = this.requestManager.getUser();

		User friend = this.userDAO.findById(id);

		if (friend != null && user.contains(friend)) {
			user.remove(friend);

			Request request = this.requestDAO.findByRequesterAndRequested(user, friend);

			this.requestDAO.remove(request);
		}

		return Response.noContent().build();
	}

}
