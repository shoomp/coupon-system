package dataBase.mine;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;

import coupon.project.core.db.ConnectionPool;
import coupon.project.core.dbdao.CompaniesDBDAO;
import coupon.project.core.dbdao.CouponsDBDAO;
import coupon.project.core.dbdao.CustomersDBDAO;
import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.types.Category;
import coupon.project.core.types.Company;
import coupon.project.core.types.Coupon;
import coupon.project.core.types.Customer;

public class RestartSQL {

	private static final CompaniesDBDAO companyDB_dao = new CompaniesDBDAO();
	private static final CouponsDBDAO couponsDB_dao = new CouponsDBDAO();
	private static final CustomersDBDAO customersDBDao = new CustomersDBDAO();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static final Company COMPANY1 = new Company(0, "Google", "google@goole.com", "google123");
	public static final Company COMPANY2 = new Company(0, "Amazon", "amazon@amazon.com", "amazon123");
	public static final Company COMPANY3 = new Company(0, "Apple", "apple@apple.com", "apple123");
	public static final Company COMPANY4 = new Company(0, "Nestle", "nestle@nestle.com", "nestle123");
	public static final Company COMPANY5 = new Company(0, "LG", "lg@lg.com", "lg123");

	public static final Company[] company = { COMPANY1, COMPANY2, COMPANY3, COMPANY4, COMPANY5 };
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// 0.........1........... 2.......... 3...... 4...... 5....... 6
	// FOOD, ELECTRICITY, RESTURANTS, TRAVEL, CAMPING, FASHION, VACATION;
	public static final Coupon COUPON1 = new Coupon(1, 1, Category.TRAVEL, "flight", "50$ discount",
			LocalDate.parse("2021-07-01"), LocalDate.parse("2022-07-01"), 10, 25, "flight.google");
	public static final Coupon COUPON2 = new Coupon(2, 1, Category.CAMPING, "camping chairs", "4 camping chairs",
			LocalDate.parse("2021-06-01"), LocalDate.parse("2022-06-01"), 15, 50, "chairs.google");
	public static final Coupon COUPON3 = new Coupon(3, 2, Category.FOOD, "gerber", "4 cups of gerber",
			LocalDate.parse("2021-07-01"), LocalDate.parse("2021-08-01"), 40, 15, "gerber.amazon");
	public static final Coupon COUPON4 = new Coupon(4, 2, Category.ELECTRICITY, "USB cable", "USB cable type b or c",
			LocalDate.parse("2021-06-10"), LocalDate.parse("2021-09-10"), 50, 10, "cable.amazon");
	public static final Coupon COUPON5 = new Coupon(5, 3, Category.ELECTRICITY, "color uprade",
			"upgrade to limit colors", LocalDate.parse("2021-05-15"), LocalDate.parse("2021-08-15"), 20, 15, "color.apple");
	public static final Coupon COUPON6 = new Coupon(6, 3, Category.ELECTRICITY, "warranty", "6 months more of warranty",
			LocalDate.parse("2021-01-01"), LocalDate.parse("2021-10-01"), 50, 20, "warranty.apple");
	public static final Coupon COUPON7 = new Coupon(7, 4, Category.FOOD, "Trix", "0 box of Trix",
			LocalDate.parse("2021-01-01"), LocalDate.parse("2022-01-01"), 100, 10, "trix.nestle");
	public static final Coupon COUPON8 = new Coupon(8, 4, Category.FOOD, "Cheerios", "0 box of Cheerios",
			LocalDate.parse("2021-06-01"), LocalDate.parse("2022-06-01"), 100, 15, "cheerios.nestle");
	public static final Coupon COUPON9 = new Coupon(9, 5, Category.ELECTRICITY, "100$ discount",
			"100$ discount only in our website", LocalDate.parse("2021-07-01"), LocalDate.parse("2021-09-01"), 15, 75,
			"discount.lg");
	public static final Coupon COUPON10 = new Coupon(10, 5, Category.ELECTRICITY, "gift", "surprise gift",
			LocalDate.parse("2021-08-01"), LocalDate.parse("2021-09-01"), 20, 15, "gift.lg");

	public static final Coupon[] coupons = { COUPON1, COUPON2, COUPON3, COUPON4, COUPON5, COUPON6, COUPON7,
			COUPON8, COUPON9, COUPON10 };
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static final Customer CUSTOMER1 = new Customer(0, "Shiran", "Hassid", "Shiran@Hassid.com", "sh123");
	public static final Customer CUSTOMER2 = new Customer(0, "Elior", "Hassid", "Elior@Hassid.com", "eh123");
	public static final Customer CUSTOMER3 = new Customer(0, "Moriya", "Hadaya", "Moriya@Hadaya.com", "mh123");
	public static final Customer CUSTOMER4 = new Customer(0, "Efrat", "Elias", "Efrat@Elias.com", "ee123");
	public static final Customer CUSTOMER5 = new Customer(0, "Daniel", "Elias", "Daniel@Elias.com", "de123");

