package Utitlties;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ExtentReportManager implements ITestListener   // extends  TestListenerAdapter
{
public ExtentSparkReporter sparkReporter;
public ExtentReports extent;
public ExtentTest test;

String repName;
public  void onStart(ITestContext testcontext)
{
	/*
	SimpleDateFormat df= new SimpleDateFormat("yyyy.mm.dd.mm.ss");
	Date dt=new Date();
	String curentdatetimesstamp=df.format(dt);
	*/
	String timeStamp= new SimpleDateFormat("yyyy.mm.dd.mm.ss").format(new Date());//Time stamp is for repeated test cases
	
	repName="Test-Report-"+ timeStamp+".html";
	sparkReporter=new ExtentSparkReporter(".\\Reports1\\"+repName);
	sparkReporter.config().setDocumentTitle("orange HRM Automation");//title of the report
	sparkReporter.config().setReportName("open functional Testing");//names of report
	sparkReporter.config().setTheme(Theme.DARK);
	extent=new ExtentReports();
	extent.attachReporter(sparkReporter);
	extent.setSystemInfo("Application", "orangeHRM");
	extent.setSystemInfo("Module", "Admin");
	extent.setSystemInfo("User Name", System.getProperty("user.name"));
	extent.setSystemInfo("Environment", "QA");
	
	String os= testcontext.getCurrentXmlTest().getParameter("os");
	extent.setSystemInfo("Operating System", os);
	
	String browser= testcontext.getCurrentXmlTest().getParameter("browser");
	extent.setSystemInfo("Browser", browser);
	
	List<String>includedGroups=testcontext.getCurrentXmlTest().getIncludedGroups();// if there is groups in testtng.xml here get lists of testings---sanity smoke regression
	if (!includedGroups.isEmpty()) {// if includedGroups variable have list then print the list of tests
	extent.setSystemInfo("Groups", includedGroups.toString());
		
	}
}
	public void onTestSuccess(ITestResult result )
	{
		test=extent.createTest(result.getTestClass().getName());// here get class name
		test.assignCategory(result.getMethod().getGroups()); // here get methods names and groups names
		test.log(Status.PASS,result.getName()+"got successfully executed" ); // here get pass message
		
	}
	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());// here get class name
		test.assignCategory(result.getMethod().getGroups()); // here get methods names and groups names
		test.log(Status.FAIL, result.getName()+"got failed");// here get fail message
		test.log(Status.INFO, result.getThrowable().getMessage());// why this is failed that message is displayed
		
		// in case screenshot was not get it throughs a file not found exception inorder to handle that exception  we put try catch
		try {
			//here we create Baseclass object for access captureScreen method
			//new BaseClass() here base class create new object, inside object have new webdiver in order to match this webdriver we put baseclass webdriver is static
			String imgPath=new TestBase.BaseClass().captureScreen(result.getName());// ontestfailure method is triggered then capture screenshot method is executes
			test.addScreenCaptureFromPath(imgPath); // this imgpath is attached screenshot to report
		} 
		catch (Exception e1) {
			e1.printStackTrace();
		}
		
	  }
	 public void onTestSkipped(ITestResult result) {
		    test=extent.createTest(result.getTestClass().getTestName());
		    test.assignCategory(result.getMethod().getGroups()); 
		    test.log(Status.SKIP, result.getName()+"got skipped");
		    test.log(Status.INFO, result.getThrowable().getMessage());
		  } 
	  public void onFinish(ITestContext testContext) 
	  {
		  extent.flush();
		  
		  // this part is automatically open the report by this below code
		  // we open the extent report manually from the reports folder so in order to overcome this issue we automatically open the extent report by this below code
		  String pathOfExtentReport=System.getProperty("user.dir")+"\\Reports1\\"+repName;
		  File extentReport= new File(pathOfExtentReport);
		// in case extent report was not get it throughs a file not found exception inorder to handle that exception  we put try catch
		  try {
			  Desktop.getDesktop().browse(extentReport.toURI());
			  
			
		     } catch (Exception e) {
			e.printStackTrace();
		     }
	  }
	  
}

