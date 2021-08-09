package coupon.system.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coupon.system.core.repositories.CompanyRepository;
import coupon.system.core.repositories.CouponRepository;
import coupon.system.core.repositories.CustomerRepository;

@Component
public abstract class Client {
	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	
	public abstract boolean login(String email, String password);

}
