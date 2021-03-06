package com.bonc.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统角色实体类;
 * 
 * @author Administrator
 * 
 */
@ApiModel(value = "User", description = "用户实体")
@Entity(name = "SYS_ROLE")
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "主键(新增时无效)", required = true)
	@Id
	@GeneratedValue
	private Long id; // 编号
	@ApiModelProperty(value = "用户角色", required = true, position = 1)
	private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
	@ApiModelProperty(value = "角色描述", position = 2)
	private String description; // 角色描述,UI界面显示使用
	@ApiModelProperty(value = "是否可用", required = true, position = 3)
	private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

	// 角色 -- 权限关系：多对多关系;
	@ApiModelProperty(value = "角色权限", required = true, position = 4)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SYS_ROLE_PERMISSION", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = {
			@JoinColumn(name = "permissionId") })
	private List<SysPermission> permissions;

	// 用户 - 角色关系定义;
	@ApiModelProperty(value = "用户角色", required = true, position = 5)
	@ManyToMany
	@JoinTable(name = "SYS_USER_ROLE", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = {
			@JoinColumn(name = "uid") })
	private List<UserInfo> userInfos;// 一个角色对应多个用户

	public List<UserInfo> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public List<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "SysRole [id=" + id + ", role=" + role + ", description=" + description + ", available=" + available
				+ ", permissions=" + permissions + "]";
	}
}
