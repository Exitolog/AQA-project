package by.sergey.belyakov.ui.tests.authorization;

import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AuthSuccessTest extends BaseTestUI {

	@Test
	public void testAuthSuccess() {
		try {
			singInBaseCredentials();
			ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver.get());
			assertTrue(managerMenuPage.createButtonIsDisplayed());
		} catch (Exception ex) {
			createScreenshot(getDriver());
		}
	}
}
