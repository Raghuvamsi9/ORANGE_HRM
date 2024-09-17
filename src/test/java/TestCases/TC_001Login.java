package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.Homepage;
import PageObjects.Loginpage;
import TestBase.BaseClass;

public class TC_001Login extends BaseClass{
@Test(groups = {"Sanity"})
public void loginHrm()
{
	try {
l.info("*** starting login page***");
Loginpage pp1=new Loginpage(driver);
l.info("enter username");
pp1.user(p.getProperty("UserName"));
l.info("enter password");
pp1.pass(p.getProperty("PassWord"));
pp1.click();
Homepage hpp=new Homepage(driver);
l.info("target page displayed");
 boolean status2 = hpp.targetpicture();
if (status2==true) {
	Assert.assertTrue(true);
} else {
	Assert.assertTrue(false);
}
l.info("*** ending login page***");
	} catch (Exception e) {
	Assert.fail();
	}
}

}
