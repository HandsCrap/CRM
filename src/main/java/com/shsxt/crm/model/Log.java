package com.shsxt.crm.model;

import java.util.Date;

public class Log {

	private Integer id;
	private String description;
	private String method;
	private String type;
	private String requestIp;
	private String exceptionCode;
	private String exceptionDetail;
	private String params;
	private Date createDate;
	private Long executeTime;
	private String createMan;
	private String result;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRequestIp() {
		return requestIp;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionDetail() {
		return exceptionDetail;
	}
	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Long getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}
	public String getCreateMan() {
		return createMan;
	}
	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", description=" + description + ", method=" + method + ", type=" + type
				+ ", requestIp=" + requestIp + ", exceptionCode=" + exceptionCode + ", exceptionDetail="
				+ exceptionDetail + ", params=" + params + ", createDate=" + createDate + ", executeTime=" + executeTime
				+ ", createMan=" + createMan + ", result=" + result + "]";
	}
	

}
