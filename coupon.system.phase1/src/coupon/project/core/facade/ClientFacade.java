package coupon.project.core.facade;

import coupon.project.core.dao.CompaniesDAO;
import coupon.project.core.dao.CouponsDAO;
import coupon.project.core.dao.CustomersDAO;
import coupon.project.core.exception.CouponSystemException;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;

	/**
	 * Allow access to coupon system according to client type
	 * 
	 * @param email    - to check access allowed by
	 * @param password - to check access allowed by
	 * @return true if access allowed or false if access denied.
	 */
	public abstract boolean login(String email, String password) throws CouponSystemException;

}
