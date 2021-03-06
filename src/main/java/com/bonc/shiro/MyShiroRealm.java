package com.bonc.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bonc.controller.UserInfoController;
import com.bonc.domain.SysPermission;
import com.bonc.domain.SysRole;
import com.bonc.domain.UserInfo;
import com.bonc.service.UserInfoService;  
  
/**  
 * 身份校验核心类  
 *   
 * @author Administrator  
 *  
 */  
public class MyShiroRealm extends AuthorizingRealm {  
	private Logger log = LoggerFactory.getLogger(UserInfoController.class);
	
    @Resource  
    private UserInfoService userInfoService;  
  
    /**  
     * 认证信息(身份验证) Authentication 是用来验证用户身份 
     * 根据登录用户名获取认证信息；提交给凭证管理器匹配 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {  
//        log.info("MyShiroRealm.doGetAuthenticationInfo()");  
        // 获取用户的输入帐号  
        String username = (String) token.getPrincipal();  
//        log.info("token.getCredentials: "+token.getCredentials());  
        // 通过username从数据库中查找 User对象，如果找到，没找到.  
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法  
        UserInfo userInfo = userInfoService.findByUsername(username);  
//        log.info("----->>userInfo=" + userInfo);  
        if (userInfo == null) {  
            return null;  
        }  
//  log.info("real salt: "+userInfo.getCredentialsSalt());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo, // 用户名  
                userInfo.getPassword(), // 密码  
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()), // salt=username+salt  
                getName() // realm name  
        );  
        return authenticationInfo;  
    }  
  
    /**
     * 获取权限信息
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
        // TODO Auto-generated method stub  
        log.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");  
  
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();  
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();  
          
        for(SysRole role:userInfo.getRoleList()){  
                
               authorizationInfo.addRole(role.getRole());  
               log.info(role.getPermissions().toString());  
               for(SysPermission p:role.getPermissions()){  
                   log.info(p.toString());  
                  authorizationInfo.addStringPermission(p.getPermission());  
               }  
           }  
        return authorizationInfo;  
    }  
      
      
} 
