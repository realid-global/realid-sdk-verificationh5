package com.realid.sdk;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private String baseUrl;
	
	// The unique identity assigned by REAL ID to each merchant.
	private String mchNo;
	// The unique key assigned by REAL ID to each merchant.
	private String secretKey;
	
	
	
	public RealidClient(String mchNo, String secretKey) {
		baseUrl = RealidConstants.REQUEST_BASE_URL;
		this.mchNo = mchNo;
		this.secretKey = secretKey;
	}


	public RealidClient(String baseUrl, String mchNo, String secretKey) {
		if(StringUtils.isEmpty(baseUrl)) {
			baseUrl = RealidConstants.REQUEST_BASE_URL;
		}else if(!baseUrl.endsWith("/")) {
			baseUrl += "/";
		}
		
		this.baseUrl = baseUrl;
		this.mchNo = mchNo;
		this.secretKey = secretKey;
	}
	

	/**
	 * @Description: create a http post request
	 * @param data the part of "data" parameter
	 */
	public <T extends RealidResult> RealidResponse<T> request(RealidRequestModel<T> data) throws RealidException {
		RealidRequest request = new RealidRequest(mchNo,data);

		String calculateSign = generateSignature(request);
		request.setSign(calculateSign);
		String jsonStr = RealidUtils.toJsonString(request);;

		String resp = null;
		try {
			resp = WebUtils.doPost(baseUrl + data.interfaceName(), jsonStr);
		} catch (IOException e) {
			throw new RealidException("do realid request error, url: "+baseUrl + data.interfaceName() , e);
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
	 *  generate a signature string
	 */
	public String generateSignature(Object object) throws RealidException {
		String signSource = RealidUtils.jointParams(RealidUtils.toSortedMap(object));
		return RealidUtils.sha256_HMAC(signSource, secretKey);
	}
	
	/**
	 *  generate a signature string
	 */
	public String generateSignature(String signSource) throws RealidException {
		return RealidUtils.sha256_HMAC(signSource, secretKey);
	}
	
	private static Pattern cosUrlPattern = Pattern.compile("\\{\"response\":(.+?),\"sign\":\"(.+?)\"\\}");
	
	/**
	 *  verify response sign
	 */
	public boolean verifySign(String response) throws RealidException {
		JSONObject jsonObject = JSON.parseObject(response);
		String sign = jsonObject.getString(RealidConstants.PARAM_SIGN);
		if(!StringUtils.isEmpty(sign)) {
			Matcher m = cosUrlPattern.matcher(response);
			if(m.matches()) {
				String signSource = m.group(1);
				if(!StringUtils.isEmpty(signSource)) {
					return (sign.equals(generateSignature(signSource)));
				}else {
					return false;
				}
			}else {
				//not matches
				return true;
			}
		}else {
			//have no sign parameters
			return true;
		}
	}
	

}
