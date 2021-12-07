package HRM_Employee;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Commons.BaseTest;
import LoginPage_action.AddEmployeePage_Object;
import LoginPage_action.DashboardPage_Object;
import LoginPage_action.EmployeeList_Object;
import LoginPage_action.LoginPage_Object;
import LoginPage_action.Page_Generator;
import LoginPage_action.PersonalDetail_Object;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic")
@Feature("Feature - Add new employee")
public class Employee_1_Add_Employee extends BaseTest{
	String statusValue;
	
	@Parameters({"browser","url"})
	@BeforeClass
	public void beforeClass(String browserName, String appUrl) {
		statusValue="Enabled";
		log.info("Pre-conditon: Step1: Open Brower" + browserName + "go to" + appUrl);
		driver = getBrowserDriver(browserName,appUrl);
		LoginPage = Page_Generator.getLoginPage(driver);
		log.info("Pre-conditon: Step2: Login by admin role");
		LoginPage.enterToTextboxByID(driver,"txtUsername","Admin");
		LoginPage.enterToTextboxByID(driver,"txtPassword", "admin123");
		LoginPage.clickToButtonByID(driver,"btnLogin");
		DashbroadPage=Page_Generator.getDashboardPage(driver);
	}
	
	@Description("Login and add new employee")
	@Story("Story HRM-37")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void Employee_1_Add_New_Employee() { 
		log.info(" AddNewEmployee - Step 1: open 'Employee list' " );
		DashbroadPage.openSubMenuPage(driver, "PIM", "Employee List");
		EmployeeListPage=Page_Generator.getEmployeeLisPage(driver);
		
		log.info(" AddNewEmployee - Step 2: click 'add' button");
		EmployeeListPage.clickToButtonByID(driver, "btnAdd");
		AddEmployeePage=Page_Generator.getAddEmployeePage(driver);
		
		log.info(" AddNewEmployee - Step 3: enter valid to 'Frist Name' textbox" );
		AddEmployeePage.enterToTextboxByID(driver, "firstName", "BANG");
		
		log.info(" AddNewEmployee - Step 4: enter valid to 'Last Name' textbox" );
		AddEmployeePage.enterToTextboxByID(driver, "lastName", "KHOA");
		
		log.info(" AddNewEmployee - Step 5: Get value of 'Employee ID'" );
		AddEmployeePage.getTextboxValueByID(driver, "employeeId");
		
		log.info(" AddNewEmployee - Step 6: click to 'create login detail'" );
		AddEmployeePage.clickToCheckboxByLabel(driver, "Create Login Details");
		
		log.info(" AddNewEmployee - Step 7: enter 'User name' textbox" );
		AddEmployeePage.enterToTextboxByID(driver, "user_name", "bang1");
		
		log.info(" AddNewEmployee - Step 8: enter 'password' textbox" );
		AddEmployeePage.enterToTextboxByID(driver, "user_password", "acb12345");
		
		log.info(" AddNewEmployee - Step 9: enter 'confirm password' textbox" );
		AddEmployeePage.enterToTextboxByID(driver, "re_password", "acb12345");
		
		log.info(" AddNewEmployee - Step 10: select 'status' checkbox = enable " );
		AddEmployeePage.selectDropdownByID(driver, "status", statusValue);
		
		log.info(" AddNewEmployee - Step 11: click 'save' button" );
		AddEmployeePage.clickToButtonByID(driver, "btnSave");
		PersonalDetailPage=Page_Generator.getPersonalDetaitPage(driver);
		
		log.info(" AddNewEmployee - Step 12: open 'Employee list' " );
		PersonalDetailPage.openSubMenuPage(driver, "PIM", "Employee List");
		EmployeeListPage=Page_Generator.getEmployeeLisPage(driver);
		
		log.info(" AddNewEmployee - Step 13: enter valid to 'employee Name' textbox" );
		EmployeeListPage.sleepInSecond(5);
		EmployeeListPage.enterToTextboxByID(driver, "empsearch_employee_name_empName", "KHOA");
		EmployeeListPage.sleepInSecond(5);
		
		log.info(" AddNewEmployee - Step 14: click 'search' button" );
		EmployeeListPage.clickToButtonByID(driver, "searchBtn");
		EmployeeListPage.sleepInSecond(5);
		
		log.info(" AddNewEmployee - Step 15: Verify emplyeee info at table" );
		verifyEquals(EmployeeListPage.getValueDisplayedAtTable(driver,"resultTable","Last Name","1"),"KHOA1123");
	}
	
	
	@Test
	public void Employee_2_Upload_Avatar() {
		
		
		
	}
	
	@Test
	public void Employee_3_Edit_Personal_Detail() {
	

	}
	
	
	@Parameters({"browser"})
	@AfterClass (alwaysRun = true)
	public void cleanBrower (String browerName) {
		{
			log.info("Post-Condition: Close brower" + browerName);
			cleanDriverInstance();
		}
	}
	WebDriver driver;	
	LoginPage_Object  LoginPage;
	DashboardPage_Object  DashbroadPage;
	PersonalDetail_Object  PersonalDetailPage;
	EmployeeList_Object  EmployeeListPage;
	AddEmployeePage_Object  AddEmployeePage;
	
}
