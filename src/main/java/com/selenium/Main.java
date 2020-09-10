package com.selenium;

import input.ReadExcel;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import output.WriteExcel;
import pages.GamePage;
import pages.ResultsPage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(System.currentTimeMillis());

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("marionette", true);
        firefoxOptions.setHeadless(true);

        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 60);
        driver.manage().window().maximize();

        ArrayList<String> matchURLs = new ArrayList<>();
        ArrayList<String> resultUrls = new ArrayList<>();
        ArrayList<String> htmls = new ArrayList<>();


        ReadExcel excelReader = new ReadExcel();
        excelReader.readExcel("C:\\Users\\Milica\\Desktop\\ResultUrls.xlsx", resultUrls);
        GamePage gamePage = new GamePage(driver, wait);

        ResultsPage resultsPage = new ResultsPage(driver, wait);
        int i = 0;
        for (String results : resultUrls) {
            driver.get(results);
            if (i == 0)
                resultsPage.acceptCookies();
            try {
                resultsPage.clickMore();
            } catch (Exception ignored) {
            }
            ArrayList<String> matches = resultsPage.getMatchURLS();
            matchURLs.addAll(matches);

            i++;

        }
        System.out.println(matchURLs.size());

        String html = null;

        for (String url : matchURLs) {
            driver.get(url);

            try {
                html = gamePage.getHTML();
            } catch (Exception ignored) {
            }
            ;
            htmls.add(html);
            System.out.println(url);

        }

        driver.quit();

        ArrayList<String> homeMins;
        ArrayList<String> awayMins;
        ArrayList<String> homeEvents;
        ArrayList<String> awayEvents;

        System.out.println(htmls.size());
        Document document = null;

        XSSFWorkbook workbookk = new XSSFWorkbook();
        XSSFSheet sheet = workbookk.createSheet("Matches");
        int s = 0;

        WriteExcel writeExcel = new WriteExcel();

        for (String source : htmls) {
            document = gamePage.getDocument(source);
            Element homeTeam = gamePage.getTeam(document, 0);
            Element awayTeam = gamePage.getTeam(document, 1);
            Element date = gamePage.getDate(document);

            homeEvents = gamePage.getEvents(document, "home");
            awayEvents = gamePage.getEvents(document, "away");
            homeMins = gamePage.getGoalsMin(homeEvents);
            awayMins = gamePage.getGoalsMin(awayEvents);

            String league = gamePage.getLeague(document);
            String dateS = gamePage.dateTrimmed(date);
            String homeTeamS = homeTeam.text();
            String awayTeamS = awayTeam.text();

            String allHomeMins = null;
            String allAwayMins = null;

            try { allHomeMins = gamePage.arrayListString(homeMins); }
            catch(Exception ignored) {}

            try { allAwayMins = gamePage.arrayListString(awayMins); }
            catch(Exception ignored) {}

            System.out.println(league);
            System.out.println(dateS);
            System.out.println(homeTeamS);
            System.out.println(awayTeamS);
            System.out.print(allHomeMins);
            System.out.print(allAwayMins);


            writeExcel.setCellValues(sheet, s, league, dateS, homeTeamS, awayTeamS, allHomeMins, allAwayMins);

            s++;
        }


        writeExcel.writeFile(workbookk, "C:\\\\Users\\\\Milica\\\\Desktop\\\\Games.xlsx");
        System.out.println(System.currentTimeMillis());
    }
}