package pt.ips.tizito.resources;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import pt.ips.tizito.cdi.annotations.Transactional;
import pt.ips.tizito.daos.RequestDAO;
import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.dtos.RequestDTO;
import pt.ips.tizito.entities.Request;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.entities.Trouble;
import pt.ips.tizito.rs.managers.RequestManager;
import pt.ips.tizito.validators.RequestValidator;

@Path("requests")
public class RequestResource {

	@Inject
	private RequestManager requestManager;

	@Inject
	private RequestValidator validator;

	@Inject
	private RequestDAO requestDAO;

	@Inject
	private UserDAO userDAO;

	@Context
	private UriInfo uriInfo;

	@POST
	@Transactional
	public Response add(RequestDTO dto) {
		if (dto == null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.REQUEST_NOT_NULL)).build();
		}

		try {
			User requester = this.requestManager.getUser();

			User requested = this.userDAO.findById(dto.getRequested());

			Request request = this.requestDAO.findByRequesterAndRequested(requester, requested);

			if (request == null) {
				this.validator.validate(dto);

				request = new Request(requester, requested);

				this.requestDAO.add(request);

				return Response.created(this.getLocation(request.getId())).build();
			} else {
				return Response.status(Status.CONFLICT).location(this.getLocation(request.getId())).build();
			}
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
		Request request = this.requestDAO.findById(id);

		if (request == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(request).build();
	}

	@GET
	public List<Request> list() {
		User requested = this.requestManager.getUser();

		return this.requestDAO.listByRequested(requested);
	}

	@PUT
	@Path("{id}/response")
	@Transactional
	public Response update(@PathParam("id") Long id, RequestDTO dto) {
		Request request = this.requestDAO.findById(id);

		if (request == null || !request.getRequested().equals(this.requestManager.getUser())) {
			return Response.status(Status.NOT_FOUND).entity(new Trouble(Error.REQUEST_NOT_FOUND)).build();
		}

		if (request.getResponse() != null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.REQUEST_ANSWERED)).build();
		}

		if (!request.getRequester().isActive()) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.REQUESTER_INACTIVE)).build();
		}

		if (dto == null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.REQUEST_NOT_NULL)).build();
		}

		pt.ips.tizito.enums.Response response = dto.getResponse();

		if (response == null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.RESPONSE_NOT_NULL)).build();
		}

		request.setResponse(response);

		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response remove(@PathParam("id") Long id) {
		Request request = this.requestDAO.findById(id);

		if (request != null && request.getRequester().equals(this.requestManager.getUser()) && request.getResponse() == null) {
			this.requestDAO.remove(request);
		}

		return Response.noContent().build();
	}

}