package coupon.project.core.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import coupon.project.core.dao.CustomersDAO;
import coupon.project.core.db.ConnectionPool;
import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.types.Category;
import coupon.project.core.types.Coupon;
import coupon.project.core.types.Customer;

public class CustomersDBDAO implements CustomersDAO {

	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from customer where email = ? and password = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			try (ResultSet res = pstmt.executeQuery();) {
				return res.next();
			}
		} catch (Exception e) {
			throw new CouponSystemException("Customer check failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public int addCustomer(Customer customer) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "insert into customer values(0, ?, ?, ?, ?);";
		try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, customer.getFirstName());
			stmt.setString(2, customer.getLastName());
			stmt.setString(3, customer.getEmail());
			stmt.setString(4, customer.getPassword());
			int result = stmt.executeUpdate();
			try (ResultSet res = stmt.getGeneratedKeys();) {

				if (result == 1) {
					System.out.println("Customer adding succeed");
					res.next();
					int autoGeneratedId = res.getInt(1);
					System.out.println("Customer id is: " + autoGeneratedId);
					return autoGeneratedId;

				} else {
					throw new CouponSystemException("Customer adding failed");
				}
			}
		} catch (Exception e) {
			throw new CouponSystemException("Adding customer failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public int updateCustomer(Customer customer) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "update customer set first_name = ?, last_name = ?, email = ?, password = ? where id = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			int res = pstmt.executeUpdate();
			return res;
		} catch (Exception e) {
			throw new CouponSystemException("Costomer update failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCustomer(int customerID) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from customer where id =?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CouponSystemException("Delete customer failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public List<Customer> getAllCustomers() throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from customer;";
		try (Statement stmt = con.createStatement(); ResultSet res = stmt.executeQuery(sql);) {

			List<Customer> customers = new ArrayList<Customer>();
			while (res.next()) {
				Customer customer = this.getCustomer(res);
				customers.add(customer);
			}
			return customers;
		} catch (Exception e) {
			throw new CouponSystemException("geAllCustomers failed ", e);
		}
	}

	@Override
	public Customer getOneCustomer(int customerID) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from customer where id = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			try (ResultSet res = pstmt.executeQuery();) {
				if (res.next()) {
					Customer customer = new Customer();
					customer.setId(customerID);
					customer.setFirstName(res.getString(2));
					customer.setLastName(res.getString(3));
					customer.setEmail(res.getString(4));
					customer.setPassword(res.getString(5));
					System.out.println(customer.toString());
					return customer;
				} else {
					throw new CouponSystemException("Customer does not exists");
				}
			}
		} catch (Exception e) {
			throw new CouponSystemException("getOneCustomer failed " + e.getMessage());
		}

	}

	@Override
	public void deleteAllCustomerCouponsPurchased(int customerID) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from customers_vs_coupons where customer_id = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CouponSystemException("Delete customer coupons failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public boolean isCustomerExistsByID(int customerID) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from customer where id = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			try (ResultSet res = pstmt.executeQuery();) {
				return res.next();
			}
		} catch (Exception e) {
			throw new CouponSystemException("Customer check failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public int getCustomerId(String email) throws CouponSystemException {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select id from customer where email = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, email);
			try (ResultSet res = pstmt.executeQuery();) {
				res.next();
				return res.getInt(1);
			}
		} catch (Exception e) {
			throw new CouponSystemException("get customer ID failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public List<Coupon> getAllCustomerCoupons(int customerID) throws CouponSystemException {

		List<Coupon> coupons = new ArrayList<Coupon>();
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from coupon where id in(select coupon_id from customers_vs_coupons where customer_id = ?);";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			try (ResultSet res = pstmt.executeQuery()) {
				while (res.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(res.getInt(1));
					coupon.setCompanyID(res.getInt(2));
					coupon.setCategory(Category.values()[res.getInt(3)]);
					coupon.setTitle(res.getString(4));
					coupon.setDescription(res.getString(5));
					coupon.setStartDate(LocalDate.parse(res.getString(6)));
					coupon.setEndDate(LocalDate.parse(res.getString(7)));
					coupon.setAmount(res.getInt(8));
					coupon.setPrice(res.getInt(9));
					coupon.setImage(res.getString(10));
					coupons.add(coupon);
				}
				return coupons;
			}
		} catch (Exception e) {
			throw new CouponSystemException("get all customer coupons failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public List<Coupon> getAllCustomerCouponByCategory(int customerId, int categoryId) throws CouponSystemException {

		List<Coupon> coupons = new ArrayList<Coupon>();
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from coupon where category_id = ? and id in(select coupon_id from customers_vs_coupons where customer_id = ?);";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, categoryId);
			pstmt.setInt(2, customerId);
			try (ResultSet res = pstmt.executeQuery();) {
				while (res.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(res.getInt(1));
					coupon.setCompanyID(res.getInt(2));
					coupon.setCategory(Category.values()[res.getInt(3)]);
					coupon.setTitle(res.getString(4));
					coupon.setDescription(res.getString(5));
					coupon.setStartDate(LocalDate.parse(res.getString(6)));
					coupon.setEndDate(LocalDate.parse(res.getString(7)));
					coupon.setAmount(res.getInt(8));
					coupon.setPrice(res.getInt(9));
					coupon.setImage(res.getString(10));
					coupons.add(coupon);
				}
				return coupons;
			}
		} catch (Exception e) {
			throw new CouponSystemException("Delete purchased failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public List<Coupon> getAllCustomerCouponUpToPrice(int customerId, double price) throws CouponSystemException {

		List<Coupon> coupons = new ArrayList<Coupon>();
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from coupon where id in (select coupon_id from customers_vs_coupons where customer_id = ?) and price < ?;";
//		String sql = "select * from customers_vs_coupons where customer_id = ? and coupon_id in (select id from coupon where price > ?);";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerId);
			pstmt.setDouble(2, price);
			try (ResultSet res = pstmt.executeQuery();) {
				while (res.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(res.getInt(1));
					coupon.setCompanyID(res.getInt(2));
					coupon.setCategory(Category.values()[res.getInt(3)]);
					coupon.setTitle(res.getString(4));
					coupon.setDescription(res.getString(5));
					coupon.setStartDate(LocalDate.parse(res.getString(6)));
					coupon.setEndDate(LocalDate.parse(res.getString(7)));
					coupon.setAmount(res.getInt(8));
					coupon.setPrice(res.getInt(9));
					coupon.setImage(res.getString(10));
					coupons.add(coupon);
				}
				return coupons;
			}
		} catch (Exception e) {
			throw new CouponSystemException("get customer's coupons by price failed ", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * Organize a resultSet to a customer.
	 * 
	 * @param res - to be organized.
	 * @return customer
	 * @throws SQLException - if DataBase access is error or resultSet is null.
	 */

	public Customer getCustomer(ResultSet res) throws SQLException {

		Customer customer = new Customer();
		customer.setId(res.getInt(1));
		customer.setFirstName(res.getString(2));
		customer.setLastName(res.getString(3));
		customer.setEmail(res.getString(4));
		customer.setPassword(res.getString(5));
		System.out.println(customer.toString());
		return customer;
	}

}




