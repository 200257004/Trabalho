package pt.ips.tizito.cdi.interceptors;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.ips.tizito.cdi.annotations.Admin;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.entities.Trouble;
import pt.ips.tizito.rs.managers.RequestManager;

@Interceptor
@Admin
@Priority(value = Interceptor.Priority.PLATFORM_BEFORE)
public class AuthorizationInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private final RequestManager requestManager;

	@Inject
	public AuthorizationInterceptor(RequestManager requestManager) {
		this.requestManager = requestManager;
	}

	@AroundInvoke
	public Object intercept(InvocationContext context) {
		User user = this.requestManager.getUser();

		if (user.isAdmin()) {
			try {
				return context.proceed();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			Trouble trouble = new Trouble(Error.AUTHORIZATION_NOT_FOUND);

			return Response.status(Status.FORBIDDEN).entity(trouble).build();
		}
	}

}
