package coupon.system.core.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import coupon.system.core.entities.Category;
import coupon.system.core.entities.Company;
import coupon.system.core.entities.Coupon;
import coupon.system.core.exceptions.CouponSystemException;

@Service
@Component(value = "CompanyService")
@Scope("prototype")
@Transactional
public class CompanyService extends Client {
	
	private int companyId;

	public int getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public boolean login(String email, String password){
		boolean is = companyRepository.existsByEmailAndPassword(email, password);
		if (is) {
			this.companyId = companyRepository.findByEmail(email).getId();
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
//			if (coupon.getStartDate().isAfter(coupon.getEndDate())) {
//				throw new CouponSystemException("coupon's date feilds are not valid");
//			}
//			if (coupon.getEndDate().isBefore(LocalDate.now())) {
//				throw new CouponSystemException("can't add coupon with passed Expiry Date ");
//			}
			if (!couponRepository.existsByCompanyIdAndTitle(coupon.getCompany().getId(),coupon.getTitle())) {
				Company company = new Company(this.companyId);
				coupon.setCompany(company);
				couponRepository.save(coupon);
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
			if (!couponRepository.existsById(coupon.getId())) {
				throw new CouponSystemException("coupon doe's not exist");
			}
			Coupon coupon1 = couponRepository.getById(coupon.getId());
			if (companyId != (coupon1.getCompany().getId())) {
				throw new CouponSystemException("coupon doe's not belong to this company");
			}
			if(coupon.getCompany().getId() != companyId) {
				throw new CouponSystemException("can't update different company id than this comapny id");
			}
			if (!(coupon1.getTitle().equals(coupon.getTitle()))
					&& couponRepository.existsByCompanyIdAndTitle(companyId, coupon.getTitle())) {
				throw new CouponSystemException("there's another coupon with the same title ");
			}
			if (coupon.getEndDate().isBefore(coupon.getStartDate())) {
				throw new CouponSystemException("start date can't be after end date");
			}
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new CouponSystemException("coupon's end date is not valid");
			}
			couponRepository.save(coupon);
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
		if (couponRepository.existsById(couponId)) {
			couponRepository.deleteById(couponId);
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
			List<Coupon> coupons = couponRepository.getAllByCompanyId(companyId);
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

		List<Coupon> coupons = couponRepository.getAllByCompanyIdAndCategory(companyId, category);
		return coupons;
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
		List<Coupon> coupons = couponRepository.getByCompanyIdAndPriceLessThan(companyId, price);
		return coupons;
	}

	/**
	 * Get company details.
	 * 
	 * @return Company
	 * @throws CouponSystemException - if getting company failed caused by SQL
	 *                               Exception or DataBase access is Error.
	 */
	public Company getDetails() throws CouponSystemException {
		return companyRepository.findById(companyId).get();
	}
}
