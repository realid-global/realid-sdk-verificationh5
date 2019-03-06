package com.realid.sdk;

import com.alibaba.fastjson.JSON;
import com.realid.sdk.model.request.CreateOrderRequest;
import com.realid.sdk.model.response.RealidResponse;
import com.realid.sdk.model.response.RealidResult;
import com.realid.sdk.util.RealidUtils;



public class CreateOrderDemo {
	
	// The unique identity assigned by REAL ID to each merchant.
	public String MCH_NO = "TEST001";
	// The unique key assigned by REAL ID to each merchant.
	public String SECRET_KEY = "TESTKEY001";
	
	
	public static void main(String[] args) {
		CreateOrderDemo ins = new CreateOrderDemo();
		ins.createOrder();
	}
	
    public void createOrder() {
		CreateOrderRequest model = new CreateOrderRequest();
		
		model.setNotifyURL(null);
		model.setReturnURL(null);
		model.setVerificationType("1");
		model.setMchOrderId(RealidUtils.randomString());
		model.setExpireDay(1);
		
		RealidClient client = new RealidClient(MCH_NO,SECRET_KEY);
		try {
			RealidResponse response = client.request(model);
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
