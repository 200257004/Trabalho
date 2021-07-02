package pt.ips.admin.controllers;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import pt.ips.admin.repositories.CategoryRepository;

@Controller
public class DashboardController {

	private final CategoryRepository repository;

	private final Result result;

	@Inject
	public DashboardController(CategoryRepository repository, Result result) {
		this.repository = repository;
		this.result = result;
	}

	DashboardController() {
		this(null, null);
	}

	@Get("/dashboard")
	public void dashboard() {
		this.result.include("categories", this.repository.list());
	}

}
