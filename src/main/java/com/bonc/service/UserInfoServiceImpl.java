package com.bonc.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bonc.domain.UserInfo;
import com.bonc.repository.UserInfoRepository;  
  
@Service  
public class UserInfoServiceImpl implements UserInfoService{  
    @Resource  
    private UserInfoRepository userInfoRepository;  
  
    @Transactional(readOnly=true)  
    @Override  
    public UserInfo findByUsername(String username) {  
        System.out.println("UserInfoServiceImpl.findByUsername()");  
        return userInfoRepository.findByUsername(username);  
    }

	@Override
	public List<UserInfo> findAllUserInfos() {
		System.out.println("UserInfoServiceImpl.findByUsername()");  
        return (List<UserInfo>) userInfoRepository.findAll();
		
	}

	@Override
	public void addUserInfo(UserInfo userInfo) {
		userInfoRepository.save(userInfo);
		
	}  
  
}  
