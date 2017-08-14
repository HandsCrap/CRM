package com.shsxt.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shsxt.crm.model.User;
import com.shsxt.crm.vo.UserVO;

public interface UserDao {
	/**
	 * 根据id查找用户
	 * 
	 * @param id
	 * @return
	 */
	@Select("select id,user_name,password,true_name,email,phone from t_user where id=#{id}")
	User findUserById(@Param(value = "id") Integer id);

	/**
	 * 根据用户名查找合法用户
	 * 
	 * @param userName
	 * @return
	 */
	@Select("select id,user_name,password,true_name,email,phone from t_user where user_name=#{userName} and is_valid=1")
	User findUserByUserName(@Param(value = "userName") String userName);

	void insert(User user);

	void update(User user);
	
	@Select("select u.id,u.true_name from t_user u join t_user_role r on u.id=r.user_id where r.role_id=3")
	List<UserVO> queryCustomerManagers();

}
