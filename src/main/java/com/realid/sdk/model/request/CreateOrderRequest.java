package com.realid.sdk.model.request;

import com.realid.sdk.RealidConstants;
import com.realid.sdk.model.response.CreateOrderResult;

public class CreateOrderRequest extends RealidRequestModel {
	
	private String mchOrderId;
	
	private String verificationType;
	
	private int expireDay;
	
	private String returnURL;
	
	private String notifyURL;

	public String getMchOrderId() {
		return mchOrderId;
	}

	public void setMchOrderId(String mchOrderId) {
		this.mchOrderId = mchOrderId;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	public int getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(int expireDay) {
		this.expireDay = expireDay;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	@Override
	public final void setInterfaceName() {
		this.setInterfaceName(RealidConstants.REQUEST_CREATE_ORDER);
	}

	@Override
	void setResultClass() {
		this.setResultClass(CreateOrderResult.class);
	}
	

}
