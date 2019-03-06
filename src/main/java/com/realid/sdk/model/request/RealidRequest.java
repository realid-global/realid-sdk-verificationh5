package com.realid.sdk.model.request;

import com.realid.sdk.RealidConstants;

public class RealidRequest {
	/**
	 * 商户号
	 */
	private String mchNo;

	/**
	 * 接口版本
	 */
	private String version = RealidConstants.SDK_VERSION;

	/**
	 * 时间戳
	 */
	private String timestamp = String.valueOf(System.currentTimeMillis());

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 签名算法
	 */
	private String signType = RealidConstants.SIGN_HMACSHA256_ALGORITHMS;

	/**
	 * 数据对象
	 */
	private RealidRequestModel data;
	
	
	

	/**
	 * @param mchNo
	 */
	public RealidRequest(String mchNo) {
		super();
		this.mchNo = mchNo;
	}
	
	

	/**
	 * @param mchNo
	 * @param data
	 */
	public RealidRequest(String mchNo, RealidRequestModel data) {
		super();
		this.mchNo = mchNo;
		this.data = data;
	}



	/**
	 * @param mchNo
	 * @param version
	 * @param signType
	 */
	public RealidRequest(String mchNo, String version, String signType) {
		super();
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

	public RealidRequestModel getData() {
		return data;
	}

	public void setData(RealidRequestModel data) {
		this.data = data;
	}

}
