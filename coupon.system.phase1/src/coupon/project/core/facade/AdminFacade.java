package coupon.project.core.facade;

import java.util.List;

import coupon.project.core.dbdao.CompaniesDBDAO;
import coupon.project.core.dbdao.CustomersDBDAO;
import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.types.Company;
import coupon.project.core.types.Customer;

/**
 * @author מוריה הדאיה
 *
 */
public class AdminFacade extends ClientFacade {

	public AdminFacade() {
		this.companiesDAO = new CompaniesDBDAO();
		this.customersDAO = new CustomersDBDAO();
	}

	@Override
	public boolean login(String email, String password) {
		boolean access = (email.equals("admin@admin.com") && password.equals("admin"));
		return access;
	}

	/**
	 * Adding new company.
	 * 
	 * @param company - to be added.
	 * @throws CouponSystemException - if the name or email are already exist, or if
	 *                               adding company failed caused by SQL Exception
	 *                               or DataBase access is error or Error filling in
	 *                               fields.
	 */
	public void addNewCompany(Company company) throws CouponSystemException {
		boolean bName = this.companiesDAO.isCompanyExistsByName(company.getName());
		boolean bEmail = this.companiesDAO.isCompanyExistsByEmail(company.getEmail());
		if (bName && bEmail) {
			throw new CouponSystemException("Company name & Email already exists");
		} else if (bEmail) {
			throw new CouponSystemException("Email already exists");
		} else if (bName) {
			throw new CouponSystemException("Company name already exists");
		}
		companiesDAO.addCompany(company);
	}

	/**
	 * Updating an exists company.
	 * 
	 * @param company - to be updated according to the current fields.
	 * @throws CouponSystemException - if the name of the company was changed, or if
	 *                               updating company failed caused by SQL Exception
	 *                               or DataBase access is error or Error filling in
	 *                               fields.
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		Company company2 = getOneCompany(company.getId());
		if (company2.getName().equals(company.getName())) {
			companiesDAO.updateCompany(company);
		} else {
			throw new CouponSystemException("company name can't be changed");
		}

	}

	/**
	 * Deleting an exists company.
	 * 
	 * @param companyId - of the selected company to delete by.
	 * @throws CouponSystemException - if there's no company with the selected ID,
	 *                               or if deleting company failed caused by SQL
	 *                               Exception or DataBase access is Error.
	 */
	public void deleteCompany(int companyId) throws CouponSystemException {
		if (companiesDAO.isCompanyExistsByID(companyId)) {
			companiesDAO.deleteAllCompanyCouponsPurchased(companyId);
			companiesDAO.deleteAllCompanyCoupons(companyId);
			companiesDAO.deleteCompany(companyId);
		} else {
			throw new CouponSystemException("company does not exist");
		}
	}

	/**
	 * Getting a list of all companies exist in DataBase.
	 * 
	 * @return List - a list of companies.
	 * @throws CouponSystemException - if there's no company in DataBase, or if
	 *                               getting companies failed caused by SQL
	 *                               Exception or DataBase access is Error.
	 */
	public List<Company> getAllCompany() throws CouponSystemException {
		try {
			List<Company> companies = companiesDAO.geAllCompanies();
			if (companies.size() > 0) {
				return companies;
			} else {
				throw new CouponSystemException("there's no any company found");
			}
		} catch (Exception e) {
			throw new CouponSystemException("getting companies failed ", e);
		}
	}

	/**
	 * Getting one selected company.
	 * 
	 * @param CompanyID - of the selected company to get by.
	 * @return Company
	 * @throws CouponSystemException - if there's no company with the selected ID,
	 *                               or if setting one company failed caused by SQL
	 *                               Exception or DataBase access is Error.
	 */
	public Company getOneCompany(int CompanyID) throws CouponSystemException {
		try {
			Company company = companiesDAO.getOneCompany(CompanyID);
			return company;
		} catch (Exception e) {
			throw new CouponSystemException("getting company failed ", e);
		}
	}

	/**
	 * Adding new customer.
	 * 
	 * @param cutomer - to be added.
	 * @throws CouponSystemException - if the email are already exist, or if adding
	 *                               customer failed caused by SQL Exception or
	 *                               DataBase access is error or Error filling in
	 *                               fields.
	 */
	public void addCustomer(Customer customer) throws CouponSystemException {
		try {
			customersDAO.addCustomer(customer);
		} catch (Exception e) {
			throw new CouponSystemException("adding customer failed", e);
		}
	}

	/**
	 * Updating an exists customer.
	 * 
	 * @param customer - to be updated according to the current fields.
	 * @throws CouponSystemException - if ID of company was changed, or if updating
	 *                               company failed caused by SQL Exception or
	 *                               DataBase access is error or Error filling in
	 *                               fields.
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		if (customersDAO.isCustomerExistsByID(customer.getId())) {
			customersDAO.updateCustomer(customer);
		} else {
			throw new CouponSystemException("customer does not exist");
		}
	}

	/**
	 * Deleting an exists customer.
	 * 
	 * @param customerId - of the selected customer to delete by.
	 * @throws CouponSystemException - if there's no customer with the selected ID,
	 *                               or if deleting customer failed caused by SQL
	 *                               Exception or DataBase access is Error.
	 */
	public void deleteCustomer(int customerId) throws CouponSystemException {
		if (customersDAO.isCustomerExistsByID(customerId)) {
			customersDAO.deleteAllCustomerCouponsPurchased(customerId);
			customersDAO.deleteCustomer(customerId);
		} else {
			throw new CouponSystemException("customer does not exist");
		}
	}

	/**
	 * Getting a list of all customers exist in DataBase.
	 * 
	 * @return List - a list of customers.
	 * @throws CouponSystemException - if there's no customer in DataBase, or if
	 *                               getting customers failed caused by SQL
	 *                               Exception or DataBase access is Error.
	 */
	public List<Customer> getAllCustomers() throws CouponSystemException {
		try {
			List<Customer> customers = customersDAO.getAllCustomers();
			if (customers.size() > 0) {
				return customers;
			} else {
				throw new CouponSystemException("there's no any customer found");
			}
		} catch (Exception e) {
			throw new CouponSystemException("gettng customers failed ", e);
		}

	}

	/**
	 * Getting one selected customer.
	 * 
	 * @param customerID - of the selected company to get by.
	 * @return Customer
	 * @throws CouponSystemException - if there's no customer with the selected ID,
	 *                               or if setting one customer failed caused by SQL
	 *                               Exception or DataBase access is Error.
	 */
	public Customer getOneCustomer(int CustomerID) throws CouponSystemException {
		try {
			Customer customer = customersDAO.getOneCustomer(CustomerID);
			return customer;
		} catch (Exception e) {
			throw new CouponSystemException("getting customer failed ", e);
		}
	}

}
