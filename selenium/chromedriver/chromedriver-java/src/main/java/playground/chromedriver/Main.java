package playground.chromedriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com/");
        Thread.sleep(5000);  // Let the user actually see something!
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();
        Thread.sleep(5000);  // Let the user actually see something!
        driver.quit();
    }
}
