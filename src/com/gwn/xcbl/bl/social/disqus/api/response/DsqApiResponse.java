package com.gwn.xcbl.bl.social.disqus.api.response;

import java.io.Serializable;

public class DsqApiResponse<M> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;
	
	private M response;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public M getResponse() {
		return response;
	}

	public void setResponse(M response) {
		this.response = response;
	}
}
