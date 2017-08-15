package com.shsxt.crm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.crm.constant.DevResult;
import com.shsxt.crm.dao.CusDevPlanDao;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.model.CusDevPlan;
import com.shsxt.crm.util.AssertUtil;

@Service
public class CusDevPlanService {

	@Autowired
	private CusDevPlanDao cusDevPlanDao;
	@Autowired
	private SaleChanceDao saleChanceDao;
	/**
	 * 查询该营销机会下的所有计划项
	 * @param saleChanceId
	 * @return
	 */
	public Map<String, Object> queryCusDevPlans(Integer saleChanceId) {
		AssertUtil.intNotNull(saleChanceId, "请选择要操作的营销机会！");
		Map<String, Object> result = new HashMap<String,Object>();
		List<CusDevPlan> list = cusDevPlanDao.queryCusDevPlans(saleChanceId);
		result.put("rows", list);
		return result;
	}
	/**
	 * 添加
	 * @param cusDevPlan
	 */
	public void add(CusDevPlan cusDevPlan) {
		Integer saleChanceId = cusDevPlan.getSaleChanceId();
		AssertUtil.intNotNull(saleChanceId, "请选择要操作的营销机会！");
		Date planDate = cusDevPlan.getPlanDate();
		AssertUtil.notNull(planDate, "请添加计划时间!");
		String planItem=cusDevPlan.getPlanItem();
		AssertUtil.isNotEmpty(planItem, "请输入计划内容！");
		String exeAffect= cusDevPlan.getExeAffect();
		AssertUtil.isNotEmpty(exeAffect, "请输入计划实施效果！");
		//添加计划管理项
		cusDevPlanDao.add(cusDevPlan);
		//修改营销机会的开发状态
		saleChanceDao.updateDevResult(saleChanceId,DevResult.DEVING.getDevResult());
	}
	/**
	 * 
	 * @param cusDevPlan
	 */
	public void update(CusDevPlan cusDevPlan) {
		Integer id = cusDevPlan.getId();
		AssertUtil.intNotNull(id, "请选择要修改的记录！");
		Integer saleChanceId = cusDevPlan.getSaleChanceId();
		AssertUtil.intNotNull(saleChanceId, "请选择要操作的营销机会！");
		Date planDate = cusDevPlan.getPlanDate();
		AssertUtil.notNull(planDate, "请添加计划时间!");
		String planItem=cusDevPlan.getPlanItem();
		AssertUtil.isNotEmpty(planItem, "请输入计划内容！");
		String exeAffect= cusDevPlan.getExeAffect();
		AssertUtil.isNotEmpty(exeAffect, "请输入计划实施效果！");
		
		cusDevPlanDao.update(cusDevPlan);
	}
	
	
	public void delete(Integer id) {
		
		AssertUtil.intNotNull(id, "请选择要删除的记录！");
		
	}
	
	
	
}
