package com.realid.sdk;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.realid.sdk.model.request.RealidRequest;
import com.realid.sdk.model.request.RealidRequestModel;
import com.realid.sdk.model.response.RealidResponse;
import com.realid.sdk.model.response.RealidResult;
import com.realid.sdk.util.RealidUtils;
import com.realid.sdk.util.StringUtils;
import com.realid.sdk.util.WebUtils;

public class RealidClient {
	
	private String BASE_URL;
	
	// The unique identity assigned by REAL ID to each merchant.
	private String MCH_NO;
	// The unique key assigned by REAL ID to each merchant.
	private String SECRET_KEY;
	
	
	
	public RealidClient(String mCH_NO, String sECRET_KEY) {
		super();
		BASE_URL = RealidConstants.REQUEST_BASE_URL;
		MCH_NO = mCH_NO;
		SECRET_KEY = sECRET_KEY;
	}


	public RealidClient(String baseUrl, String mchNo, String secretKey) {
		super();
		
		if(StringUtils.isEmpty(baseUrl)) {
			baseUrl = RealidConstants.REQUEST_BASE_URL;
		}else if(!baseUrl.endsWith("/")) {
			baseUrl += "/";
		}
		
		BASE_URL = baseUrl;
		MCH_NO = mchNo;
		SECRET_KEY = secretKey;
	}
	

	/**
	 * @Description: create a http post request
	 * @param data the part of "data" parameter
	 */
	public <T extends RealidResult> RealidResponse<T> request(RealidRequestModel<T> data) throws RealidException {
		RealidRequest request = new RealidRequest(MCH_NO,data);

		String calculateSign = generateSignature(request);
		request.setSign(calculateSign);
		String jsonStr = JSON.toJSON(request).toString();

		String resp = null;
		try {
			resp = WebUtils.doPost(BASE_URL + data.getInterfaceName(), jsonStr);
		} catch (IOException e) {
			throw new RealidException("do realid request error, url: "+BASE_URL + data.getInterfaceName() , e);
		}
		// check response sign
		if (!verifySign(resp)) {
			throw new RealidException("response verify sign error, response :" + resp);
		}

		JSONObject jsonObject = JSON.parseObject(resp);
		JSONObject jsonResult = jsonObject.getJSONObject(RealidConstants.PARAM_RESPONSE).getJSONObject(RealidConstants.PARAM_RESULT);
		
		RealidResponse<T> response = JSON.parseObject(resp, new TypeReference<RealidResponse<T>>(){});
		response.getResponse().setResult( JSON.toJavaObject(jsonResult, data.getTClass()));
		
		return response;
	}
	
	
	/**
	 * @Description: generate a signature string
	 */
	public String generateSignature(Object object) throws RealidException {
		String signSource = RealidUtils.jointParams(RealidUtils.toSortedMap(object));
		return RealidUtils.sha256_HMAC(signSource, SECRET_KEY);
	}
	
	/**
	 * @Description: generate a signature string
	 */
	public String generateSignature(String signSource) throws RealidException {
		return RealidUtils.sha256_HMAC(signSource, SECRET_KEY);
	}
	
	/**
	 * @Description: verify response sign
	 */
	public boolean verifySign(String response) throws RealidException {
		JSONObject object = JSON.parseObject(response);
		String sign = object.getString(RealidConstants.PARAM_SIGN);
		String signSource = object.get(RealidConstants.PARAM_RESPONSE).toString();
		return (sign.equals(generateSignature(signSource)));
	}
	

}
