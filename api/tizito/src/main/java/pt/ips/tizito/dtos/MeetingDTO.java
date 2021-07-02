package pt.ips.tizito.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class MeetingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;

	private BigDecimal latitude;

	private BigDecimal longitude;

	private Calendar datetime;

	private List<Long> inviteds;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Calendar getDatetime() {
		return datetime;
	}

	public void setDatetime(Calendar datetime) {
		this.datetime = datetime;
	}

	public List<Long> getInviteds() {
		return inviteds;
	}

	public void setInviteds(List<Long> inviteds) {
		this.inviteds = inviteds;
	}

}
