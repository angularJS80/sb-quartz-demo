package com.javabypatel.demo.job;

import com.javabypatel.demo.service.JobService;
import com.javabypatel.demo.service.TestTaskService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class ServiceCallJob extends QuartzJobBean implements InterruptableJob{

	private volatile boolean toStopFlag = true;

	@Autowired
	TestTaskService testTaskService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		testTaskService.getTeset();
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		System.out.println("Stopping thread... ");
		toStopFlag = false;
	}

}