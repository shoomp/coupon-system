package coupon.system.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import coupon.system.core.jobs.DailyJob;

@SpringBootApplication
public class JobTest {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AdminTest.class, args);

		DailyJob dailyJob = ctx.getBean(DailyJob.class);
		
		dailyJob.start();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		dailyJob.stopJobWhenDayEnds();
//		dailyJob.stopJobNow();
	}
}
