package coupon.system.core;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import coupon.system.core.entities.Category;
import coupon.system.core.entities.Company;
import coupon.system.core.entities.Coupon;
import coupon.system.core.exceptions.CouponSystemException;
import coupon.system.core.services.CompanyService;

@SpringBootApplication
public class CompanyTest {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CompanyTest.class, args);

		CompanyService company = ctx.getBean(CompanyService.class);
		company.login("amazon@amazon.com", "amazon123");
		testUpdate(company);
		testGetAll(company);
		testGetByCategory(company);
		testGetByPrice(company);
		testGetDetails(company);
		testDelete(company);
	}

	public static void testUpdate(CompanyService company) {

		{// update coupon
			System.out.println();
			System.out.println(">>>>>>>>>>>>>>>>>>>    update coupon test  >>>>>>>>>>>>>>>>");
			System.out.println();

			try {// suppose to success
				Coupon COUPON = new Coupon(3, new Company(2), Category.RESTURANTS, "gerber", "8 cups of gerber",
						LocalDate.parse("2021-07-01"), LocalDate.parse("2021-08-01"), 40, 15, "gerber.amazon");
				company.updateCoupon(COUPON);
				System.out.println();
				System.out.println("updated === test 1 success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("not updated === test 1 failed!!!");
				e.printStackTrace();
				System.out.println();
			}
			try { // suppose to fail = wrong company id
				Coupon COUPON = new Coupon(3, new Company(4), Category.RESTURANTS, "gerber", "8 cups of gerber",
						LocalDate.parse("2021-07-01"), LocalDate.parse("2021-08-01"), 40, 15, "gerber.amazon");
				company.updateCoupon(COUPON);
				System.out.println();
				System.out.println("updated === test 2 failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println(" not updated === test 2 success!!!    >>>> " + e.getCause());
				System.out.println();
			}
			try { // suppose to fail = coupon doesn't belong company id
				Coupon COUPON = new Coupon(2, new Company(1), Category.CAMPING, "camping chairs", "4 camping chairs",
						LocalDate.parse("2021-06-01"), LocalDate.parse("2022-06-01"), 15, 50, "chairs.google");
				company.updateCoupon(COUPON);
				System.out.println();
				System.out.println("updated === test 2 failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println(" not updated === test 2 success!!!    >>>> " + e.getCause());
				System.out.println();
			}
			try { // suppose to fail = title already exists in another coupon
				Coupon COUPON = new Coupon(3, new Company(2), Category.FOOD, "USB cable", "4 cups of gerber",
						LocalDate.parse("2021-07-01"), LocalDate.parse("2021-08-01"), 40, 15, "gerber.amazon");
				company.updateCoupon(COUPON);
				System.out.println();
				System.out.println("updated === test 3 failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println(" not updated === test 3 success!!!    >>>> " + e.getCause());
				System.out.println();
			}
			try {// suppose to fail = end date is before start date
				Coupon COUPON = new Coupon(3, new Company(2), Category.FOOD, "gerber", "4 cups of gerber",
						LocalDate.parse("2021-09-01"), LocalDate.parse("2021-08-01"), 40, 15, "gerber.amazon");
				company.updateCoupon(COUPON);
				System.out.println();
				System.out.println("updated === test 4 failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println(" not updated === test 4 success!!!    >>>> " + e.getCause());
				System.out.println();
			}
			try {// suppose to fail = end date had passed
				Coupon COUPON = new Coupon(3, new Company(2), Category.RESTURANTS, "gerber", "8 cups of gerber",
						LocalDate.parse("2021-07-01"), LocalDate.parse("2021-07-02"), 40, 15, "gerber.amazon");
				company.updateCoupon(COUPON);
				System.out.println();
				System.out.println("updated === test 5 failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println(" not updated === test 5 success!!!    >>>> " + e.getCause());
				System.out.println();
			}
		}
	}

	public static void testDelete(CompanyService company) {
		{// delete coupon
			System.out.println();
			System.out.println(">>>>>>>>>>>>>>>>>>>    delete coupon test  >>>>>>>>>>>>>>>>");
			System.out.println();

			try {// suppose to success
				company.deleteCoupon(3);
				System.out.println();
				System.out.println("deleted === test 1 success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("deleted === test 1 failed!!!");
				System.out.println();
				e.printStackTrace();
			}
			try {// suppose to fail = coupon doesn't exist
				company.deleteCoupon(12);
				System.out.println();
				System.out.println("deleted === test 2 failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("deleted === test 2 success!!!   >>>  " + e.getCause());
				System.out.println();
			}
		}
	}

	public static void testGetAll(CompanyService company) {
		{// get all company coupons
			System.out.println();
			System.out.println(">>>>>>>>>>>>>>>>>>>    get all company coupons test  >>>>>>>>>>>>>>>>");
			System.out.println();
			try {
				List<Coupon> coupons = company.getAllCompanyCoupons();
				coupons.forEach(System.out::println);
				System.out.println();
				System.out.println("get all company coupons === test success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get all company coupons === test failed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}
	}

	public static void testGetByCategory(CompanyService company) {
		{// get all coupon by category
			System.out.println();
			System.out.println(">>>>>>>>>>>>>>>>>>>    get all company coupons by category test  >>>>>>>>>>>>>>>>");
			System.out.println();

			try {
				List<Coupon> coupons = company.getCouponByCategory(Category.ELECTRICITY);
				coupons.forEach(System.out::println);
				System.out.println();
				System.out.println("get all company coupons by category === test success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get all company coupons by category === test failed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}
	}

	public static void testGetByPrice(CompanyService company) {
		{// get all coupon up to price
			System.out.println();
			System.out.println(">>>>>>>>>>>>>>>>>>>    get all company coupons up to price test  >>>>>>>>>>>>>>>>");
			System.out.println();

			try {
				List<Coupon> coupons = company.getCouponByPrice(12.0);
				coupons.forEach(System.out::println);
				System.out.println();
				System.out.println("get all company coupons up to price === test success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get all company coupons up to price === test failed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}
	}

	public static void testGetDetails(CompanyService company) {
		{// get details
			System.out.println();
			System.out.println(">>>>>>>>>>>>>>>>>>>    get company details test  >>>>>>>>>>>>>>>>");
			System.out.println();

			try {
				Company c = company.getDetails();
				System.out.println(c);
				System.out.println();
				System.out.println("get company details === test success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get company details === test failed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}
	}
}