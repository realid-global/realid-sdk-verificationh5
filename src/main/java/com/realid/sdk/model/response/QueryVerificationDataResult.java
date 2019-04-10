package com.realid.sdk.model.response;


import com.alibaba.fastjson.annotation.JSONField;
import com.realid.sdk.model.IdType;
import com.realid.sdk.model.OrderStatus;

public class QueryVerificationDataResult extends RealidResult {
	@JSONField(serialize=false) 
	private transient OrderStatus orderStatusEnum;
	@JSONField(serialize=false) 
	private transient IdType idTypeEnum;
	
	private String idImage1;
	
	private String idImage2;
	
	private String idData; 
	
	private String facialSnapshot;
	
	private String verificationScore;
	
	private String addressImage;
	
	@JSONField(name = "orderStatus")
	public Integer getOrderStatus() {
		return orderStatusEnum.getCode();
	}
	@JSONField(name = "orderStatus")
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatusEnum = OrderStatus.getEnum(orderStatus);
	}
	public OrderStatus getOrderStatusEnum() {
		return orderStatusEnum;
	}
	public void setOrderStatusEnum(OrderStatus orderStatusEnum) {
		this.orderStatusEnum = orderStatusEnum;
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

	public String getIdData() {
		return idData;
	}

	public void setIdData(String idData) {
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
	
	

}
