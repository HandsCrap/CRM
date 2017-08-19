package com.shsxt.crm.exception;

@SuppressWarnings("serial")
public class UnAuthPermissionException extends RuntimeException{
	private String permissionCode;

	public UnAuthPermissionException() {
		super();
	}
	
	public UnAuthPermissionException(String message) {
		super(message);
	}

	public UnAuthPermissionException(String permissionCode,String messsage) {
		super();
		this.permissionCode = permissionCode;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
	
}
