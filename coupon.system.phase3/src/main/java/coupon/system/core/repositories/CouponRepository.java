package coupon.system.core.repositories;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import coupon.system.core.entities.Category;
import coupon.system.core.entities.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer>{

	public boolean existsByCompanyIdAndTitle(int companyId, String title);

	public List<Coupon> getAllByCompanyId(int companyId);

	public List<Coupon> getAllByCompanyIdAndCategory(int companyId, Category category);

	public List<Coupon> getByCompanyIdAndPriceLessThan(int companyId, double price);

	public List<Coupon> getByCategoryAndCustomersId(Category category, int customerId);
	
	public List<Coupon> getByCategory(Category category);

	public List<Coupon> getAllCouponByCustomersIdAndPriceLessThan(int customerId, double price);

	public void deleteAllByEndDateBefore(LocalDate date);

}
