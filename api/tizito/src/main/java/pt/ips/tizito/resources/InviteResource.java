package pt.ips.tizito.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.ips.tizito.cdi.annotations.Transactional;
import pt.ips.tizito.daos.InviteDAO;
import pt.ips.tizito.dtos.InviteDTO;
import pt.ips.tizito.entities.Invite;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.entities.Trouble;
import pt.ips.tizito.rs.managers.RequestManager;

@Path("invites")
public class InviteResource {

	@Inject
	private RequestManager requestManager;

	@Inject
	private InviteDAO inviteDAO;

	@GET
	public List<Invite> list() {
		User user = this.requestManager.getUser();

		return this.inviteDAO.listAll(user);
	}

	@PUT
	@Path("{id}/response")
	@Transactional
	public Response update(@PathParam("id") Long id, InviteDTO dto) {
		Invite invite = this.inviteDAO.findById(id);

		if (invite == null || !invite.getInvited().equals(this.requestManager.getUser())) {
			return Response.status(Status.NOT_FOUND).entity(new Trouble(Error.INVITE_NOT_FOUND)).build();
		}

		if (invite.getResponse() != null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.INVITE_ANSWERED)).build();
		}

		if (dto == null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.INVITE_NOT_NULL)).build();
		}

		pt.ips.tizito.enums.Response response = dto.getResponse();

		if (response == null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.RESPONSE_NOT_NULL)).build();
		}

		invite.setResponse(response);

		return Response.ok().build();
	}

}
