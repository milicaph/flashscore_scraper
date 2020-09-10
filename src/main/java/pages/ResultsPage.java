package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "onetrust-accept-btn-handler")
    private
    WebElement cookies;
    @FindBy(css = "div[id*=g_1_]")
    private
    List<WebElement> matches;
    private
    @FindBy(css = "a[class*=event__more]")
    WebElement showMore;

    public ResultsPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    public void acceptCookies() {
        try {Thread.sleep(1000);} catch(Exception e) { }
        cookies.click();
    }

    private boolean ifPresent() {
        if(driver.findElements(By.cssSelector("a[class*=event__more]")).size() > 0 ){
            return true;
        } else { return false; }
    }

    public void clickMore() {
        //acceptCookies();
        try {Thread.sleep(3000);} catch(Exception e) { }
        try {
            while (ifPresent()) {
                showMore.click();
                try { Thread.sleep(5000); } catch (Exception e) { }
            }
        } catch (Exception E) { showMore.click(); }
    }

    public ArrayList<String> getMatchURLS() {
        ArrayList<String> list = new ArrayList<>();
        for(WebElement match : matches){
            String matchId = match.getAttribute("id")
                                  .substring(4);
            String matchURL = "https://www.flashscore.com/match/" + matchId + "/#match-summary";
            list.add(matchURL);
        }
        return list;
    }




}
