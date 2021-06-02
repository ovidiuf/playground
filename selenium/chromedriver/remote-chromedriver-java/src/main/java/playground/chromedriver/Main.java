package playground.chromedriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        WebDriver remoteWebDriver = new RemoteWebDriver(new URL("http://127.0.0.1:9515"), new ChromeOptions());

        System.out.println(remoteWebDriver);
        remoteWebDriver.get("http://www.google.com/");
        Thread.sleep(5000);  // Let the user actually see something!
        WebElement searchBox = remoteWebDriver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();
        Thread.sleep(5000);  // Let the user actually see something!
        remoteWebDriver.quit();
    }
}
