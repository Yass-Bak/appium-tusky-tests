package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class AppiumListener implements WebDriverListener {

    private static final Logger logger = LogManager.getLogger(AppiumListener.class);

    @Override
    public void beforeClick(WebElement element) {
        logger.info("Ready to click on element: " + getElementDescription(element));
    }

    @Override
    public void afterClick(WebElement element) {
        logger.info("Successfully clicked on element: " + getElementDescription(element));
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        if (keysToSend != null && keysToSend.length > 0) {
            logger.info("Typing '" + keysToSend[0] + "' into element...");
        }
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        if (keysToSend != null && keysToSend.length > 0) {
            logger.info("Successfully typed text into element!");
        }
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        logger.info("Searching for element by locator: " + locator.toString());
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        logger.info("Successfully found element.");
    }

    private String getElementDescription(WebElement element) {
        try {
            String text = element.getText();
            if (text != null && !text.isEmpty()) {
                return "'" + text + "'";
            }
            return element.toString();
        } catch (Exception e) {
            return "Unknown Element";
        }
    }
}
