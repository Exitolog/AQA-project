package by.sergey.belyakov.ui.tests.issues;

import by.sergey.belyakov.ui.pages.IssuesListPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class GetNotExistsIssueTest extends BaseTestUI {

	@DataProvider(name= "wrongHeaders", parallel = true)
	public Object[][]dataProviderMethodForWrongHeaders() {
		return new Object[][] {
				{"Несуществующий заголовок"},
				{"Абракадабра"},
				{"Двабракадабра"}};
	}

	@Test(dataProvider = "wrongHeaders")
	public void testGetNotExistsIssue(String header) {
		try {
			singInBaseCredentials();

			ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver);
			managerMenuPage.goToIssuesPage();

			IssuesListPage issuesListPage = new IssuesListPage(driver);
			boolean notExists = issuesListPage.isIssueDisplayed(header);
			assertFalse(notExists);
		} catch (Exception ex) {
			createScreenshot(getDriver());
		}
	}
}
