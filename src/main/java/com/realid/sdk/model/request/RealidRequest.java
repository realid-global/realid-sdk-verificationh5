package com.realid.sdk.model.request;

import com.realid.sdk.RealidConstants;

public class RealidRequest {

	private String mchNo;

	private String version = RealidConstants.SDK_VERSION;

	private String timestamp = String.valueOf(System.currentTimeMillis());

	private String sign;

	private String signType = RealidConstants.SIGN_HMACSHA256_ALGORITHMS;

	private RealidRequestModel<?> data;
	

	public RealidRequest(String mchNo) {
		this.mchNo = mchNo;
	}
	
	public RealidRequest(String mchNo, RealidRequestModel<?> data) {
		this.mchNo = mchNo;
		this.data = data;
	}

	public RealidRequest(String mchNo, String version, String signType) {
		this.mchNo = mchNo;
		this.version = version;
		this.signType = signType;
	}

	public String getMchNo() {
		return mchNo;
	}

	public void setMchNo(String mchNo) {
		this.mchNo = mchNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public RealidRequestModel<?> getData() {
		return data;
	}

	public void setData(RealidRequestModel<?> data) {
		this.data = data;
	}

}
