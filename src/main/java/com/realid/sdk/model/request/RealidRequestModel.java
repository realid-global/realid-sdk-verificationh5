package com.realid.sdk.model.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.realid.sdk.model.response.RealidResult;

public abstract class RealidRequestModel {

	@JSONField(serialize=false) 
	private transient String interfaceName;
	@JSONField(serialize=false) 
	private transient Class<? extends RealidResult> resultClass;
	
	public RealidRequestModel() {
		super();
		setInterfaceName();
		setResultClass();
	}

	public String getInterfaceName() {
		return interfaceName;
	}
	
	void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}









	public Class<? extends RealidResult> getResultClass() {
		return resultClass;
	}



	void setResultClass(Class<? extends RealidResult> resultClass) {
		this.resultClass = resultClass;
	}



	abstract void setInterfaceName();
	
	abstract void setResultClass();
}
