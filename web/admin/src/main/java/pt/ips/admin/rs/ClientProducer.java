package pt.ips.admin.rs;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;

@ApplicationScoped
public class ClientProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Client client;

	static {
		ClientConfig config = new ClientConfig();

		client = ClientBuilder.newClient(config);
		client.register(GsonMessageBodyHandler.class);
	}

	@Produces
	public Client createClient() {
		return client;
	}

	@PreDestroy
	public void preDestroy() {
		client.close();
	}

}
