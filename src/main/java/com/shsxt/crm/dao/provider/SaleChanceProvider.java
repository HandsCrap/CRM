package com.shsxt.crm.dao.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shsxt.crm.dto.SaleChanceQuery;


public class SaleChanceProvider {

	private static Logger logger = LoggerFactory.getLogger(SaleChanceProvider.class);
	
	private static final String COLUMNS = "t.id, t.customer_id, t.customer_name, t.overview, t.link_man, t.link_phone, "
			+ " t.create_man, t.create_date, t.assign_man,t.assign_time,t.state, t.cgjl, t.description, t.chance_source,t.dev_result";
	public String selectForPage(final SaleChanceQuery query){
			String sql = new SQL(){{
				SELECT(COLUMNS);
				FROM("t_sale_chance t");
				WHERE("is_valid=1");
				//客户名称
				if(StringUtils.isNoneBlank(query.getCustomerName())){
					AND().WHERE("customer_name like '%"+ query.getCustomerName() +"%'");
				}
				//概述
				if (StringUtils.isNoneBlank(query.getOverview())) {
					AND().WHERE("overview like '%"+ query.getOverview() +"%'");
				}
				//添加人
				if (StringUtils.isNoneBlank(query.getCreateMan())) {
					AND().WHERE("create_man like '%"+ query.getCreateMan() +"%'");
				}
				//分配状态
				if (query.getState() != null) {
					AND().WHERE("state = #{state}");
				}
				//开发状态
				if (query.getDevResult() != null) {
					AND().WHERE("devResult = #{devResult}");
				}
			}}.toString();
			logger.debug("查询的sql语句："+sql);
			return sql;
			
		}
	public String queryById(@Param(value="id")Integer id){
		String sql = new SQL(){{

			SELECT(COLUMNS);
			FROM("t_sale_chance t");
			WHERE("is_valid = 1 and id = #{id}");
			
		}}.toString();
		logger.debug("查询的sql语句是："+sql);
		return sql;
		
	}
}
