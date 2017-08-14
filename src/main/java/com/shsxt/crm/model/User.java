package com.shsxt.crm.model;

import java.util.Date;

import com.shsxt.crm.base.BaseModel;

public class User extends BaseModel{
	private String userName;
	private String password;
	private String trueName;
	private String email;
	private Integer phone;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Integer id, String userName, String password, String trueName, String email, Integer phone,
			Integer isValid, Date createDate, Date updateDate) {
		super();
		this.userName = userName;
		this.password = password;
		this.trueName = trueName;
		this.email = email;
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	
}
