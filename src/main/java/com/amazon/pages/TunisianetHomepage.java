
package com.amazon.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TunisianetHomepage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // À vérifier avec l'inspecteur du navigateur
    private final By searchInput = By.cssSelector(
        "input[name='s']"
    );

    private final By searchButton = By.cssSelector(
        "button[type='submit']"
    );

    private final By productLinks = By.cssSelector(
        ".product-title a, h2 a"
    );

    public TunisianetHomepage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }

    public void searchProduct(String product)
            throws InterruptedException {

        WebElement input = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                searchInput
            )
        );

        input.clear();
        input.sendKeys(product);
        input.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.or(
            ExpectedConditions.presenceOfElementLocated(
                productLinks
            ),
            ExpectedConditions.urlContains("search")
        ));
    }

    public boolean isSearchResultDisplayed(String product) {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                        "//*[contains(translate(normalize-space(.),"
                        + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + " 'abcdefghijklmnopqrstuvwxyz'),"
                        + " '" + product.toLowerCase() + "')]"
                    )
                )
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isNoSearchResultDisplayed() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(
                        ".page-not-found, .no-products, .alert-warning, "
                        + ".product-list-empty"
                    )
                )
            ).isDisplayed();
        } catch (TimeoutException e) {
            return driver.getPageSource().toLowerCase()
                .contains("aucun produit");
        }
    }

    public void selectFirstSearchResult() {
        wait.until(
            ExpectedConditions.elementToBeClickable(productLinks)
        ).click();
    }

    public boolean isLaptopsCategoryDisplayed() {
        return driver.getPageSource().toLowerCase()
            .contains("ordinateur portable");
    }

    public void goToLoginPage() {
        driver.get(
            "https://www.tunisianet.com.tn/connexion"
        );
    }

   /*  public void goToContactPage() {
        // À remplacer par le lien de contact réellement observé
        // dans le site.
        driver.findElement(
            By.partialLinkText("Contact")
        ).click();
    }

    public boolean isContactPageDisplayed() {
        return driver.getCurrentUrl().toLowerCase()
            .contains("contact");
    }*/
}
