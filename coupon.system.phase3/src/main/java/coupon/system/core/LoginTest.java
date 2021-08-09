package coupon.system.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import coupon.system.core.entities.Company;
import coupon.system.core.exceptions.CouponSystemException;
import coupon.system.core.login.ClientType;
import coupon.system.core.login.LoginManager;
import coupon.system.core.services.AdminService;
import coupon.system.core.services.CompanyService;
import coupon.system.core.services.CustomerService;

@SpringBootApplication
public class LoginTest {

	public static void main(String[] args) throws CouponSystemException {
		ApplicationContext ctx = SpringApplication.run(AdminTest.class, args);
		
//		sRestartDatabase.restart(ctx);
		
		LoginManager loginManager = ctx.getBean(LoginManager.class);
		
		//admin test
		AdminService admin = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMIN);
		System.out.println("success");
	
		//company test
		Company COMPANY2 = new Company(0, "Amazon", "amazon@amazon.com", "amazon123");
		CompanyService company = (CompanyService) loginManager.login(COMPANY2.getEmail(), COMPANY2.getPassword(), ClientType.COMPANY);
		System.out.println(company.getCompanyId());

		//custmer test
		CustomerService customer = (CustomerService) loginManager.login("shiran@hassid.com", "sh123", ClientType.CUSTOMER);
		System.out.println(customer.getCustomerDetails());
		
		
		
	}
	
	

}
