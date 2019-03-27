 
#  Real ID
 The purpose of this java-sdk project is to support merchants use Realid HTML5 Verification Service.

For more infomation about our company, please visit:
[Realid Website](https://www.realid.global/) ,
and the product API, please visit: 
[Realid HTML5 Verification API](https://api.realid.global/doc/) 

# Getting Started

### Get permission

~~~java
// The unique identity assigned by REAL ID to each merchant.
public String MCH_NO = "TEST001";
// The unique key assigned by REAL ID to each merchant.
public String SECRET_KEY = "TESTKEY001";
// default BASE_URL : https://api.realid.global/sdk/
RealidClient client = new RealidClient(MCH_NO,SECRET_KEY);
~~~


### Create Verification Order (Merchant->REAL ID)

~~~java
CreateOrderRequest model = new CreateOrderRequest();
model.setNotifyURL(null);
model.setReturnURL(null);
model.setVerificationType("1");
model.setMchOrderId(RealidUtils.randomString());
model.setExpireDay(1);

RealidResponse<CreateOrderResult> response = client.request(model);
if(response.getResponse().getCode() == 0) {
	CreateOrderResult result = response.getResponse().getResult();
	System.out.println(JSON.toJSONString(result));
}else {
	System.out.println("failed");
}
~~~

### Open HTML5 Verification Page(User->REAL ID)

User open the HTML5 verification URL in browser and enter the verification flow.if merchant need to redirect own page after completing the submission, the merchant should send the parameter“returnURL”in the Create Verification Order step, otherwise no redirection would happen

### Verify signature of Asynchronous Notification(REAL ID->Merchant)

~~~java
public void verifySignature() throws RealidException {
	NotificationRequestModel model = this.getNotificationModel();
	
	RealidClient client = new RealidClient(MCH_NO,SECRET_KEY);
	String calculateSign =  client.generateSignature(model);
	System.out.println(calculateSign.equals(model.getSign()));
}

/**
* Suppose this is the json object returned by realid
*/
private NotificationRequestModel getNotificationModel() {
	String json = "{\"idType\":1,\"facialSnapshot\":\"xxx\",\"idImage1\":\"xxx\",\"idImage2\":null,\"mchNo\":\"TEST001\",\"mchOrderId\":\"1fba9dfe8c804fd9b1b98f24564cc039\",\"orderId\":\"0771F9B7B9C94B3A983ADF890066A185\",\"sign\":\"fbad7c814d2b94eb6387d9506eb46776df7715ca28c1e2af293cc8e4b7b72330\",\"signType\":\"HmacSHA256\",\"timestamp\":\"1551669012023\",\"verificationScore\":\"0.92\"}";
	
	return JSON.parseObject(json, NotificationRequestModel.class);
}
~~~

### Query User Information(Merchant->REAL ID)

~~~java
QueryVerificationDataRequest data = new QueryVerificationDataRequest();
data.setOrderId("D945F9E12350432B9CCE1997F06697CA");
	
RealidResponse<QueryVerificationDataResult> response = client.request(data);
if(response.getResponse().getCode() == 0) {
	QueryVerificationDataResult result = response.getResponse().getResult();
	System.out.println(JSON.toJSONString(result));
}else {
	System.out.println("failed");
}
~~~~