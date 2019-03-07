package com.realid.sdk.model.request;

import java.lang.reflect.ParameterizedType;

import com.realid.sdk.model.response.RealidResult;

public abstract class RealidRequestModel<T extends RealidResult> {


	public abstract String interfaceName();


	@SuppressWarnings("unchecked")
	public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
	
}
