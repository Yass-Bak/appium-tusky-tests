package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ServerSelectionPage {
    private final WebDriver driver;

    // Locators extracted from the DOM Analysis
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

    public boolean isWhatsAnInstanceLinkVisible() {
        return !driver.findElements(whatsAnInstanceLink).isEmpty()
                && driver.findElement(whatsAnInstanceLink).isDisplayed();
    }

    public boolean isLoginWithBrowserMenuVisible() {
        return !driver.findElements(loginWithBrowserMenu).isEmpty()
                && driver.findElement(loginWithBrowserMenu).isDisplayed();
    }
}
