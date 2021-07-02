package pt.ips.tizito.rs.managers;

import javax.enterprise.context.RequestScoped;

import pt.ips.tizito.entities.User;

@RequestScoped
public class RequestManager {

	private User user;

	public boolean isEmpty() {
		return this.user == null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
