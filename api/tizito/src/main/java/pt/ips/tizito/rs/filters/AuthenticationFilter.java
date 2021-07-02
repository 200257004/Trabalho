package pt.ips.tizito.rs.filters;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.entities.Trouble;
import pt.ips.tizito.rs.managers.RequestManager;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

	private static final String ACCOUNTS = "accounts";

	private static final String AUTHORIZATION_HEADER = "Authorization";

	private static final String BASIC_PREFIX = "Basic ";

	@Inject
	private UserDAO userDAO;

	@Inject
	private RequestManager requestManager;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		boolean isAccountResource = requestContext.getUriInfo().getPath().equals(ACCOUNTS);

		if (!isAccountResource) {
			this.verifyUser(requestContext);

			if (this.requestManager.isEmpty()) {
				Trouble trouble = new Trouble(Error.AUTHENTICATION_NOT_FOUND);

				Response response = Response.status(Status.UNAUTHORIZED).entity(trouble).build();

				requestContext.abortWith(response);
			}
		}
	}

	private void verifyUser(ContainerRequestContext requestContext) {
		List<String> headers = requestContext.getHeaders().get(AUTHORIZATION_HEADER);

		if (headers != null && !headers.isEmpty()) {
			String value = headers.get(0);

			if (value != null && value.startsWith(BASIC_PREFIX)) {
				String token = value.replaceFirst(BASIC_PREFIX, "");

				StringTokenizer tokenizer = new StringTokenizer(Base64.decodeAsString(token), ":");

				if (tokenizer.hasMoreTokens()) {
					String email = tokenizer.nextToken();

					if (tokenizer.hasMoreTokens()) {
						String password = tokenizer.nextToken();

						User user = this.userDAO.findByEmail(email);

						if (user != null && user.hasPassword(password) && user.isActive()) {
							this.requestManager.setUser(user);
						}
					}
				}
			}
		}
	}

}
