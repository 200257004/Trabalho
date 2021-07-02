package pt.ips.tizito.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import pt.ips.tizito.entities.Project;

@Path("/")
public class ProjectResource {

//	TODO Configurar Swagger
	@GET
	public Project get() {
		Project project = new Project();
		project.setName("Tizito");
		project.setDescription("My social calendar.");
		project.setUrl("https://www.tizito.com");

		return project;
	}

}
