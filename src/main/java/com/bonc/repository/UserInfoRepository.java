package com.bonc.repository;

import org.springframework.data.repository.CrudRepository;

import com.bonc.domain.UserInfo;  
  
/**  
 * UserInfo持久化类  
 *   
 * @author Administrator  
 *  
 */  
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {  
    /** 通过username查找用户信息 **/  
    public UserInfo findByUsername(String username);  
  
}  
