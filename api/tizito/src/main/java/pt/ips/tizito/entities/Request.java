package pt.ips.tizito.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import pt.ips.tizito.enums.Response;
import pt.ips.tizito.rs.annotations.Ignore;

@Entity
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "requester")
	private User requester;

	@Ignore
	@ManyToOne
	@JoinColumn(name = "requested")
	private User requested;

	private Calendar register;

	@Enumerated(EnumType.STRING)
	private Response response;

	protected Request() {
		this.register = Calendar.getInstance();
	}

	public Request(User requester, User requested) {
		this();
		this.requester = requester;
		this.requested = requested;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Request request = (Request) obj;

		boolean equals = new EqualsBuilder().append(this.requester, request.getRequester()).append(this.requested, request.getRequested()).isEquals();

		if (!equals) {
			return new EqualsBuilder().append(this.requester, request.getRequested()).append(this.requested, request.getRequester()).isEquals();
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.requester).append(this.requested).toHashCode();
	}

	public Long getId() {
		return id;
	}

	public User getRequester() {
		return requester;
	}

	public User getRequested() {
		return requested;
	}

	public Calendar getRegister() {
		return register;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		if (response.equals(Response.ACCEPTED)) {
			this.requested.add(this.requester);
		}

		this.response = response;
	}

	@Override
	public String toString() {
		return "Request [requester=" + requester + ", requested=" + requested + "]";
	}

}