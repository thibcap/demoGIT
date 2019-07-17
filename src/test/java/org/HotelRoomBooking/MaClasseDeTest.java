package org.HotelRoomBooking;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class MaClasseDeTest {

	
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
	assertEquals("HTML5 Hotel Room Booking (JavaScript/PHP)", driver.findElement(By.xpath("//h1")).getText());
	
	//clic sur la première cellule du planing
	WebElement cellule1 = driver.findElement(By.xpath("//div[@class='scheduler_default_cell']"));
	String position = cellule1.getAttribute("style").substring(0, 46);
	System.out.println(" voici la position de la cellule sélectionnée par le test : "+ position);
	cellule1.click();
	
	
	//gestion des frame : switch sur la popup
	driver.switchTo().frame(0);
	
	//verification du titre de la pop up
	assertEquals("New Reservation", driver.findElement(By.xpath("//h1")).getText());

	//ecriture du nom de la réservation
	driver.findElement(By.id("name")).sendKeys("resa1");
	
	// sauvegarde de la reservation
	driver.findElement(By.xpath("//input[@value='Save']")).click();
	
	//gestion des frame : switch sur le contenu html par défaut
	driver.switchTo().defaultContent();
	
	//vérification de l'enregistrement de la réservation
	try {
		WebElement reservation = driver.findElement(By.xpath("//div[@class='scheduler_default_event_inner']"));
		assertTrue(reservation.getText().contains("resa1"));
		assertTrue(reservation.findElement(By.xpath("..")).getAttribute("style").contains(position));
	}
	catch(Exception e) {
		System.out.println(" [[ECHEC]] la reservation ne semble pas avoir été enregistrée");
		throw e;
	}
	
	
	System.out.println("[[SUCCES]] la reservation a bien été enregistrée");
	
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
