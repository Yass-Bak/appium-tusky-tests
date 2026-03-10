package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * ServerSelectionPage class to handle server selection
 */
public class ServerSelectionPage {
    private final WebDriver driver;

    // Locators extracted from the DOM
    private final By serverDomainInput = AppiumBy.id("com.keylesspalace.tusky:id/domainEditText");
    private final By loginButton = AppiumBy.id("com.keylesspalace.tusky:id/loginButton");
    private final By whatsAnInstanceLink = AppiumBy.id("com.keylesspalace.tusky:id/whatsAnInstanceTextView");
    private final By moreOptionsButton = AppiumBy.accessibilityId("More options");
    private final By loginWithBrowserMenu = AppiumBy.xpath("//*[@text='Login with Browser']");

    public ServerSelectionPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterServerDomain(String domain) {
        driver.findElement(serverDomainInput).sendKeys(domain);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void openMoreOptions() {
        driver.findElement(moreOptionsButton).click();
    }

    // Assertions
    public boolean isServerInputVisible() {
        return !driver.findElements(serverDomainInput).isEmpty() && driver.findElement(serverDomainInput).isDisplayed();
    }

    public void waitForServerInputToDisappear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(serverDomainInput));
    }

    public boolean isWhatsAnInstanceLinkVisible() {
        return !driver.findElements(whatsAnInstanceLink).isEmpty()
                && driver.findElement(whatsAnInstanceLink).isDisplayed();
    }

    public boolean isLoginWithBrowserMenuVisible() {
        return !driver.findElements(loginWithBrowserMenu).isEmpty()
                && driver.findElement(loginWithBrowserMenu).isDisplayed();
    }
}
