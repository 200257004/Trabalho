package pt.ips.ubot.resources;

import static pt.ips.ubot.constants.Web.ADMIN_VALUE;
import static pt.ips.ubot.constants.Web.CATEGORIES_URI;
import static pt.ips.ubot.constants.Web.HEADER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import pt.ips.ubot.dtos.CategoryDTO;
import pt.ips.ubot.dtos.IdeaDTO;
import pt.ips.ubot.entities.Category;
import pt.ips.ubot.rs.entities.Trouble;
import pt.ips.ubot.rs.handlers.GsonMessageBodyHandler;

@RunWith(JUnitPlatform.class)
public class CategoryResourceTest {

	private static Client client;

	private WebTarget target;

	@BeforeAll
	public static void configura() {
		ClientConfig config = new ClientConfig();

		client = ClientBuilder.newClient(config);
		client.register(GsonMessageBodyHandler.class);
	}

	@BeforeEach
	public void inicializa() {
		this.target = client.target(CATEGORIES_URI);
	}

	@Test
	public void naoAdicionaUmaCategoriaNula() {
		Entity<CategoryDTO> entity = Entity.entity(null, MediaType.APPLICATION_JSON);

		Response response = this.target.request().header(HEADER, ADMIN_VALUE).post(entity);
		assertThat(response.getStatus(), equalTo(400));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("Category must not be null."));
	}

	@Test
	public void naoAdicionaUmaCategoriaInvalida() {
		CategoryDTO dto = new CategoryDTO();

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response response = this.target.request().header(HEADER, ADMIN_VALUE).post(entity);
		assertThat(response.getStatus(), equalTo(400));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("Description must not be null."));
	}

	@Test
	public void adicionaUmaCategoriaSemIdeias() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response response = this.target.request().header(HEADER, ADMIN_VALUE).post(entity);
		assertThat(response.getStatus(), equalTo(201));

		String location = response.getHeaderString("Location");

		this.remove(location);
	}

	@Test
	public void adicionaUmaCategoriaComIdeias() {
		IdeaDTO ideaDTO = new IdeaDTO();
		ideaDTO.setDescription("Karaokê");

		List<IdeaDTO> ideaDTOs = new ArrayList<IdeaDTO>();
		ideaDTOs.add(ideaDTO);

		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");
		dto.setIdeaDTOs(ideaDTOs);

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response response = this.target.request().header(HEADER, ADMIN_VALUE).post(entity);
		assertThat(response.getStatus(), equalTo(201));

		String location = response.getHeaderString("Location");

		this.remove(location);
	}

	@Test
	public void naoAlteraUmaCategoriaInexistente() {
		Long id = Long.MAX_VALUE;

		CategoryDTO newDTO = new CategoryDTO();
		newDTO.setDescription("Andar de ...");

		Entity<CategoryDTO> newEntity = Entity.entity(newDTO, MediaType.APPLICATION_JSON);

		Response response = this.target.path("/" + id).request().header(HEADER, ADMIN_VALUE).put(newEntity);
		assertThat(response.getStatus(), equalTo(404));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("Category not found."));
	}

	@Test
	public void naoAlteraUmaCategoriaComDadosInvalidos() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		CategoryDTO newDTO = new CategoryDTO();
		newDTO.setDescription("A");

		Entity<CategoryDTO> newEntity = Entity.entity(newDTO, MediaType.APPLICATION_JSON);

		Response response = client.target(location).request().header(HEADER, ADMIN_VALUE).put(newEntity);
		assertThat(response.getStatus(), equalTo(400));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("Description size must be between 2 and 20."));

		this.remove(location);
	}

	@Test
	public void alteraUmaCategoriaSemIdeias() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		CategoryDTO newDTO = new CategoryDTO();
		newDTO.setDescription("Andar de ...");

		Entity<CategoryDTO> newEntity = Entity.entity(newDTO, MediaType.APPLICATION_JSON);

		Response response = client.target(location).request().header(HEADER, ADMIN_VALUE).put(newEntity);
		assertThat(response.getStatus(), equalTo(200));

		this.remove(location);
	}

	@Test
	public void alteraUmaCategoriaComIdeias() {
		IdeaDTO ideaDTO = new IdeaDTO();
		ideaDTO.setDescription("Caminhada");

		List<IdeaDTO> ideaDTOs = new ArrayList<IdeaDTO>();
		ideaDTOs.add(ideaDTO);

		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");
		dto.setIdeaDTOs(ideaDTOs);

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		IdeaDTO newIdeaDTO = new IdeaDTO();
		newIdeaDTO.setDescription("Patins");

		ideaDTOs.add(newIdeaDTO);

		CategoryDTO newDTO = new CategoryDTO();
		newDTO.setDescription("Andar de ...");
		newDTO.setIdeaDTOs(ideaDTOs);

		Entity<CategoryDTO> newEntity = Entity.entity(newDTO, MediaType.APPLICATION_JSON);

		Response response = client.target(location).request().header(HEADER, ADMIN_VALUE).put(newEntity);
		assertThat(response.getStatus(), equalTo(200));

		this.remove(location);
	}

	@Test
	public void buscaUmaCategoriaPorId() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		Response response = client.target(location).request().header(HEADER, ADMIN_VALUE).get();
		assertThat(response.getStatus(), equalTo(200));

		Category category = response.readEntity(Category.class);
		assertThat(category.getDescription(), equalTo("Combinar um(a) ..."));

		this.remove(location);
	}

	@Test
	public void naoBuscaUmaCategoriaInexistente() {
		Long id = Long.MAX_VALUE;

		Response response = this.target.path("/" + id).request().header(HEADER, ADMIN_VALUE).get();
		assertThat(response.getStatus(), equalTo(404));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("Category not found."));
	}

	@Test
	public void listaCategorias() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		Response response = this.target.request().header(HEADER, ADMIN_VALUE).get();
		assertThat(response.getStatus(), equalTo(200));

		List<Category> list = response.readEntity(new GenericType<List<Category>>() {
		});
		assertThat(list, hasSize(1));

		Category category = list.get(0);
		assertThat(category.getDescription(), equalTo("Combinar um(a) ..."));

		this.remove(location);
	}

	@Test
	public void removeUmaCategoria() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		Response response = client.target(location).request().header(HEADER, ADMIN_VALUE).delete();
		assertThat(response.getStatus(), equalTo(204));

		assertThat(client.target(location).request().header(HEADER, ADMIN_VALUE).get().getStatus(), equalTo(404));
	}

	@Test
	public void removeTodasAsCategorias() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		Response response = this.target.request().header(HEADER, ADMIN_VALUE).delete();
		assertThat(response.getStatus(), equalTo(204));

		assertThat(client.target(location).request().header(HEADER, ADMIN_VALUE).get().getStatus(), equalTo(404));
	}

	private void remove(String location) {
		Response response = client.target(location).request().header(HEADER, ADMIN_VALUE).delete();
		assertThat(response.getStatus(), equalTo(204));
	}

	@AfterAll
	public static void libera() {
		client.close();
	}

}
