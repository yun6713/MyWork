package com.bonc.vo;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.bonc.domain.SysRole;
import com.bonc.domain.UserInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UserInfoVO", description = "用户信息")
public class UserInfoVO {
	@ApiModelProperty(value = "主键(新增时无效)", required = true)
	private long uid;// 用户id

	@ApiModelProperty(value = "用户名", required = true, position = 2)
	private String username;// 帐号

	@ApiModelProperty(value = "真实名字", required = true, position = 3)
	private String name;// 名称（昵称或者真实姓名，不同系统不同定义）

	@ApiModelProperty(value = "用户状态", required = true, position = 6)
	private byte state = 1;// 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 ,
							// 1:正常状态,2：用户被锁定.

	@ApiModelProperty(value = "用户角色", required = true, position = 7)
	private String roles;// 一个用户具有多个角色

	public UserInfoVO() {}
	
	public UserInfoVO(long uid, String username, String name, byte state, List<SysRole> roleList) {
		super();
		this.uid = uid;
		this.username = username;
		this.name = name;
		this.state = state;
		this.roles = "";
		for (SysRole role : roleList)
			this.roles += role.getRole() + " ";
	}

	public UserInfoVO(UserInfo userInfo) {
		super();
		this.uid = userInfo.getUid();
		this.username = userInfo.getUsername();
		this.name = userInfo.getName();
		this.state = userInfo.getState();
		this.roles = "";
		for (SysRole role : userInfo.getRoleList())
			this.roles += role.getRole() + " ";
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserInfoVO [uid=" + uid + ", username=" + username + ", name=" + name + ", state=" + state + ", roles="
				+ roles + "]";
	}

	

}
