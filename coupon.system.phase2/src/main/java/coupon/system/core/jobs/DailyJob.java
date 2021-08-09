package coupon.system.core.jobs;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coupon.system.core.services.JobService;


@Service
public class DailyJob extends Thread {

	@Autowired
	private JobService jobService;
	private boolean quit;
	ScheduledExecutorService executorService;
	/**
	 * this method calculates to minutes to wait before 00:01 then if the "quit"
	 * field is false, it starts deleting all expired coupons, and repeat doing so
	 * every single day
	 */
	@Override
	public void run() {
		System.out.println("**********Coupon Exterminator program has initialized**********");
		int hourToWait = 23 - LocalTime.now().getHour();
		int minutesToWait = 61 - LocalTime.now().getMinute();
		long timeToWaitUntilMidnightInMinutes = hourToWait * 60 + minutesToWait;
		try {
//			TimeUnit.MINUTES.sleep(timeToWaitUntilMidnightInMinutes);
			while (true) {
				if (!quit) {
					System.out.println("deleting started >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//					executorService = Executors.newSingleThreadScheduledExecutor();
//					executorService.scheduleWithFixedDelay(null, minutesToWait, timeToWaitUntilMidnightInMinutes, null)
					jobService.deleteCoupons();
				} else {
					break;
				}
//				TimeUnit.DAYS.sleep(1);
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			System.out.println("############ Coupon Exterminator program Shut Down Dou to Interruption ###########");
		}
	}

	/**
	 * this method is used to stop thread
	 */
	public void stopJobWhenDayEnds() {
		quit = true;
		System.out.println("**********Coupon Exterminator program Shut Down*************~");
	}

	public void stopJobNow() {
		this.interrupt();
	}
}
