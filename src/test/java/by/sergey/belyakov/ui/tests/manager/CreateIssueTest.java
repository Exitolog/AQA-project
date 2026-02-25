package by.sergey.belyakov.ui.tests.manager;

import by.sergey.belyakov.ui.pages.CreateNewTaskPage;
import by.sergey.belyakov.ui.pages.IssuesListPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.Test;


import java.time.LocalDateTime;

import static org.testng.Assert.assertTrue;

public class CreateIssueTest extends BaseTestUI {

	@Test
	public void testCreateIssue() {
			singInBaseCredentials();

			ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver);
			managerMenuPage.createNewTask();

			String header = "Тестовая здаача от " + LocalDateTime.now();

			CreateNewTaskPage createTaskPage = new CreateNewTaskPage(driver);
			createTaskPage.createTask(header, "Описание задачи");

			createTaskPage.switchToTabByUrl("http://localhost:8080/dashboard");

			managerMenuPage.goToIssuesPage();

			IssuesListPage issuesListPage = new IssuesListPage(driver);
			boolean exists = issuesListPage.isIssueDisplayed(header);
			assertTrue(exists);
	}
}
