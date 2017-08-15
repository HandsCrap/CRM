package com.shsxt.crm.constant;

public enum DevResult {
	//未开发
	UN_DEV(0),
	//已开发
	DEVING(1),
	//开发成功
	DEV_FINISHED(2),
	//开发失败
	DEV_FAILURE(3);
	private int devResult;

	public int getDevResult() {
		return devResult;
	}

	public void setDevResult(int devResult) {
		this.devResult = devResult;
	}
	
	private DevResult(int devResult) {
		this.devResult = devResult;
	}
}
