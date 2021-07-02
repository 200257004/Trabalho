package pt.ips.admin.dtos;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Idea implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String poster;

	private String description;

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Idea idea = (Idea) obj;

		return new EqualsBuilder().append(this.id, idea.getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).toHashCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
