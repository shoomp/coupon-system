package coupon.project.core.dao;

import java.time.LocalDate;
import java.util.List;

import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.types.Coupon;

public interface CouponsDAO {

	/**
	 * Check if coupon already exists in DataBase.
	 * 
	 * @param id - of coupon to be checked by.
	 * @return true if coupon exists, false if coupon does not exist.
	 * @throws CouponSystemException - if checking coupon from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 * 
	 */
	public boolean isCouponExists(int id) throws CouponSystemException;

	/**
	 * Add a new coupon to DataBase.
	 * 
	 * @param coupon - to be added.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if adding coupon to DataBase failed caused by
	 *                               SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public int addCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * Update an exists coupon in DataBase.
	 * 
	 * @param coupon - to be update by the current fields.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if updating coupon in DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public int updateCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * Delete an exists coupon in DataBase.
	 * 
	 * @param couponID - of coupon to delete a coupon by.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if deleting coupon from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public int deleteCoupon(int couponID) throws CouponSystemException;

	/**
	 * Get a list of coupon from DataBase.
	 * 
	 * @return List - of coupons.
	 * @throws CouponSystemException - if getting coupons from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public List<Coupon> getAllCoupons() throws CouponSystemException;

	/**
	 * Get one specific coupon from DataBase.
	 * 
	 * @param couponID - of a coupon to get by.
	 * @return Coupon
	 * @throws CouponSystemException - if getting one coupon from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public Coupon getOneCoupon(int couponID) throws CouponSystemException;

	/**
	 * Register a purchase of coupon by customer.
	 * 
	 * @param customerId - of customer who bought the coupon.
	 * @param couponID   - of coupon got bought.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if registering coupon purchase to DataBase
	 *                               failed caused by SQL Exception or DataBase
	 *                               access is error or Error filling in fields.
	 */
	public int addCouponPurchase(int customerId, int couponID) throws CouponSystemException;

	/**
	 * Remove a coupon from customer.
	 * 
	 * @param customerId - of customer to remove coupon.
	 * @param couponID   - of coupon to be removed.
	 * @return number of affected rows, 0 if there're no affected rows.
	 * @throws CouponSystemException - if deleting coupon purchase from DataBase
	 *                               failed caused by SQL Exception or DataBase
	 *                               access is error or Error filling in fields.
	 */
	public int deleteCouponPurchase(int customerId, int couponID) throws CouponSystemException;

	/**
	 * Check if coupon title already exists in selected company in
	 * DataBase.
	 * 
	 * @param title     - of coupon to be checked by.
	 * @param companyId - of company to check by.
	 * @return true if coupon title exists in the company, false if coupon title
	 *         does not exist.
	 * @throws CouponSystemException - if checking coupon from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 * 
	 */
	public boolean isCouponExistsInCompanyByTitle(String title, int companyId) throws CouponSystemException;

	/**
	 * Deleting all coupon purchases.
	 * 
	 * @param couponID - of coupon to delete by.
	 * @throws CouponSystemException - if delete coupon purchases from DataBase
	 *                               failed caused by SQL Exception or DataBase
	 *                               access is error or Error filling in fields.
	 */
	public void deleteAllCouponsPurchases(int couponID) throws CouponSystemException;

	/**
	 * Get a list of coupon belongs to specific company from DataBase.
	 * 
	 * @param companyId - Id of company to get coupons by.
	 * @return List - of coupons.
	 * @throws CouponSystemException - if getting coupons from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public List<Coupon> getAllCouponsByCompany(int companyId) throws CouponSystemException;

	/**
	 * Get a list of coupon belongs to specific company in specific
	 * category from DataBase.
	 * 
	 * @param companyId  - Id of company to get coupons by.
	 * @param categoryId - to sort by.
	 * @return List - of coupons.
	 * @throws CouponSystemException -if there's no coupon found on this category or
	 *                               company or if getting coupons from DataBase
	 *                               failed caused by SQL Exception or DataBase
	 *                               access is error or Error filling in fields.
	 */
	public List<Coupon> getAllCouponsOfCompanyByCategory(int companyId, int categoryId) throws CouponSystemException;

	/**
	 * Get a list of coupon belongs to specific company which price lower
	 * than the given price from DataBase.
	 * 
	 * @param companyId - to get coupons belongs to.
	 * @param price     - to sort by.
	 * @return list - a list of coupons
	 * @throws CouponSystemException - if there's no coupons found by those
	 *                               conditions or if getting coupons from DataBase
	 *                               failed caused by SQL Exception or DataBase
	 *                               access is error or Error filling in fields.
	 */
	public List<Coupon> getCouponsOfCompanyByMaxPrice(int companyId, double price) throws CouponSystemException;

	/**
	 * Check if customer already purchased specific coupon.
	 * 
	 * @param couponId   - to check purchases by.
	 * @param customerID - to check purchases belongs to selected customer
	 * @return true if coupon already purchased by this customer, false if this
	 *         customer didn't purchase the coupon yet.
	 * @throws CouponSystemException - if there's no coupons found by those
	 *                               parameters or if getting coupons from DataBase
	 *                               failed caused by SQL Exception or DataBase
	 *                               access is error or Error filling in fields.
	 */
	public boolean isCouponPurchased(int couponId, int customerID) throws CouponSystemException;

	/**
	 * Delete coupons for DataBase which expire date is given date.
	 * 
	 * @param date - to filter by.
	 * @throws CouponSystemException - if deleting coupons in DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public void deleteCouponByExpiryDate(LocalDate date) throws CouponSystemException;

}
