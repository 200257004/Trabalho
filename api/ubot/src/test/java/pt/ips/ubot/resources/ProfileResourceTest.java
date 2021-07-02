package pt.ips.ubot.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static pt.ips.ubot.constants.Web.ACTIVE_VALUE;
import static pt.ips.ubot.constants.Web.ADMIN_VALUE;
import static pt.ips.ubot.constants.Web.HEADER;
import static pt.ips.ubot.constants.Web.PROFILE_URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import pt.ips.ubot.entities.Profile;
import pt.ips.ubot.rs.handlers.GsonMessageBodyHandler;

@RunWith(JUnitPlatform.class)
public class ProfileResourceTest {

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
		this.target = client.target(PROFILE_URI);
	}

	@Test
	public void buscaOPerfilDoAdministrador() {
		Response response = this.target.request().header(HEADER, ADMIN_VALUE).get();
		assertThat(response.getStatus(), equalTo(200));

		Profile profile = response.readEntity(Profile.class);
		assertThat(profile.getName(), equalTo("Administrator"));
	}

	@Test
	public void buscaOPerfilDoUsuario() {
		Response response = this.target.request().header(HEADER, ACTIVE_VALUE).get();
		assertThat(response.getStatus(), equalTo(200));

		Profile profile = response.readEntity(Profile.class);
		assertThat(profile.getName(), equalTo("Active"));
	}

	@AfterAll
	public static void libera() {
		client.close();
	}

}
