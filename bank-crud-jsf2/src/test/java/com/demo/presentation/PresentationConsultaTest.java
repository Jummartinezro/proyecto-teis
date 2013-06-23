/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.presentation;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author juanmanuelmartinezromero
 */
public class PresentationConsultaTest {

    private static final int DELAY = 40;
    private static WebDriver driver;
    private static String baseUrl;
    private static StringBuffer verificationErrors = new StringBuffer();

    public PresentationConsultaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8084/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl + "/bank-crud-jsf2/pages/register.jsf");
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    /**
     * Verifica que el conteo que muestra sea realmente el de la base de datos
     */
    @Test
    public void testCount(){
        assertEquals("Count: 10", driver.findElement(By.xpath("//*[@id=\'listForm\']/table[1]/tbody/tr/td/span")).getText());
    }
}
