package coupon.project.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coupon.project.core.exception.CouponSystemException;

public class ConnectionPool {

	private Set<Connection> connections = new HashSet<Connection>();
	private String url = "jdbc:mysql://localhost:3306/coupon_system_db";
	private String user = "root";
	private String pass = "1234";
	private boolean isOpen;
	private static final int MAX = 10;

	private static ConnectionPool instance;

	private ConnectionPool() throws SQLException {
		for (int i = 0; i < MAX; i++) {
			this.connections.add(DriverManager.getConnection(url, user, pass));
		}
		this.isOpen = true;
	}

	public static ConnectionPool getInstance() throws CouponSystemException {
		if (instance == null) {
			try {
				instance = new ConnectionPool();
			} catch (SQLException e) {
				throw new CouponSystemException("connection pool init failed", e);
			}
		}
		return instance;
	}

	public synchronized Connection getConnection() throws CouponSystemException {

		if (this.isOpen) {
			Iterator<Connection> t = connections.iterator();
			if (!t.hasNext()) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Connection con = t.next();
			t.remove();
			return con;
		} else {
			throw new CouponSystemException("System is closed, Please try later");
		}
	}

	public synchronized void restoreConnection(Connection con) {
		this.connections.add(con);
		notify();
	}

	public synchronized void closePool() throws CouponSystemException {
		// close the pool
		this.isOpen = false;
		// wait for all connection to return
		while (connections.size() < MAX) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// after all connections returned - close all of them
		try {
			for (Connection connection : connections) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new CouponSystemException("closePool failed");
		}
	}

}
