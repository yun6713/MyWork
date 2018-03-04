package com.bonc.service;

import java.util.List;

import com.bonc.domain.SysRole;  

public interface SysRoleService {  
  
    public SysRole findByRole(String role);
    
    public List<SysRole> findAllSysRoles(List<String> roles);
  
} 
