package pt.ips.admin.controllers;

import java.util.List;

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
import pt.ips.admin.dtos.Idea;
import pt.ips.admin.repositories.CategoryRepository;

@Controller
public class IdeaController {

	private final Validator validator;

	private final CategoryRepository repository;

	private final Result result;

	@Inject
	public IdeaController(Validator validator, CategoryRepository categoryRepository, Result result) {
		this.validator = validator;
		this.repository = categoryRepository;
		this.result = result;
	}

	IdeaController() {
		this(null, null, null);
	}

	@Get("/category/{id}/idea/new")
	public void form(Long id) {
		Category category = this.repository.get(id);

		if (category == null) {
			this.result.notFound();
		} else {
			this.result.include(category);
		}
	}

	@Post("/category/{id}/idea")
	public void add(Long id, Idea idea) {
		Category category = this.repository.get(id);

		if (category == null) {
			this.result.notFound();
		} else {
			try {
				if (idea.getId() == null) {
					category.getIdeas().add(idea);
				} else {
					Idea old = this.get(category, idea.getId());
					old.setDescription(idea.getDescription());
				}

				this.repository.update(category);

				this.result.redirectTo(CategoryController.class).view(id);
			} catch (RuntimeException e) {
				this.validator.add(new SimpleMessage("error", e.getMessage()));
				this.validator.onErrorRedirectTo(this).form(id);
			}
		}
	}

	@Get("/category/{id}/idea/{ideaId}")
	public void view(Long id, Long ideaId) {
		Category category = this.repository.get(id);

		if (category == null) {
			this.result.notFound();
		} else {
			Idea idea = this.get(category, ideaId);

			if (idea == null) {
				this.result.notFound();
			} else {
				this.result.include(idea);
				this.result.redirectTo(this).form(id);
			}
		}
	}

	@Get("/category/{id}/idea/{ideaId}/poster")
	public Download poster(Long id, Long ideaId) {
		return null;
	}

	@Delete("/category/{id}/idea/{ideaId}")
	public void remove(Long id, Long ideaId) {
		Category category = this.repository.get(id);

		if (category == null) {
			this.result.notFound();
		} else {
			try {
				Idea idea = this.get(category, ideaId);

				if (idea == null) {
					this.result.notFound();
				} else {
					category.getIdeas().remove(idea);
				}

				this.repository.update(category);

				this.result.redirectTo(CategoryController.class).view(id);
			} catch (RuntimeException e) {
				this.validator.add(new SimpleMessage("error", e.getMessage()));
				this.validator.onErrorRedirectTo(this).form(id);
			}
		}
	}

	private Idea get(Category category, Long ideaId) {
		List<Idea> ideas = category.getIdeas();

		for (Idea idea : ideas) {
			if (idea.getId().equals(ideaId)) {
				return idea;
			}
		}

		return null;
	}

}
