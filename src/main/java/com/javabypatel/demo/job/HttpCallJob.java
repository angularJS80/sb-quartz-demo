package com.javabypatel.demo.job;

import com.javabypatel.demo.service.JobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class HttpCallJob extends QuartzJobBean implements InterruptableJob{
	private volatile boolean toStopFlag = true;

	@Autowired
	JobService jobService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		JobKey key = jobExecutionContext.getJobDetail().getKey();
		System.out.println("Cron Job started with key :" + key.getName() + ", Group :"+key.getGroup() + " , Thread Name :"+Thread.currentThread().getName() + " ,Time now :"+new Date());

		RestTemplate rt = new RestTemplate();
		String getUrl = "http://localhost:8080/board";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> reganswer = restTemplate.getForEntity(getUrl,String.class);

		//*********** For retrieving stored key-value pairs ***********/
		JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
		String myValue = dataMap.getString("myKey");
		System.out.println("Value:" + myValue);

		System.out.println("Thread: "+ Thread.currentThread().getName() +" stopped.");
	}


	@Override
	public void interrupt() throws UnableToInterruptJobException {
		System.out.println("Stopping thread... ");
		toStopFlag = false;
	}

}