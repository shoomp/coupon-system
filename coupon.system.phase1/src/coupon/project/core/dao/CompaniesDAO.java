package coupon.project.core.dao;

import java.util.List;

import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.types.Company;

/**
 * @author מוריה הדאיה
 *
 */
public interface CompaniesDAO {

	/**
	 * Check if company already exists in DataBase.
	 * 
	 * @param email    - the Email to be checked by.
	 * @param password - the Password to be checked by.
	 * @return true if company exists, false if company does not exists.
	 * @throws CouponSystemException - if checking company in DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public boolean isCompanyExists(String email, String password) throws CouponSystemException;

	/**
	 * Add a new company to DataBase.
	 * 
	 * @param company - to be added.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if adding company to DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public int addCompany(Company company) throws CouponSystemException;

	/**
	 * Update an exists company in DataBase.
	 * 
	 * @param company - to be updated.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if updating company in DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public int updateCompany(Company company) throws CouponSystemException;

	/**
	 * Delete an exists company in DataBase.
	 * 
	 * @param companyID - company ID to delete the company by.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if deleting company from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public int deleteCompany(int companyID) throws CouponSystemException;

	/**
	 * Get a list of companies from DataBase.
	 * 
	 * @return List - list of company.
	 * @throws CouponSystemException - if getting companies from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public List<Company> geAllCompanies() throws CouponSystemException;

	/**
	 * Get one company from DataBase.
	 * 
	 * @param companyID - company ID to find the correct company by.
	 * @return Company
	 * @throws CouponSystemException - if getting one company DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public Company getOneCompany(int companyID) throws CouponSystemException;

	/**
	 * Check if company already exists in DataBase by 'name' and 'email'.
	 * 
	 * @param name - of company to be checked by.
	 * @return true if there's a company with the same name, false if not.
	 * @throws CouponSystemException - if checking company in DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public boolean isCompanyExistsByName(String name) throws CouponSystemException;

	/**
	 * Check if company already exists in DataBase by 'name' and 'email'.
	 * 
	 * @param email - of company to be checked by.
	 * @return true if there's a company with the same email, false if not.
	 * @throws CouponSystemException - if checking company in DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public boolean isCompanyExistsByEmail(String email) throws CouponSystemException;

	/**
	 * Delete all coupons purchased belong to specific company.
	 * 
	 * @param companyID - of company to delete according to.
	 * @throws CouponSystemException - if delete coupons from DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public void deleteAllCompanyCoupons(int companyID) throws CouponSystemException;

	/**
	 * Delete all coupons purchased belong to specific company.
	 * 
	 * @param companyID - of company to delete according to.
	 * @throws CouponSystemException - if delete purchase from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public void deleteAllCompanyCouponsPurchased(int companyID) throws CouponSystemException;

	/**
	 * Check if company already exists in DataBase and get the company if
	 * exists.
	 * 
	 * @param id  of company to be checked by.
	 * @param con - to connect to DataBase
	 * @return Result set
	 * @throws CouponSystemException - if checking company in DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public boolean isCompanyExistsByID(int id) throws CouponSystemException;

	/**
	 * Get company id by email.
	 * 
	 * @param email - to check by.
	 * @return id - the company id.
	 * @throws CouponSystemException
	 */
	public int getCompanyId(String email) throws CouponSystemException;
}
