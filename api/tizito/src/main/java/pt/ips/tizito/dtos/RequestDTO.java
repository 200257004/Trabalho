package pt.ips.tizito.dtos;

import java.io.Serializable;

import pt.ips.tizito.enums.Response;

public class RequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long requested;

	private Response response;

	public Long getRequested() {
		return requested;
	}

	public void setRequested(Long requested) {
		this.requested = requested;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
