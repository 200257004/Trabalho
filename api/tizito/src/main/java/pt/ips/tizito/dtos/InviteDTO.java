package pt.ips.tizito.dtos;

import java.io.Serializable;

import pt.ips.tizito.enums.Response;

public class InviteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
