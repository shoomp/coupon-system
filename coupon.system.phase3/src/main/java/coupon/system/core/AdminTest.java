package coupon.system.core;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import coupon.system.core.entities.Company;
import coupon.system.core.entities.Customer;
import coupon.system.core.exceptions.CouponSystemException;
import coupon.system.core.services.AdminService;

@SpringBootApplication
public class AdminTest {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AdminTest.class, args);
		
//		RestartDatabase.restart(ctx);
		
		AdminService admin = ctx.getBean(AdminService.class);
		Company COMPANY1 = new Company(1, "Google", "google@google.com", "google123");
		Company COMPANY2 = new Company(1, "Googlle", "google@google.com", "google123");

		testUpdateCompany(admin, COMPANY1, COMPANY2);
		testGetOneAndAllCompanies(admin);
		testDeleteCompany(admin);
		testUpdateCustomer(admin);
		testUpdateCustomer(admin);
		testGetOneAnsAllCustomer(admin);
		testDeleteCustomer(admin);
		
	}

	public static void testUpdateCompany(AdminService admin, Company COMPANY1, Company COMPANY2) {
		{// update company test
			System.out.println();
			System.out.println(">>>>>>>>>>>>>        update company test       >>>>>>>>>>>>>>");
			System.out.println();
			try {
				admin.updateCompany(COMPANY1);
				System.out.println();
				System.out.println("updated === success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("not updated === failed!!!");
				System.out.println();
				e.printStackTrace();
			}
			try {
				admin.updateCompany(COMPANY2);
				System.out.println();
				System.out.println("updated === failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("if not updated === success!!!    >>> " + e.getMessage());
				System.out.println();
			}
		}
	}

	public static void testDeleteCompany(AdminService admin) {
		{// delete company test
			System.out.println();
			System.out.println(">>>>>>>>>>>>>        delete company test       >>>>>>>>>>>>>>");
			System.out.println();
			try {
				admin.deleteCompany(1);
				System.out.println();
				System.out.println("deleted === success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("not deleted === failed!!!");
				System.out.println();
				e.printStackTrace();
			}
			try {
				admin.deleteCompany(8);
				System.out.println();
				System.out.println("deleted === failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("if not deleted === success!!!   >>>> " + e.getMessage());
				System.out.println();
			}
		}
	}

	public static void testGetOneAndAllCompanies(AdminService admin) {
		{// get all companies test
			System.out.println();
			System.out.println(">>>>>>>>>>>>>        get all companies test       >>>>>>>>>>>>>>");
			System.out.println();

			try {
				List<Company> companies = admin.getAllCompany();
				companies.forEach(System.out::println);
				System.out.println();
				System.out.println("get all companies === success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get all companies test=== failed!!!");
				System.out.println();
				e.printStackTrace();
			}

		}

		{// get 1 company test
			System.out.println();
			System.out.println(">>>>>>>>>>>>>        get 1 company test       >>>>>>>>>>>>>>");
			System.out.println();

			try {
				Company company = admin.getOneCompany(3);
				System.out.println(company);
				System.out.println();
				System.out.println("get 1 company === success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get 1 company test === failed!!!");
				System.out.println();
				e.printStackTrace();
			}

		}
	}

	public static void testUpdateCustomer(AdminService admin) {

		Customer CUSTOMER1 = new Customer(1, "Shirany", "Hassid", "Shiran@Hassid.com", "sh123");

		{// update customer test
			System.out.println();
			System.out.println(">>>>>>>>>>>>>        update customer test       >>>>>>>>>>>>>>");
			System.out.println();
			try {
				admin.updateCustomer(CUSTOMER1);
				System.out.println();
				System.out.println("updated === success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("not updated === failed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}

	}

	public static void testGetOneAnsAllCustomer(AdminService admin) {

		{// get all customers test
			System.out.println();
			System.out.println(">>>>>>>>>>>>>        get all customers test       >>>>>>>>>>>>>>");
			System.out.println();

			try {
				List<Customer> customers = admin.getAllCustomers();
				customers.forEach(System.out::println);
				System.out.println();
				System.out.println("get all customers === success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get all customers test === failed!!!");
				System.out.println();
				e.printStackTrace();
			}
		}

		{// get 1 customer test
			System.out.println();
			System.out.println(">>>>>>>>>>>>>        get 1 customer test       >>>>>>>>>>>>>>");
			System.out.println();

			try {
				Customer customer = admin.getOneCustomer(3);
				System.out.println(customer);
				System.out.println();
				System.out.println("get 1 customer === success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get 1 customer test === failed!!!");
				System.out.println();
				e.printStackTrace();
			}

			try {
				Customer customer = admin.getOneCustomer(15);
				System.out.println(customer);
				System.out.println();
				System.out.println("get 1 customer test  === failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("get 1 customer test === success!!!   >>> " + e.getMessage());
				System.out.println();
			}
		}
	}
	
	public static void testDeleteCustomer(AdminService admin) {
		{// delete company test
			System.out.println();
			System.out.println(">>>>>>>>>>>>>        delete customer test       >>>>>>>>>>>>>>");
			System.out.println();
			try {
				admin.deleteCustomer(1);
				System.out.println();
				System.out.println("deleted === success!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("not deleted === failed!!!");
				System.out.println();
				e.printStackTrace();
			}
			try {
				admin.deleteCustomer(8);
				System.out.println();
				System.out.println("deleted === failed!!!");
				System.out.println();
			} catch (CouponSystemException e) {
				System.out.println();
				System.out.println("if not deleted === success!!!   >>>> " + e.getMessage());
				System.out.println();
			}
		}
		
	}
}
