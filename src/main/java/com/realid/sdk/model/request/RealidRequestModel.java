package com.realid.sdk.model.request;

import java.lang.reflect.ParameterizedType;

import com.alibaba.fastjson.annotation.JSONField;
import com.realid.sdk.model.response.RealidResult;

public abstract class RealidRequestModel<T extends RealidResult> {

	@JSONField(serialize=false) 
	private transient String interfaceName;
	
	public RealidRequestModel() {
		super();
		setInterfaceName();
	}

	public String getInterfaceName() {
		return interfaceName;
	}
	
	void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}


	@SuppressWarnings("unchecked")
	public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

	abstract void setInterfaceName();
	
}
