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

import pt.ips.tizito.cdi.annotations.Admin;
import pt.ips.tizito.cdi.annotations.Auditable;
import pt.ips.tizito.cdi.annotations.Transactional;
import pt.ips.tizito.daos.CategoryDAO;
import pt.ips.tizito.dtos.CategoryDTO;
import pt.ips.tizito.entities.Category;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.entities.Trouble;

@Path("categories")
public class CategoryResource {

	@Inject
	private CategoryDAO categoryDAO;

	@Context
	private UriInfo uriInfo;

	@POST
	@Admin
	@Auditable
	@Transactional
	public Response add(CategoryDTO dto) {
		if (dto == null) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(Error.CATEGORY_NOT_NULL)).build();
		}

		try {
			Category category = new Category(dto);

			this.categoryDAO.add(category);

			return Response.created(this.getLocation(category.getId())).build();
		} catch (RuntimeException e) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(e.getMessage())).build();
		}
	}

	private URI getLocation(Long id) {
		return this.uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
	}

	@PUT
	@Path("{id}")
	@Admin
	@Auditable
	@Transactional
	public Response update(@PathParam("id") Long id, CategoryDTO dto) {
		Category category = this.categoryDAO.findById(id);

		if (category == null) {
			return Response.status(Status.NOT_FOUND).entity(new Trouble(Error.CATEGORY_NOT_FOUND)).build();
		}

		try {
			category.setDTO(dto);

			this.categoryDAO.update(category);

			return Response.ok().build();
		} catch (RuntimeException e) {
			return Response.status(Status.BAD_REQUEST).entity(new Trouble(e.getMessage())).build();
		}
	}

	@GET
	@Path("{id}")
	public Response get(@PathParam("id") Long id) {
		Category category = this.categoryDAO.findById(id);

		if (category == null) {
			return Response.status(Status.NOT_FOUND).entity(new Trouble(Error.CATEGORY_NOT_FOUND)).build();
		}

		return Response.ok(category).build();
	}

	@GET
	public List<Category> list() {
		return this.categoryDAO.list();
	}

	@DELETE
	@Path("{id}")
	@Admin
	@Auditable
	@Transactional
	public Response remove(@PathParam("id") Long id) {
		Category category = this.categoryDAO.findById(id);

		if (category != null) {
			this.categoryDAO.remove(category);
		}

		return Response.noContent().build();
	}

	@DELETE
	@Admin
	@Auditable
	@Transactional
	public Response remove() {
		List<Category> categories = this.categoryDAO.list();

		for (Category category : categories) {
			this.categoryDAO.remove(category);
		}

		return Response.noContent().build();
	}

}
