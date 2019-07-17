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

public class MaClasseDeTest2 {

	
	WebDriver driver;
	String b = "firefox";
	String url= "http://localhost/TutorialHtml5HotelPhp/";
	
	@Before
	public void setup() {
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//choisirNavigateur(b);
	}
	
	@After
	public void tearDown() throws Exception{
		System.out.println("\n@After");
		//driver.close();
		BDD_outils.deleteAllData("src\\main\\resources\\datasets\\delete_reservation.xml");
	}
	
	
	@Test
	public void testDeSchwitchFrame() throws Exception {
	//connexion à l'application TutorialHtml5HotelPhp
		choisirNavigateur(b);
		driver.get(url);
	
	//vérification de l'affichage de la page "HTML5 Hotel Room Booking"
		assertEquals("FAIL title n'est pas bon","HTML5 Hotel Room Booking (JavaScript/PHP)" , driver.getTitle());
	
	//clic sur la première cellule du planing
		WebElement cellule_resa = driver.findElement(By.xpath("//div[@class='scheduler_default_cell'][1]"));
		cellule_resa.click();
		Point locator = cellule_resa.getLocation();
		System.out.println("\n locator "+locator.getX()+" "+locator.getY());
	
	//gestion des frames : switch sur la popup
		 driver.switchTo().frame(0);
	
	//verification du titre de la pop up
		 assertEquals("New Reservation", driver.findElement(By.xpath("//h1")).getText());

	//ecriture du nom de la réservation
		 WebElement name = driver.findElement(By.name("name"));
		 name.sendKeys("resa1");
	
	// sauvegarde de la reservation
		 WebElement save = driver.findElement(By.xpath("//input[@value='Save']"));
		 save.click();
		 driver.switchTo().defaultContent();
	
	//gestion des frame : switch sur le contenu html par défaut
		 WebElement evenement = driver.findElement(By.xpath("//div[@class='scheduler_default_event_inner'][contains(.,'resa1')]"));
		 assertTrue(evenement.isDisplayed());
	
	//vérification de l'enregistrement de la réservation
		 assertTrue("FAIL location", evenement.getLocation().equals(locator));
		 
		 System.out.println("\n locator2  "+evenement.getLocation().getX()+" "+evenement.getLocation().getY());
		 
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
