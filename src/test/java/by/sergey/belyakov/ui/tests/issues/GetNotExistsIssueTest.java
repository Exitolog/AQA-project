package by.sergey.belyakov.ui.tests.issues;

import by.sergey.belyakov.ui.pages.IssuesListPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class GetNotExistsIssueTest extends BaseTestUI {

	@DataProvider(name = "wrongHeaders", parallel = true)
	public Object[][] dataProviderMethodForWrongHeaders() {
		return new Object[][]{{"Несуществующий заголовок"}, {"Абракадабра"}, {"Двабракадабра"}};
	}

	@Test(dataProvider = "wrongHeaders")
	public void testGetNotExistsIssue(String header) {
		singInBaseCredentials();
		ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver);
		managerMenuPage.goToIssuesPage();
		IssuesListPage issuesListPage = new IssuesListPage(driver);
		try {
			issuesListPage.isIssueDisplayed(header);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Задача не найдена в списке с заголовком " + header));
		}
	}
}
