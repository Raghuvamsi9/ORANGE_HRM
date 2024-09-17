package Utitlties;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager1 implements ITestListener {
    public ExtentReports ER;
    public ExtentSparkReporter ESR;
    public ExtentTest ET;
    String Repname;

    
    public void onStart(ITestContext testContext) {
        // Create a timestamp for the report
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Repname = "Test-Report-" + timeStamp + ".html";

        ER = new ExtentReports();
        ESR = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "Reports1" + File.separator + Repname);

        // Configure the ExtentSparkReporter
        ExtentSparkReporterConfig config = ESR.config();
        config.setDocumentTitle("orange HRM Automation");
        config.setReportName("Functional Testing");
        config.setTheme(Theme.DARK);

        ER.attachReporter(ESR);
        ER.setSystemInfo("Application", "OrangeHRM");
        ER.setSystemInfo("Module", "Login");
        ER.setSystemInfo("Username", "raghuvamsi");
        ER.setSystemInfo("User Name", System.getProperty("user.name"));
        ER.setSystemInfo("Environment", "QA");
        ER.setSystemInfo("Operating System", testContext.getCurrentXmlTest().getParameter("os"));
        ER.setSystemInfo("Browser", testContext.getCurrentXmlTest().getParameter("browser"));

        String os=testContext.getCurrentXmlTest().getParameter("os");
  	    ER.setSystemInfo("operating system", os);
  	    String browser=testContext.getCurrentXmlTest().getParameter("browser");
  	    ER.setSystemInfo("operating system", browser);
  	  
        
        List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includeGroups.isEmpty()) {
            ER.setSystemInfo("Groups", includeGroups.toString());
        }
    }

    
    public void onTestSuccess(ITestResult result) {
        ER.createTest(result.getTestClass().getName())
                .assignCategory(result.getMethod().getGroups())
                .log(Status.PASS, result.getName() + " got successfully executed");
    }

    
    public void onTestFailure(ITestResult result) {
        ER.createTest(result.getTestClass().getName())
                .assignCategory(result.getMethod().getGroups())
                .log(Status.FAIL, result.getName() + " got failed")
                .log(Status.INFO, result.getThrowable().getMessage());

        try {
            // Capture screenshot and attach to report
            String imgPath = new TestBase.BaseClass().captureScreen(result.getName());
            ET.addScreenCaptureFromPath(imgPath);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    
    public void onTestSkipped(ITestResult result) {
        ER.createTest(result.getTestClass().getName())
                .assignCategory(result.getMethod().getGroups())
                .log(Status.SKIP, result.getName() + " got skipped")
                .log(Status.INFO, result.getThrowable().getMessage());
    }

    
    public void onFinish(ITestContext testContext) {
        ER.flush();
 /*       try {
			  Desktop.getDesktop().browse(new File(System.getProperty("user.dir")+"\\Reports1\\"+Repname).toURI());
			  
			
		     } catch (Exception e) {
			e.printStackTrace();
		     }
    
    */
        // this part is automatically open the report by this below code
		  // we open the extent report manually from the reports folder so in order to overcome this issue we automatically open the extent report by this below code
		  String pathOfExtentReport1=System.getProperty("user.dir")+"\\Reports1\\"+Repname;
		  File extentReport1= new File(pathOfExtentReport1);
		// in case extent report was not get it throughs a file not found exception inorder to handle that exception  we put try catch
		  try {
			  Desktop.getDesktop().browse(extentReport1.toURI());
			  
			
		     } catch (Exception e) {
			e.printStackTrace();
		     }
    }
}
