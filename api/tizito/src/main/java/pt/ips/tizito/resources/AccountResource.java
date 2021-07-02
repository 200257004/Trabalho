package pt.ips.tizito.resources;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import pt.ips.tizito.cdi.annotations.Admin;
import pt.ips.tizito.cdi.annotations.Auditable;
import pt.ips.tizito.cdi.annotations.Transactional;
import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.dtos.AccountDTO;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.entities.Trouble;

@Path("accounts")
public class AccountResource {

	@Inject
	private UserDAO userDAO;

	@Context
	private UriInfo uriInfo;

	@POST
	@Transactional
	public Response add(AccountDTO dto) {
		if (dto == null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.ACCOUNT_NOT_NULL)).build();
		}

		if (this.userDAO.findByEmail(dto.getEmail()) != null) {
			return Response.status(Status.CONFLICT).build();
		}

		try {
			User user = new User(dto);

			this.userDAO.add(user);

			return Response.created(this.getLocation(user.getEmail())).build();
		} catch (RuntimeException e) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(e.getMessage())).build();
		}
	}

	private URI getLocation(String email) {
		return this.uriInfo.getAbsolutePathBuilder().path(email).build();
	}

	@DELETE
	@Path("{email}")
	@Admin
	@Auditable
	@Transactional
	public Response remove(@PathParam("email") String email) {
		User user = this.userDAO.findByEmail(email);

		if (user != null) {
			this.userDAO.remove(user);
		}

		return Response.noContent().build();
	}

}
