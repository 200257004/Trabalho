package pt.ips.ubot.cdi.interceptors;

import static pt.ips.ubot.constants.Web.ACTIVE_VALUE;
import static pt.ips.ubot.constants.Web.ADMIN_VALUE;
import static pt.ips.ubot.constants.Web.CATEGORIES_URI;
import static pt.ips.ubot.constants.Web.HEADER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import pt.ips.ubot.constants.Web;
import pt.ips.ubot.dtos.CategoryDTO;
import pt.ips.ubot.rs.entities.Trouble;
import pt.ips.ubot.rs.handlers.GsonMessageBodyHandler;

@RunWith(JUnitPlatform.class)
public class AuthorizationInterceptorTest {

	private static Client client;

	private WebTarget target;

	@BeforeAll
	public static void carrega() {
		ClientConfig config = new ClientConfig();

		client = ClientBuilder.newClient(config);
		client.register(GsonMessageBodyHandler.class);
	}

	@BeforeEach
	public void inicializa() {
		this.target = client.target(CATEGORIES_URI);
	}

	@Test
	public void autorizaUmAdministradorAdicionarUmaCategoria() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response response = this.target.request().header(HEADER, ADMIN_VALUE).post(entity);
		assertThat(response.getStatus(), equalTo(201));

		String location = response.getHeaderString("Location");

		this.remove(location);
	}

	@Test
	public void naoAutorizaUmUsuarioAdicionarUmaCategoria() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response response = this.target.request().header(HEADER, ACTIVE_VALUE).post(entity);
		assertThat(response.getStatus(), equalTo(403));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authorization."));
	}

	@Test
	public void naoAutorizaUmUsuarioAtualizarUmaCategoria() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		CategoryDTO newDTO = new CategoryDTO();
		newDTO.setDescription("Andar de ...");

		Entity<CategoryDTO> newEntity = Entity.entity(newDTO, MediaType.APPLICATION_JSON);

		Response response = client.target(location).request().header(HEADER, ACTIVE_VALUE).put(newEntity);
		assertThat(response.getStatus(), equalTo(403));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authorization."));

		this.remove(location);
	}

	@Test
	public void naoAutorizaUmUsuarioRemoverUmaCategoria() {
		CategoryDTO dto = new CategoryDTO();
		dto.setDescription("Combinar um(a) ...");

		Entity<CategoryDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		String location = this.target.request().header(HEADER, ADMIN_VALUE).post(entity).getHeaderString("Location");

		Response response = client.target(location).request().header(HEADER, ACTIVE_VALUE).delete();
		assertThat(response.getStatus(), equalTo(403));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authorization."));

		this.remove(location);
	}
	
	@Test
	public void naoAutorizaUmUsuarioRemoverUmaConta() {
		this.target = client.target(Web.ACCOUNTS_URI);
		
		Response response = this.target.path("/admin@ips.pt").request().header(HEADER, ACTIVE_VALUE).delete();
		assertThat(response.getStatus(), equalTo(403));
		
		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authorization."));
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
