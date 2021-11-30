//tip lay ra danh sach menu trên F12
// $x(xpath").forEach(function(text){console.log(text);});

package Commons;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import LoginPage_UI.BasePage_UI;


public class BasePage {
	public BasePage() {}
	
	public static BasePage getBasePage() {
		return new BasePage();
	}
	
	public void openURL (WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}
	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}
	public String getPageUrl (WebDriver driver) {
		return driver.getCurrentUrl();
	}
	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}
	public Alert waitForAlertPresence(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, short_timeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	public void acceptAlert(WebDriver driver) {
		alert= waitForAlertPresence(driver);
		alert.accept();
	}
	public void cancelAlert(WebDriver driver) {
		alert=waitForAlertPresence(driver);
		alert.dismiss();
	}
	public void sendkeyAlert(WebDriver driver, String value) {
		alert=waitForAlertPresence(driver);
		alert.sendKeys(value);
	}
	public String getAlertText(WebDriver driver, String value) {
		alert=waitForAlertPresence(driver);
		 return alert.getText();
	}
	public void switchToWindowByTitle(WebDriver driver, String expectedWindowTitle) {
		// Lấy ra tất cả id của window/tab đang có
		// dùng set do chỉ lấy ra giá trị duy nhất, còn List sẽ lấy ra giá trị trùng nhau
		Set<String> allWindows = driver.getWindowHandles();
		// Dùng vòng lặp duyệt qua các ID
		for (String listWindown : allWindows) {
			// Swtich vào từng window/tab
			driver.switchTo().window(listWindown);
			// Lấy ra title của tab đó
			String currentTitlePage = driver.getTitle();
			// Kiểm tra title của page nào bằng với title của page expected thì truy�?n vào
			if (currentTitlePage.equals(expectedWindowTitle)) {
				// Thoát vòng lặp
				break;
			}
		}
	}
	public void switchToWindowByID(WebDriver driver, String pageID) {
		// Lấy ra tất cả id của window/tab đang có
		// dùng set do chỉ lấy ra giá trị duy nhất, còn List sẽ lấy ra giá trị trùng nhau
		Set<String> allWindows = driver.getWindowHandles();
		// Dùng vòng lặp duyệt qua các ID
		for (String listWindown : allWindows) {
			// Kiểm tra title của page nào bằng với title của page expected thì truy�?n vào
			if (!listWindown.equals(pageID)) {
				// Swtich vào từng window/tab
				driver.switchTo().window(listWindown);
				// Thoát vòng lặp
				break;
			}
		}
	}
	public boolean closeAllWindowExceptParent(WebDriver driver, String parentID) {
		// Lấy ra tất cả ID
		Set<String> idWindow = driver.getWindowHandles();
		// Dùng vòng lặp duyệt qua từng ID
		for (String allidwindow : idWindow) {
			// Nếu như ID khác với ID của parent
			if (!allidwindow.equals(parentID)) {
				// Switch vào ID đó
				driver.switchTo().window(allidwindow);
				driver.close();
			}
		}

		driver.switchTo().window(parentID);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}
	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	public void backToPage(WebDriver driver) {
		driver.navigate().back();;
	}
	public void refeshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}
	
	public By getByXpath(String locator) {
		return By.xpath(locator);
	}
	public WebElement getElement(WebDriver driver, String locator) {
		return driver.findElement(getByXpath(locator));
	}
	
	public WebElement getElement(WebDriver driver, String locator,String...params) {
		return driver.findElement(getByXpath(getDynamicLocator(locator, params)));
	}
	
	public List<WebElement> getElements(WebDriver driver, String locator) {
		return driver.findElements(getByXpath(locator));
	}
	
	public String getDynamicLocator(String locator, String...params) {
		return String.format(locator, (Object[])params);
	}
	// no dynamic locator
	public void clickToElement(WebDriver driver, String locator) {
		getElement(driver,locator).click();
	}
	//dynamic locator
	public void clickToElement(WebDriver driver, String locator, String...params) {
		waitForElementClickable(driver, locator, params);
		getElement(driver,getDynamicLocator(locator,params)).click();
	}

	public void sendKeyToElement(WebDriver driver, String locator, String value) {
		getElement(driver,locator).clear();
		getElement(driver,locator).sendKeys(value);
	}
	
	
	public void sendKeyToElement(WebDriver driver, String locator, String value, String...params) {
		locator=getDynamicLocator(locator,params);
		getElement(driver,locator).clear();
		getElement(driver,locator).sendKeys(value);
	}
	
	
	public int getElementSize(WebDriver driver, String locator) {
		return getElements(driver,locator).size();
	}
	
	public int getElementSize(WebDriver driver, String locator,String...params) {
		locator=getDynamicLocator(locator,params);
		return getElements(driver,locator).size();
	}
	
	
	
	public void selectDropdownByText(WebDriver driver, String locator, String itemText) {
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}
	
	public void selectDropdownByText(WebDriver driver, String locator, String itemText, String...params) {
		locator=getDynamicLocator(locator, params);
		select = new Select(getElement(driver, locator));
		select.selectByVisibleText(itemText);
	}
	
	public String getSelectedDropdown(WebDriver driver, String locator) {
		select = new Select(getElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}
	
	public String getSelectedDropdown(WebDriver driver, String locator, String...params) {
		locator=getDynamicLocator(locator, params);
		select = new Select(getElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}
	
	
	public boolean isDropdownMultipe(WebDriver driver, String locator) {
		select = new Select(getElement(driver, locator));
		return select.isMultiple();
	}
	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator, String expectedItem) {
		getElement(driver, parentLocator).click();
		sleepInSecond(1);

		explicitWait = new WebDriverWait(driver, short_timeout);
		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);
				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}
	public String getElementAttributeValue(WebDriver driver, String locator, String attributeName) {
		return getElement(driver,locator).getAttribute(attributeName);
	}
	
	public String getElementAttributeValue(WebDriver driver, String locator, String attributeName, String...params) {
		return getElement(driver,getDynamicLocator(locator, params)).getAttribute(attributeName);
	}
	
	public String getElementText(WebDriver driver, String locator) {
		return getElement(driver,locator).getText().trim();
	}
	
	public String getElementText(WebDriver driver, String locator, String...params) {
		return getElement(driver,getDynamicLocator(locator, params)).getText().trim();
	}
	
	public void checktoCheckboxOrRadio(WebDriver driver, String locator) {
		if(!isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}
	
	public void checktoCheckboxOrRadio(WebDriver driver, String locator, String...params) {
		locator=getDynamicLocator(locator, params);
		if(!isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}
	
	public void unchecktoCheckbox(WebDriver driver, String locator) {
		if(isElementSelected(driver, locator)) {
			getElement(driver, locator).click();
		}
	}
	
	public boolean isElementDisplayed(WebDriver driver, String locator) {
		try {
			return getElement(driver,locator).isDisplayed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
	
	public void overrideTIMEOUT(WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		
	}
	
	public boolean isElementUNDisplayed(WebDriver driver, String locator) {
		System.out.println("Start time = " + new Date().toString());
		overrideTIMEOUT(driver,short_timeout);
		List<WebElement>elements = getElements(driver, locator);
		overrideTIMEOUT(driver,long_timeout);
		if (elements.size()==0) {
			System.out.println("Element NOT in DOM");
			System.out.println("End time = " + new Date().toString());
			return true;
		} else if (elements.size()>0 && !elements.get(0).isDisplayed()) {
			System.out.println("Element in DOM but NOT in UI");
			System.out.println("End time = " + new Date().toString());
			return true;
		} else {
			System.out.println("Element in DOM and UI");
			return false;
		}
	}
	
	public boolean isElementDisplayed(WebDriver driver, String locator, String...params) {
		return getElement(driver, getDynamicLocator(locator, params)).isDisplayed(); 
	}
	 
	public boolean isElementEnable(WebDriver driver, String locator) {
		return getElement(driver, locator).isEnabled(); 
	}
	public boolean isElementSelected(WebDriver driver, String locator) {
		return getElement(driver, locator).isSelected(); 
	}
	public WebDriver switchToIframeByElement(WebDriver driver, String locator) {
		return driver.switchTo().frame(getElement(driver, locator));
	}
	public WebDriver switchToDefaultContent(WebDriver driver) {
		return driver.switchTo().defaultContent();
	}
	
	public void hoverToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.moveToElement(getElement(driver, locator)).perform();
	}
	
	public void hoverToElement(WebDriver driver, String locator, String...params) {
		 
		action = new Actions(driver);
		action.moveToElement(getElement(driver, getDynamicLocator(locator, params))).perform();
	}
	
	public void doubleClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.doubleClick(getElement(driver, locator)).perform();
	}
	public void rightClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.contextClick(getElement(driver, locator)).perform();
	}
	public void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
		action = new Actions(driver);
		action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
	}
	public void pressKeyToElement(WebDriver driver, String locator, Keys key) {
		action = new Actions(driver);
		action.sendKeys(getElement(driver, locator), key).perform();
	}
	
	public void pressKeyToElement(WebDriver driver, String locator, Keys key, String...params) {
		action = new Actions(driver);
		locator= getDynamicLocator(locator, params);
		action.sendKeys(getElement(driver, locator), key).perform();
	}
	
	public Object executeForBrowser(WebDriver driver, String javaScript) {
		jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}
	public String getInnerText(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}
	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}
	public void scrollToBottomPage(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}
	public void navigateToUrlByJS(WebDriver driver, String url) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}
	public void highlightElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}
	public void clickToElementByJS(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getElement(driver, locator));
	}
	public void scrollToElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator));
	}
	
	public void scrollToElement(WebDriver driver, String locator, String...params) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator, params));
	}
	
	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver, locator));
	}
	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, locator));
	}
	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, short_timeout);
		jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}
	public String getElementValidationMessage(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(driver, locator));
	}
	public boolean isImageLoaded(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getElement(driver, locator));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void waitForElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, short_timeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
	}
	
	public void waitForElementVisible(WebDriver driver, String locator,String...params) {
		locator = getDynamicLocator(locator, params);
		explicitWait = new WebDriverWait(driver, short_timeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
	}
	
	public void waitForAllElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, short_timeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));
	}
	public void waitForElementClickable(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, short_timeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
	}
	
	public void waitForElementClickable(WebDriver driver, String locator, String...params) {
		locator = getDynamicLocator(locator, params);
		explicitWait = new WebDriverWait(driver, short_timeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
	}
	
	
	public void waitForElementInVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, short_timeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
	}
	
	public void waitForElementInVisible(WebDriver driver, String locator, String...params) {
		locator = getDynamicLocator(locator, params);
		explicitWait = new WebDriverWait(driver, short_timeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
	}
	

	//----------------------------------------------------/specific case/--------------------------------------------------	
	public void enterToTextboxByID(WebDriver driver, String textboxID, String value) {
		waitForElementVisible(driver, BasePage_UI.TEXTBOX_BY_ID, textboxID);
		sendKeyToElement(driver,  BasePage_UI.TEXTBOX_BY_ID, value,textboxID);
	}
	
	public void clickToCheckboxByLabel(WebDriver driver, String checkboxLabelName) {
		waitForElementClickable(driver, BasePage_UI.CHECKBOX_BY_LABEL , checkboxLabelName);
		checktoCheckboxOrRadio(driver, BasePage_UI.CHECKBOX_BY_LABEL ,checkboxLabelName);
	}
	
	public void clickToRadioByLabel(WebDriver driver, String radioLabelName) {
		waitForElementClickable(driver, BasePage_UI.RADIO_BY_LABEL , radioLabelName);
		checktoCheckboxOrRadio(driver, BasePage_UI.RADIO_BY_LABEL ,radioLabelName);
	}  
	
	public void selectDropdownByID(WebDriver driver, String dropdownID, String itemName) {
		waitForElementClickable(driver,BasePage_UI.DROPDOWN_BY_ID, dropdownID);
		selectDropdownByText(driver, BasePage_UI.DROPDOWN_BY_ID, itemName, dropdownID);
		
	}
	
	public String getDropdownSelectedValueByID(WebDriver driver, String dropdownID ) {
		waitForElementClickable(driver,BasePage_UI.DROPDOWN_BY_ID, dropdownID);
		return getSelectedDropdown(driver, BasePage_UI.DROPDOWN_BY_ID);
		
	}
	
	public void clickToButtonByID(WebDriver driver, String buttonId) {
		waitForElementClickable(driver, BasePage_UI.BUTTON_BY_ID, buttonId);
		clickToElement(driver, BasePage_UI.BUTTON_BY_ID,buttonId);
	}
	
	public void openMenuPage(WebDriver driver, String menuPageName) {
		waitForElementClickable(driver,BasePage_UI.MENU_BY_NAME, menuPageName);
		clickToElement(driver, BasePage_UI.MENU_BY_NAME,menuPageName);
	}
	
	
	public void openSubMenuPage(WebDriver driver, String menuPageName, String subMenuPageName) {
		waitForElementClickable(driver,BasePage_UI.MENU_BY_NAME, menuPageName);
		clickToElement(driver, BasePage_UI.MENU_BY_NAME,menuPageName);
		waitForElementClickable(driver,BasePage_UI.MENU_BY_NAME, subMenuPageName);
		clickToElement(driver, BasePage_UI.MENU_BY_NAME,subMenuPageName);
	}
	
	public void openChildSubMenuPage(WebDriver driver, String menuPageName, String subMenuPageName, String ChildSubMenuPage) {
		waitForElementClickable(driver,BasePage_UI.MENU_BY_NAME, menuPageName);
		clickToElement(driver, BasePage_UI.MENU_BY_NAME,menuPageName);
		waitForElementVisible(driver,BasePage_UI.MENU_BY_NAME, subMenuPageName);
		hoverToElement(driver, BasePage_UI.MENU_BY_NAME,subMenuPageName);
		waitForElementClickable(driver,BasePage_UI.MENU_BY_NAME, ChildSubMenuPage);
		clickToElement(driver, BasePage_UI.MENU_BY_NAME,ChildSubMenuPage);
	}
	
	/**
	 * Get textbox value by textbox ID
	 * @author BANG
	 * @param driver
	 * @param textboxIDName
	 * @return attribute value
	 */
	public String getTextboxValueByID(WebDriver driver, String textboxIDName) {
		waitForElementVisible(driver, BasePage_UI.TEXTBOX_BY_ID,textboxIDName);
		return getElementAttributeValue(driver, BasePage_UI.TEXTBOX_BY_ID, "value",textboxIDName);
	}
	

	public String getValueDisplayedAtTable(WebDriver driver,String tableID, String headerName, String rowIndex) {
		int columnIndex=getElementSize(driver, BasePage_UI.TABLE_HEADER_BY_ID_AND_NAME, tableID,headerName)+1;
		waitForElementVisible(driver,  BasePage_UI.TABLE_ROW_BY_COLUMN_INDEX_AND_ROW_INDEX, tableID,rowIndex,String.valueOf(columnIndex));
		return getElementText(driver, BasePage_UI.TABLE_ROW_BY_COLUMN_INDEX_AND_ROW_INDEX, tableID,rowIndex,String.valueOf(columnIndex));
	}
	
	//----------------------------------------------------//--------------------------------------------------
	private Alert alert;
	private WebDriverWait explicitWait;
	private long short_timeout = 8;
	private long long_timeout = 8;
	private Select select;
	private JavascriptExecutor jsExecutor;
	private Actions action;
}
