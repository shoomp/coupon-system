package coupon.system.core;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import coupon.system.core.entities.Category;
import coupon.system.core.entities.Company;
import coupon.system.core.entities.Coupon;
import coupon.system.core.entities.Customer;
import coupon.system.core.exceptions.CouponSystemException;
import coupon.system.core.services.AdminService;
import coupon.system.core.services.CompanyService;
import coupon.system.core.services.CustomerService;

@Configuration
public class Config {
	
	@Bean
	public CommandLineRunner restore(ApplicationContext ctx) {
		CommandLineRunner runner = new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {

				AdminService admin = ctx.getBean(AdminService.class);

				Company COMPANY1 = new Company(0, "Google", "google@google.com", "google123");
				Company COMPANY2 = new Company(0, "Amazon", "amazon@amazon.com", "amazon123");
				Company COMPANY3 = new Company(0, "Apple", "apple@apple.com", "apple123");
				Company COMPANY4 = new Company(0, "Nestle", "nestle@nestle.com", "nestle123");
				Company COMPANY5 = new Company(0, "LG", "lg@lg.com", "lg123");

				try {
					admin.addNewCompany(COMPANY1);
					admin.addNewCompany(COMPANY2);
					admin.addNewCompany(COMPANY3);
					admin.addNewCompany(COMPANY4);
					admin.addNewCompany(COMPANY5);
				} catch (CouponSystemException e) {
					e.printStackTrace();
				}

				CompanyService company1 = ctx.getBean(CompanyService.class);
				CompanyService company2 = ctx.getBean(CompanyService.class);
				CompanyService company3 = ctx.getBean(CompanyService.class);
				CompanyService company4 = ctx.getBean(CompanyService.class);
				CompanyService company5 = ctx.getBean(CompanyService.class);

				company1.login(COMPANY1.getEmail(), COMPANY1.getPassword());
				company2.login(COMPANY2.getEmail(), COMPANY2.getPassword());
				company3.login(COMPANY3.getEmail(), COMPANY3.getPassword());
				company4.login(COMPANY4.getEmail(), COMPANY4.getPassword());
				company5.login(COMPANY5.getEmail(), COMPANY5.getPassword());

				Customer CUSTOMER1 = new Customer(0, "Shiran", "Hassid", "Shiran@Hassid.com", "sh123");
				Customer CUSTOMER2 = new Customer(0, "Elior", "Hassid", "Elior@Hassid.com", "eh123");
				Customer CUSTOMER3 = new Customer(0, "Moriya", "Hadaya", "Moriya@Hadaya.com", "mh123");
				Customer CUSTOMER4 = new Customer(0, "Efrat", "Elias", "Efrat@Elias.com", "ee123");
				Customer CUSTOMER5 = new Customer(0, "Daniel", "Elias", "Daniel@Elias.com", "de123");

				try {
					admin.addCustomer(CUSTOMER1);
					admin.addCustomer(CUSTOMER2);
					admin.addCustomer(CUSTOMER3);
					admin.addCustomer(CUSTOMER4);
					admin.addCustomer(CUSTOMER5);
				} catch (CouponSystemException e) {
					e.printStackTrace();
				}

				CustomerService customer1 = ctx.getBean(CustomerService.class);
				CustomerService customer2 = ctx.getBean(CustomerService.class);
				CustomerService customer3 = ctx.getBean(CustomerService.class);
				CustomerService customer4 = ctx.getBean(CustomerService.class);
				CustomerService customer5 = ctx.getBean(CustomerService.class);

				customer1.login(CUSTOMER1.getEmail(), CUSTOMER1.getPassword());
				customer2.login(CUSTOMER2.getEmail(), CUSTOMER2.getPassword());
				customer3.login(CUSTOMER3.getEmail(), CUSTOMER3.getPassword());
				customer4.login(CUSTOMER4.getEmail(), CUSTOMER4.getPassword());
				customer5.login(CUSTOMER5.getEmail(), CUSTOMER5.getPassword());

				Coupon COUPON1 = new Coupon(1, COMPANY1, Category.TRAVEL, "flight", "50$ discount",
						LocalDate.parse("2021-07-01"), LocalDate.parse("2022-07-01"), 10, 25, "flight.google");
				Coupon COUPON2 = new Coupon(2, COMPANY1, Category.CAMPING, "camping chairs", "4 camping chairs",
						LocalDate.parse("2021-06-01"), LocalDate.parse("2022-06-01"), 15, 50, "chairs.google");
				Coupon COUPON3 = new Coupon(3, COMPANY2, Category.FOOD, "gerber", "4 cups of gerber",
						LocalDate.parse("2021-07-01"), LocalDate.parse("2021-08-01"), 40, 15, "gerber.amazon");
				Coupon COUPON4 = new Coupon(4, COMPANY2, Category.ELECTRICITY, "USB cable", "USB cable type b or c",
						LocalDate.parse("2021-06-10"), LocalDate.parse("2021-09-10"), 50, 10, "cable.amazon");
				Coupon COUPON5 = new Coupon(5, COMPANY3, Category.ELECTRICITY, "color uprade", "upgrade to limit colors",
						LocalDate.parse("2021-05-15"), LocalDate.parse("2021-08-15"), 20, 15, "color.apple");
				Coupon COUPON6 = new Coupon(6, COMPANY3, Category.ELECTRICITY, "warranty", "6 months more of warranty",
						LocalDate.parse("2021-01-01"), LocalDate.parse("2021-10-01"), 50, 20, "warranty.apple");
				Coupon COUPON7 = new Coupon(7, COMPANY4, Category.FOOD, "Trix", "0 box of Trix", LocalDate.parse("2021-01-01"),
						LocalDate.parse("2022-01-01"), 100, 10, "trix.nestle");
				Coupon COUPON8 = new Coupon(8, COMPANY4, Category.FOOD, "Cheerios", "0 box of Cheerios",
						LocalDate.parse("2021-06-01"), LocalDate.parse("2022-06-01"), 100, 15, "cheerios.nestle");
				Coupon COUPON9 = new Coupon(9, COMPANY5, Category.ELECTRICITY, "100$ discount",
						"100$ discount only in our website", LocalDate.parse("2021-07-01"), LocalDate.parse("2021-09-01"), 15,
						75, "discount.lg");
				Coupon COUPON10 = new Coupon(10, COMPANY5, Category.ELECTRICITY, "gift", "surprise gift",
						LocalDate.parse("2021-01-01"), LocalDate.parse("2021-06-01"), 20, 15, "gift.lg");

				try {
					company1.addCoupon(COUPON1);
					company1.addCoupon(COUPON2);
					company2.addCoupon(COUPON3);
					company2.addCoupon(COUPON4);
					company3.addCoupon(COUPON5);
					company3.addCoupon(COUPON6);
					company4.addCoupon(COUPON7);
					company4.addCoupon(COUPON8);
					company5.addCoupon(COUPON9);
					company5.addCoupon(COUPON10);
				} catch (CouponSystemException e) {
					e.printStackTrace();
				}

				try {
					customer1.purchaseCoupon(COUPON3);
					customer1.purchaseCoupon(COUPON5);
					customer1.purchaseCoupon(COUPON7);
					customer2.purchaseCoupon(COUPON1);
					customer2.purchaseCoupon(COUPON6);
					customer3.purchaseCoupon(COUPON7);
					customer3.purchaseCoupon(COUPON8);
					customer3.purchaseCoupon(COUPON10);
					customer4.purchaseCoupon(COUPON2);
					customer4.purchaseCoupon(COUPON4);
					customer4.purchaseCoupon(COUPON9);
					customer5.purchaseCoupon(COUPON10);
					customer5.purchaseCoupon(COUPON5);
					customer5.purchaseCoupon(COUPON6);
					customer5.purchaseCoupon(COUPON7);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		return runner;
	}

}
