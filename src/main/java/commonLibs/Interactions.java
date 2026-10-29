package commonLibs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Interactions {

    private WebDriver driver;

    public Interactions(WebDriver driver) {
        this.driver = driver;
    }

    // ✅ Cliquer sur un élément
    public void clickElement(WebElement element) throws Exception {
        if (element.isDisplayed() && element.isEnabled()) {
            element.click();
        } else {
            throw new Exception("Element not clickable: " + element);
        }
    }

    // ✅ Écrire du texte dans un champ
    public void setText(WebElement element, String text) throws Exception {
        if (element.isDisplayed()) {
            element.clear();
            element.sendKeys(text);
        } else {
            throw new Exception("Element not visible: " + element);
        }
    }

    // ✅ Effacer le texte
    public void clearText(WebElement element) throws Exception {
        if (element.isDisplayed()) {
            element.clear();
        }
    }

    // ✅ Sélectionner dans un menu déroulant par texte visible
    public void selectByVisibleText(WebElement element, String visibleText) throws Exception {
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    // ✅ Passer la souris sur un élément
    public void hoverOverElement(WebElement element) throws Exception {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    // ✅ Récupérer le texte d’un élément
    public String getText(WebElement element) {
        return element.getText();
    }

    // ✅ Vérifier si un élément est visible
    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
