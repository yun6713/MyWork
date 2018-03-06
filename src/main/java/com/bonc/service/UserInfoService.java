package com.bonc.service;

import java.util.List;

import com.bonc.domain.UserInfo;  

public interface UserInfoService {  
  
    public UserInfo findByUsername(String username);

    public UserInfo findByUid(long uid);
    
    public List<UserInfo> findAllUserInfos();
    
    public void addUserInfo(UserInfo userInfo);

	public boolean delete(long uid);
  
} 
