package coupon.system.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.system.core.entities.Company;

public abstract interface CompanyRepository extends JpaRepository<Company, Integer>{

	public boolean existsByName(String name);

	public boolean existsByEmail(String email);

	public boolean existsByEmailAndPassword(String email, String password);

	public Company findByEmail(String email);

}
