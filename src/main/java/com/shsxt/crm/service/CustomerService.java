package com.shsxt.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.crm.dao.CustomerDao;
import com.shsxt.crm.vo.CustomerVO;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;
	
	public List<CustomerVO> queryAll(){
		return customerDao.queryAll();
	}
	
}
