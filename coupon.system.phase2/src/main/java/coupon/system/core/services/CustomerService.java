package coupon.system.core.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import coupon.system.core.entities.Category;
import coupon.system.core.entities.Coupon;
import coupon.system.core.entities.Customer;
import coupon.system.core.exceptions.CouponSystemException;

@Service
@Component(value = "CustomerService")
@Scope("prototype")
@Transactional
public class CustomerService extends Client {

	private int customerId;

	@Override
	public boolean login(String email, String password) {
		boolean is = customerRepository.existsByEmailAndPassword(email, password);
		if (is) {
			this.customerId = customerRepository.findByEmail(email).getId();
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
			couponRepository.existsById(coupon.getId());
			Customer c = new Customer(customerId);
//		List<Customer> customers = couponRepository.getById(coupon.getId()).getCustomers();
//		for (Customer customer : customers) {
//		if (customer.getId() == customerId) {
			if (!couponRepository.getById(coupon.getId()).getCustomers().contains(c)) {
//				if (isCouponPurchasable(coupon)) {
					coupon.addCustomer(c);
					couponRepository.save(coupon);
//				} else {
//					throw new CouponSystemException("coupon doesn't purchasable");
//				}
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
		List<Coupon> coupons = customerRepository.getById(customerId).getCoupons();
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

	List<Coupon> coupons = couponRepository.getByCategoryAndCustomersId(category, customerId);
//	List<Coupon> coupons = couponRepository.getByCategory(category);
//	Customer customer = new Customer(this.customerId);
//	for (Coupon coupon : coupons) {
//	if(!(coupon.getCustomers().contains(customer))){
//			coupons.remove(coupon);
//		}
//	}
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

	List<Coupon> coupons = couponRepository.getAllCouponByCustomersIdAndPriceLessThan(customerId, price);
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
	return customerRepository.findById(customerId).get();
}

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
///**
// * Check purchasability of coupon.
// * 
// * @param coupon - to check purchasability.
// * @return - true if coupon purchase is available, false if coupon purchase is
// *         unavailable.
// * @throws CouponSystemException - if excepted a SQL exception or Error access
// *                               to DataBase or error filling in fields.
// */
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
