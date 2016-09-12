package com.toncho.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestJob {

	public TestJob() {
		System.out.println("MyJob创建成功");
	}

	@Scheduled(cron = "0 0 0/1 * * ? ") // 每隔1h执行一次
	public void run() {
		System.out.println("Hello MyJob  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()));
	}

}
