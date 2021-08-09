package coupon.project.core.dao;

import java.util.List;

import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.types.Category;
import coupon.project.core.types.Coupon;
import coupon.project.core.types.Customer;

/**
 * @author מוריה הדאיה
 *
 */
public interface CustomersDAO {

	/**
	 * Check if customer already exists in DataBase.
	 * 
	 * @param email    - the Email to be checked by
	 * @param password - the Password to be checked by
	 * @return true if customer exists, false if customer does not exist.
	 * @throws CouponSystemException- if checking customer in DataBase failed caused
	 *                                by SQL Exception or DataBase access is error
	 *                                or Error filling in fields.
	 */
	public boolean isCustomerExists(String email, String password) throws CouponSystemException;

	/**
	 * Add a new customer to DataBase.
	 * 
	 * @param customer - to be added.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if adding customer to DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public int addCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Update an exists customer in DataBase.
	 * 
	 * @param customer - to be updated.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if updating customer in DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public int updateCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Delete a customer from DataBase.
	 * 
	 * @param customerID - of customer to be deleted by.
	 * @throws CouponSystemException - if deleting customer from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public void deleteCustomer(int customerID) throws CouponSystemException;

	/**
	 * Get a list of customers from DataBase.
	 * 
	 * @return List - list of customer.
	 * @throws CouponSystemException - if getting customers from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public List<Customer> getAllCustomers() throws CouponSystemException;

	/**
	 * Get one customer from DataBase.
	 * 
	 * @param customerID - of customer to be get by.
	 * @return customer
	 * @throws CouponSystemException - if getting one customer from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public Customer getOneCustomer(int customerID) throws CouponSystemException;

	/**
	 * Delete all coupons purchased belong to specific customer.
	 * 
	 * @param companyID - of customer to delete according to.
	 * @throws CouponSystemException - if getting companies from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	void deleteAllCustomerCouponsPurchased(int customerID) throws CouponSystemException;

	/**
	 * Check if customer already exists in DataBase and get the customer if
	 * exists.
	 * 
	 * @param id - of customer to be checked by.
	 * @return Result set
	 * @throws CouponSystemException - if checking customer in DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	boolean isCustomerExistsByID(int customerID) throws CouponSystemException;

	/**
	 * Get customer id by email
	 * 
	 * @param email - to check by.
	 * @return id - the company id.
	 * @throws CouponSystemException
	 */
	int getCustomerId(String email) throws CouponSystemException;

	/**
	 * Get all customer coupons..
	 * 
	 * @param customerId - the ID of the selected customer.
	 * @return list - a list of coupons.
	 * @throws Exception - if getting customer's coupons from DataBase failed caused
	 *                   by SQL Exception or DataBase access is error or Error
	 *                   filling in fields.
	 * 
	 */
	List<Coupon> getAllCustomerCoupons(int customerID) throws CouponSystemException;

	/**
	 * Get all customer coupons in specific category.
	 * 
	 * @param customerId - the ID of the selected customer.
	 * @param category   - to filter by.
	 * @return list - a list of coupons.
	 * @throws Exception - if getting customer's coupons from DataBase failed caused
	 *                   by SQL Exception or DataBase access is error or Error
	 *                   filling in fields.
	 * 
	 */
	List<Coupon> getAllCustomerCouponByCategory(int customerId, int categoryId) throws CouponSystemException;

	/**
	 * Get all customer coupons up to specific price.
	 * 
	 * @param customerId - the ID of the selected customer.
	 * @param price      - to filter by.
	 * @return list - a list of coupons.
	 * @throws Exception - if getting customer's coupons from DataBase failed caused
	 *                   by SQL Exception or DataBase access is error or Error
	 *                   filling in fields.
	 * 
	 */
	List<Coupon> getAllCustomerCouponUpToPrice(int customerId, double price) throws CouponSystemException;

}
