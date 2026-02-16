package by.sergey.belyakov;


import by.sergey.belyakov.ui.authorization.LoginPage;
import by.sergey.belyakov.ui.issues.IssuesListPage;
import by.sergey.belyakov.ui.manager.CreateNewTaskPage;
import by.sergey.belyakov.ui.manager.ManagerMenu;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class MyTestsYouTrack {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
//        driver.get("http://localhost:8080");
//        Thread.sleep(3000);
//        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void navigateToBaseUrl() {
        driver.get("http://localhost:8080");
    }
    
    @DataProvider(name = "wrongCredentials")
    public Object[][] dataProviderMethodForWrongCredentials() {
        return new Object[][] {
                {"azamat", "passwordss"},
                {"kredit", "pazzwords"},
                {"wrong", "mypass" }};
    }

    @DataProvider(name = "optionsIssues")
    public Object[][] dataProviderMethodForNewIssues() {
        return new Object[][] {
                {"Первый заголовок", "Первое описание"},
                { "Second header","second description"},
                { "3 zagolovok","3 opisanie"}};
    }

    @DataProvider(name= "wrongHeaders")
    public Object[][]dataProviderMethodForWrongHeaders() {
        return new Object[][] {
                {"Несуществующий заголовок"},
                {"Абракадабра"},
                {"Двабракадабра"}};
    }

    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void testAuthSuccess() {
        LoginPage loginPage = new LoginPage(driver);
        boolean isLoggedInSuccessfully = loginPage.login("admin", "admin");
        ManagerMenu managerMenu = new ManagerMenu(driver);

        assertTrue(isLoggedInSuccessfully);
        assertTrue(managerMenu.createButtonIsDisplayed());
    }

    @Test(dataProvider = "wrongCredentials")
    public void testAuthFailure(String username, String wrongPass) {
        LoginPage loginPage = new LoginPage(driver);
        boolean isLoggedInSuccessfully = loginPage.login(username, wrongPass);
        ManagerMenu managerMenu = new ManagerMenu(driver);

        assertFalse(isLoggedInSuccessfully);
        assertFalse(managerMenu.createButtonIsDisplayed());
    }

    @Test
    public void testCreateIssue() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "admin");

        ManagerMenu managerMenu = new ManagerMenu(driver);
        managerMenu.createNewTask();

        CreateNewTaskPage createTaskPage = new CreateNewTaskPage(driver);
        createTaskPage.createTask("Тестовая задача", "Описание задачи");

        // Переключаемся обратно на вкладку со списком
        createTaskPage.switchToNewTab(0);

        managerMenu.goToIssuesPage();

        IssuesListPage issuesListPage = new IssuesListPage(driver);
        boolean exists = issuesListPage.isIssueDisplayed("Тестовая задача");
        assertTrue(exists);
}

    @Test(dataProvider = "wrongHeaders")
    public void testGetNotExistsIssue(String header) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "admin");

        ManagerMenu managerMenu = new ManagerMenu(driver);
        managerMenu.goToIssuesPage();

        IssuesListPage issuesListPage = new IssuesListPage(driver);
        boolean notExists = issuesListPage.isIssueDisplayed(header);
        assertFalse(notExists);
    }

    @Test(dataProvider = "optionsIssues")
    public void testCreateAndDeleteIssue(String header, String description) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "admin");

        ManagerMenu managerMenu = new ManagerMenu(driver);
        managerMenu.createNewTask();

        CreateNewTaskPage createTaskPage = new CreateNewTaskPage(driver);
        createTaskPage.createTask(header, description);

        // Переключаемся обратно на вкладку со списком
        createTaskPage.switchToNewTab(0);

        managerMenu.goToIssuesPage();

        IssuesListPage issuesListPage = new IssuesListPage(driver);

        issuesListPage.deleteIssue(header);

        boolean notExists = issuesListPage.isIssueDisplayed(header);

        assertFalse(notExists);
    }
}
