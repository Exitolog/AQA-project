package by.sergey.belyakov.ui.tests.authorization;

import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AuthSuccessTest extends BaseTestUI {

	@Test
	public void testAuthSuccess() {
			singInBaseCredentials();
			ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver);
			assertTrue(managerMenuPage.createButtonIsDisplayed());
	}
}
