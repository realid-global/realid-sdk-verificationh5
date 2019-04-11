package com.realid.sdk.model.request;

import com.realid.sdk.RealidConstants;
import com.realid.sdk.model.response.CreateNativeOrderResult;

public class CreateNativeOrderRequest extends RealidRequestModel<CreateNativeOrderResult> {
	
	private String mchOrderId;
	
	private String verificationType;
	
	private int expireDay;
	
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

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}


	@Override
	public String interfaceName() {
		return RealidConstants.REQUEST_CREATE_NATIVE_ORDER;
	}
	

}
