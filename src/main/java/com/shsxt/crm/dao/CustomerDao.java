package com.shsxt.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.shsxt.crm.vo.CustomerVO;

public interface CustomerDao {

	@Select("select id,name from t_customer where is_valid=1" )
	List<CustomerVO> queryAll();
}
