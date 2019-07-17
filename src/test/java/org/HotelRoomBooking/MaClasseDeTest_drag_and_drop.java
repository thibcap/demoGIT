package org.HotelRoomBooking;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MaClasseDeTest_drag_and_drop {

	
	WebDriver driver;
	String b = "firefox";
	String url= "http://localhost/TutorialHtml5HotelPhp/";
	WebDriverWait wait;
	@Before
	public void setup() throws Exception {
		BDD_outils.insertData("src\\main\\resources\\datasets\\insert_reservation.xml");
		choisirNavigateur(b);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		wait= new WebDriverWait(driver,4);
		
	
	}
	
	@After
	public void tearDown() throws Exception{
		System.out.println("\n@After");
		//driver.close();
		BDD_outils.deleteAllData("src\\main\\resources\\datasets\\delete_reservation.xml");
		
	}
	
	@Test
	public void testDragAndDrop() throws InterruptedException {
	
	//vérification de l'affichage de la page "HTML5 Hotel Room Booking"
	assertEquals("HTML5 Hotel Room Booking (JavaScript/PHP)", driver.findElement(By.xpath("//h1")).getText());
	
	//effectue un dragAndDrop de la cellule1 à la cellule 2
	WebElement cellule1=driver.findElement(By.xpath("//div[@class='scheduler_default_event scheduler_default_event_line0']"));
	WebElement cellule2=driver.findElement(By.xpath("//div[@class='scheduler_default_cell'][6]"));
	
	Actions actions = new Actions(driver);
	actions.clickAndHold(cellule1).moveToElement(cellule2).release(cellule2).build().perform();
	//attends 10 sec
	
	WebElement message=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[.='Update successful']"))));
	
	//verifie l'appartition du message "update successfull"
	assertTrue(message.isDisplayed());
	
	//attends 10 sec
	Thread.sleep(7000);
	
	//Vérifie la disparition du message "update successfull"
	assertFalse(message.isDisplayed());
	}
	

	public void choisirNavigateur(String browser) {
		switch (browser) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "src/main/resources/driver/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;

		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;

		default:
			System.out.println("browser mal renseigné");
		}
	}
}
