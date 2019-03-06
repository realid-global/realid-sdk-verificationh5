package com.realid.sdk;


import com.alibaba.fastjson.JSON;
import com.realid.sdk.model.request.QueryVerificationDataRequest;
import com.realid.sdk.model.response.RealidResponse;
import com.realid.sdk.model.response.RealidResult;



public class QueryVerificationDataDemo {
	
	// The unique identity assigned by REAL ID to each merchant.
	public String MCH_NO = "TEST001";
	// The unique key assigned by REAL ID to each merchant.
	public String SECRET_KEY = "TESTKEY001";
	
	public static void main(String[] args) {
		QueryVerificationDataDemo ins = new QueryVerificationDataDemo();
		ins.queryVerificationData();
	}
	
    public void queryVerificationData() {
		QueryVerificationDataRequest data = new QueryVerificationDataRequest();
		
		data.setOrderId("D945F9E12350432B9CCE1997F06697CA");
		
		RealidClient client = new RealidClient(MCH_NO,SECRET_KEY);
		try {
			RealidResponse response = client.request(data);
			if(response.getResponse().getCode() == 0) {
				RealidResult result = response.getResponse().getResult();
				System.out.println(JSON.toJSONString(result));
			}else {
				System.out.println("failed");
			}
		} catch (RealidException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
