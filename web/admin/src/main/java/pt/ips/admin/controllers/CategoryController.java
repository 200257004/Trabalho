package pt.ips.admin.controllers;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import pt.ips.admin.dtos.Category;
import pt.ips.admin.repositories.CategoryRepository;

@Controller
public class CategoryController {

	private final Validator validator;
	
	private final CategoryRepository repository;

	private final Result result;

	@Inject
	public CategoryController(Validator validator, CategoryRepository repository, Result result) {
		this.validator = validator;
		this.repository = repository;
		this.result = result;
	}

	CategoryController() {
		this(null, null, null);
	}

	@Get("/category/new")
	public void form() {

	}

	@Post("/category")
	public void add(Category category) {
		try {
			if (category.getId() == null) {
				this.repository.add(category);
			} else {
				this.repository.update(category);
			}

			this.result.redirectTo(this).view(category.getId());
		} catch (RuntimeException e) {
			this.validator.add(new SimpleMessage("error", e.getMessage()));
			this.validator.onErrorRedirectTo(this).form();
		}
	}

	@Get("/category/{id}")
	public void view(Long id) {
		Category category = this.repository.get(id);

		if (category == null) {
			this.result.notFound();
		} else {
			this.result.include(category);
		}
	}

	@Get("/category/{id}/versao")
	public void edit(Long id) {
		this.view(id);

		if (this.result.included().containsKey("category")) {
			this.result.redirectTo(this).form();
		}
	}

	@Get("/category/{id}/poster")
	public Download poster(Long id) {
		return null;
	}

	@Delete("/category/{id}")
	public void remove(Long id) {
		Category category = this.repository.get(id);

		if (category == null) {
			this.result.notFound();
		} else {
			try {
				this.repository.delete(id);
				
				this.result.redirectTo(DashboardController.class).dashboard();
			} catch (RuntimeException e) {
				this.validator.add(new SimpleMessage("error", e.getMessage()));
				this.validator.onErrorRedirectTo(this).view(id);
			}
		}
	}

}
