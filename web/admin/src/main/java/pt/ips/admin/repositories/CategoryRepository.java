package pt.ips.admin.repositories;

import static pt.ips.admin.constants.API.BASE_URI;
import static pt.ips.admin.constants.API.BASIC_PREFIX;
import static pt.ips.admin.constants.API.HEADER;
import static pt.ips.admin.constants.API.STATUS_CREATED;
import static pt.ips.admin.constants.API.STATUS_NO_CONTENT;
import static pt.ips.admin.constants.API.STATUS_OK;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ips.admin.dtos.Category;
import pt.ips.admin.dtos.Trouble;
import pt.ips.admin.security.Session;

public class CategoryRepository {

	private static final String CATEGORIES_URI = BASE_URI + "/categories";

	private final Session session;

	private WebTarget target;

	@Inject
	public CategoryRepository(Session session, Client client) {
		this.session = session;

		this.target = client.target(CATEGORIES_URI);
	}

	CategoryRepository() {
		this(null, null);
	}

	public void add(Category category) {
		Entity<Category> entity = Entity.entity(category, MediaType.APPLICATION_JSON);

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + this.session.getToken()).post(entity);

		if (response.getStatus() == STATUS_CREATED) {
			String location = response.getHeaderString("Location");

			Long id = Long.valueOf(location.substring(location.lastIndexOf("/") + 1, location.length()));

			category.setId(id);
		} else {
			Trouble trouble = response.readEntity(Trouble.class);

			throw new RuntimeException(trouble.getMessage());
		}
	}

	public void update(Category category) {
		Entity<Category> entity = Entity.entity(category, MediaType.APPLICATION_JSON);

		Response response = this.target.path("/" + category.getId()).request().header(HEADER, BASIC_PREFIX + this.session.getToken()).put(entity);

		if (response.getStatus() != STATUS_OK) {
			Trouble trouble = response.readEntity(Trouble.class);

			throw new RuntimeException(trouble.getMessage());
		}
	}

	public Category get(Long id) {
		Response response = this.target.path("/" + id).request().header(HEADER, BASIC_PREFIX + this.session.getToken()).get();

		if (response.getStatus() == STATUS_OK) {
			return response.readEntity(Category.class);
		} else {
			return null;
		}
	}

	public List<Category> list() {
		Response response = this.target.request().header(HEADER, BASIC_PREFIX + this.session.getToken()).get();

		if (response.getStatus() == STATUS_OK) {
			return response.readEntity(new GenericType<List<Category>>() {
			});
		} else {
			return new ArrayList<Category>();
		}
	}

	public void delete(Long id) {
		Response response = this.target.path("/" + id).request().header(HEADER, BASIC_PREFIX + this.session.getToken()).delete();

		if (response.getStatus() != STATUS_NO_CONTENT) {
			Trouble trouble = response.readEntity(Trouble.class);

			throw new RuntimeException(trouble.getMessage());
		}
	}

}
