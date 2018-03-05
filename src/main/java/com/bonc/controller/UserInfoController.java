package com.bonc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    //加入Qulifier注解，通过名称注入bean
    @Autowired @Qualifier("Scheduler")
    private Scheduler scheduler;
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
     * 用户开启定时任务.  
     * @return  
     */  
	@ApiOperation(value = "开启定时任务")
	@RequestMapping(value = "/addjob", method = RequestMethod.POST)
	@RequiresPermissions("userInfo:view")//权限管理; 
	@ApiImplicitParams({ @ApiImplicitParam(name = "jobClassName", value = "注册用户名", paramType = "form", required = true),
		@ApiImplicitParam(name = "jobGroupName", value = "用户密码", paramType = "form", required = true),
		@ApiImplicitParam(name = "cronExpression", value = "用户密码", paramType = "form", required = true)})
	@ResponseBody
	public void addjob(@RequestParam(value="jobClassName")String jobClassName, 
            @RequestParam(value="jobGroupName")String jobGroupName, 
            @RequestParam(value="cronExpression")String cronExpression) throws Exception
    {           
        addJob(jobClassName, jobGroupName, cronExpression);
    }

    public void addJob(String jobClassName, String jobGroupName, String cronExpression)throws Exception{

        // 启动调度器  
        scheduler.start(); 

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
            .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败"+e);
            throw new Exception("创建定时任务失败");
        }
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
		userInfo.setSalt(salt);
		password = getNewPassword(password,userInfo.getCredentialsSalt());//密码加密
		userInfo.setPassword(password);
		SysRole sr = sysRoleService.findByRole(roles);
		List<SysRole> roleList = new ArrayList<SysRole>();
		roleList.add(sr);
		userInfo.setRoleList(roleList);
		userInfoService.addUserInfo(userInfo);
       return userInfoService.findByUsername(userName).toString();  
    }

	//获取加密后的密码
	private String getNewPassword(String password, String salt) {

		System.out.println("register pwd:"+password+"|");
		password = new SimpleHash("MD5",password,salt.getBytes(),2).toHex();
		System.out.println("register salt:"+salt+"|");
		System.out.println("register pwd: "+password);
		return password;
	} 
    
	 public static Job getClass(String classname) throws Exception 
	    {
	        Class<?> class1 = Class.forName(classname);
	        return (Job)class1.newInstance();
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