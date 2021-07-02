package pt.ips.tizito.resources;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import pt.ips.tizito.cdi.annotations.Transactional;
import pt.ips.tizito.daos.MeetingDAO;
import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.dtos.MeetingDTO;
import pt.ips.tizito.entities.Invite;
import pt.ips.tizito.entities.Meeting;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.entities.Trouble;
import pt.ips.tizito.rs.managers.RequestManager;
import pt.ips.tizito.validators.MeetingValidator;

@Path("meetings")
public class MeetingResource {

	@Inject
	private RequestManager requestManager;

	@Inject
	private MeetingValidator validator;

	@Inject
	private MeetingDAO meetindDAO;
	
	@Inject
	private UserDAO userDAO;

	@Context
	private UriInfo uriInfo;

	@POST
	@Transactional
	public Response add(MeetingDTO dto) {
		if (dto == null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.MEETING_NOT_NULL)).build();
		}

		try {
			User user = this.requestManager.getUser();

			this.validator.validate(dto);

			Meeting meeting = new Meeting(user, dto);
			
			List<Long> inviteds = dto.getInviteds();
			
			for (Long invitedId : inviteds) {
				User invited = this.userDAO.findById(invitedId);
				
				Invite invite = new Invite(invited);
				
				meeting.add(invite);
			}

			this.meetindDAO.add(meeting);

			return Response.created(this.getLocation(meeting.getId())).build();
		} catch (RuntimeException e) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(e.getMessage())).build();
		}
	}

	private URI getLocation(Long id) {
		return this.uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
	}

	@GET
	@Path("{id}")
	public Response get(@PathParam("id") Long id) {
		Meeting meeting = this.meetindDAO.findById(id);

		if (meeting == null) {
			return Response.status(Status.NOT_FOUND).entity(new Trouble(Error.MEETING_NOT_FOUND)).build();
		}

		return Response.ok(meeting).build();
	}

	@GET
	public List<Meeting> list() {
		User user = this.requestManager.getUser();

		return this.meetindDAO.list(user);
	}

}
