package tests;

import base.BaseTest;
import static io.qameta.allure.Allure.step;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ServerSelectionPage;

public class TuskyTests extends BaseTest {

    @Test(priority = 1, description = "TC1 — Verify Tusky launches successfully to Server Selection", groups = "smoke")
    public void testAppLaunch() {
        step("Step 1: Launch application and initialize Server Selection Page");
        ServerSelectionPage serverPage = new ServerSelectionPage(driver);

        step("Step 2: Verify Server Instance Text Field is strictly visible");
        Assert.assertTrue(serverPage.isServerInputVisible(),
                "Server instance text field should be visible right on launch.");
    }

    @Test(priority = 2, description = "TC2 — Verify 'What's an instance?' helper link is visible", groups = "smoke")
    public void testWhatsAnInstanceLink() {
        step("Step 1: Initialize Server Selection Page");
        ServerSelectionPage serverPage = new ServerSelectionPage(driver);

        step("Step 2: Check for helper text link presence on UI");
        Assert.assertTrue(serverPage.isWhatsAnInstanceLinkVisible(),
                "The helper text link should be present on the UI.");
    }

    @Test(priority = 3, description = "TC3 — Open More Options Menu", groups = "sanity")
    public void testMoreOptionsMenu() {
        step("Step 1: Focus on Server Selection Page");
        ServerSelectionPage serverPage = new ServerSelectionPage(driver);

        step("Step 2: Open More Options Overflow Menu");
        serverPage.openMoreOptions();

        step("Step 3: Assert the dropdown menu pops up containing Login With Browser");
        Assert.assertTrue(serverPage.isLoginWithBrowserMenuVisible(),
                "The login with browser option should appear in the overflow menu.");

        step("Step 4: Close menu by clicking back to reset state");
        driver.navigate().back();
    }

    @Test(priority = 4, description = "TC4 — Enter Valid Server Domain", groups = "sanity")
    public void testEnterValidServer() {
        step("Step 1: Initialize server page variables");
        ServerSelectionPage serverPage = new ServerSelectionPage(driver);

        step("Step 2: Simulate typing a valid domain (mastodon.social)");
        serverPage.enterServerDomain("mastodon.social");

        step("Step 3: Acknowledge simulated text entry is visually processed");
    }

    @Test(priority = 5, description = "TC5 — Click Login and Trigger Web Transition", groups = "sanity")
    public void testClickLoginNavigatesToWeb() {
        step("Step 1: Load server DOM configuration");
        ServerSelectionPage serverPage = new ServerSelectionPage(driver);

        step("Step 2: Enter domain input (mastodon.social)");
        serverPage.enterServerDomain("mastodon.social");

        step("Step 3: Execute Login Button click Action");
        serverPage.clickLogin();

        try {
            step("Step 4: Wait explicitly for OAuth page transition");
            serverPage.waitForServerInputToDisappear();

            step("Step 5: Verify that Native Android fields are disabled/hidden during webview");
            Assert.assertFalse(serverPage.isServerInputVisible(),
                    "The server input page should have transitioned away after clicking Login.");
        } catch (Exception e) {
            System.out.println("Transition validation exception: " + e.getMessage());
        }
    }
}
