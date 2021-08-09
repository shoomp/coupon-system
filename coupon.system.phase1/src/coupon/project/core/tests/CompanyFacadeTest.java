package coupon.project.core.tests;

import java.time.LocalDate;

import coupon.project.core.dbdao.CompaniesDBDAO;
import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.facade.CompanyFacade;
import coupon.project.core.types.Category;
import coupon.project.core.types.Company;
import coupon.project.core.types.Coupon;
import dataBase.mine.RestartSQL;

public class CompanyFacadeTest {

	CompanyFacade cF;

	public CompanyFacadeTest(CompanyFacade cF) {
		super();
		this.cF = cF;
	}

	public void testLogin() {
		System.out.println("======================test login METHOD: ===========================");
		System.out.print("test 1: (legit login) :");
		try {
			if (cF.login("apple@apple.com", "apple123")) {
				System.out.println("success");
			}
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 2 : (wrong password) :");
		try {
			if (cF.login("apple@gmail.com", "4321")) {
				System.out.println("test failed");
			} else {
				System.out.println("success");
			}
		} catch (Exception e) {
			System.out.println("success ... " + e.getCause());
		}

		System.out.print("test 3 : (trying after changed password) :");
		try {
			CompaniesDBDAO companies = new CompaniesDBDAO();
			Company c = new Company(3, "Apple", "apple@apple.com", "4321");
			companies.updateCompany(c);
			if (cF.login("apple@apple.com", "4321")) {
				System.out.println("success");
			}
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}
	}

	public void testAddCoupon() {
		System.out.println("======test for addCoupon METHOD ===============");
		System.out.print("test 1: (add legit Coupon)  :");
		try {
			cF.addCoupon(new Coupon(100, 1, Category.ELECTRICITY, "20% off Ipads", "get 20% discount on smartphones",
					LocalDate.parse("2021-01-01"), LocalDate.parse("2022-12-31"), 5, 100, "@@@"));
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}
		System.out.print("test 2: (add coupon with same Title for the same company) :");
		try {
			cF.addCoupon(new Coupon(100, 1, Category.FOOD, "20% off Ipads", "get 20% discountartphones",
					LocalDate.parse("2021-01-02"), LocalDate.parse("2022-11-30"), 4, 99, "@@@@"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
		System.out.print("test 3: (add coupon with endDate before Today) :");
		try {
			cF.addCoupon(new Coupon(100, 1, Category.FOOD, "20% off Ipadsss", "get 20% discountartphones",
					LocalDate.parse("2021-01-02"), LocalDate.parse("2021-06-01"), 4, 99, "@@@@"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
		System.out.print("test 4: (add coupon with endDate before startDate:");
		try {
			cF.addCoupon(new Coupon(100, 1, Category.FOOD, "20% off Ipadsss", "get 20% discountartphones",
					LocalDate.parse("2021-10-02"), LocalDate.parse("2021-09-01"), 4, 99, "@@@@"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
	}

	public void testUpdateCoupon() {
		System.out.println("=========test updateCoupon METHOD===========================");
		System.out.print("test1: (legitimate update) :");
		try {
			Coupon COUPON1 = new Coupon(5, 3, Category.FOOD, "20% off smartphonesss", "get 20% discount on smartphones",
					LocalDate.parse("2021-01-01"), LocalDate.parse("2022-12-31"), 3000, 100, "@@@");
			cF.updateCoupon(COUPON1);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + e.getCause() + "]");
			e.printStackTrace();
		}
		System.out.println("test2: (update coupon that doesnt belong to company) :");
		try {
			Coupon COUPON1 = new Coupon(1, 3, Category.FOOD, "20% off smartphonesss", "get 20% discount on smartphones",
					LocalDate.parse("2021-01-01"), LocalDate.parse("2022-12-31"), 3000, 100, "@@@");
			cF.updateCoupon(COUPON1);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
		System.out.print("test 3: (update title to already exist title on other coupon of the same company) :");
		try {
			Coupon COUPON1 = new Coupon(5, 3, Category.FOOD, "warranty", "get 20% discount on smartphones",
					LocalDate.parse("2021-01-01"), LocalDate.parse("2022-12-31"), 3000, 100, "@@@");
			cF.updateCoupon(COUPON1);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
		System.out.print("test 4: (update title to title exist in other company) :");
		try {
			Coupon COUPON1 = new Coupon(5, 3, Category.FOOD, "Trix", "get 20% discount on smartphones",
					LocalDate.parse("2021-01-01"), LocalDate.parse("2022-12-31"), 3000, 100, "@@@");
			cF.updateCoupon(COUPON1);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
			e.printStackTrace();
		}
		System.out.println("test 5: (update coupon end date before start date) :");
		try {
			Coupon COUPON1 = new Coupon(5, 3, Category.FOOD, "get 20% of smartphones ",
					"get 20% discount on smartphones", LocalDate.parse("2023-01-01"), LocalDate.parse("2022-12-31"),
					3000, 100, "@@@");
			cF.updateCoupon(COUPON1);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
	}

	public void testDeleteCoupon() {
		System.out.println("============test deleteCoupon METHOD====================");
		System.out.print("test 1: (legit delete) :");
		try {
			cF.deleteCoupon(5);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}
		System.out.print("test 2: (deleting coupon doesnt belong to this company) :");
		try {
			cF.deleteCoupon(7);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}
	}

	public void testGetAllCoupons() {
		System.out.println("============tes getAllCoupons METHOD=====================");
		System.out.print("test 1: (legit)   :");
		try {
			cF.getAllCompanyCoupons();
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}
		System.out.println("test 2: (if there is no coupons)");
		try {
			cF.deleteCoupon(5);
			cF.deleteCoupon(6);
		} catch (CouponSystemException e1) {
			e1.printStackTrace();
		}
		try {
			cF.getAllCompanyCoupons();
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
	}

	public void testGetAllCouponsByCategory() {
		System.out.println("=========test getAllCouponsByCategory====================");
		System.out.print("test1: (legit)        :");
		CompanyFacade cF2 = new CompanyFacade(2);
		try {
			if (cF2.getCouponByCategory(Category.ELECTRICITY).size() == 1) {
				System.out.println("success");
			} else {
				throw new CouponSystemException();
			}
		} catch (CouponSystemException e) {
			e.printStackTrace();
			System.out.println("test failed [" + e.getMessage() + "]");
		}
		System.out.print("test2: (suppose to be empty) :");
		try {
			cF2.deleteCoupon(4);
		} catch (Exception e) {
		}
		try {
			cF2.getCouponByCategory(Category.ELECTRICITY);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
	}

	public void testGetAllCouponsUpToPrice() {
		CompanyFacade cF4 = new CompanyFacade(4);
		System.out.println("============testing get all coupons by max price method===============");
		System.out.print("test1 : (legit) :");
		try {
			if (cF4.getCouponByPrice(11).size() == 1) {
				System.out.println("success");
			} else {
				throw new CouponSystemException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("test failed [" + e.getMessage() + "]");
		}
		System.out.print("test 2 : (null)   :");
		try {
			cF4.getCouponByPrice(1);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + e.getCause() + "]");
		}
		try {
			System.out.println(cF4.getCouponByPrice(400));
		} catch (CouponSystemException e1) {
			e1.printStackTrace();
		}
	}
		public void testGetCompanyDetails() {
		System.out.print("test getCompanyDetails METHOD:");
		CompanyFacade cF4 = new CompanyFacade(4);
		try {
			Company company = cF4.gatDetails();
			System.out.println("success" + company);
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

	}
}
