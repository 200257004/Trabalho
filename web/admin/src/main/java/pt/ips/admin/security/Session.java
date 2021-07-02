package pt.ips.admin.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import pt.ips.admin.dtos.User;

@Named
@SessionScoped
public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	private String token;

	public void setUser(User user, String token) {
		this.user = user;
		this.token = token;
	}

	public void clear() {
		this.user = null;
		this.token = null;
	}

	public User getUser() {
		return user;
	}

	public String getToken() {
		return token;
	}

}
