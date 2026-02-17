package by.sergey.belyakov.ui.tests.authorization;

import by.sergey.belyakov.ui.pages.LoginPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AuthSuccessTest extends BaseTestUI {

	@Test
	public void testAuthSuccess() {
		LoginPage loginPage = new LoginPage(driver.get());

		boolean isLoggedInSuccessfully = loginPage.login(getBaseUsername(), getBasePassword());
		ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver.get());

		assertTrue(isLoggedInSuccessfully);
		assertTrue(managerMenuPage.createButtonIsDisplayed());
	}
}
