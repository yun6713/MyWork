package com.bonc.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bonc.controller.UserInfoController;

public class HelloJob2 implements Job {
	private Logger log = LoggerFactory.getLogger(UserInfoController.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("hello2");
	}

}
