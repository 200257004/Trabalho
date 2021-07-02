package pt.ips.admin.interceptors;

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import pt.ips.admin.controllers.IndexController;
import pt.ips.admin.security.Open;
import pt.ips.admin.security.Session;

@Intercepts
public class AuthorizationInterceptor {

	private final Session session;

	private final Result result;

	private final ControllerMethod method;

	@Inject
	public AuthorizationInterceptor(Session session, Result result, ControllerMethod method) {
		this.session = session;
		this.result = result;
		this.method = method;
	}

	AuthorizationInterceptor() {
		this(null, null, null);
	}

	@Accepts
	public boolean accepts() {
		boolean isOpenMethod = this.method.containsAnnotation(Open.class);

		return !isOpenMethod;
	}

	@AroundCall
	public void intercepts(SimpleInterceptorStack stack) {
		if (this.session.getUser() == null) {
			this.result.redirectTo(IndexController.class).login();
		} else {
			stack.next();
		}
	}

}
