package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Loginpage extends Basepage{

public Loginpage(WebDriver driver)
{
	super(driver);
}
@FindBy(xpath = "//input[@name='username']")WebElement usr;
@FindBy(xpath = "//input[@name='password']")WebElement psd;
@FindBy(xpath = "//button[@type='submit']")WebElement click;
public void user(String u)
{
	usr.sendKeys(u);
}
public void pass(String p)
{
	psd.sendKeys(p);
}
public void click()
{
	click.click();
}
}
