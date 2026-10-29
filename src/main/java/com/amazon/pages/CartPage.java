/*
package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import commonLibs.Interactions;

public class CartPage {

    private WebDriver driver;
    private Interactions elementControl;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        elementControl = new Interactions(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isProductAddedToCart() {
        return driver.getPageSource().contains("Produit ajouté au panier avec succès");
    }

    public void openCart() throws Exception {
        WebElement cartIcon = driver.findElement(By.cssSelector("a[href*='panier']"));
        elementControl.clickElement(cartIcon);
    }

    public void removeProductFromCart() throws Exception {
          Thread.sleep(2000);
        WebElement deleteButton = driver.findElement(By.cssSelector(".cart-item .remove-from-cart"));
        elementControl.clickElement(deleteButton);
        Thread.sleep(2000);
    }
    public boolean isCartEmpty() {
        String page = driver.getPageSource().toLowerCase();
        return page.contains("il n'y a plus d'articles dans votre panier");
    }

}
*/


package com.amazon.pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }

    public void openCart() {
        driver.get(
            "https://www.tunisianet.com.tn/commande"
        );
    }

    public boolean isProductAddedToCart() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(
                        ".cart-overview, .cart-items, "
                        + "#cart, .cart-container"
                    )
                )
            ).isDisplayed()
                && !isCartEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isCartEmpty() {
        String page = driver.getPageSource().toLowerCase();

        return page.contains("votre panier est vide")
            || page.contains("your shopping cart is empty");
    }

    public void removeProductFromCart() {
        By removeButton = By.cssSelector(
            ".remove-from-cart, "
            + "a.remove-from-cart, "
            + "[data-link-action='delete-from-cart']"
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(
                removeButton
            )
        ).click();

        wait.until(driver ->
            isCartEmpty()
        );
    }
}
