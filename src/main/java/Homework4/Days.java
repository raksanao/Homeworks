package Homework4;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Days {

    protected static WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().version("79").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void days() {
        driver.get("http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCheckBox");
        List<WebElement> l1 = driver.findElements(By.cssSelector(".gwt-CheckBox>label"));
        List<WebElement> checkboxes = driver.findElements(By.cssSelector(".gwt-CheckBox>input"));
        Random r = new Random();
        int count = 0;
        while (count < 3) {
            // this method will return any value between 0 and 7
            int index = r.nextInt(l1.size());
            if (checkboxes.get(index).isEnabled()) {
                l1.get(index).click();
                if (l1.get(index).getText().equals("Friday")) {
                    count++;
                }
                System.out.println(l1.get(index).getText());
                l1.get(index).click();
            }


        }
    }

    @Test
    public void todays_date() {
        driver.get("http://practice.cybertekschool.com/dropdown");

        WebElement year = driver.findElement(By.id("year"));
        WebElement month = driver.findElement(By.id("month"));
        WebElement day = driver.findElement(By.id("day"));
        Select y = new Select(year);
        Select m = new Select(driver.findElement(By.id("month")));
        Select d = new Select(day);
        String year_value = y.getFirstSelectedOption().getText();
        String month_value = m.getFirstSelectedOption().getText();
        String day_value = d.getFirstSelectedOption().getText();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMMMdd");
        Assert.assertEquals(year_value + month_value + day_value, sf.format(new Date()));

    }

    @Test
    public void years_months_days() {
        driver.get("http://practice.cybertekschool.com/dropdown");
        WebElement year = driver.findElement(By.id("year"));
        WebElement month = driver.findElement(By.id("month"));
        WebElement day = driver.findElement(By.id("day"));
        Select y = new Select(year);
        Select m = new Select(driver.findElement(By.id("month")));
        Select d = new Select(day);
        Random r = new Random();
        int index = r.nextInt(y.getOptions().size());
        y.selectByIndex(index);
        List<String> months31 = new ArrayList<>(Arrays.asList(new String[]{"January", "March", "May", "July", "August", "October", "December"}));
        int fevDays;
        fevDays = Integer.parseInt(y.getFirstSelectedOption().getText()) % 4 == 0 ? 29 : 28;
        int yearValue = Integer.parseInt(y.getFirstSelectedOption().getText());
        if (yearValue % 400 == 0 || (yearValue % 4 == 0 && yearValue % 100 != 0)) {
            fevDays = 29;
        } else {
            fevDays = 28;

        }
        for (int i = 0; i < 12; i++) {
            m.selectByIndex(i);
            if (months31.contains(m.getFirstSelectedOption().getText())) {
                Assert.assertEquals(d.getOptions().size(), 31);
            } else if (m.getFirstSelectedOption().getText().equals("February")) {
                Assert.assertEquals(d.getOptions().size(), fevDays);
            } else {
                Assert.assertEquals(d.getOptions().size(), 30);
            }
        }
    }

    @Test//done
    public void departmentStore() {
        driver.get("https://www.amazon.com/");
        Assert.assertEquals(driver.findElement(By.className("nav-search-label")).getText(), "All");
        List<WebElement> l1 = new Select(driver.findElement(By.id("searchDropdownBox"))).getOptions();
        boolean notAlphebeticalOrder = false;
        for (int i = 0; i < l1.size() - 1; i++) {
            if (l1.get(i).getText().compareTo(l1.get(i + 1).getText()) > 0) {

                notAlphebeticalOrder = true;
                break;

            }

        }
        Assert.assertTrue(notAlphebeticalOrder);

    }

    @Test
    public void main_department() {
        driver.get("https://amazon.com/gp/site-directory");
        List<WebElement> mainDep = driver.findElements(By.tagName("h2"));
        List<WebElement> allDep = new Select(driver.findElement(By.id("searchDropdownBox"))).getOptions();

        Set<String> mainDeps = new HashSet<>();
        Set<String> allDeps = new HashSet<>();
        for (WebElement each : mainDep) {
            mainDeps.add(each.getText());

        }
        for (WebElement each : allDep) {
            allDeps.add(each.getText());
        }
        for (String each : mainDeps) {
            if (!allDeps.contains(each)) ;
            System.out.println(each);
            System.out.println("This main dep is not in All department list");
        }
        Assert.assertTrue(allDep.contains(mainDeps));
    }

    @Test//done
    public void links() {

        driver.get("https://www.w3schools.com/");
        List<WebElement> tagsA = driver.findElements(By.tagName("a"));
        for (WebElement ele : tagsA) {
            if (ele.isDisplayed()) {
                System.out.println(ele.getText());
                System.out.println(ele.getAttribute("href"));

            }
        }
    }

    @Test//done
    public void validLinks() throws MalformedURLException {

        driver.get("https://www.selenium.dev/documentation/en/");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (int i = 0; i < links.size(); i++) {
            String href = links.get(i).getAttribute("href");
            try {
                URL url = new URL(href);
                HttpURLConnection hhtpConnection = (HttpURLConnection) url.openConnection();
                hhtpConnection.setConnectTimeout(3000);
                hhtpConnection.connect();
                Assert.assertEquals(hhtpConnection.getResponseCode(), 200);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void cart() {
        driver.get("https://amazon.com");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("wooden spoon");
        driver.findElement(By.xpath("//span[@id='nav-search-submit-text']/following-sibling::input")).click();
        List<WebElement> price = driver.findElements(By.xpath("//span[@class='a-price']/span[@class='a-offscreen']"));
        int x = new Random().nextInt(price.size());
        x = x == 0 ? 1 : x;
        String originName = driver.findElement(By.xpath("(//span[@class='a-size-base-plus a-color-base a-text-normal'])[" + x + "]")).getText();
        String originPrice = "$" +
                driver.findElement(By.xpath("(//span[@class='a-price']/span[2]/span[2])[" + x + "]")).getText() + "." +
                driver.findElement(By.xpath("(//span[@class='a-price']/span[2]/span[3])[" + x + "]")).getText();
        driver.findElement(By.xpath("(//span[@class='a-price-fraction'])[" + x + "]")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Qty:']/following-sibling::span")).getText(), "1");
        Assert.assertEquals(driver.findElement(By.id("productTitle")).getText(), originName);
        Assert.assertEquals(driver.findElement(By.id("price_inside_buybox")).getText(), originPrice);
        Assert.assertTrue(driver.findElement(By.id("add-to-cart-button")).isDisplayed());


    }

    @Test
    public void prime() {
        driver.get("https://amazon.com");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("wooden spoon");
        driver.findElement(By.xpath("//span[@id='nav-search-submit-text']/following-sibling::input")).click();
        WebElement firstPrimeName = driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]"));
        String name1 = firstPrimeName.getText();
        driver.findElement(By.xpath("//i[@class='a-icon a-icon-prime a-icon-medium']/../div/label/i")).click();
        String name2 = driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]")).getText();
        Assert.assertEquals(name2, name1);
        driver.findElement(By.xpath("//div[@id='brandsRefinements']//ul/li[last()]//i")).click();
        String name3 = driver.findElement(By.xpath("(//i[@aria-label='Amazon Prime']/../../../../../..//h2)[1]")).getText();
        Assert.assertNotEquals(name1, name3);
    }
    @Test
    public void cheapSpoon(){



    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}
