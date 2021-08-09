package coupon.system.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.system.core.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	public boolean existsByEmailAndPassword(String email, String password);

	public Customer findByEmail(String email);

}
