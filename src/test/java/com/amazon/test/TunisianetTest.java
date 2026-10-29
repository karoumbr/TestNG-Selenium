package com.amazon.test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.amazon.pages.*;
import commonLibs.CommonDriver;

public class TunisianetTest {

    CommonDriver cmnDriver;
    TunisianetHomepage homepage;
    ProductPage productPage;
    CartPage cartPage;
    LoginPage loginPage;
    WebDriver driver;

    String url = "https://www.tunisianet.com.tn/";

    @BeforeClass
    public void invokeBrowser() throws Exception {
        //https://storage.googleapis.com/chrome-for-testing-public/156.0.8078.47/win64/chromedriver-win64.zip
        //https://github.com/mozilla/geckodriver/releases/download/v0.37.1/geckodriver-v0.37.1-win64.zip
        //https://msedgedriver.microsoft.com/156.0.4314.42/edgedriver_win64.zip

        cmnDriver = new CommonDriver("edge");
        cmnDriver.setPageloadTimeout(60);
        cmnDriver.setElementDetectionTimeout(10);
        cmnDriver.navigateToFirstUrl(url);
        driver = cmnDriver.getDriver();

        homepage = new TunisianetHomepage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void openHomepage() {
        Assert.assertTrue(
            driver.getCurrentUrl().startsWith(url),
            "L'URL de la page d'accueil est incorrecte"
        );

        Assert.assertFalse(
            driver.getTitle().isBlank(),
            "Le titre de la page est vide"
        );
    }

    @Test(priority = 2)
    public void searchExistingProduct() throws Exception {
        homepage.searchProduct("BMAX MaxBook X15 Pro");

        Assert.assertTrue(
            homepage.isSearchResultDisplayed("BMAX MaxBook X15 Pro"),
            "Le produit recherché n'est pas affiché"
        );
    }

    @Test(priority = 3)
    public void searchUnknownProduct() throws Exception {
        homepage.searchProduct("PRODUIT_INEXISTANT_TEST_987654");

        Assert.assertTrue(
            homepage.isNoSearchResultDisplayed(),
            "Le site devrait signaler l'absence de résultats"
        );
    }

    @Test(priority = 4)
    public void selectProduct() throws Exception {
        homepage.searchProduct("BMAX MaxBook X15 Pro");
        homepage.selectFirstSearchResult();

        Assert.assertTrue(
            productPage.isProductTitleVisible(),
            "Le titre du produit n'est pas visible"
        );
    }

    @Test(priority = 5)
    public void addProductToCart() throws Exception {
        // Préparer la page produit indépendamment
        homepage.searchProduct("BMAX MaxBook X15 Pro");
        homepage.selectFirstSearchResult();

        productPage.addToCart();

        Assert.assertTrue(
            cartPage.isProductAddedToCart(),
            "L'ajout au panier n'a pas été confirmé"
        );
    }

    @Test(priority = 6, dependsOnMethods = "addProductToCart")
    public void verifyCart() throws Exception {
        cartPage.openCart();

        Assert.assertTrue(
            cartPage.isProductAddedToCart(),
            "Le produit devrait être présent dans le panier"
        );
    }

    @Test(priority = 7, dependsOnMethods = "addProductToCart")
    public void removeProductFromCart() throws Exception {
        cartPage.openCart();
        cartPage.removeProductFromCart();

        Assert.assertTrue(
            cartPage.isCartEmpty(),
            "Le panier devrait être vide après suppression"
        );
    }

    @Test(priority = 8)
    public void openLoginPage() throws Exception {
        homepage.goToLoginPage();

        Assert.assertTrue(
            loginPage.isLoginFormDisplayed(),
            "Le formulaire de connexion n'est pas affiché"
        );
    }

    @Test(priority = 9)
    public void invalidLogin() throws Exception {
        homepage.goToLoginPage();
        loginPage.login(
            "adresse-inexistante@example.com",
            "MotDePasseIncorrect123!"
        );

        Assert.assertTrue(
            loginPage.isErrorDisplayed(),
            "Un message d'erreur est attendu"
        );
    }

    @Test(priority = 10)
    public void openLaptopsCategory() throws Exception {
        driver.get(
            "https://www.tunisianet.com.tn/702-ordinateur-portable"
        );

        Assert.assertTrue(
            driver.getCurrentUrl().contains("ordinateur-portable"),
            "La page Ordinateur Portable n'est pas accessible"
        );

        Assert.assertTrue(
            homepage.isLaptopsCategoryDisplayed(),
            "La catégorie des ordinateurs portables n'est pas visible"
        );
    }

   /*  @Test(priority = 11)
    public void openContactPage() throws Exception {
        homepage.goToContactPage();

        Assert.assertTrue(
            homepage.isContactPageDisplayed(),
            "La page de contact n'est pas affichée"
        );
    }*/

    @AfterClass(alwaysRun = true)
    public void closeBrowser() throws Exception {
        if (cmnDriver != null) {
            cmnDriver.closeAllBrowsers();
        }
    }
}
