package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

public class Homepage extends Basepage {
	public Homepage(WebDriver driver) {
		super(driver);
	}
	@FindBy(xpath = "//span[@class='oxd-userdropdown-tab']/img")WebElement targetpic;
	@FindBy(xpath = "//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")WebElement profile;
	@FindBy(xpath="//a[text()='Logout']")WebElement logoutHRM;
public boolean targetpicture()
{   try {
	return (targetpic.isDisplayed());// it gives true incase it give exception on time catch block catch the exception in the form of true
} catch (Exception e) {
	return false;//false
}
}

public void profile() {
	
	profile.click();	
}
public void logoutHRM()
{
	logoutHRM.click();
}

}
