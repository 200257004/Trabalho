package pt.ips.tizito.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import pt.ips.tizito.dtos.IdeaDTO;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.annotations.Ignore;

@Entity
public class Idea implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int MIN_SIZE = 2;
	private static final int MAX_SIZE = 15;

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	@Ignore
	@ManyToOne
	@JoinColumn(name = "category")
	private Category category;

	protected Idea() {

	}

	public Idea(String description) {
		this.setDescription(description);
	}
	
	public Idea(IdeaDTO dto) {
		this.setDescription(dto.getDescription());
	}

	private void validateDescription(String description) {
		if (description == null) {
			throw new NullPointerException(Error.DESCRIPTION_NOT_NULL);
		}

		if (description.length() < MIN_SIZE || description.length() > MAX_SIZE) {
			throw new IllegalArgumentException(String.format(Error.DESCRIPTION_SIZE, MIN_SIZE, MAX_SIZE));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Idea idea = (Idea) obj;

		return new EqualsBuilder().append(this.description, idea.getDescription()).append(this.category, idea.getCategory()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.description).append(this.category).toHashCode();
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Idea [description=" + description + ", category=" + category + "]";
	}

}
