package pt.ips.ubot.rs.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static pt.ips.ubot.constants.Web.BASIC_PREFIX;
import static pt.ips.ubot.constants.Web.HEADER;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.internal.util.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import pt.ips.ubot.constants.Web;
import pt.ips.ubot.dtos.AccountDTO;
import pt.ips.ubot.entities.Project;
import pt.ips.ubot.rs.entities.Trouble;
import pt.ips.ubot.rs.handlers.GsonMessageBodyHandler;

@RunWith(JUnitPlatform.class)
public class AuthenticationFilterTest {

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
		this.target = client.target(Web.BASE_URI);
	}

	@Test
	public void naoAceitaRequisicoesSemCabecalho() {
		Response response = this.target.request().get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesComCabecalhoVazio() {
		Response response = this.target.request().header("", "").get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesComCabecalhoDesconhecido() {
		Response response = this.target.request().header("Token", "").get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesSemValor() {
		Response response = this.target.request().header(HEADER, null).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesComValorVazio() {
		Response response = this.target.request().header(HEADER, "").get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesSemPrefixo() {
		Response response = this.target.request().header(HEADER, "admin@ips.pt:!10Admin").get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesSemToken() {
		Response response = this.target.request().header(HEADER, BASIC_PREFIX).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesSemEmail() {
		String token = Base64.encodeAsString(":!10Admin");

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesSemSenha() {
		String token = Base64.encodeAsString("admin@ips.pt:");

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesSemEmailESenha() {
		String token = Base64.encodeAsString(":");

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesComEmailInexistente() {
		String token = Base64.encodeAsString("administrator@ips.pt:!10Admin");

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesComSenhaInexistente() {
		String token = Base64.encodeAsString("admin@ips.pt:!10Administrator");

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesComEmailESenhaInexistentes() {
		String token = Base64.encodeAsString("administrator@ips.pt:!10Administrator");

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void naoAceitaRequisicoesComUsuarioInativo() {
		String token = Base64.encodeAsString("inactive@ips.pt:!10Inactive");

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();
		assertThat(response.getStatus(), equalTo(401));

		Trouble trouble = response.readEntity(Trouble.class);
		assertThat(trouble.getMessage(), equalTo("This request requires user authentication."));
	}

	@Test
	public void aceitaRequisicoesComUsuarioAtivo() {
		String token = Base64.encodeAsString("active@ips.pt:!10Active");

		Response response = this.target.request().header(HEADER, BASIC_PREFIX + token).get();
		assertThat(response.getStatus(), equalTo(200));

		Project project = response.readEntity(Project.class);
		assertThat(project.getName(), equalTo("Tizito"));
	}

	@Test
	public void aceitaRequisicoesSemUsuario() {
		AccountDTO dto = new AccountDTO();
		dto.setName("Active");
		dto.setEmail("active@ips.pt");
		dto.setPassword("!10Active");

		Entity<AccountDTO> entity = Entity.entity(dto, MediaType.APPLICATION_JSON);
		
		Response response = this.target.path("/accounts").request().post(entity);
		assertThat(response.getStatus(), equalTo(409));
	}

	@AfterAll
	public static void libera() {
		client.close();
	}

}
