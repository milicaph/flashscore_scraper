package pages;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.util.ArrayList;



public class GamePage {
    private static String teams = "div.tname__text > a.participant-imglink",
                         allEvents = "div[class*=incidentRow]",
                         awayEventsOdd = "div.detailMS__incidentRow.incidentRow--away.odd",
                         awayEventsEven = "div.detailMS__incidentRow.incidentRow--away.even",
                         homeEventOddMin = "div.detailMS__incidentRow.incidentRow--home.odd",
                         homeEventEvenMin = "div.detailMS__incidentRow.incidentRow--home.even",
                         date = "utime"; // id

    private WebDriver driver;
    private WebDriverWait wait;

    public GamePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    public String getHTML(){
        try {
            Thread.sleep(3000);
        } catch (Exception E) {  }
        String html = driver.getPageSource();

        while(!html.contains("incidentRow"))
            html = driver.getPageSource();
        return  html;
    }

    public  Document getDocument(String html) throws IOException {
        Document document = Jsoup.parse(html);
        return document;
    }

    public Elements getElements(Document document, String locator){
        Elements elements = document.select(locator);
        return elements;
    }

    public  Element getElement(Document document, String locator){
        Element element = document.select(locator).first();
        return element;
    }

    public String arrayListString(ArrayList<String> aL){
        String listString = null;
        String lis2 = null;
        for(String s: aL){
            listString += s;
        }
        if(aL.size() > 0) {
            listString = listString.replaceAll("'", ",")
                                   .replaceAll("null", "")
                                   .replaceAll("\\s","");
            try { lis2 = listString.substring(0, listString.length()-1); } catch(Exception ignored) { }

        } else lis2 = " ";
        return lis2;
    }

    public String getLeague(Document document){
        Element leagueCountry = getElement(document, "span.description__country");
        Element leagueName = getElement(document, "span.description__country a");
        String leagueFullName = leagueCountry.text() + " " + leagueName.text();
        return leagueFullName;
    }


    public  Element getTeam(Document document, int index){
        Element element = document.select(teams)
                                   .get(index);
        return element;
    }

    public Element getDate(Document document){
        Element element = document.select("#" + date).first();
        return element;
    }

    public String dateTrimmed(Element dateElement){
        String date = dateElement.text();
        return date;
    }

    public ArrayList<String> getEvents(Document document, String whichTeam){
        ArrayList<String> requiredEvents = new ArrayList<>();
        Elements allEventElements = getElements(document, allEvents);
        for(Element element : allEventElements){
            if(element.attr("class").contains(whichTeam)){
                requiredEvents.add(element.toString());
            }
        }
        return requiredEvents;
    }

    public ArrayList<String> getGoalsMin(ArrayList<String> events){
        ArrayList<String> goalMins = new ArrayList<>();
        for (String event : events) {
            if (event.contains("soccer-ball")) {
                if(event.contains("time-box-wide")) {
                    event = StringUtils.substringBetween(event, "<div class=\"time-box-wide\">", "</div>");
                } else {
                    event = StringUtils.substringBetween(event, "<div class=\"time-box\">", "</div>");
                }
                goalMins.add(event);
            } else goalMins.add(" ");
        }
        return goalMins;
    }



}
