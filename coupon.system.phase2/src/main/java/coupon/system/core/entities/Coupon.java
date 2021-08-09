package coupon.system.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	@Enumerated(EnumType.STRING)
	private Category category;
//	@Column(unique = true)
	private String title;
	private String description;
//	@Check(constraints = "startDate <= endDate")
	private LocalDate startDate;
//	@Check(constraints = "endDate >= CURRENT_DATE")
	private LocalDate endDate;
	@PositiveOrZero
	private int amount;
	@PositiveOrZero
	private double price;
	private String image;
	@ManyToMany
	@JoinTable(name = "customers_vs_coupons", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	List<Customer> customers;
	
	public void addCustomer(Customer c) {
		if(customers == null) {
			customers = new ArrayList<>();
		}
		customers.add(c);
		this.amount = (this.amount-1);
	}

	public Coupon(int id, Company company, Category category, String title, String description, LocalDate startDate,
			LocalDate endDate, @PositiveOrZero int amount, @PositiveOrZero double price, String image) {
		super();
		this.id = id;
		this.company = company;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", company id =" + company.getId() + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "]";
	}

	
	
}
