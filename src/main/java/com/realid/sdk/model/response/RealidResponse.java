package com.realid.sdk.model.response;


public class RealidResponse<T extends RealidResult> {
	private String sign;

	private RealidResponseBody<T> response;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public RealidResponseBody<T> getResponse() {
		return response;
	}

	public void setResponse(RealidResponseBody<T> response) {
		this.response = response;
	}

}
