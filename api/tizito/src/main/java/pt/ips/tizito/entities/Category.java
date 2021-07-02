package pt.ips.tizito.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import pt.ips.tizito.dtos.CategoryDTO;
import pt.ips.tizito.dtos.IdeaDTO;
import pt.ips.tizito.messages.Error;

@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int MIN_SIZE = 2;
	private static final int MAX_SIZE = 20;

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	@OneToMany(mappedBy = "category", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	@OrderBy(value = "description")
	private List<Idea> ideas;

	protected Category() {
		this.ideas = new ArrayList<Idea>();
	}

	public Category(String description) {
		this();

		this.setDescription(description);
	}

	public Category(CategoryDTO dto) {
		this();

		this.setDTO(dto);
	}

	public void setDTO(CategoryDTO dto) {
		this.clear();

		this.setDescription(dto.getDescription());

		List<IdeaDTO> ideaDTOs = dto.getIdeaDTOs();

		if (ideaDTOs != null) {
			for (IdeaDTO ideaDTO : ideaDTOs) {
				Idea idea = new Idea(ideaDTO);

				this.add(idea);
			}
		}
	}

	private void clear() {
		List<Idea> ideias = new ArrayList<Idea>(this.ideas);

		for (Idea idea : ideias) {
			this.remove(idea);
		}
	}

	private void validateDescription(String description) {
		if (description == null) {
			throw new NullPointerException(Error.DESCRIPTION_NOT_NULL);
		}

		if (description.length() < MIN_SIZE || description.length() > MAX_SIZE) {
			throw new IllegalArgumentException(String.format(Error.DESCRIPTION_SIZE, MIN_SIZE, MAX_SIZE));
		}
	}

	public void add(Idea idea) {
		if (idea == null) {
			throw new NullPointerException(Error.IDEIA_NOT_NULL);
		}

		if (!this.contains(idea)) {
			this.ideas.add(idea);

			idea.setCategory(this);
		}
	}

	public void remove(Idea idea) {
		if (this.contains(idea)) {
			this.ideas.remove(idea);

			idea.setCategory(null);
		}
	}

	public boolean contains(Idea idea) {
		return this.ideas.contains(idea);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Category category = (Category) obj;

		return new EqualsBuilder().append(this.description, category.getDescription()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.description).toHashCode();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.validateDescription(description);

		this.description = description;
	}

	public List<Idea> getIdeas() {
		return Collections.unmodifiableList(ideas);
	}

	@Override
	public String toString() {
		return "Category [description=" + description + "]";
	}

}
