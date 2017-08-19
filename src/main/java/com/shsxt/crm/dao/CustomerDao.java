package com.shsxt.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.crm.dto.CustomerQuery;
import com.shsxt.crm.model.Customer;
import com.shsxt.crm.vo.CustomerVO;

public interface CustomerDao {

	@Select("select id,name from t_customer where is_valid=1" )
	List<CustomerVO> queryAll();

	PageList<Customer> selectForPage(CustomerQuery query, PageBounds pageBounds);
	
}
