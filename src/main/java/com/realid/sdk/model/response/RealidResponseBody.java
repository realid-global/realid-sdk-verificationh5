package com.realid.sdk.model.response;


public class RealidResponseBody {
	
	private Integer code;

	private String message;

	private RealidResult result;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RealidResult getResult() {
		return result;
	}

	public void setResult(RealidResult result) {
		this.result = result;
	}

	

}
