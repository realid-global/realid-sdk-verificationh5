package com.realid.sdk;


import com.alibaba.fastjson.JSON;
import com.realid.sdk.model.request.NotificationRequestModel;

public class NotificationDemo {
	
	// The unique identity assigned by REAL ID to each merchant.
	public final String MCH_NO = "TEST001";
	// The unique key assigned by REAL ID to each merchant.
	public final String SECRET_KEY = "TESTKEY001";
	
	
	public static void main(String[] args) {
		NotificationDemo ins = new NotificationDemo();
		try {
			ins.verifySignature();
		} catch (RealidException e) {
			e.printStackTrace();
		}
	}
	
	public void verifySignature() throws RealidException {
		NotificationRequestModel model = this.getNotificationModel();
		
		RealidClient client = new RealidClient(MCH_NO,SECRET_KEY);
		String calculateSign =  client.generateSignature(model);
		System.out.println(calculateSign.equals(model.getSign()));
	}
	
	private NotificationRequestModel getNotificationModel() {
		String json = "{\"idType\":1,\"facialSnapshot\":\"xxx\",\"idImage1\":\"xxx\",\"idImage2\":null,\"mchNo\":\"TEST001\",\"mchOrderId\":\"1fba9dfe8c804fd9b1b98f24564cc039\",\"orderId\":\"0771F9B7B9C94B3A983ADF890066A185\",\"sign\":\"fbad7c814d2b94eb6387d9506eb46776df7715ca28c1e2af293cc8e4b7b72330\",\"signType\":\"HmacSHA256\",\"timestamp\":\"1551669012023\",\"verificationScore\":\"0.92\"}";
		
		return JSON.parseObject(json, NotificationRequestModel.class);
	}
	
	
			
		
	

}
