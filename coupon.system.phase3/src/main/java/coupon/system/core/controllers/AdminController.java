package coupon.system.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import coupon.system.core.entities.Company;
import coupon.system.core.exceptions.CouponSystemException;
import coupon.system.core.login.LoginManager;
import coupon.system.core.services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private LoginManager manager;
	@Autowired
	private AdminService admin;
	
	@PostMapping(path= "/add-company", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addCompany(@RequestBody Company company) throws CouponSystemException {
		admin.addNewCompany(company);
	}
	
	@PutMapping(path= "/update-company", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateCompany(@RequestBody Company company) throws CouponSystemException {
		admin.updateCompany(company);
	}
	
	@DeleteMapping(path= "/delete-company{id}")
	public void updateCompany(@PathVariable int id) throws CouponSystemException {
		admin.deleteCompany(id);
	}
	
	@GetMapping(path= "/get-all-companies", produces = {"application/json", "application/xml"})
	public List<Company> getAllCompanies() throws CouponSystemException {
		return admin.getAllCompany();
	}
	
	@GetMapping(path= "/get-company", produces = {"application/json", "application/xml"})
	public Company getCompany(@RequestParam int id) throws CouponSystemException {
		return admin.getOneCompany(id);
	}
	
	
	
	@ExceptionHandler
	public String exceptionHandler(CouponSystemException e ) {
		return e.getMessage();
	}
	
}
