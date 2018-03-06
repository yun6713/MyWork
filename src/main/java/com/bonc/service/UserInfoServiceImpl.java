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
        return userInfoRepository.findByUsername(username);  
    }

	@Override
	public List<UserInfo> findAllUserInfos() {
		return (List<UserInfo>) userInfoRepository.findAll();		
	}

	@Override
	public void addUserInfo(UserInfo userInfo) {
		userInfoRepository.save(userInfo);
		
	}

	@Override
	public UserInfo findByUid(long uid) {
		return userInfoRepository.findOne(uid);
	}

	@Override
	public boolean delete(long uid) {
		userInfoRepository.delete(uid);
		return userInfoRepository.findOne(uid)==null;
	}  
  
}  
