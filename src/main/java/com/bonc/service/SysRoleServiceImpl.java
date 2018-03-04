package com.bonc.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bonc.domain.SysRole;
import com.bonc.repository.SysRoleRepository;
@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	SysRoleRepository sysRoleRepository;

	@Override
	public SysRole findByRole(String role) {
		return sysRoleRepository.findByRole(role);
	}

	@Override
	public List<SysRole> findAllSysRoles(List<String> roles) {
		// TODO Auto-generated method stub
		return sysRoleRepository.findByRole(roles);
	}

	
	
	
}
