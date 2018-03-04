package com.bonc.domain;

import java.io.Serializable;  
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
 * 权限实体类;  
 *   
 */  
@ApiModel(value = "UserInfo", description = "用户信息")
@Entity(name="sys_permission")  
public class SysPermission implements Serializable {  
    private static final long serialVersionUID = 1L;  
    @ApiModelProperty(value = "主键(新增时无效)", required = true)
    @Id  
    @GeneratedValue  
    private long id;// 主键.
    
    @ApiModelProperty(value = "权限名", required = true, position = 1)
    private String name;// 名称.  
    @ApiModelProperty(value = "资源类型", required = true, position = 2)
        @Column(columnDefinition = "enum('menu','button')")  
    private String resourceType;// 资源类型，[menu|button]  
    @ApiModelProperty(value = "资源路径", required = true, position = 3)
    private String url;// 资源路径.  
    @ApiModelProperty(value = "权限字符串", required = true, position = 4)
    private String permission; // 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view  
    @ApiModelProperty(value = "父编号", required = true, position = 5)
    private Long parentId; // 父编号  
    @ApiModelProperty(value = "父编号列表", required = true, position = 6)
    private String parentIds; // 父编号列表  
    @ApiModelProperty(value = "是否可用",  position = 7)
    private Boolean available = Boolean.FALSE;  
  
//  @ManyToMany(fetch = FetchType.LAZY)  
//  @JoinTable(name = "SysRolePermission", joinColumns = { @JoinColumn(name = "permissionId") }, inverseJoinColumns = {  
//          @JoinColumn(name = "roleId") })  
//  private List<SysRole> roles;  
  
    public long getId() {  
        return id;  
    }  
  
    public void setId(long id) {  
        this.id = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public String getResourceType() {  
        return resourceType;  
    }  
  
    public void setResourceType(String resourceType) {  
        this.resourceType = resourceType;  
    }  
  
    public String getUrl() {  
        return url;  
    }  
  
    public void setUrl(String url) {  
        this.url = url;  
    }  
  
    public String getPermission() {  
        return permission;  
    }  
  
    public void setPermission(String permission) {  
        this.permission = permission;  
    }  
  
    public Long getParentId() {  
        return parentId;  
    }  
  
    public void setParentId(Long parentId) {  
        this.parentId = parentId;  
    }  
  
    public String getParentIds() {  
        return parentIds;  
    }  
  
    public void setParentIds(String parentIds) {  
        this.parentIds = parentIds;  
    }  
  
    public Boolean getAvailable() {  
        return available;  
    }  
  
    public void setAvailable(Boolean available) {  
        this.available = available;  
    }  
  
//  public List<SysRole> getRoles() {  
//      return roles;  
//  }  
//  
//  public void setRoles(List<SysRole> roles) {  
//      this.roles = roles;  
//  }  
  
    @Override  
    public String toString() {  
        return "SysPermission [id=" + id + ", name=" + name + ", resourceType=" + resourceType + ", url=" + url  
                + ", permission=" + permission + ", parentId=" + parentId + ", parentIds=" + parentIds + ", available="  
                + available + "]";  
    }  
  
} 
