package pt.ips.tizito.dtos;

import java.io.Serializable;

public class IdeaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}