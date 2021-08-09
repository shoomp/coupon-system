package coupon.project.core.facade;

import java.time.LocalDate;
import java.util.List;

import coupon.project.core.dbdao.CompaniesDBDAO;
import coupon.project.core.dbdao.CouponsDBDAO;
import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.types.Category;
import coupon.project.core.types.Company;
import coupon.project.core.types.Coupon;

public class CompanyFacade extends ClientFacade {

	private int companyId;

	{
		this.companiesDAO = new CompaniesDBDAO();
		this.couponsDAO = new CouponsDBDAO();
	}

	public CompanyFacade() {
	}

	public CompanyFacade(int i) {
		super();
		this.companyId = i;
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		boolean is = companiesDAO.isCompanyExists(email, password);
		if (is) {
			this.companyId = companiesDAO.getCompanyId(email);
			System.out.println(companyId);
		}
		return is;
	}

	/**
	 * Adding new coupon to company.
	 * 
	 * @param coupon - to be add to company
	 * @throws CouponSystemException -
	 *                               <p>
	 *                               1) if the expire date had passed.
	 *                               <p>
	 *                               2) if company does not exists.
	 *                               <p>
	 *                               3) if coupon title already exists in the same
	 *                               company.
	 *                               <p>
	 *                               4)or if adding coupon to DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		try {
			if (coupon.getStartDate().isAfter(coupon.getEndDate())) {
				throw new CouponSystemException("coupon's date feilds are not valid");
			}
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException("can't add coupon with passed Expiry Date ");
			}
			if (!couponsDAO.isCouponExistsInCompanyByTitle(coupon.getTitle(), coupon.getCompanyID())) {
				couponsDAO.addCoupon(coupon);
			} else {
				throw new CouponSystemException(" coupon title already exists ");
			}
		} catch (Exception e) {
			throw new CouponSystemException("coupon adding failed ", e);
		}
	}

	/**
	 * Updating an exist coupon belongs to selected company.
	 * 
	 * @param coupon - to update according to the current fields
	 * @throws CouponSystemException -
	 *                               <p>
	 *                               1) if coupon doesn't exist
	 *                               <p>
	 *                               2) if coupon doesn't belongs to the selected
	 *                               company.
	 *                               <p>
	 *                               3) if coupon's title already exists in another
	 *                               coupon.
	 *                               <p>
	 *                               4) if validity isn't valid.
	 *                               <p>
	 *                               5)or if update coupon to DataBase failed caused
	 *                               by SQL Exception or DataBase access is error or
	 *                               Error filling in fields.
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		try {
			if (!checkCompanyID(coupon.getCompanyID())) {
				throw new CouponSystemException(
						"can't update coupon which company id is different than this comapny id");
			}
			Coupon coupon1 = couponsDAO.getOneCoupon(coupon.getId());
			if (!checkCompanyID(coupon1.getCompanyID())) {
				throw new CouponSystemException("coupon doe's not belong to this company");
			}
			if (!coupon1.getTitle().equals(coupon.getTitle())
					&& couponsDAO.isCouponExistsInCompanyByTitle(coupon.getTitle(), companyId)) {
				throw new CouponSystemException("there's another coupon with the same title ");
			}
			if (coupon.getEndDate().isBefore(coupon.getStartDate())) {
				throw new CouponSystemException("start date can't be after end date");
			}
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException("coupon's end date is not valid");
			}
			couponsDAO.updateCoupon(coupon1);
		} catch (Exception e) {
			throw new CouponSystemException("update couppon failed ", e);
		}

	}

	/**
	 * Delete coupon and all the belongs to.
	 * 
	 * @param couponId - to delete coupon and the belongs to by.
	 * @throws CouponSystemException -
	 *                               <p>
	 *                               1) if coupon does not exist
	 *                               <p>
	 *                               2) if delete coupon failed caused by SQL
	 *                               exception, Error access to DataBase or Error
	 *                               filling in fields.
	 */
	public void deleteCoupon(int couponId) throws CouponSystemException {
		if (couponsDAO.isCouponExists(couponId)) {
			if(couponsDAO.getOneCoupon(couponId).getCompanyID() == this.companyId) {
				
			couponsDAO.deleteAllCouponsPurchases(couponId);
			couponsDAO.deleteCoupon(couponId);
			}else {
				throw new CouponSystemException("coupon doesn't belong to this company");
			}
		} else {
			throw new CouponSystemException("coupon doe's not exist ");
		}
	}

	/**
	 * Getting a list of all coupons exist in DataBase and belong to this
	 * company.
	 * 
	 * @return List - a list of coupons.
	 * @throws CouponSystemException - if there's no coupon belongs to this company
	 *                               in DataBase, or if getting coupons failed
	 *                               caused by SQL Exception or DataBase access is
	 *                               Error.
	 */
	public List<Coupon> getAllCompanyCoupons() throws CouponSystemException {
		try {
			List<Coupon> coupons = couponsDAO.getAllCouponsByCompany(companyId);
			if (coupons.size() > 0) {
				return coupons;
			} else {
				throw new CouponSystemException("no coupon belongs to this company found ");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException("get oupons failed ", e);
		}
	}

	/**
	 * Getting a list of all coupons on specific category exist in DataBase
	 * and belong to this company.
	 * 
	 * @param category - to sort by.
	 * @return List - a list of coupons.
	 * @throws CouponSystemException - if there's no coupon belongs to this company
	 *                               or on this category in DataBase, or if getting
	 *                               coupons failed caused by SQL Exception or
	 *                               DataBase access is Error.
	 */
	public List<Coupon> getCouponByCategory(Category category) throws CouponSystemException {

		try {
			List<Coupon> coupons = couponsDAO.getAllCouponsOfCompanyByCategory(companyId, category.ordinal());
			return coupons;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("get coupons by company failed ", e);
		}

	}

	/**
	 * Getting a list of all coupons which price lower than given price
	 * exist in DataBase and belong to this company.
	 * 
	 * @param companyId - of the company to sort by.
	 * @param category  - to sort by.
	 * @return List - a list of coupons.
	 * @throws CouponSystemException - if there's no coupon belongs to this company
	 *                               or on this category in DataBase, or if getting
	 *                               coupons failed caused by SQL Exception or
	 *                               DataBase access is Error.
	 */
	public List<Coupon> getCouponByPrice(double price) throws CouponSystemException {

		try {
			List<Coupon> coupons = couponsDAO.getCouponsOfCompanyByMaxPrice(companyId, price);
			return coupons;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("get coupons by company failed ", e);
		}
	}

	/**
	 * Get company details.
	 * 
	 * @return Company
	 * @throws CouponSystemException - if getting company failed caused by SQL
	 *                               Exception or DataBase access is Error.
	 */
	public Company gatDetails() throws CouponSystemException {
		return companiesDAO.getOneCompany(companyId);
	}

	/**
	 * Checking if coupon owns selected conpanyId belong to this company.
	 * 
	 * @param couponCompanyID - company id of coupon to check by.
	 * @return true if the company id of the coupon is the same to this company id,
	 *         false if the coupon's company id is different.
	 */
	public boolean checkCompanyID(int couponCompanyID) {
		if (couponCompanyID != this.companyId) {
			return false;
		}
		return true;
	}
}