package by.sergey.belyakov.ui.tests.manager;

import by.sergey.belyakov.ui.pages.CreateNewTaskPage;
import by.sergey.belyakov.ui.pages.IssuesListPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.Test;


import static org.testng.Assert.assertTrue;

public class CreateIssueTest extends BaseTestUI {

	@Test
	public void testCreateIssue() {
		try {
			singInBaseCredentials();

			ManagerMenuPage managerMenuPage = new ManagerMenuPage(getDriver());
			managerMenuPage.createNewTask();

			CreateNewTaskPage createTaskPage = new CreateNewTaskPage(getDriver());
			createTaskPage.createTask("Тестовая задача", "Описание задачи");

			managerMenuPage.goToIssuesPage();

			IssuesListPage issuesListPage = new IssuesListPage(getDriver());
			boolean exists = issuesListPage.isIssueDisplayed("Тестовая задача");
			assertTrue(exists);

		} catch (Exception ex) {
			createScreenshot(getDriver());
		}
	}
}
