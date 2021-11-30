package LoginPage_action;

import org.openqa.selenium.WebDriver;

import Commons.BasePage;

public class Page_Generator extends BasePage{
	WebDriver driver;
	
	public Page_Generator(WebDriver driver) {
		this.driver=driver;
	}

	public static LoginPage_Object getLoginPage( WebDriver driver) {
		return new LoginPage_Object(driver);
	}
	
	public static AddEmployeePage_Object getAddEmployeePage( WebDriver driver) {
		return new AddEmployeePage_Object(driver);
	}
	
	public static DashboardPage_Object getDashboardPage( WebDriver driver) {
		return new DashboardPage_Object(driver);
	}
	
	public static EmployeeList_Object getEmployeeLisPage( WebDriver driver) {
		return new EmployeeList_Object(driver);
	}
	
	public static PersonalDetail_Object getPersonalDetaitPage( WebDriver driver) {
		return new PersonalDetail_Object(driver);
	}
}
