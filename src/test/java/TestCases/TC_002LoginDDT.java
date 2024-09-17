package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.Homepage;
import PageObjects.Loginpage;
import TestBase.BaseClass;
import Utitlties.DataProviders;


public class TC_002LoginDDT extends BaseClass {
	@Test(dataProvider ="LoginData1",dataProviderClass = DataProviders.class ,groups="Sanity")
	public void loginHrm1(String uname,String pwd,String expResult)
	{
		try {
	l.info("*** starting login page***");
	Loginpage pp1=new Loginpage(driver);
	l.info("enter username");
	pp1.user(uname);
	l.info("enter password");
	pp1.pass(pwd);
	pp1.click();
	Homepage hpp=new Homepage(driver);
	l.info("target page displayed");
	 boolean targetpage = hpp.targetpicture();
	   if(expResult.equalsIgnoreCase("valid")) //data is valid
	   {
		   if (targetpage==true)//login success
		   { 
			 Thread.sleep(1000);
			 hpp.profile();  
		 	 hpp.logoutHRM();//logout  after assert nothing will executes
			 Assert.assertTrue(true);//test pass  
		   }
		   else 
		   {
			 Assert.assertTrue(false);//test fail
		   }
	   }
//	   if(expResult.equalsIgnoreCase("invalid")) //data is valid
	   else
	   {
			if (targetpage==true)//login success
			{   
				Thread.sleep(1000);
				hpp.profile();  
			 	hpp.logoutHRM();//logout  after assert nothing will executes
			  Assert.assertTrue(false);//test fail
			}
			else 
			{
			  Assert.assertTrue(true);//test pass -here get both are invalid then it is negative test case that why it pass
			}
	   }
	l.info("*** ending login page***");
		} catch (Exception e) {
		Assert.fail();
		}
	}

	}

