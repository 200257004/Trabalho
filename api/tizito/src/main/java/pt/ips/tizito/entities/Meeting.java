package pt.ips.tizito.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import pt.ips.tizito.dtos.MeetingDTO;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.annotations.Ignore;

@Entity
public class Meeting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "inviter")
	private User inviter;

	private String description;

	private BigDecimal latitude;

	private BigDecimal longitude;

	private Calendar datetime;

	@Ignore
	private Calendar register;

	@OneToMany(mappedBy = "meeting", cascade = { CascadeType.PERSIST })
	private List<Invite> invites;

	protected Meeting() {
		this.register = Calendar.getInstance();

		this.invites = new ArrayList<Invite>();
	}

	public Meeting(User inviter, String description, BigDecimal latitude, BigDecimal longitude, Calendar datetime) {
		this();
		this.inviter = inviter;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.datetime = datetime;
	}

	public Meeting(User inviter, MeetingDTO dto) {
		this(inviter, dto.getDescription(), dto.getLatitude(), dto.getLongitude(), dto.getDatetime());
	}

	public void add(Invite invite) {
		if (invite == null) {
			throw new NullPointerException(Error.INVITE_NOT_NULL);
		}

		if (!this.contains(invite)) {
			this.invites.add(invite);

			invite.setMeeting(this);
		}
	}

	public void remove(Invite invite) {
		if (this.contains(invite)) {
			this.invites.remove(invite);

			invite.setMeeting(null);
		}
	}

	public boolean contains(Invite invite) {
		return this.invites.contains(invite);
	}

	public Long getId() {
		return id;
	}

	public User getInviter() {
		return inviter;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public Calendar getDatetime() {
		return datetime;
	}

	public Calendar getRegister() {
		return register;
	}

	public List<Invite> getInvites() {
		return Collections.unmodifiableList(invites);
	}

	@Override
	public String toString() {
		return "Meeting [description=" + description + ", datetime=" + datetime + "]";
	}

}
