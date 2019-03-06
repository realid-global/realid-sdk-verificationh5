package com.realid.sdk.model;


public enum IdType{
	PASSPORT(1),
	ID_CARD(2),
	DRIVER_LICENSE(3),
	
	
	;
	
	private int code;
	
	

	private IdType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public static IdType getEnum(int code) {
		for(IdType type: values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		return null;
	}

}
