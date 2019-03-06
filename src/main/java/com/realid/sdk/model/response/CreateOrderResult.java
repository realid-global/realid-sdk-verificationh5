package com.realid.sdk.model.response;

public class CreateOrderResult extends RealidResult {
	
	private String orderId;
	private String verificationURL;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getVerificationURL() {
		return verificationURL;
	}
	public void setVerificationURL(String verificationURL) {
		this.verificationURL = verificationURL;
	}
	
	

}
