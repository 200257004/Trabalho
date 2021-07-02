package pt.ips.admin.controllers;

import javax.inject.Inject;

import org.glassfish.jersey.internal.util.Base64;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import pt.ips.admin.dtos.User;
import pt.ips.admin.repositories.UserRepository;
import pt.ips.admin.security.Open;
import pt.ips.admin.security.Session;

@Controller
public class SessionController {

	private final Validator validator;

	private final UserRepository repository;

	private final Session session;

	private final Result result;

	@Inject
	public SessionController(Validator validator, UserRepository repository, Session session, Result result) {
		this.validator = validator;
		this.repository = repository;
		this.session = session;
		this.result = result;
	}

	SessionController() {
		this(null, null, null, null);
	}

	@Open
	@Post("/signin")
	public void signin(String email, String password) {
		String token = Base64.encodeAsString(email + ":" + password);

		User user = this.repository.findByToken(token);

		if (user == null) {
			this.validator.add(new I18nMessage("signin", "user.not.found"));
			this.validator.onErrorRedirectTo(IndexController.class).login();
		} else {
			this.session.setUser(user, token);

			this.result.redirectTo(DashboardController.class).dashboard();
		}
	}

	@Get("/signout")
	public void signout() {
		this.session.clear();

		this.result.redirectTo(IndexController.class).login();
	}

}
