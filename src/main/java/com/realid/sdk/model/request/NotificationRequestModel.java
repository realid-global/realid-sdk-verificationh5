package com.realid.sdk.model.request;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.realid.sdk.model.IdType;

public class NotificationRequestModel {
	
	private String mchNo;
	private String mchOrderId;
	private String orderId;
	private String timestamp;
	@JSONField(serialize=false) 
	private transient IdType idTypeEnum;
	private String idImage1;
	private String idImage2;
	private Map<String,String> idData;
	private String facialSnapshot;
	private String verificationScore;
	private String addressImage;
	private String sign;
	private String signType;
	
	public String getMchNo() {
		return mchNo;
	}
	public void setMchNo(String mchNo) {
		this.mchNo = mchNo;
	}
	public String getMchOrderId() {
		return mchOrderId;
	}
	public void setMchOrderId(String mchOrderId) {
		this.mchOrderId = mchOrderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@JSONField(name = "idType")
	public Integer getIdType() {
		return idTypeEnum.getCode();
	}
	@JSONField(name = "idType")
	public void setIdType(Integer idType) {
		this.idTypeEnum = IdType.getEnum(idType);
	}
	public IdType getIdTypeEnum() {
		return idTypeEnum;
	}
	public void setIdTypeEnum(IdType idTypeEnum) {
		this.idTypeEnum = idTypeEnum;
	}
	public String getIdImage1() {
		return idImage1;
	}
	public void setIdImage1(String idImage1) {
		this.idImage1 = idImage1;
	}
	public String getIdImage2() {
		return idImage2;
	}
	public void setIdImage2(String idImage2) {
		this.idImage2 = idImage2;
	}
	public Map<String, String> getIdData() {
		return idData;
	}
	public void setIdData(Map<String, String> idData) {
		this.idData = idData;
	}
	public String getFacialSnapshot() {
		return facialSnapshot;
	}
	public void setFacialSnapshot(String facialSnapshot) {
		this.facialSnapshot = facialSnapshot;
	}
	public String getVerificationScore() {
		return verificationScore;
	}
	public void setVerificationScore(String verificationScore) {
		this.verificationScore = verificationScore;
	}
	public String getAddressImage() {
		return addressImage;
	}
	public void setAddressImage(String addressImage) {
		this.addressImage = addressImage;
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

}
