package com.shsxt.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.crm.dao.CustomerDao;
import com.shsxt.crm.dto.CustomerQuery;
import com.shsxt.crm.model.Customer;
import com.shsxt.crm.vo.CustomerVO;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;
	
	public List<CustomerVO> queryAll(){
		return customerDao.queryAll();
	}

	public Map<String , Object> selectForPage(CustomerQuery query) {
		PageList<Customer> customers = customerDao.selectForPage(query,query.buildPageBounds());
		Map<String, Object> result=new HashMap<>();
		result.put("paginator", customers.getPaginator());
		result.put("rows", customers);
		result.put("total", customers.getPaginator().getTotalCount());
		return result;
	}
	
}
