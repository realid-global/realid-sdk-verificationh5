package com.realid.sdk.model.response;


public class RealidResponse {
	private String sign;

	private RealidResponseBody response;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public RealidResponseBody getResponse() {
		return response;
	}

	public void setResponse(RealidResponseBody response) {
		this.response = response;
	}

}
