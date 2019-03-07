package com.realid.sdk.model.request;

import com.realid.sdk.RealidConstants;
import com.realid.sdk.model.response.QueryVerificationDataResult;

public class QueryVerificationDataRequest extends RealidRequestModel<QueryVerificationDataResult>{
	
	private String orderId;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	void setInterfaceName() {
		this.setInterfaceName(RealidConstants.REQUEST_QUERY_VERIFICATION_DATA);
	}
	

}
