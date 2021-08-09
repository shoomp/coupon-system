package coupon.system.core.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, name = "name")
	private String name;
	@Column(unique = true, name = "email")
	@Email
	private String email;
	@Column(name = "password")
	private String password;
	@lombok.ToString.Exclude
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Coupon> coupons;
	
	public Company(int companyId) {
		super();
		this.id = companyId;
	}

	public Company(int id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email =email;
		this.password =password;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
}
