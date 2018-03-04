package com.bonc.service;

import java.util.List;

import com.bonc.domain.UserInfo;  

public interface UserInfoService {  
  
    public UserInfo findByUsername(String username);
    
    public List<UserInfo> findAllUserInfos();
    
    public void addUserInfo(UserInfo userInfo);
  
} 
