/*
package com.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import commonLibs.Interactions;

public class LoginPage {

    private WebDriver driver;
    private Interactions elementControl;

    @CacheLookup
    @FindBy(name = "email")
    private WebElement emailBox;

    @CacheLookup
    @FindBy(name = "password")
    private WebElement passwordBox;

    @FindBy(id = "submit-login")
    private WebElement signInButton;

    @FindBy(css = ".alert-danger")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        elementControl = new Interactions(driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) throws Exception {
        elementControl.setText(emailBox, email);
        elementControl.setText(passwordBox, password);
        elementControl.clickElement(signInButton);
    }

    public boolean isErrorDisplayed() {
        return elementControl.isElementDisplayed(errorMessage);
    }
}
*/

package com.amazon.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.cssSelector(
        "input[name='email']"
    );

    private final By passwordInput = By.cssSelector(
        "input[name='password']"
    );

    private final By submitButton = By.cssSelector(
        "button#submit-login, "
        + "button[name='submit-login']"
    );

    private final By errorMessage = By.cssSelector(
        ".alert-danger, .help-block, "
        + ".form-error, .invalid-feedback"
    );

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }

    public boolean isLoginFormDisplayed() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    emailInput
                )
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void login(String email, String password) {
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                emailInput
            )
        ).sendKeys(email);

        driver.findElement(passwordInput).sendKeys(password);

        wait.until(
            ExpectedConditions.elementToBeClickable(
                submitButton
            )
        ).click();
    }

    public boolean isErrorDisplayed() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    errorMessage
                )
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
