package com.shsxt.crm.exception;

import com.shsxt.crm.constant.Constant;

@SuppressWarnings("serial")
public class ParamException extends RuntimeException{
	
	private Integer errorCode;
	
	public ParamException() {
		
	}

	public ParamException(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	public ParamException(String message) {
		super(message);
		this.errorCode = Constant.ERROR_CODE;
	}
	
	public ParamException(Integer errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
