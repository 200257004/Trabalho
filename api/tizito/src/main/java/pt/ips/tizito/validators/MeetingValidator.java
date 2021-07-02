package pt.ips.tizito.validators;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.dtos.MeetingDTO;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.managers.RequestManager;

public class MeetingValidator {

	private static final int MIN_SIZE = 2;
	private static final int MAX_SIZE = 20;

	public static final double MIN_LATITUDE = -90;
	public static final double MAX_LATITUDE = 90;

	public static final double MIN_LONGITUDE = -180;
	public static final double MAX_LONGITUDE = 180;

	@Inject
	private RequestManager requestManager;

	@Inject
	private UserDAO userDAO;

	public void validate(MeetingDTO dto) {
		String description = dto.getDescription();

		if (description == null) {
			throw new NullPointerException(Error.DESCRIPTION_NOT_NULL);
		}

		if (description.length() < MIN_SIZE || description.length() > MAX_SIZE) {
			throw new IllegalArgumentException(String.format(Error.DESCRIPTION_SIZE, MIN_SIZE, MAX_SIZE));
		}

		BigDecimal latitude = dto.getLatitude();

		if (latitude == null) {
			throw new NullPointerException(Error.LATITUDE_NOT_NULL);
		}

		if (latitude.doubleValue() < MIN_LATITUDE || latitude.doubleValue() > MAX_LATITUDE) {
			throw new IllegalArgumentException(String.format(Error.LATITUDE_SIZE, MIN_LATITUDE, MAX_LATITUDE));
		}

		BigDecimal longitude = dto.getLongitude();

		if (longitude == null) {
			throw new NullPointerException(Error.LONGITUDE_NOT_NULL);
		}

		if (longitude.doubleValue() < MIN_LONGITUDE || longitude.doubleValue() > MAX_LONGITUDE) {
			throw new IllegalArgumentException(String.format(Error.LONGITUDE_SIZE, MIN_LONGITUDE, MAX_LONGITUDE));
		}

		Calendar datetime = dto.getDatetime();

		if (datetime == null) {
			throw new NullPointerException(Error.DATETIME_NOT_NULL);
		}

		List<Long> inviteds = dto.getInviteds();

		if (inviteds == null || inviteds.isEmpty()) {
			throw new NullPointerException(Error.INVITEDS_NOT_NULL);
		}

		User inviter = this.requestManager.getUser();

		for (Long invitedId : inviteds) {
			User invited = this.userDAO.findById(invitedId);

			if (invited == null) {
				throw new IllegalArgumentException(Error.INVITED_NOT_FOUND);
			}

			if (!invited.isActive()) {
				throw new IllegalArgumentException(Error.INVITED_INACTIVE);
			}

			if (inviter.equals(invited) || !inviter.contains(invited)) {
				throw new IllegalArgumentException(Error.INVITED_UNAUTHORIZED);
			}
		}
	}
	
}
