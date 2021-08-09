package coupon.system.core.services;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coupon.system.core.repositories.CouponRepository;

@Service
@Transactional
public class JobService{

	@Autowired
	private CouponRepository couponRepository;
	
	public void deleteCoupons() {
		couponRepository.deleteAllByEndDateBefore(LocalDate.now());
	}
	
	
}
