package by.sergey.belyakov.ui.tests.authorization;


import by.sergey.belyakov.ui.pages.LoginPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class AuthFailureTest extends BaseTestUI {


	@DataProvider(name = "wrongCredentials", parallel = true)
	public Object[][] dataProviderMethodForWrongCredentials() {
		return new Object[][] {
				{"azamat", "passwordss"},
				{"kredit", "pazzwords"},
				{"wrong", "mypass" }};
	}

	@Test(dataProvider = "wrongCredentials")
	public void testAuthFailure(String username, String wrongPass) {
		LoginPage loginPage = new LoginPage(getDriver());
		boolean isLoggedInSuccessfully = loginPage.login(username, wrongPass);
		ManagerMenuPage managerMenuPage = new ManagerMenuPage(getDriver());

		assertFalse(isLoggedInSuccessfully);
		assertFalse(managerMenuPage.createButtonIsDisplayed());
	}
}
