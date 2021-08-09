package coupon.project.core.tests;

import java.util.Arrays;

import coupon.project.core.facade.AdminFacade;
import coupon.project.core.types.Company;
import coupon.project.core.types.Customer;

public class AdminFacadeTest {

	private AdminFacade aF;
	
	public AdminFacadeTest(AdminFacade aF) {
		super();
		this.aF = aF;
	}

	public void testLogin() {

		System.out.println();
		System.out.print("testing login METHOD: ");
		try {
			aF.login("admin@admin.com", "admin");
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}
		System.out.println();
	}
	
	public void testAddCompany() {
		System.out.println();
		System.out.println("==========testing addNewCompany METHOD: ==========");
		System.out.print("test 1: (adding legit company) :");
		try {
			aF.addNewCompany(new Company(11, "TheNorthFace", "thenorthface@gmail.com", "1234"));
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 2: (adding company with the same name) :");
		try {
			aF.addNewCompany(new Company(12, "TheNorthFace", "123@gmail.com", "1234"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}

		System.out.print("test 3: (adding company with the same Email) :");
		try {
			aF.addNewCompany(new Company(13, "TheNorthFacee", "thenorthface@gmail.com", "1234"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}

		System.out.print("test 4: (adding company with the same Email and the same Name): ");
		try {
			aF.addNewCompany(new Company(14, "TheNorthFace", "thenorthface@gmail.com", "1234"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println( "success [" + e.getMessage() + "]");
		}
	}
	public void testUpdateCopany() {
		System.out.println("=============test update company METHOD:  ================");
		System.out.print("test 1: (updating legit) :");
		try {
			aF.updateCompany(new Company(3, "Apple", "newemailforApple@gmail.com", "909090"));
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 2: (updating by changing name):");
		try {
			aF.updateCompany(new Company(3, "Applee", "newemailforApple@gmail.com", "909090"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}

		System.out.print("test 3: (updating with no valid ID):");
		try {
			aF.updateCompany(new Company(14, "Appleee", "newemailforApple1@gmail.com", "909090"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}
		System.out.println();
	}
	
	public void testDeleteCompany() {
		System.out.println();
		System.out.println("==========test deleteCompany METHOD============");
		System.out.print("test 1: (deleting legit) :");
		try {
			aF.deleteCompany(2);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 2: (deleting legit) :");
		try {
			aF.deleteCompany(3);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 3: (deleting legit) :");
		try {
			aF.deleteCompany(4);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + Arrays.toString(e.getStackTrace()) + "]");
		}

		System.out.println("test 4 (deleting not legit) :");
		try {
			aF.deleteCompany(4);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}
	}
	
	public void testGetOneCompany() {
		System.out.print("test getOneCompany Method: ");
		try {
			aF.getOneCompany(5);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + Arrays.toString(e.getStackTrace()) + "]");
		}
		System.out.println();
	}
	
	public void testGetAllCompanies() {

		System.out.println();
		System.out.println("====================test getAllCompanies METHOD: ============================");
		System.out.print("test 1: (get all companies when there is companies)   :");
		try {
			aF.getAllCompany();
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 2: (get all companies when there isn't any companies) : ");
		for (int i = 0; i <= 20; i++) {
			try {
				aF.deleteCompany(i);
			} catch (Exception e) {
			}
		}
		try {
			System.out.println(aF.getAllCompany());
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}

		System.out.println();
	}
	
	public void testAddNewCustomer() {
		System.out.println();
		System.out.println("================test addNewClient Method : ===============");
		System.out.print("test 1: (adding a legit customer) :");
		try {
			aF.addCustomer(new Customer(11, "Yael", "Hassid", "yael@hassid.com", "yh123"));
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.println("test 2: (creating customer with the same Email) :");
		try {
			aF.addCustomer(new Customer(12, "Yaely", "chassid", "yael@hassid.com", "yh1234"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}
		System.out.println();
	}
	
	public void testUpdateCustomer() {
		System.out.println();
		System.out.println("==================testing updateCustomerMethod=================");
		System.out.print("test 1: (updating legit) :");
		try {
			aF.updateCustomer(new Customer(1, "Avrahamm", "Cohenn", "avrahamcohenn@gmail.com", "12345"));
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}
		System.out.print("test 2: (updating not legit) :");
		try {
			aF.updateCustomer(new Customer(35, "Avrahamm", "Cohenn", "avrahamcohenn@gmail.com", "12345"));
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}
		System.out.println();
	}
	
	public void testDeleteCustomer() {
		System.out.println();
		System.out.println("===========test deleteCustomer METHOD===================");

		System.out.print("test 1: (delete legit) :");
		try {
			aF.deleteCustomer(1);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 2: (not legit delete) :");
		try {
			aF.deleteCustomer(35);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}
		System.out.println();
	}
	public void testGetOneCustomer() {
		System.out.println();
		System.out.println("=============test getOneCustomer METHOD=====================");
		System.out.print("test 1 : (get one customer legit) :");
		try {
			aF.getOneCustomer(4);
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 2 : (get one customer that doesnt exist) :");
		try {
			aF.getOneCustomer(30);
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}
		System.out.println();
	}
	
	public void testGetAllCustomers() {
		System.out.println();
		System.out.println("======test getAllCustomers METHOD========================");
		System.out.print("test 1 (get all customers legit) :");
		try {
			aF.getAllCustomers();
			System.out.println("success");
		} catch (Exception e) {
			System.out.println("test failed [" + e.getMessage() + "]");
		}

		System.out.print("test 2: (get all customers when there aren't any customers) : ");
		for (int i = 0; i <= 12; i++) {
			try {
				aF.deleteCustomer(i);
			} catch (Exception e) {
			}
		}
		try {
			System.out.println(aF.getAllCustomers());
			System.out.println("test failed");
		} catch (Exception e) {
			System.out.println("success [" + e.getMessage() + "]");
		}
		System.out.println();
	}
}
