package com.realid.sdk.model;

public enum OrderStatus {
	INITIAL(0),
	PROCESSING(1),
	COMPLETE(2),
	EXPIRED(3),
	INVALID(5),

	;
	
	private int code;

	private OrderStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public static OrderStatus getEnum(int code) {
		for(OrderStatus type: values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		return null;
	}
	
	
}
