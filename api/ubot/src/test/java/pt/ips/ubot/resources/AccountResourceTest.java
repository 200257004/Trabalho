package pt.ips.ubot.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static pt.ips.ubot.constants.Web.ACCOUNTS_URI;
import static pt.ips.ubot.constants.Web.ADMIN_VALUE;
import static pt.ips.ubot.constants.Web.HEADER;

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

import pt.ips.ubot.dtos.AccountDTO;
import pt.ips.ubot.rs.entities.Trouble;
import pt.ips.ubot.rs.handlers.GsonMessageBodyHandler;

@RunWith(JUnitPlatform.class)
public class AccountResourceTest {

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
		this.target = client.target(ACCOUNTS_URI);
	}

	@Test
	public void naoAdicionaUmaContaNula() {
		Entity<AccountDTO> entity = Entity.entity(null, MediaType.APPLICATION_JSON);

		Response response = this.target.request().post(entity);
		assertThat(response.getStatus(), equalTo(400));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("Account must not be null."));
	}

	@Test
	public void naoAdicionaUmaContaInvalida() {
		AccountDTO dto = new AccountDTO();

		Entity<AccountDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response response = this.target.request().post(entity);
		assertThat(response.getStatus(), equalTo(400));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("Name must not be null."));
	}

	@Test
	public void adicionaUmaConta() {
		AccountDTO dto = new AccountDTO();
		dto.setName("Fulano");
		dto.setEmail("fulano@gmail.com");
		dto.setPassword("!10Fulano");

		Entity<AccountDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response response = this.target.request().post(entity);
		assertThat(response.getStatus(), equalTo(201));

		String location = response.getHeaderString("Location");

		this.remove(location);
	}

	@Test
	public void naoAdicionaUmaContaRepetida() {
		AccountDTO dto = new AccountDTO();
		dto.setName("Inactive");
		dto.setEmail("inactive@ips.pt");
		dto.setPassword("!10Inactive");

		Entity<AccountDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);

		Response response = this.target.request().post(entity);
		assertThat(response.getStatus(), equalTo(409));
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
