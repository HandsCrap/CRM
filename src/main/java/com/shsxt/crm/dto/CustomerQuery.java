package com.shsxt.crm.dto;

import com.shsxt.crm.base.BaseQuery;

public class CustomerQuery extends BaseQuery{
	private String customerNo;
	private String customerName;
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
