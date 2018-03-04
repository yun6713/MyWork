package com.bonc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bonc.domain.SysRole;
@Repository
public interface SysRoleRepository extends CrudRepository<SysRole, Long> {
	public SysRole findByRole(String role);
	public List<SysRole> findByRole(List<String> roles);
	
}
