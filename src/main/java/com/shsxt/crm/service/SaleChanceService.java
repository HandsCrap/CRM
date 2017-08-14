package com.shsxt.crm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.dto.SaleChanceDto;
import com.shsxt.crm.dto.SaleChanceQuery;
import com.shsxt.crm.model.SaleChance;
import com.shsxt.crm.util.AssertUtil;

@Service
public class SaleChanceService{
	
	@Autowired
	private SaleChanceDao saleChanceDao; 
	
	private static Logger logger = LoggerFactory.getLogger(SaleChanceService.class);
	
	//分页查找
	public Map<String, Object> queryForPage(SaleChanceQuery query){
		Integer page = query.getPage();
		if(page==null){
			page=1;
		}
		Integer rows = query.getRows();
		if(rows==null){
			rows=10;
		}
		String sort = query.getSort();
		if(StringUtils.isBlank(sort)){
			sort="id.asc";//默认排序方式
		}
		//分页查询对象
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sort));
		//查询数据
		List<SaleChance> saleChances = saleChanceDao.querySaleChancesForPage(query, pageBounds);
		//将查询结果强转为分页数据
		PageList<SaleChance> list = (PageList<SaleChance>) saleChances;
		//由分页数据获得分页对象
		Paginator paginator = list.getPaginator();
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("paginator", paginator);
		result.put("rows", list);
		result.put("total", paginator.getTotalCount());
		return result;
	}
	//添加
	public void add(SaleChanceDto saleChanceDto, String userName) {
		//参数非空判断
		checkParams(saleChanceDto.getCustomerId(),saleChanceDto.getCustomerName(),saleChanceDto.getCgjl());
		//判断分配状态
		String assignMan = saleChanceDto.getAssignMan();
		int state = 0; // 未分配
		Date assignTime = null;
		if (StringUtils.isNoneBlank(assignMan)) {
			state = 1; // 已分配
			assignTime = new Date();
		}
		SaleChance saleChance = new SaleChance();
		BeanUtils.copyProperties(saleChanceDto, saleChance); // 属性拷贝
		saleChance.setAssignTime(assignTime);
		saleChance.setState(state);
		saleChance.setCreateMan(userName);
		int count = saleChanceDao.insert(saleChance);
		logger.debug("插入的记录数为：{}, 主键为：", count, saleChance.getId());
	}
	//修改
	public void update(SaleChance saleChance) {
		Integer id= saleChance.getId();
		AssertUtil.intNotNull(id, "请选择要修改的记录！");
		checkParams(saleChance.getCustomerId(),saleChance.getCustomerName(),saleChance.getCgjl());
		checkState(saleChance);
		saleChance.setUpdateDate(new Date());
		saleChanceDao.updateSaleChance(saleChance);
	}
	//删除
	public void delete(String ids) {
		AssertUtil.isNotEmpty(ids, "请选择要删除的记录！");
		saleChanceDao.delete(ids);
	}
	//基本参数验证
	private void checkParams(Integer customerId,String customerName,Integer cgjl){
		AssertUtil.intNotNull(customerId, "请选择客户！");
		AssertUtil.isNotEmpty(customerName, "请选择客户！");
		AssertUtil.intNotNull(cgjl, "请输入成功几率！");
	}
	//验证分配状态
	private void checkState(SaleChance saleChance){
		SaleChance temp = saleChanceDao.querySaleChanceById(saleChance.getId());
		AssertUtil.notNull(temp, "该记录不存在！");
		int state= temp.getState();
		Date assignTime = null;
		String assignMan = temp.getAssignMan();
		if(state==0){
			//未分配
			if(StringUtils.isNoneBlank(saleChance.getAssignMan())){
				state=1;
				assignTime = new Date();
			}
		}else{
			//已分配
			if(StringUtils.isBlank(saleChance.getAssignMan())){
				//改为未分配
				state=0;
				assignTime=null;
			}else{
				//重新分配
				assignMan = saleChance.getAssignMan();
				assignTime = new Date();
			}
		}
		saleChance.setAssignMan(assignMan);
		saleChance.setAssignTime(assignTime);
		saleChance.setState(state);
	}
	public SaleChance queryById(Integer saleChanceId) {
		return saleChanceDao.querySaleChanceById(saleChanceId);
	}
}
