package pt.ips.admin.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String poster;

	private String description;

	private List<Idea> ideas;
	
	public Category() {
		this.ideas = new ArrayList<Idea>();
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

	public List<Idea> getIdeas() {
		return ideas;
	}

	public void setIdeas(List<Idea> ideas) {
		this.ideas = ideas;
	}

}
