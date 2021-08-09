package coupon.project.core.facade;

import java.time.LocalDate;
import java.util.List;

import coupon.project.core.dbdao.CouponsDBDAO;
import coupon.project.core.dbdao.CustomersDBDAO;
import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.types.Category;
import coupon.project.core.types.Coupon;
import coupon.project.core.types.Customer;

public class CustomerFacade extends ClientFacade {

	private int customerId;

	{
		this.couponsDAO = new CouponsDBDAO();
		this.customersDAO = new CustomersDBDAO();
	}

	public CustomerFacade() {
	}

	public CustomerFacade(int i) {
		super();
		this.customerId = i;
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		boolean is = customersDAO.isCustomerExists(email, password);
		if (is) {
			this.customerId = customersDAO.getCustomerId(email);
		}
		return is;
	}

	/**
	 * Register a new coupon purchase by customer.
	 * 
	 * @param coupon - to be purchased.
	 * @throws CouponSystemException -
	 *                               <p>
	 *                               1) if coupon doesn't exist in DataBase.
	 *                               <p>
	 *                               2) if coupon already purchased by this
	 *                               customer.
	 *                               <p>
	 *                               3) if coupon doesn't purchasable cause its
	 *                               start date didn't arrive or its expire date had
	 *                               passed or its amount is 0.
	 *                               <p>
	 *                               4)if updating coupon amount in DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		try {
			couponsDAO.isCouponExists(coupon.getId());
			if (!couponsDAO.isCouponPurchased(coupon.getId(), customerId)) {
				if (isCouponPurchasable(coupon)) {
					couponsDAO.addCouponPurchase(customerId, coupon.getId());
					coupon.setAmount(coupon.getAmount() - 1);
					couponsDAO.updateCoupon(coupon);
				} else {
					throw new CouponSystemException("coupon doesn't purchasable");
				}
			} else {
				throw new CouponSystemException("customer already parchased this coupon");
			}
		} catch (Exception e) {
			throw new CouponSystemException("coupon purchase failed ", e);
		}
	}

	/**
	 * Get all coupons of the customer.
	 * 
	 * @return List - list of coupons.
	 * @throws CouponSystemException -
	 *                               <p>
	 *                               1) if there's no coupon purchased by the
	 *                               selected customer.
	 *                               <p>
	 *                               2)if getting coupons from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 * 
	 */
	public List<Coupon> getCustomerCoupons() throws CouponSystemException {
		List<Coupon> coupons = customersDAO.getAllCustomerCoupons(customerId);
		if (coupons.size() > 0) {
			return coupons;
		} else {
			throw new CouponSystemException("No coupon had found");
		}
	}

	/**
	 * Get all coupons of the customer in specific category.
	 * 
	 * @return List - list of coupons.
	 * @throws CouponSystemException -
	 *                               <p>
	 *                               1) if there's no coupon purchased by the
	 *                               selected customer in the selected category.
	 *                               <p>
	 *                               2)if getting coupons from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 * 
	 */
	public List<Coupon> getCustomerCouponsByCategory(Category category) throws CouponSystemException {

		List<Coupon> coupons = customersDAO.getAllCustomerCouponByCategory(customerId, category.ordinal());
		if (coupons.size() > 0) {
			return coupons;
		} else {
			throw new CouponSystemException("No coupon had found");
		}

	}

	/**
	 * Get all coupons of the customer.
	 * 
	 * @return List - list of coupons.
	 * @throws CouponSystemException -
	 *                               <p>
	 *                               1) if there's no coupon purchased by the
	 *                               selected customer up to that price.
	 *                               <p>
	 *                               2)if getting coupons from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 * 
	 */
	public List<Coupon> getCustomerCouponByPrice(double price) throws CouponSystemException {

		List<Coupon> coupons = customersDAO.getAllCustomerCouponUpToPrice(customerId, price);
		if (coupons.size() > 0) {
			return coupons;
		} else {
			throw new CouponSystemException("No coupon had found");
		}

	}

	/**
	 * Get customer details from DataBase.
	 * 
	 * @return Customer
	 * @throws CouponSystemException -
	 *                               <p>
	 *                               1) if customer doesn't exist in DataBase.
	 *                               <p>
	 *                               2)if getting coupons from DataBase failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               error or Error filling in fields.
	 * 
	 */
	public Customer getCustomerDetails() throws CouponSystemException {
		Customer customer = customersDAO.getOneCustomer(customerId);
		return customer;
	}

	/**
	 * Check purchasability of coupon.
	 * 
	 * @param coupon - to check purchasability.
	 * @return - true if coupon purchase is available, false if coupon purchase is
	 *         unavailable.
	 * @throws CouponSystemException - if excepted a SQL exception or Error access
	 *                               to DataBase or error filling in fields.
	 */
	public boolean isCouponPurchasable(Coupon coupon) throws CouponSystemException {
		try {
			if (coupon.getStartDate().isBefore(LocalDate.now()) && coupon.getEndDate().isAfter(LocalDate.now())
					&& coupon.getAmount() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new CouponSystemException("check coupon purchasability failed ", e);
		}
	}

}
