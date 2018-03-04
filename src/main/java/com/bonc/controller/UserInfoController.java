package com.bonc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bonc.domain.SysRole;
import com.bonc.domain.UserInfo;
import com.bonc.service.SysRoleService;
import com.bonc.service.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;  
  
@Controller
@Api(tags = "用户管理", description = "对系统内的用户信息进行管理")
@RequestMapping("/userInfo")  
public class UserInfoController { 
	private final UserInfoService userInfoService;
	private final SysRoleService sysRoleService;
	@Autowired
	public UserInfoController(UserInfoService userInfoService,
			SysRoleService sysRoleService) {
		this.userInfoService = userInfoService;
		this.sysRoleService = sysRoleService;
	}

    /**  
     * 用户查询.  
     * @return  
     */  
	@ApiOperation(value = "查询并展示所有用户")
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	@RequiresPermissions("userInfo:view")//权限管理; 
	@ResponseBody
    public String userInfo(){  
		System.out.println("/userList");
       return userInfoService.findAllUserInfos().toString();  
    } 
	
    /**  
     * 用户登录页.  
     * @return  
     */  
	@ApiOperation(value = "用户登录页")
    @RequestMapping(value = "/login", method = RequestMethod.GET)  
    public String login() {  
        return "/login";  
    }  
    
	@ApiOperation(value = "登录成功页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)  
    public String index() {  
        return "/index";  
    }
	
	@ApiOperation(value = "用户退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)  
    @ResponseBody
    public String logout() {  
        return "logout success";  
    }
  
	@ApiOperation(value = "用户登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", value = "注册用户名", paramType = "form", required = true),
		@ApiImplicitParam(name = "password", value = "用户密码", paramType = "form", required = true) })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	@RequiresPermissions("userInfo:view")//权限管理; 
	public String login(HttpServletRequest request, Map<String, Object> map) {  
        System.out.println("HomeController.login");  
        // 登录失败从request中获取shiro处理的异常信息  
        // shiroLoginFailure:就是shiro异常类的全类名  
        String exception = (String) request.getAttribute("shiroLoginFailure");  
        System.out.println("exception: "+exception);
        String msg = "";  
        if (exception != null) {  
            if (UnknownAccountException.class.getName().equals(exception)) {  
                System.out.println("UnknownAccountException -->帐号不存在：");  
                msg = "UnknownAccountException -->帐号不存在：";  
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {  
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");  
                msg = "IncorrectCredentialsException -- > 密码不正确：";  
            } else if ("kaptchaValidateFailed".equals(exception)) {  
                System.out.println("kaptchaValidateFailed -- > 验证码错误");  
                msg = "kaptchaValidateFailed -- > 验证码错误";  
            } else {  
                msg = "else >> " + exception;  
                System.out.println("else -- >" + exception);  
            }  
        }  
        map.put("msg", msg);  
        // 此方法不处理登录成功,由shiro进行处理.  
        return "/login";  
    }
    /**  
     * 用户添加;  
     * @return  
     */  
	@ApiOperation(value = "添加用户行为")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userName", value = "注册用户名", paramType = "form", required = true),
			@ApiImplicitParam(name = "name", value = "真实名字", paramType = "form", required = true),
			@ApiImplicitParam(name = "password", value = "用户密码", paramType = "form", required = true),
			@ApiImplicitParam(name = "salt", value = "密码盐", paramType = "form"),
			@ApiImplicitParam(name = "roles", value = "角色，admin/vip", paramType = "form", required = true) })
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
//	@RequiresPermissions("userInfo:add")//权限管理; 
	@ResponseBody
    public String userInfoAdd(@RequestParam String userName, 
			@RequestParam String name,
			@RequestParam String password, 
			@RequestParam(required = false, defaultValue = "helloWorld") String salt,
			@RequestParam String roles){ 
		if(userInfoService.findByUsername(userName) != null)
			return "user exist";//用户已存在，不能重复注册
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(userName);
		userInfo.setName(name);
		userInfo.setPassword(password);
		userInfo.setSalt(salt);
		SysRole sr = sysRoleService.findByRole(roles);
		List<SysRole> roleList = new ArrayList<SysRole>();
		roleList.add(sr);
		userInfo.setRoleList(roleList);
		userInfoService.addUserInfo(userInfo);
       return userInfoService.findByUsername(userName).toString();  
    } 
    
    /**  
     * 用户删除;  
     * @return  
     */  
//    @RequestMapping("/userDel")  
//    @RequiresPermissions("userInfo:del")//权限管理;  
//    public String userDel(){  
//       return "userInfoDel";  
//    }  
}  