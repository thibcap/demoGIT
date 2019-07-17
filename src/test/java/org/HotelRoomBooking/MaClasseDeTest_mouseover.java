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

public class MaClasseDeTest_mouseover {

	
	WebDriver driver;
	String b = "firefox";
	String url= "http://localhost/TutorialHtml5HotelPhp/";
	
	@Before
	public void setup() throws Exception {
		BDD_outils.insertData("src\\main\\resources\\datasets\\insert_reservation2.xml");
		choisirNavigateur(b);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		
	
	}
	
	@After
	public void tearDown() throws Exception{
		System.out.println("\n@After");
		//driver.close();
		//BDD_outils.deleteAllData("src\\main\\resources\\datasets\\delete_reservation.xml");
		
	}
	
	@Test
	
	public void testMouseHover() throws InterruptedException {
		
//		//vérification de l'affichage de la page "HTML5 Hotel Room Booking"
//		assertEquals("HTML5 Hotel Room Booking (JavaScript/PHP)", driver.findElement(By.xpath("//h1")).getText());
WebElement element=driver.findElement(By.xpath("//div[@class='scheduler_default_event scheduler_default_event_line0']"));
		
		//Opère le mouseHover sur la réservation
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
		
		//clic sur le bouton delete
		driver.findElement(By.xpath("//div[@class='scheduler_default_event_delete']")).click();

		
		//verifie l'appartition du message "Deleted"
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.xpath("//div[.='Deleted.']")).isDisplayed());
		
		//attends 10 sec
		Thread.sleep(7000);
		
		//Vérifie la disparition du message "Deleted"
		assertFalse(driver.findElement(By.xpath("//div[.='Deleted.']")).isDisplayed());
		
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
