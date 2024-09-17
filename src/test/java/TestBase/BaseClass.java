package TestBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class BaseClass {
	public  static WebDriver driver;
	 public Properties p;
	 public Logger l;
	@Parameters({"os","browser"})
	@BeforeClass(groups= {"Sanity"})
	public void openbrowser(String os ,String browser) throws IOException
	{
		FileReader f=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(f);
	    if (p.getProperty("execution_env").equalsIgnoreCase("remote")) 
	    {
		  DesiredCapabilities Capabilities=new DesiredCapabilities();
		  //os
		  if (os.equalsIgnoreCase("windows")) 
		     {
			  Capabilities.setPlatform(Platform.WIN11);
		     }
		  else if (os.equalsIgnoreCase("mac")) 
		     {
			  Capabilities.setPlatform(Platform.MAC);
		     }
		  else if (os.equalsIgnoreCase("linux")) 
		     {
			  Capabilities.setPlatform(Platform.LINUX);
		     }
		  else
		     {
			  System.out.println("no matching os ");
		     }
		  //browser
		  switch (browser.toLowerCase())
		  {
		  case "firefox":Capabilities.setBrowserName("firefox");break;
		  case "chrome":Capabilities.setBrowserName("chrome");break;
		  case "edge":Capabilities.setBrowserName("MicrosoftEdge");break;	
		  default:System.out.println("no matching browser");return;
		  }
		  
	driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), Capabilities);
     } 
	
	  switch (browser.toLowerCase()) {
	case "chrome":
		driver=new ChromeDriver();
		break;
	case "edge":
		driver=new EdgeDriver();
		break;
	case "firefox":
		driver=new FirefoxDriver();
		break;
	default:
		System.out.println("no browser");
		return;
	}
	  l=LogManager.getLogger(this.getClass());
	  driver.manage().deleteAllCookies();
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  driver.get(p.getProperty("AppUrl"));
	  
	}
	
	@AfterClass(groups= {"Sanity"})
	public void closebrowser()
	{
		driver.quit();
	}
	public String captureScreen(String tname)
    {
    	String timeStamp=new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
    	TakesScreenshot takesScreenShot=(TakesScreenshot)driver;
    	File sourceFile=takesScreenShot.getScreenshotAs(OutputType.FILE);
    	String targetFilePath=System.getProperty("user.dir")+"\\screenshotsHRM\\"+tname+"_"+timeStamp+"_"+".jpg";
    	File targetFile=new File(targetFilePath);
    	sourceFile.renameTo(targetFile);
		return targetFilePath;// this return  will send screenshot to report otherwise it was in screenshot folder
    	
    }
}
