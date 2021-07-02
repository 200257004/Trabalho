package pt.ips.tizito.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import pt.ips.tizito.enums.Response;
import pt.ips.tizito.rs.annotations.Ignore;

@Entity
public class Invite implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "invited")
	private User invited;

	@Enumerated(EnumType.STRING)
	private Response response;

	@Ignore
	@ManyToOne
	@JoinColumn(name = "meeting")
	private Meeting meeting;
	
	@Transient
	private Meeting details;

	protected Invite() {
		
	}

	public Invite(User invited) {
		this();
		this.invited = invited;
	}

	public Invite(Invite invite, Meeting meeting) {
		this.id = invite.getId();
		this.invited = invite.getInvited();
		this.response = invite.getResponse();

		this.details = meeting;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Invite invite = (Invite) obj;

		return new EqualsBuilder().append(this.invited, invite.getInvited()).append(this.meeting, invite.getMeeting())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.invited).append(this.meeting).toHashCode();
	}

	public Long getId() {
		return id;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public User getInvited() {
		return invited;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Invite [invited=" + invited + ", meeting=" + meeting + "]";
	}

}
