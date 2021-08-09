package coupon.system.core.login;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import coupon.system.core.services.AdminService;
import coupon.system.core.services.Client;
import coupon.system.core.services.CompanyService;
import coupon.system.core.services.CustomerService;

@Service
@Transactional
public class LoginManager {


	private Client client;
	@Autowired
	private ApplicationContext ctx;
	
	public Client login(String email, String password, ClientType type) {
		
		switch (type) {
		case ADMIN:
			client = ctx.getBean(AdminService.class);
			if(client.login(email, password)) {
				System.out.println("Admin login success");
				return client;
			}else {
				return null;
			}
			
//		case COMPANY:
//			client = ctx.getBean(CompanyService.class);
//			if(client.login(email, password)) {
//				System.out.println("Company login success");
//				return client;
//			}else {
//				System.out.println("this is null");
//				return null;
//			}
			
		case COMPANY:
			CompanyService company= ctx.getBean(CompanyService.class);
			if(company.login(email, password)) {
				System.out.println("Company login success");
				return company;
			}else {
				System.out.println("this is null");
				return null;
			}
		
		case CUSTOMER:		
			client = ctx.getBean(CustomerService.class);
			if(client.login(email, password)) {
				System.out.println("Customer login success");
				return client;
			}else {
				return null;
			}
		default:
			return null;
		}
	}
}