	public static final Customer[] customers = { CUSTOMER1, CUSTOMER2, CUSTOMER3, CUSTOMER4, CUSTOMER5};
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * this function drops all the tables in the coupons_sys_db and restores them
	 */
	public static void emptyAllTables() {
		try {
			Connection con = ConnectionPool.getInstance().getConnection();
			String sql1 = "drop table customers_vs_coupons;";
			String sql2 = "drop table customer;";
			String sql3 = "drop table coupon;";
			String sql4 = "drop table company;";
			String sql5 = "drop table category;";
			String sql6 = """
					create table `company`(
					`id` int primary key auto_increment,
					`name` varchar(30) not null,
					`Email` varchar(30) unique not null,
					`password` varchar(30) not null
					);""";
			String sql7 = """
					create table `customer`(
					`id` int primary key auto_increment,
					`first_name` varchar(30) not null,
					`last_name` varchar(30) not null,
					`Email` varchar(30) unique not null,
					`password` varchar(30) not null
					);""";
			String sql8 = """
					create table `category`(
					`id` int primary key,
					`name` varchar(30) not null
					);""";
			String sql9 = """
					create table `coupon`(
					`id` int primary key auto_increment,
					`company_id` int,
					`category_id` int,
					`title` varchar(30) not null,
					`description` varchar(200) not null,
					`start_date` date not null,
					`end_date` date not null,
					`amount` int not null,
					`price` double not null,
					`image` varchar(256),
					foreign key(`company_id`) references company(`id`),
					foreign key(`category_id`) references category(`id`)
					);""";
			String sql10 = """
					create table `customers_vs_coupons`(
					`customer_id` int,
					`coupon_id` int,
					primary key(customer_id, coupon_id),
					foreign key(`customer_id`) references customer(`id`),
					foreign key(`coupon_id`) references coupon(`id`)
					);""";
			String[] sqlStatements = { sql1, sql2, sql3, sql4, sql5, sql6, sql7, sql8, sql9, sql10 };
//			String[] sqlStatements = { sql6, sql7, sql8, sql9, sql10 };
			try (Statement stmt = con.createStatement()) {
				for (String sqlStatement : sqlStatements) {
					stmt.executeUpdate(sqlStatement);
				}
				System.out.println("tables dropped and restored");
				ConnectionPool.getInstance().restoreConnection(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * this function initializing the coupons_sys_db with some initial data to work
	 * with
	 */
	public static void restartSQL() {
		emptyAllTables();
		// 0.........1........... 2.......... 3...... 4...... 5....... 6
		// FOOD, ELECTRICITY, RESTURANTS, TRAVEL, CAMPING, FASHION, VACATION;
		String sql1 = "insert into category values(0, \"Food\");";
		String sql2 = "insert into category values(1, \"Electricity\")";
		String sql3 = "insert into category values(2, \"Restaurant\");";
		String sql4 = "insert into category values(3, \"Travel\");";
		String sql5 = "insert into category values(4, \"Camping\");";
		String sql6 = "insert into category values(5, \"Fashion\");";
		String[] sqlStatements = { sql1, sql2, sql3, sql4, sql5, sql6 };
		try {
			Connection con = ConnectionPool.getInstance().getConnection();
			try (Statement stmt = con.createStatement()) {
				for (String sqlStatement : sqlStatements) {
					stmt.executeUpdate(sqlStatement);
				}
				for (Company company : company) {
					companyDB_dao.addCompany(company);
				}
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println("<><>     # finished adding all company #        <><>");
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println();
				for (Customer customer : customers) {
					customersDBDao.addCustomer(customer);
				}
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println("<><>      @ finished adding all customers @       <><>");
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println();
				for (Coupon coupon : coupons) {
					couponsDB_dao.addCoupon(coupon);
				}
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println("<><>       $ finished adding all coupons $        <><>");
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println();
				addCouponPurchases();
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println("<><>  & finished adding all coupons purchases &   <><>");
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println("""

						***************************
						***************************
						******     SQL coupons_sys_db        ~~~~~~
						******   INITIALIZED SUCCESSFULLY    ~~~~~~
						***************************
						***************************

						""");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * this function add some data to the customers_vs_coupons table at the
	 * coupon_sys_db. it's only use is for the `restartSQL() method` and that is why
	 * it is private.
	 * 
	 * @throws CouponSystemException if something goes wrong with the database
	 */
	private static void addCouponPurchases() throws CouponSystemException {
		couponsDB_dao.addCouponPurchase(1, 3);
		couponsDB_dao.addCouponPurchase(1, 5);
		couponsDB_dao.addCouponPurchase(1, 7);
		couponsDB_dao.addCouponPurchase(2, 1);
		couponsDB_dao.addCouponPurchase(2, 6);
		couponsDB_dao.addCouponPurchase(3, 7);
		couponsDB_dao.addCouponPurchase(3, 8);
		couponsDB_dao.addCouponPurchase(3, 10);
		couponsDB_dao.addCouponPurchase(4, 2);
		couponsDB_dao.addCouponPurchase(4, 4);
		couponsDB_dao.addCouponPurchase(4, 9);
		couponsDB_dao.addCouponPurchase(5, 5);
		couponsDB_dao.addCouponPurchase(5, 6);
		couponsDB_dao.addCouponPurchase(5, 7);
	}

}
