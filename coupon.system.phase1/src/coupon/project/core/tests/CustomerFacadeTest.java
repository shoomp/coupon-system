package coupon.project.core.tests;

import java.time.LocalDate;
import java.util.List;

import coupon.project.core.dbdao.CouponsDBDAO;
import coupon.project.core.dbdao.CustomersDBDAO;
import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.facade.CustomerFacade;
import coupon.project.core.types.Category;
import coupon.project.core.types.Coupon;
import coupon.project.core.types.Customer;

public class CustomerFacadeTest {

	CustomersDBDAO customersDBDao = new CustomersDBDAO();
	CustomerFacade customerFacade1 = new CustomerFacade();

	public void testLogin(Customer c) {
		System.out.print("testing login METHOD: ");
		try {
			if (customerFacade1.login(c.getEmail(), c.getPassword())) {
				System.out.println("success");
			} else {
				System.out.println("test failed");
			}
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	public void testPurchaseCoupon() {
		System.out.println("===============testing Purchase Coupon Method===============");
		System.out.print("test1: (legit)   :");
		CouponsDBDAO couponsDB_dao = new CouponsDBDAO();
		try {
			Coupon c1 = couponsDB_dao.getOneCoupon(1);
			customerFacade1.purchaseCoupon(c1);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("test failed []");
		}
		System.out.print("test2: (coupon already purchased) :");
		try {
			Coupon c3 = new Coupon(3);
			customerFacade1.purchaseCoupon(c3);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
		System.out.print("test3: (coupon is not exist) : ");
		try {
			Coupon c30 = new Coupon(30);
			customerFacade1.purchaseCoupon(c30);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
		System.out.print("test4: (coupon is out of amount) :");
		try {
			couponsDB_dao.updateCoupon(
					new Coupon(24, 9, Category.ELECTRICITY, "20% off smartphones", "get 20% discount on smartphones",
							LocalDate.parse("2021-01-01"), LocalDate.parse("2022-12-31"), 0, 74.76, "@@@"));
			customerFacade1.purchaseCoupon(
					new Coupon(24, 4, Category.ELECTRICITY, "20% off computers", "get 20% discount on computers",
							LocalDate.parse("2021-01-01"), LocalDate.parse("2022-12-31"), 5, 79.9, "@@@"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
		System.out.print("test5: (Coupon start date has not yet come) :");
		try {
			Coupon c0 = new Coupon(2, 1, Category.CAMPING, "camping chairs", "4 camping chairs",
					LocalDate.parse("2021-06-01"), LocalDate.parse("2022-06-01"), 0, 50, "chairs.google");
			couponsDB_dao.updateCoupon(c0);
			customerFacade1.purchaseCoupon(c0);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}

		System.out.print("test6: (Coupon expiration date has passed) :");
		try {
			Coupon cPass = new Coupon(1, 1, Category.TRAVEL, "flight", "50$ discount", LocalDate.parse("2021-06-01"),
					LocalDate.parse("2021-07-01"), 10, 25, "flight.google");
			couponsDB_dao.updateCoupon(cPass);
			customerFacade1.purchaseCoupon(cPass);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
	}

	public void testGetAllCoupons() {
		System.out.println("========test getAllCoupons METHOD==============");
		System.out.print("test1: (legit) :");
		try {
			List<Coupon> coupons = customerFacade1.getCustomerCoupons();
//	            List<Coupon> coupons1 = cF2.getAllCoupons();
//	            System.out.println(coupons1);
			if (coupons.size() == 4) {
				System.out.println("success");
			} else {
				throw new CouponSystemException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("test failed [" + e.getMessage() + "]");
		}
		System.out.print("test2: (not any coupons) :");
		try {
			CouponsDBDAO couponsDB_dao = new CouponsDBDAO();
			couponsDB_dao.deleteCouponPurchase(1, 1);
			couponsDB_dao.deleteCouponPurchase(1, 3);
			couponsDB_dao.deleteCouponPurchase(1, 5);
			couponsDB_dao.deleteCouponPurchase(1, 7);
			customerFacade1.getCustomerCoupons();
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + " - " + e.getCause() + " ]");
		}
	}

	public void testGetAllCouponByCategory() {
		System.out.println("========test getAllCouponsByCategory METHOD=========");
		System.out.print("test1: (legit) :");
		try {
			List<Coupon> coupons = customerFacade1.getCustomerCouponsByCategory(Category.FOOD);
			if (coupons.size() > 0) {
				System.out.println("success");
			} else {
				throw new CouponSystemException();
			}
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + " - " + e.getCause() + "]");
		}
		System.out.print("test2: (not any) :");
		try {
			List<Coupon> coupons = customerFacade1.getCustomerCouponsByCategory(Category.RESTURANTS);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + " - " + e.getCause() + "]");
		}
	}

	public void testGetAllCouponsOfCompanyUpToPrice() {
		System.out.println("===========test getAllCouponsOfCustomeryByMaxPrice METHOD =======");
		System.out.print("test1 : (legit) :");
		try {
			List<Coupon> coupons = customerFacade1.getCustomerCouponByPrice(20);
			if (coupons.size() == 3) {
				System.out.println("success");
			} else {
				throw new CouponSystemException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("test failed [" + e.getMessage() + " - " + e.getCause() + "]");
		}

		System.out.print("test2 : (not any coupons) :");
		try {
			List<Coupon> coupons = customerFacade1.getCustomerCouponByPrice(1);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + " - " + e.getCause() + "]");
		}
	}

	public void testGetCustomerDetails() {
		System.out.print("test getOneCustomer METHOD: ");
		try {
			System.out.println(customerFacade1.getCustomerDetails());
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

	}

}
