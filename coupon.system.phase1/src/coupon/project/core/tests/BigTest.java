package coupon.project.core.tests;

import coupon.project.core.facade.AdminFacade;
import coupon.project.core.facade.CompanyFacade;
import coupon.project.core.facade.CustomerFacade;
import coupon.project.core.types.Customer;
import dataBase.mine.RestartSQL;

public class BigTest {

	public static void main(String[] args) {

		RestartSQL.restartSQL();

		AdminFacade adfc = new AdminFacade();
		CompanyFacade comcf = new CompanyFacade();

		{// mute after checking once
			System.out.println(">>>>>>>>>>>>>> test AdminFacade >>>>>>>>>>");
			AdminFacadeTest admin = new AdminFacadeTest(adfc);
			admin.testLogin();
			{// test admin - company.
				admin.testAddCompany();
				admin.testUpdateCopany();
				admin.testDeleteCompany();
				admin.testGetOneCompany();
				admin.testGetAllCompanies();
			}
			{// test admin - customer
				admin.testAddNewCustomer();
				admin.testUpdateCustomer();
				admin.testDeleteCustomer();
				admin.testGetOneCustomer();
				admin.testGetAllCustomers();
			}
		}

		{// mute all this block after checking
			System.out.println(">>>>>>>>>>>>>> test AdminFacade >>>>>>>>>>");
			CompanyFacadeTest company = new CompanyFacadeTest(comcf);
			company.testLogin();
			company.testAddCoupon();
			company.testUpdateCoupon();
			company.testDeleteCoupon();
			company.testGetAllCoupons();
			company.testGetAllCouponsByCategory();
			company.testGetAllCouponsUpToPrice();
			company.testGetCompanyDetails();
		}
		{
			System.out.println(">>>>>>>>>>>>>> test CustomerFacade >>>>>>>>>>");
			CustomerFacadeTest customer = new CustomerFacadeTest();
			Customer c = new Customer(0, "Shiran", "Hassid", "Shiran@Hassid.com", "sh123");
			customer.testLogin(c);
			customer.testPurchaseCoupon();

			{// mute after checking once!!!.
//				customer.testGetAllCoupons();
			}

			customer.testGetAllCouponByCategory();
			customer.testGetAllCouponsOfCompanyUpToPrice();
			customer.testGetCustomerDetails();
		}
	}

}
