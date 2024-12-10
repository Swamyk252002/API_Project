package test.tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

public class API_Program {
    @Test
    public static void Api_Program () throws IOException {
        System.getProperty("webdriver.chrome.driver","C:\\Users\\SwamyKalaveni\\OneDrive - SoftClouds LLC\\Desktop\\MACU\\Chrome Driver\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.flipkart.com/");
        String apiUrl = "https://automationexercise.com/api/productsList";

        URL u=new URL(apiUrl);
        HttpURLConnection con=(HttpURLConnection)u.openConnection();
        con.setRequestMethod("GET");
        if(con.getResponseCode()>=400){
            System.out.println("Wrong URL"+u+" : "+ con.getResponseCode());
        }
        else{
            System.out.println("Right URL"+u+" : "+ con.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //System.out.println("Response Content: " + response.toString());

        JSONObject jsonobject=new JSONObject(response.toString());
        JSONArray products = jsonobject.getJSONArray("products");

        for (int i = 0; i < products.length(); i++) {
            JSONObject product = products.getJSONObject(i);
            JSONObject category = product.getJSONObject("category");
            JSONObject usertype = category.getJSONObject("usertype");

            System.out.println("Response Product: " +product );
            System.out.println("Response category: " +category );
            System.out.println("Response usertype: " +usertype );
            JSONObject jsonObject = new JSONObject(usertype);
            String ss= String.valueOf(usertype);
            String ss1=ss.substring(13,17);
            System.out.println("ss1 Info : " +ss1 );

            if(i==8){

                driver.findElement(By.xpath("//input[@title='Search for Products, Brands and More']")).sendKeys(ss1+" toys");
                driver.findElement(By.xpath("//button[@type='submit']")).click();

            }
        }

    }
}
