package coupon.system.core;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import coupon.system.core.entities.Category;
import coupon.system.core.entities.Coupon;
import coupon.system.core.entities.Customer;
import coupon.system.core.exceptions.CouponSystemException;
import coupon.system.core.services.CustomerService;

@SpringBootApplication
public class CustomerTest {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CustomerTest.class, args);

//		RestartDatabase.restart(ctx);
		
		CustomerService customer = ctx.getBean(CustomerService.class);
		customer.login("Efrat@Elias.com", "ee123");
		testGetCoupon(customer);
		testGetCoupon(customer);
		testGetByCategory(customer);
		testGetByPrice(customer);
		testGetDetails(customer);
	}

	public static void testGetCoupon(CustomerService customer) {
		{// get all customer coupons
			System.out.println();
			System.out.println(">>>>>>>>>>>>>       get all customer coupons test       >>>>>>>>>>>>>>");
			System.out.println();
			try {
				List<Coupon> coupons = customer.getCustomerCoupons();
				System.out.println();
				coupons.forEach(System.out::println);
				System.out.println();
				System.out.println("get all customer coupons  === test success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get all customer coupons  === test filed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}
	}

	public static void testGetByCategory(CustomerService customer) {
		{// get all customer coupons by category
			System.out.println();
			System.out.println(">>>>>>>>>>>>>       get all customer coupons by category test       >>>>>>>>>>>>>>");
			System.out.println();
			try {
				List<Coupon> coupons = customer.getCustomerCouponsByCategory(Category.ELECTRICITY);
				System.out.println();
				coupons.forEach(System.out::println);
				System.out.println();
				System.out.println("get all customer coupons by category === test success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get all customer coupons by category === test filed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}
	}

	public static void testGetByPrice(CustomerService customer) {
		{// get all customer coupons up to price
			System.out.println();
			System.out.println(">>>>>>>>>>>>>       get all customer coupons up to price test       >>>>>>>>>>>>>>");
			System.out.println();
			try {
				List<Coupon> coupons = customer.getCustomerCouponByPrice(50.0);
				System.out.println();
				coupons.forEach(System.out::println);
				System.out.println();
				System.out.println("get all customer coupons up to price === test success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get all customer couponsup to price === test filed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}
	}

	public static void testGetDetails(CustomerService customer) {
		{// get customer details
			System.out.println();
			System.out.println(">>>>>>>>>>>>>       get customer details test       >>>>>>>>>>>>>>");
			System.out.println();
			try {
				Customer c = customer.getCustomerDetails();
				System.out.println();
				System.out.println(c);
				System.out.println();
				System.out.println("get customer details === test success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get customer details === test filed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}
	}
}
