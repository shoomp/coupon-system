package coupon.project.core.jobs;

import java.time.LocalDate;

import coupon.project.core.dao.CouponsDAO;
import coupon.project.core.dbdao.CouponsDBDAO;

/**
 * @author מוריה הדאיה
 *
 */
public class DailyJob extends Thread {

	private CouponsDAO couponsDAO;
	private boolean quit;
	private int checkTime = 86_400_000;

	{
		couponsDAO = new CouponsDBDAO();
		this.setDaemon(true);

	}

	@Override
	public void run() {
		quit = true;
		while (quit) {
			try {
				Thread.sleep(checkTime);
				couponsDAO.deleteCouponByExpiryDate(LocalDate.now());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("check thread is closed");

	}

	public void stopJob() {
		quit = false;
	}
}
