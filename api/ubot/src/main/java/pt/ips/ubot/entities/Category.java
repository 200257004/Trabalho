package pt.ips.ubot.entities;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String description;

	private List<Idea> ideas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Idea> getIdeas() {
		return ideas;
	}

	public void setIdeas(List<Idea> ideas) {
		this.ideas = ideas;
	}

}
