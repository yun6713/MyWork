package com.bonc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
 * 用户信息.  
 * @author Administrator  
 *  
 */  
@ApiModel(value = "UserInfo", description = "用户信息")
@Entity(name = "USER_INFO")  
public class UserInfo implements Serializable {  
  
    /**  
     *   
     */  
    private static final long serialVersionUID = 1L;  
  
    @ApiModelProperty(value = "主键(新增时无效)", required = true)
    @Id  
    @GeneratedValue  
    private long uid;// 用户id  
  
    @ApiModelProperty(value = "用户名", required = true, position = 2)
    @Column(unique = true)  
    private String username;// 帐号  
  
    @ApiModelProperty(value = "真实名字", required = true, position = 3)
    private String name;// 名称（昵称或者真实姓名，不同系统不同定义）  
  
    @ApiModelProperty(value = "密码(获取用户信息时无此字段)", required = true, position = 4)
    private String password; // 密码;  
    
    @ApiModelProperty(value = "加密密码的盐", required = true, position = 5)
    private String salt;// 加密密码的盐  
  
    @ApiModelProperty(value = "用户状态",required = true, position = 6)
    private byte state=1;// 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 ,  
                        // 1:正常状态,2：用户被锁定.  
  
    @ApiModelProperty(value = "用户角色",required = true, position = 7)
    @ManyToMany(fetch = FetchType.EAGER) // 立即从数据库中进行加载数据  
    @JoinTable(name = "SYS_USER_ROLE", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {  
            @JoinColumn(name = "roleId") })  
    private List<SysRole> roleList;// 一个用户具有多个角色  
  
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
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public String getSalt() {  
        return salt;  
    }  
  
    public void setSalt(String salt) {  
        this.salt = salt;  
    }  
  
    public byte getState() {  
        return state;  
    }  
  
    public void setState(byte state) {  
        this.state = state;  
    }  
  
    public List<SysRole> getRoleList() {  
        return roleList;  
    }  
  
    public void setRoleList(List<SysRole> roleList) {  
        this.roleList = roleList;  
    }  
    
    /**  
     * 密码盐.  
     *   
     * @return  
     */  
    public String getCredentialsSalt() {  
        return this.username + this.salt;  
    }  
  
    @Override  
    public String toString() {  
        return "UserInfo [uid=" + uid + ", username=" + username + ", name=" + name + ", password=" + password  
                + ", salt=" + salt + ", state=" + state + "]";  
    }  
  
}  
