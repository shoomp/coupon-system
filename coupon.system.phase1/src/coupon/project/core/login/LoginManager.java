package coupon.project.core.login;

import coupon.project.core.exception.CouponSystemException;
import coupon.project.core.facade.AdminFacade;
import coupon.project.core.facade.ClientFacade;
import coupon.project.core.facade.CompanyFacade;
import coupon.project.core.facade.CustomerFacade;

/**
 * @author מוריה הדאיה
 *
 */
public class LoginManager {

	private AdminFacade adminFacade;

	private LoginManager() {
		super();
	}

	private static LoginManager instance = new LoginManager();

	public static LoginManager getInstance() {
		return instance;
	}

	public static void setInstance(LoginManager instance) {
		LoginManager.instance = instance;
	}

	/**
	 * Sort client by client type, check if exists in DataBase and get his
	 * Data.
	 * 
	 * @param email      - to check by.
	 * @param password   - to check by.
	 * @param clientType - to sort by.
	 * @return ClientFacade - the matching facade according to given details.
	 * @throws CouponSystemException - if excepted an exception in called methods.
	 */
	public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
		ClientFacade clientFacade;
		switch (clientType) {

		case ADMINISTRATOR -> {
			clientFacade = adminFacade;
			if (clientFacade.login(email, password)) {
				return clientFacade;
			} else {
				return null;
			}
		}

		case COMPANY -> {
			clientFacade = new CompanyFacade();
			if (clientFacade.login(email, password)) {
				return clientFacade;
			} else {
				return null;
			}
		}
		default -> {
			clientFacade = new CustomerFacade();
			if (clientFacade.login(email, password)) {
				return clientFacade;
			} else {
				return null;
			}
		}
		}
	}
}
