package com.shsxt.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.crm.constant.ModuleGrade;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.dao.PermissionDao;
import com.shsxt.crm.model.Module;
import com.shsxt.crm.model.Permission;
import com.shsxt.crm.model.Role;
import com.shsxt.crm.util.AssertUtil;

@Service
public class PermissionService {
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired 
	private ModuleDao moduleDao;
	/**
	 * 授权或取消权限
	 * @param roleId
	 * @param moduleId
	 * @param checked
	 */
	public void addRelate(Integer roleId, Integer moduleId, boolean checked) {
		//基本参数验证
		AssertUtil.intNotNull(roleId, "请选择角色");
		AssertUtil.intNotNull(moduleId, "请选择模块");
		//角色验证
		Role role = roleService.queryByid(roleId);
		AssertUtil.notNull(role, "该角色不存在");
		//模块验证
		Module module =moduleService.queryById(moduleId);
		AssertUtil.notNull(module, "该模块不存在");
		//判断checked
		if(checked){//授权
			//先把该模块授权给该角色
			List<Permission> permissions = new ArrayList<Permission>();
			Permission permission = new Permission();
			permission.setRoleId(roleId);
			permission.setModuleId(moduleId);
			permission.setAclValue(module.getOptValue());
			permissions.add(permission);
			//把父模块授权给该角色
			if(ModuleGrade.first.getGrade()==module.getGrade()){//一级模块有一级父模块
				//先判断父模块是否已授权
				Permission parentPermission = permissionDao.queryById(roleId,module.getParentId());
				if(parentPermission==null){
					parentPermission = new Permission();
					Module parentModule = moduleService.queryById(module.getParentId());
					parentPermission.setAclValue(parentModule.getOptValue());
					parentPermission.setModuleId(parentModule.getId());
					parentPermission.setRoleId(roleId);
					permissions.add(parentPermission);
				}
			}else if(module.getParentId()!=null&&module.getParentId()>ModuleGrade.first.getGrade()){//一级以上的模块，有多级父模块
				//获取父模块的id集合：treePath中包含类父模块id
				String [] parentIds = module.getTreePath().substring(1,  module.getTreePath().lastIndexOf(",")).split(",");//,1,2,=>[1,2]
				for(String parentId:parentIds ){
					Permission parentPermission=permissionDao.queryById(roleId, Integer.parseInt(parentId));
					if(parentPermission==null){
						parentPermission = new Permission();
						Module parentModule = moduleService.queryById(Integer.parseInt(parentId));
						parentPermission.setAclValue(parentModule.getOptValue());
						parentPermission.setModuleId(parentModule.getId());
						parentPermission.setRoleId(roleId);
						permissions.add(parentPermission);
					}
				}
			}
			//把子模块授权给该角色:根据treePath可查找到子模块
			String treePath = "";
			if(ModuleGrade.root.getGrade()==module .getGrade()){
				treePath = ","+module.getTreePath()+",";
			}else{
				treePath =module.getTreePath()+moduleId+",";
			}
			List<Module> childrenModules = moduleDao.queryChiledrenModules(treePath);
			for(Module childModule:childrenModules){
				Permission childPermission=new Permission();
				childPermission.setAclValue(childModule.getOptValue());
				childPermission.setModuleId(childModule.getId());
				childPermission.setRoleId(roleId);
				permissions.add(childPermission);
			}
			permissionDao.insertBatch(permissions);
		}else{//取消权限
			//先解绑本模块
			permissionDao.delete(roleId,moduleId);
			//解绑子模块
			String treePath = "";
			if(ModuleGrade.root.getGrade()==module .getGrade()){
				treePath = ","+module.getGrade()+",";
			}else{
				treePath =module .getTreePath()+moduleId+",";
			}
			List<Module> childrenModules = moduleDao.queryChiledrenModules(treePath);
			for(Module childModule:childrenModules){
				permissionDao.delete(roleId, childModule.getId());
			}
			//解绑父模块
			if(module.getGrade()==ModuleGrade.root.getGrade()){//根级无父级
				return;
			}
			//获取所有父级id
			//判断每个父级下还有没有其他模块与此角色绑定：要从离本模块最近的父模块开始解绑
			String[] parentIds=module.getTreePath().substring(1, module.getTreePath().lastIndexOf(",")).split(",");
			for(int i=parentIds.length-1;i>=0;i--){
				Integer parentId=Integer.parseInt(parentIds[i]);
				Module parentModule=moduleDao.queryById(parentId);
				if(ModuleGrade.root.getGrade()==parentModule .getGrade()){
					treePath = ","+parentModule.getGrade()+",";
				}else{
					treePath =parentModule.getTreePath()+parentId+",";
				}
				Integer count=permissionDao.queryChildrens(treePath,roleId);
				//没有子级与该角色绑定，可直接删除
				if(count==null||count==0){
					permissionDao.delete(roleId, parentId);
				}
			}
			
		}
	}
	/**
	 * 查找该用户拥有的权限值
	 * @param userId
	 * @return
	 */
	public List<String> queryUserPermissions(Integer userId) {
		AssertUtil.intNotNull(userId, "请登录!");
		return permissionDao.queryUserPermissions(userId);
	}
	
}
