package pt.ips.tizito.dtos;

import java.io.Serializable;
import java.util.List;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;

	private List<IdeaDTO> ideas;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<IdeaDTO> getIdeaDTOs() {
		return ideas;
	}

	public void setIdeaDTOs(List<IdeaDTO> ideaDTOs) {
		this.ideas = ideaDTOs;
	}

}
