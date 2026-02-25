package by.sergey.belyakov.ui.tests.authorization;


import by.sergey.belyakov.ui.pages.LoginPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Slf4j
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
			LoginPage loginPage = new LoginPage(driver);
			try {
				loginPage.login(username, wrongPass);
			} catch (RuntimeException e) {
				assertEquals(e.getMessage(), "Авторизация не удалась");
			}
	}
}
