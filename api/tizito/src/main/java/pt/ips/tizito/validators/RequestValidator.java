package pt.ips.tizito.validators;

import javax.inject.Inject;

import pt.ips.tizito.daos.UserDAO;
import pt.ips.tizito.dtos.RequestDTO;
import pt.ips.tizito.entities.User;
import pt.ips.tizito.messages.Error;
import pt.ips.tizito.rs.managers.RequestManager;

public class RequestValidator {

	@Inject
	private RequestManager requestManager;

	@Inject
	private UserDAO userDAO;

	public void validate(RequestDTO dto) {
		Long requestedId = dto.getRequested();

		if (requestedId == null) {
			throw new NullPointerException(Error.REQUESTED_NOT_NULL);
		}

		User requested = this.userDAO.findById(requestedId);

		if (requested == null) {
			throw new IllegalArgumentException(Error.REQUESTED_NOT_FOUND);
		}

		if (!requested.isActive()) {
			throw new IllegalArgumentException(Error.REQUESTED_INATIVO);
		}

		if (requested.equals(this.requestManager.getUser())) {
			throw new IllegalArgumentException(Error.REQUESTED_UNAUTHORIZED);
		}
	}

}
