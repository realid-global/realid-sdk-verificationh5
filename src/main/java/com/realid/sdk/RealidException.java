
package com.realid.sdk;


public class RealidException extends Exception {

	private static final long serialVersionUID = -7147217828584639940L;
	
	private String            errCode;
    private String            errMsg;

    public RealidException() {
        super();
    }

    public RealidException(String message, Throwable cause) {
        super(message, cause);
    }

    public RealidException(String message) {
        super(message);
    }

    public RealidException(Throwable cause) {
        super(cause);
    }

    public RealidException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

}