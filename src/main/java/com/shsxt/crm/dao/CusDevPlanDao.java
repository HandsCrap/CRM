package com.shsxt.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shsxt.crm.model.CusDevPlan;


public interface CusDevPlanDao {
	/**
	 * 查询
	 * @param saleChanceId
	 * @return
	 */
	@Select("select id,plan_item,plan_date,exe_affect,create_date,update_date from t_cus_dev_plan where is_valid = 1 and sale_chance_id=#{saleChanceId}")
	public List<CusDevPlan> queryCusDevPlans(@Param(value="saleChanceId")Integer saleChanceId);
	/**
	 * 添加
	 * @param cusDevPlan
	 */
	@Insert("insert into t_cus_dev_plan (plan_item, plan_date, exe_affect,create_date, update_date, is_valid, sale_chance_id)"
			+ " values(#{planItem}, #{planDate},#{exeAffect}, now(), now(), 1, #{saleChanceId})")
	@Options(useGeneratedKeys = true, keyProperty="id")
	public void add(CusDevPlan cusDevPlan);
	/**
	 * 修改
	 * @param cusDevPlan
	 */
	@Update("update t_cus_dev_plan set plan_item = #{planItem}, plan_date = #{planDate}, "
			+ "exe_affect = #{exeAffect}, update_date = now() where id = #{id}")
	void update(CusDevPlan cusDevPlan);
	/**
	 * 删除
	 * @param id
	 */
	@Update("update t_cus_dev_plan set is_valid = 0 where id = #{id}")
	void delete(@Param(value="id")Integer id);
}
