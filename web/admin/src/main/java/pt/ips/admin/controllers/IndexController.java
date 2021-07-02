package pt.ips.admin.controllers;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import pt.ips.admin.security.Open;

@Controller
public class IndexController {

	@Open
	@Get("/about")
	public void about() {

	}

	@Open
	@Get("/")
	public void login() {

	}

}
